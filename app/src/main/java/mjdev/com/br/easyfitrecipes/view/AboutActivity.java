package mjdev.com.br.easyfitrecipes.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import mjdev.com.br.easyfitrecipes.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


        TextView tvCellphone = (TextView)findViewById(R.id.tv_cellphone);

        tvCellphone.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                makeCall();
            }
        });
    }

    public void makeCall(){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:+551195206931"));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 105);
            } else {
                startActivity(callIntent);
            }
        } else {
            startActivity(callIntent);
        }
    }


}
