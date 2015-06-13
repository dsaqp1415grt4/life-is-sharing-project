package edu.upc.eetac.dsa.iarroyo.lifeissharing;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import edu.upc.eetac.dsa.iarroyo.lifeissharing.api.AppException;
import edu.upc.eetac.dsa.iarroyo.lifeissharing.api.Item;
import edu.upc.eetac.dsa.iarroyo.lifeissharing.api.LifeissharingAPI;
import edu.upc.eetac.dsa.iarroyo.lifeissharing.api.User;

/**
 * Created by nacho on 30/05/15.
 */
public class LoginActivity extends Activity {
    private final static String TAG = LoginActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        SharedPreferences prefs = getSharedPreferences("life-profile",
                Context.MODE_PRIVATE);

        String username = prefs.getString("username", null); //Recupera el usuario y contraseña almacenados
        String password = prefs.getString("password", null);



        if ((username != null) && (password != null)) { //Si usuario y contraseña no son nulos, inicia la actividad
            Intent intent = new Intent(this, LifeMainActivity.class);
            startActivity(intent);
            finish();
        }

        setContentView(R.layout.login_layout);
    }




    public void signIn(View v) {
        EditText etUsername = (EditText) findViewById(R.id.etUsername);
        EditText etPassword = (EditText) findViewById(R.id.etPassword);

        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();





        (new FetchLoginTask()).execute(username, password);
    }

    private void startLifeMainActivity() {
        Intent intent = new Intent(this, LifeMainActivity.class);
        startActivity(intent);
        finish();
    }

 private void logeado(boolean login){


     if (login) {
         EditText etUsername = (EditText) findViewById(R.id.etUsername);
         EditText etPassword = (EditText) findViewById(R.id.etPassword);

         final String username = etUsername.getText().toString();
         final String password = etPassword.getText().toString();

         SharedPreferences prefs = getSharedPreferences("life-profile",
                 Context.MODE_PRIVATE);
         SharedPreferences.Editor editor = prefs.edit();
         editor.clear();
         editor.putString("username", username);
         editor.putString("password", password);
         boolean done = editor.commit();
         if (done)
             Log.d(TAG, "preferences set");
         else
             Log.d(TAG, "preferences not set. THIS A SEVERE PROBLEM");

         startLifeMainActivity();
     } else {
         Context context = getApplicationContext();
         CharSequence text = "Usuario o contraseña incorrectos";
         int duration = Toast.LENGTH_LONG;

         Toast toast = Toast.makeText(context, text, duration);
         toast.setGravity(Gravity.CENTER, 0, 0);
         toast.show();
     }


 }


    private class FetchLoginTask extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog pd;

        @Override
        protected Boolean doInBackground(String... params) {
            Boolean login = false;
            User user;
            try {
                user = LifeissharingAPI.getInstance(LoginActivity.this)
                        .getLogin(params[0], params[1]);
                login = user.isLoginSuccessful();
            } catch (AppException e) {
                Log.e(TAG, e.getMessage(), e);
            }
            return login;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            logeado(result);
            if (pd != null) {
                pd.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(LoginActivity.this);

            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }
    }




}
