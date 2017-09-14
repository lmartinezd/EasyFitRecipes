package mjdev.com.br.easyfitrecipes.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import mjdev.com.br.easyfitrecipes.R;
import mjdev.com.br.easyfitrecipes.adapter.MyRecyclerViewAdapter;
import mjdev.com.br.easyfitrecipes.database.DataBaseHelper;
import mjdev.com.br.easyfitrecipes.model.Recipes;

public class SubmenuActivity extends AppCompatActivity {

    DataBaseHelper dbhelper;
    private MyRecyclerViewAdapter mAdapter;
    private RecyclerView rvMenu;
    private List<Recipes> lRecipes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submenu);

        dbhelper = new DataBaseHelper(getBaseContext());

        rvMenu = (RecyclerView)findViewById(R.id.rvMenuBreakfast);
        mAdapter = new MyRecyclerViewAdapter(this, lRecipes);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        rvMenu.setLayoutManager(layoutManager);
        rvMenu.setHasFixedSize(true);
        rvMenu.setItemAnimator(new DefaultItemAnimator());
        rvMenu.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        rvMenu.setAdapter(mAdapter);

        String option = getIntent().getStringExtra("option");
        prepareUsersData(option);

        switch( option ){
            case "1":
                this.setTitle(getString(R.string.tv_optbreakfast));
                break;
            case "2":
                this.setTitle(getString(R.string.tv_optlunch));
                break;
            case"3":
                this.setTitle(getString(R.string.tv_optdinner));
                break;
            case "4":
                this.setTitle(getString(R.string.tv_optdessert));
                break;
            default:
                break;
        }
    }

    private void prepareUsersData(String idCat) {
        List<Recipes> listrecipes = dbhelper.getRecipesCategory(idCat);

        if (!listrecipes.isEmpty())
        {
            for (Recipes x : listrecipes) {
                lRecipes.add(x);
            }
        }
        mAdapter.notifyDataSetChanged();
    }
}
