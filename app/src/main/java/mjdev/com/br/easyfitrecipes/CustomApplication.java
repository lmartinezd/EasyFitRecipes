package mjdev.com.br.easyfitrecipes;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by tairo on 8/29/17.
 */

public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
