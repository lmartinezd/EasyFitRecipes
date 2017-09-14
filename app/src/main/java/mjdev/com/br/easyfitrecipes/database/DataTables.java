package mjdev.com.br.easyfitrecipes.database;

/**
 * Created by luana.martinez on 26/07/2017.
 */

public class DataTables {
    private static String CREATE = "CREATE TABLE ";

    public static class USERS {
        public static String TABLE = "users";
        public static String USERNAME = "username";
        public static String PASSWORD = "password";
        public static String GUID = "guid";
    }

    public static class RECIPES {
        public static String TABLE = "recipes";
        public static String IDRECIPE = "idRecipe";
        public static String TITLE = "title";
        public static String CATEGORY = "category";
        public static String INGREDIENTS = "ingredients";
        public static String DESCRIPTION = "description";
        public static String IMAGE = "image";
    }

    public static String[] SELECT_ALL_USER = {
            USERS.GUID,
            USERS.USERNAME,
            USERS.PASSWORD,
    };

    public static String[] SELECT_ALL_RECIPES = {
            RECIPES.IDRECIPE,
            RECIPES.TITLE,
            RECIPES.CATEGORY,
            RECIPES.INGREDIENTS,
            RECIPES.DESCRIPTION,
            RECIPES.IMAGE,
    };

    public static String CREATE_USERS = CREATE + "IF NOT EXISTS users ( " +
            " guid INTEGER, " +
            " username varchar(40), " +
            " password varchar(11), " +
            " PRIMARY KEY(guid ASC))";

    public static String CREATE_RECIPES = CREATE + "IF NOT EXISTS recipes ( " +
            " idRecipe INTEGER, " +
            " title varchar(50), " +
            " category INTEGER, " +
            " ingredients varchar(200), " +
            " description varchar(500), " +
            " image BLOB, " +
            " PRIMARY KEY(idRecipe ASC))";
}
