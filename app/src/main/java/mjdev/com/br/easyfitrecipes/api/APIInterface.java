package mjdev.com.br.easyfitrecipes.api;

import mjdev.com.br.easyfitrecipes.model.Users;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by luana.martinez on 26/07/2017.
 */

public interface APIInterface {

    @GET("/v2/58b9b1740f0000b614f09d2f")
    Observable<Users> getUsersLogin();

}
