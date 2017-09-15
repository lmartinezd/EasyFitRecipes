package mjdev.com.br.easyfitrecipes.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mjdev.com.br.easyfitrecipes.R;
import mjdev.com.br.easyfitrecipes.database.DataBaseHelper;
import mjdev.com.br.easyfitrecipes.model.Recipes;
import mjdev.com.br.easyfitrecipes.view.CreateRecipeActivity;
/**
 * Created by luana.martinez on 22/08/2017.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.RVAHolder> {

    public List<Recipes> lRecipes = new ArrayList<>();
    private Context mContext;
    DataBaseHelper dbhelper;

    public class RVAHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle, tvIngredients, tvDescription;
        public ImageView ivImgRecipe, overflow;


        public RVAHolder(View itemView) {
            super(itemView);

            dbhelper = new DataBaseHelper(mContext);

            tvTitle = (TextView) itemView.findViewById(R.id.tvOPTTitle);
            tvIngredients = (TextView) itemView.findViewById(R.id.tvOPTIngredients);
            tvDescription = (TextView) itemView.findViewById(R.id.tvOPTDescription);
            ivImgRecipe = (ImageView) itemView.findViewById(R.id.ivOPTImgDish);
            overflow = (ImageView) itemView.findViewById(R.id.overflow);
        }
    }

    public MyRecyclerViewAdapter(Context mContext, List<Recipes> lRecipes) {
        this.mContext = mContext;
        this.lRecipes = lRecipes;
    }

    @Override
    public RVAHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.listrow_submenu, parent, false);

        return new RVAHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RVAHolder holder,final int position) {
        Recipes recipe = lRecipes.get(position);
        holder.tvTitle.setText(recipe.getTitle());
        holder.tvIngredients.setText(recipe.getIngredients());
        holder.tvDescription.setText(recipe.getDescription());

        // GET IMAGEN
        Bitmap bitmap = BitmapFactory.decodeByteArray(recipe.getImage(), 0, recipe.getImage().length);
        if (bitmap != null){
            holder.ivImgRecipe.setImageBitmap(bitmap);
        }else {
            holder.ivImgRecipe
                    .setImageDrawable(ContextCompat
                    .getDrawable(holder.ivImgRecipe.getContext(), R.drawable.notfound));
        }

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow, position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return this.lRecipes.size();
    }

    public void update(List<Recipes> lrecipes) {
        this.lRecipes = lrecipes;
        notifyDataSetChanged();
    }

    private void removeItem(int position) {
        this.lRecipes.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, this.lRecipes.size());
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view, int position) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_settings, popup.getMenu());

        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(position));
        popup.show();
    }
    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        private int position;

        public MyMenuItemClickListener(int position) {
            this.position = position;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_edit:
                    Intent intent = new Intent(mContext, CreateRecipeActivity.class);
                        intent.putExtra("ACTV","UPD");
                        intent.putExtra("IDRECP", lRecipes.get(position).getIdRecipes().toString());
                        mContext.startActivity(intent);
                    return true;

                case R.id.action_remove:
                    AlertDialog.Builder builderR = new AlertDialog.Builder(mContext);
                    builderR.setMessage(R.string.submenu_msg_title)
                        .setCancelable(false)
                        .setPositiveButton(R.string.exit_yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int arg1) {
                                if (dbhelper.deleteRecipe (lRecipes.get(position).getIdRecipes().toString())){
                                    removeItem(position);
                                    Toast.makeText(mContext, R.string.msg_submenu_removeOK, Toast.LENGTH_SHORT).show();
                                }
                                else
                                    Toast.makeText(mContext, R.string.msg_submenu_ERR, Toast.LENGTH_SHORT).show();
                            }
                        })
                        //define um botão como negativo.
                        .setNegativeButton(R.string.exit_cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int arg1) {
                                dialog.cancel();
                            }
                        });
                    builderR.show();
                    return true;

                default:
            }
            return false;
        }
    }
}
