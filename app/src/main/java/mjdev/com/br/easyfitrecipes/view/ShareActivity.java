package mjdev.com.br.easyfitrecipes.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import mjdev.com.br.easyfitrecipes.R;

public class ShareActivity extends AppCompatActivity {
    ShareDialog shareDialog;
    LoginActivity callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        final Button btnShare = (Button)findViewById(R.id.btnShare);

        shareDialog = new ShareDialog(this);

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentUrl(Uri.parse("http://developers.facebook.com/android"))
                            .build();
                    shareDialog.show(linkContent);
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
