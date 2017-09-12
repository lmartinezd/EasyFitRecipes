package mjdev.com.br.easyfitrecipes.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import mjdev.com.br.easyfitrecipes.R;

public class ShareActivity extends AppCompatActivity {
    ShareDialog shareDialog;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        final Button btnShare = (Button)findViewById(R.id.btnShare);

        // Initialize the facebook sdk and then callback manager will handle the login responses.
        callbackManager = CallbackManager.Factory.create();

        // intialize facebook shareDialog.
        shareDialog = new ShareDialog(this);

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //test
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentUrl(Uri.parse("http://developers.facebook.com/android"))
                            .build();

                    shareDialog.show(linkContent, ShareDialog.Mode.AUTOMATIC);
                    shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                        @Override
                        public void onSuccess(Sharer.Result result) {
                            Toast.makeText(ShareActivity.this,"Postada com sucesso", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancel() {
                        }

                        @Override
                        public void onError(FacebookException error) {
                            Toast.makeText(ShareActivity.this,error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }
}
