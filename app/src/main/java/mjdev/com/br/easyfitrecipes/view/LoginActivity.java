package mjdev.com.br.easyfitrecipes.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import mjdev.com.br.easyfitrecipes.R;
import mjdev.com.br.easyfitrecipes.database.DataBaseHelper;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class LoginActivity extends AppCompatActivity {

    private EditText tilUser;
    private EditText tilPass;
    CallbackManager callbackManager;

    DataBaseHelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbhelper = new DataBaseHelper(getBaseContext());

        tilUser = (EditText) findViewById(R.id.tilUser);
        tilPass = (EditText) findViewById(R.id.tilPassword);

        final Button btnLogin = (Button) findViewById(R.id.btLogin);
        final CheckBox chkKeepConn = (CheckBox) findViewById(R.id.ckKeepConn);

        if (getSession()){
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            startActivity(intent);
            finish();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.d("*****TEST***", "onClick " + tilUser.getText().toString() + "-" + tilPass.getText().toString());
                boolean flagExists = dbhelper.userExists(tilUser.getText().toString(), tilPass.getText().toString());
                if (flagExists) {
                    Log.d("*****TEST***", "onClick, OK ");
                    SharedPreferences sharedPref = getSharedPreferences("appSharedPref", MODE_PRIVATE);
                    SharedPreferences.Editor editorSP = sharedPref.edit();

                    editorSP.putString("user", tilUser.getText().toString());
                    editorSP.putString("pass", tilPass.getText().toString());
                    editorSP.putBoolean("flag", chkKeepConn.isChecked());
                    editorSP.commit();

                    Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.d("*****TEST***", "onClick, RETORNO FALSE ");
                    Toast.makeText(getApplicationContext(), R.string.toast_msgerror, Toast.LENGTH_SHORT).show();
                }
            }
        });

        //FACEBOOK
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_buttonfb);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                finish();
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancel() {
                Log.v("LoginActivity", "cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.v("LoginActivity", error.getCause().toString());
                Toast.makeText(LoginActivity.this, "Facebook error", Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean getSession() {
        boolean retorno=false;

        SharedPreferences sharedPref = getSharedPreferences("appSharedPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        if (!sharedPref.getAll().isEmpty()){
            if (sharedPref.getBoolean("flag",true))
                retorno = true;
            else{
                editor.putString("user", "");
                editor.putString("pass", "");
                editor.putBoolean("flag", false);
                editor.apply();
                retorno = false;
            }
        }
        return retorno;
    }

    @Override
    protected void onActivityResult(int requestCode, int resulCode,Intent data){
        super.onActivityResult(requestCode,resulCode,data);
        callbackManager.onActivityResult(requestCode,resulCode,data);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
