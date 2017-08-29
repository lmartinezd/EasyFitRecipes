package mjdev.com.br.easyfitrecipes.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import mjdev.com.br.easyfitrecipes.R;
import mjdev.com.br.easyfitrecipes.model.Recipes;

/**
 * Created by luana.martinez on 22/08/2017.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.RVAHolder> {

    private List<Recipes> lRecipes = new ArrayList<>();

    public class RVAHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle,tvCategory, tvIngredients, tvDescription;
        public ImageView ivImgRecipe;

        public RVAHolder(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tvOPTTitle);
            tvIngredients = (TextView) itemView.findViewById(R.id.tvOPTIngredients);
            tvDescription = (TextView) itemView.findViewById(R.id.tvOPTDescription);
            ivImgRecipe = (ImageView) itemView.findViewById(R.id.ivOPTImgDish);
        }
    }

    public MyRecyclerViewAdapter(List<Recipes> lRecipes){
        this.lRecipes = lRecipes;
    }

    @Override
    public RVAHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.listrow_submenu,parent,false);

        return new RVAHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RVAHolder holder, int position) {
        Recipes recipe = lRecipes.get(position);
        holder.tvTitle.setText(recipe.getTitle());
        holder.tvIngredients.setText(recipe.getIngredients());
        holder.tvDescription.setText(recipe.getDescription());

//        File imgFile = new  File(recipe.getImage());
//        Bitmap bitmap = null;
//        try {
//            FileInputStream fis = Context.openFileInput(recipe.getImage());
//            bitmap = BitmapFactory.decodeStream(new FileInputStream(fis));
//            bitmap.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        boolean test= false;
        File file = new File("storage/emulated/0/DCIM/Camera/IMG_20170827_000232547_HDR.jpg");
        if(file.exists())
            test = true;

        Picasso.with(holder.itemView.getContext())
                .load("file:///"+file)
                .placeholder(R.drawable.notfound)
                .error(R.drawable.notfound)
                .into(holder.ivImgRecipe);
    }

    @Override
    public int getItemCount() {
        return this.lRecipes.size();
    }

    public void update(List<Recipes> lrecipes){
        this.lRecipes = lrecipes;
        notifyDataSetChanged();
    }

}
