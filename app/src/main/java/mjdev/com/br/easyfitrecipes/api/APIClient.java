package mjdev.com.br.easyfitrecipes.api;

/**
 * Created by luana.martinez on 26/07/2017.
 */

public class APIClient {
    public static final String BASE_URL = "http://www.mocky.io";

    public static APIInterface getClientConnection() {
        return RetrofitClient.getClient(BASE_URL).create(APIInterface.class);
    }
}
