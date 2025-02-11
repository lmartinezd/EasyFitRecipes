package mjdev.com.br.easyfitrecipes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import mjdev.com.br.easyfitrecipes.model.Recipes;
import mjdev.com.br.easyfitrecipes.model.Users;

/**
 * Created by luana.martinez on 26/07/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "tbeasyfitrecipes.db";
    private static String DROP_USER = "DROP TABLE IF EXISTS " + DataTables.USERS.TABLE;
    private static String DROP_RECIPES = "DROP TABLE IF EXISTS " + DataTables.RECIPES.TABLE;
    private final Context context;

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(DataTables.CREATE_USERS);
            db.execSQL(DataTables.CREATE_RECIPES);
        } catch (Exception e) {
            System.out.println("create-error: " + e.getMessage());
        }
    }

    public void dropAll(SQLiteDatabase db) {
        db.execSQL(DROP_USER);
        db.execSQL(DROP_RECIPES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropAll(db);
        onCreate(db);
    }

    public void initializeDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        onUpgrade(db, 0, 0);
    }

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public boolean insertUsers(Users user){
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            ContentValues clienteValues = new ContentValues();

            clienteValues.put(DataTables.USERS.USERNAME, user.getUsuario());
            clienteValues.put(DataTables.USERS.PASSWORD, user.getSenha());

            long idUser = db.insertOrThrow(DataTables.USERS.TABLE, null, clienteValues);

            db.close();
            return true;
        } catch (Exception e) {
            System.out.println("ERRO INSERT USER " + e.getMessage());
            db.close();
            return false;
        }
    }

    public boolean userExists(String vUser, String vPassword) {
        Log.d("**********","userExists " + vUser + "pass: "+ vPassword);

        String[] columns = new String[]{DataTables.USERS.USERNAME, DataTables.USERS.PASSWORD};

        String whereClause = DataTables.USERS.USERNAME + " = ? AND " +
                DataTables.USERS.PASSWORD + " = ? ";

        String[] whereArgs = new String[]{vUser, vPassword};

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(DataTables.USERS.TABLE, columns, whereClause, whereArgs, null, null, null);

        if (cursor.moveToFirst()) {
            String username = cursor.getString(0);
            String password = cursor.getString(1);
            cursor.close();
            db.close();
            return true;
        } else {
            cursor.close();
            db.close();
            return false;
        }
    }


    public Users getUser()
    {
        Users user =new Users();

        String[] columns = DataTables.SELECT_ALL_USER;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(DataTables.USERS.TABLE, columns,null,null,null,null,null);
        if(cursor.moveToFirst())
        {
            user.setGuid(cursor.getString(cursor.getColumnIndex(DataTables.USERS.GUID)));
            user.setUsuario(cursor.getString(cursor.getColumnIndex(DataTables.USERS.USERNAME)));
            user.setSenha(cursor.getString(cursor.getColumnIndex(DataTables.USERS.PASSWORD)));
        }else
            user = null;

        cursor.close();
        db.close();
        return user;
    }

    public Recipes getRecipe(String idRecipe)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Recipes recipes =new Recipes();

        String[] columns = DataTables.SELECT_ALL_RECIPES;
        String whereClause = DataTables.RECIPES.IDRECIPE + " = ? " ;
        String[] whereArgs = new String[]{idRecipe};

        Cursor cursor = db.query(DataTables.RECIPES.TABLE, columns, whereClause, whereArgs, null, null, null);
        if(cursor.moveToFirst())
        {
            recipes.setIdRecipes(cursor.getString(cursor.getColumnIndex(DataTables.RECIPES.IDRECIPE)));
            recipes.setTitle(cursor.getString(cursor.getColumnIndex(DataTables.RECIPES.TITLE)));
            recipes.setCategory(cursor.getString(cursor.getColumnIndex(DataTables.RECIPES.CATEGORY)));
            recipes.setIngredients(cursor.getString(cursor.getColumnIndex(DataTables.RECIPES.INGREDIENTS)));
            recipes.setDescription(cursor.getString(cursor.getColumnIndex(DataTables.RECIPES.DESCRIPTION)));
            recipes.setImage(cursor.getBlob(cursor.getColumnIndex(DataTables.RECIPES.IMAGE)));
        }else
            recipes = new Recipes();

        cursor.close();
        db.close();
        return recipes;
    }

    public List<Recipes> getRecipesCategory(String idCategory)
    {
        List<Recipes> lRecipes = new ArrayList<>();

        String[] columns = DataTables.SELECT_ALL_RECIPES;
        String whereClause = DataTables.RECIPES.CATEGORY + " = ? " ;
        String[] whereArgs = new String[]{idCategory};

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(DataTables.RECIPES.TABLE, columns, whereClause, whereArgs, null, null, null);

        try {
            if (cursor.getCount() > 0) {

                // looping through all rows and adding to list
                cursor.moveToFirst();

                do {
                    Recipes recipe = new Recipes();
                    recipe.setIdRecipes(cursor.getString(cursor.getColumnIndex(DataTables.RECIPES.IDRECIPE)));
                    recipe.setTitle(cursor.getString(cursor.getColumnIndex(DataTables.RECIPES.TITLE)));
                    recipe.setCategory(cursor.getString(cursor.getColumnIndex(DataTables.RECIPES.CATEGORY)));
                    recipe.setIngredients(cursor.getString(cursor.getColumnIndex(DataTables.RECIPES.INGREDIENTS)));
                    recipe.setDescription(cursor.getString(cursor.getColumnIndex(DataTables.RECIPES.DESCRIPTION)));
                    recipe.setImage(cursor.getBlob(cursor.getColumnIndex(DataTables.RECIPES.IMAGE)));
                    // Adding contact to list
                    lRecipes.add(recipe);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();

            return lRecipes;

        } catch (Exception e) {
            Log.i("TAG", "getRecipesCategory: " + e.getMessage());
        }
        return lRecipes;
    }

    public boolean insertRecipes(Recipes recipe){
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            ContentValues cntValues = new ContentValues();

            cntValues.put(DataTables.RECIPES.TITLE, recipe.getTitle());
            cntValues.put(DataTables.RECIPES.CATEGORY, recipe.getCategory());
            cntValues.put(DataTables.RECIPES.INGREDIENTS, recipe.getIngredients());
            cntValues.put(DataTables.RECIPES.DESCRIPTION, recipe.getDescription());
            cntValues.put(DataTables.RECIPES.IMAGE, recipe.getImage());

            db.insertOrThrow(DataTables.RECIPES.TABLE, null, cntValues);

            db.close();
            return true;
        } catch (Exception e) {
            System.out.println("ERRO INSERT RECIPES " + e.getMessage());
            db.close();
            return false;
        }
    }


    public boolean updateRecipe(Recipes recipe){
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            ContentValues cntValues = new ContentValues();

            cntValues.put(DataTables.RECIPES.TITLE, recipe.getTitle());
            cntValues.put(DataTables.RECIPES.CATEGORY, recipe.getCategory());
            cntValues.put(DataTables.RECIPES.INGREDIENTS, recipe.getIngredients());
            cntValues.put(DataTables.RECIPES.DESCRIPTION, recipe.getDescription());
            cntValues.put(DataTables.RECIPES.IMAGE, recipe.getImage());

            String where = "idRecipe=?";
            String[] whereArgs = new String[] {recipe.getIdRecipes()};

            db.update(DataTables.RECIPES.TABLE, cntValues, where, whereArgs);
            db.close();
            return true;
        } catch (Exception e) {
            System.out.println("ERRO UPDATE RECIPES " + e.getMessage());
            db.close();
            return false;
        }
    }

    public boolean deleteRecipe(String idRecipe){
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            String whereClause = "idRecipe=?";
            String[] whereArgs = new String[] { String.valueOf(idRecipe) };

            db.delete(DataTables.RECIPES.TABLE, whereClause, whereArgs);

            db.close();
            return true;
        } catch (Exception e) {
            System.out.println("ERRO DELETE RECIPES " + e.getMessage());
            db.close();
            return false;
        }
    }
}


