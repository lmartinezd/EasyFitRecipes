package mjdev.com.br.easyfitrecipes.view;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.content.Intent.ACTION_PICK;
import static android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
import static com.squareup.picasso.Callback.EmptyCallback;

import mjdev.com.br.easyfitrecipes.R;
import mjdev.com.br.easyfitrecipes.database.DataBaseHelper;
import mjdev.com.br.easyfitrecipes.model.Recipes;

public class CreateRecipeActivity extends AppCompatActivity {

    private static final int GALLERY_REQUEST = 9391;
    private static final String KEY_IMAGE = "com.example.picasso:image";

    private ImageView imageView;
    private ViewAnimator animator;
    private TextView etTitle, etDescription, etIngredients;
    private Spinner spCategory;
    private String image;
    private Context context = this;

    DataBaseHelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);
        dbhelper = new DataBaseHelper(context);

        animator = (ViewAnimator) findViewById(R.id.va_gallery);
        imageView = (ImageView) findViewById(R.id.iv_gallery);
        etTitle = (TextView)findViewById(R.id.et_title);
        spCategory = (Spinner)findViewById(R.id.sp_category);
        etIngredients = (TextView)findViewById(R.id.et_ingredients);
        etDescription = (TextView)findViewById(R.id.et_description);

        findViewById(R.id.bt_more).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Intent gallery = new Intent(ACTION_PICK, EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery, GALLERY_REQUEST);
            }
        });

        findViewById(R.id.bt_register).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Toast toast = null;
                Recipes objRecipes = new Recipes();
                objRecipes.setTitle(etTitle.getText().toString());
                objRecipes.setCategory(String.valueOf(spCategory.getSelectedItemPosition()));
                objRecipes.setIngredients(etIngredients.getText().toString());
                objRecipes.setDescription(etDescription.getText().toString());
                objRecipes.setImage(imageView.toString());

                if (dbhelper.insertRecipes(objRecipes))
                {
                    toast.makeText(context,R.string.msgOk, Toast.LENGTH_LONG).show();
                }else{
                    toast.makeText(context,R.string.msgError, Toast.LENGTH_LONG).show();
                }
//                picassoImageTarget(context,image);
            }
        });

        if (savedInstanceState != null) {
            image = savedInstanceState.getString(KEY_IMAGE);
            if (image != null) {
                loadImage();
            }
        }
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
            @Override public void onSuccess() {
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
