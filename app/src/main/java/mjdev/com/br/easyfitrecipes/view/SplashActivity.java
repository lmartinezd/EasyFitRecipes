package mjdev.com.br.easyfitrecipes.view;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import mjdev.com.br.easyfitrecipes.R;
import mjdev.com.br.easyfitrecipes.api.APIClient;
import mjdev.com.br.easyfitrecipes.api.APIInterface;
import mjdev.com.br.easyfitrecipes.database.DataBaseHelper;
import mjdev.com.br.easyfitrecipes.model.Users;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SplashActivity extends AppCompatActivity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    private ImageView image;
    private ProgressBar progressBar;
    private Animation animationFadeIn, animationFadeOut;
    private APIInterface userAPI;
    private String sUsername;
    private String sPassword;
    DataBaseHelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        dbhelper = new DataBaseHelper(getBaseContext());

        image = (ImageView) findViewById(R.id.ivlogo);

        animationFadeIn = AnimationUtils.loadAnimation(this,R.anim.fadein);
        animationFadeIn.setAnimationListener(animationInListener);

        animationFadeOut = AnimationUtils.loadAnimation(this,R.anim.fadeout);
        animationFadeOut.setAnimationListener(animationOutListener);

        image.startAnimation(animationFadeIn);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                DataBaseHelper dbhelper = new DataBaseHelper(getBaseContext());

                userAPI = APIClient.getClientConnection();

                createTableLogin();

                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    protected void onPause(){
        super.onPause();
        image.clearAnimation();
    }

    Animation.AnimationListener animationInListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            image.startAnimation(animationFadeOut);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    };


    Animation.AnimationListener animationOutListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            image.startAnimation(animationFadeIn);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    };

    public void createTableLogin() {

        userAPI.getUsersLogin()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Users>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Users users) {
                        if (dbhelper.insertUsers(users))
                            Toast.makeText(getApplicationContext(),"foi inserido com sucesso",Toast.LENGTH_SHORT);

                        Log.d("SPLASHACTIVITY","username"+ users.getUsuario().toString());
                    }
                });


    }
}