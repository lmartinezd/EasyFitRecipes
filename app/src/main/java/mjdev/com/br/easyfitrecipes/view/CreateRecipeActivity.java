package mjdev.com.br.easyfitrecipes.view;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import mjdev.com.br.easyfitrecipes.R;
import mjdev.com.br.easyfitrecipes.database.DataBaseHelper;
import mjdev.com.br.easyfitrecipes.model.Recipes;

import static android.content.Intent.ACTION_PICK;
import static android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
import static com.squareup.picasso.Callback.EmptyCallback;

public class CreateRecipeActivity extends AppCompatActivity {

    private static final int GALLERY_REQUEST = 9391;
    private static final String KEY_IMAGE = "com.example.picasso:image";

    private ImageView imageView;
    private ViewAnimator animator;
    private TextView etTitle, etDescription, etIngredients;
    private Spinner spCategory;
    private String image, strIDRecipe;
    private Context context = this;
    private boolean flagUPD = false;
    private Button btMore, btRegister;

    DataBaseHelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);

        animator = (ViewAnimator) findViewById(R.id.va_gallery);
        imageView = (ImageView) findViewById(R.id.iv_gallery);
        etTitle = (TextView) findViewById(R.id.et_title);
        spCategory = (Spinner) findViewById(R.id.sp_category);
        etIngredients = (TextView) findViewById(R.id.et_ingredients);
        etDescription = (TextView) findViewById(R.id.et_description);
        imageView.setDrawingCacheEnabled(true);

        btMore = (Button)findViewById(R.id.bt_more);
        btRegister = (Button)findViewById(R.id.bt_register);

        btMore.setFocusable(true);
        btRegister.setEnabled(true);
        flagUPD = false;

        if( getIntent().getExtras() != null) {
            if (getIntent().getExtras().getString("ACTV").equals("UPD")) {

                setTitle(R.string.title_activity_editrecipe);
                Button btnRegister = (Button) findViewById(R.id.bt_register);
                btnRegister.setText(R.string.msg_button_edit);
                flagUPD = true;
                if (getIntent().getExtras().getParcelable("OBJREC") != null) {
                    clearAll();

                    Recipes objRecipe = (Recipes)getIntent().getExtras().getParcelable("OBJREC");

                    strIDRecipe = objRecipe.getIdRecipes().toString();
                    etTitle.setText(objRecipe.getTitle().toString());
                    etIngredients.setText(objRecipe.getIngredients().toString());
                    etDescription.setText(objRecipe.getDescription().toString());
                    spCategory.setSelection( Integer.parseInt( objRecipe.getCategory().toString())-1);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(objRecipe.getImage(), 0, objRecipe.getImage().length);
                    if (bitmap != null){
                        imageView.setImageBitmap(bitmap);
                    }else {
                        imageView.setImageDrawable(ContextCompat
                                .getDrawable(imageView.getContext(), R.drawable.notfound));
                    }
                }
            }else{
                clearAll();
            }
        }

        dbhelper = new DataBaseHelper(context);

        btMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(ACTION_PICK, EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery, GALLERY_REQUEST);
            }
        });

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateText()){
                    etTitle.setError("validar campo");
//                    break
                }


                Toast toast = null;
                Recipes objRecipes = new Recipes();
                objRecipes.setTitle(etTitle.getText().toString());
                objRecipes.setCategory(String.valueOf(spCategory.getSelectedItemPosition() + 1));
                objRecipes.setIngredients(etIngredients.getText().toString());
                objRecipes.setDescription(etDescription.getText().toString());
                objRecipes.setIdRecipes(strIDRecipe);

                Bitmap bitmap = imageView.getDrawingCache();

                if (bitmap != null){
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] bitmapdata = stream.toByteArray();
                    objRecipes.setImage(bitmapdata);

                    if (flagUPD) {
                        if (dbhelper.updateRecipe(objRecipes)) {
                            toast.makeText(context, R.string.update_msgOk, Toast.LENGTH_SHORT).show();
                            btRegister.setEnabled(false);
                        } else {
                            toast.makeText(context, R.string.update_msgError, Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        if (dbhelper.insertRecipes(objRecipes)) {
                            toast.makeText(context, R.string.insert_msgOk, Toast.LENGTH_SHORT).show();
                            btRegister.setEnabled(false);
                        } else {
                            toast.makeText(context, R.string.insert_msgError, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        if (savedInstanceState != null) {
            image = savedInstanceState.getString(KEY_IMAGE);
            if (image != null) {
                loadImage();
            }
        }
    }

    private boolean validateText(){
        return true;
    }

    private void loadImage() {
        // Index 1 is the progress bar. Show it while we're loading the image.
        animator.setDisplayedChild(1);

        Picasso.with(context)
                .load(image)
//                .fit()
//                .resize(6000, 2000)
//                .onlyScaleDown()
//                .centerInside()
                .into(imageView, new EmptyCallback() {
                    @Override
                    public void onSuccess() {
                        // Index 0 is the image view.
                        animator.setDisplayedChild(0);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && data != null) {
            image = data.getData().toString();
            loadImage();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void clearAll(){
        imageView.setDrawingCacheEnabled(true);
        etTitle.setText("");
        spCategory.setSelection(0);
        etIngredients.setText("");
        etDescription.setText("");
    }

    private Target picassoImageTarget(Context context, final String imageName) {
        Log.d("picassoImageTarget", " picassoImageTarget");
        ContextWrapper cw = new ContextWrapper(context);
        String imageDir = Environment.getExternalStorageDirectory().getPath();

        final File directory = cw.getDir(imageDir, Context.MODE_PRIVATE); // path to /data/data/yourapp/app_imageDir
        return new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final File myImageFile = new File(directory, imageName); // Create image file
                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(myImageFile);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.i("image", "image saved to >>>" + myImageFile.getAbsolutePath());

                    }
                }).start();
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                if (placeHolderDrawable != null) {
                }
            }
        };
    }
}
