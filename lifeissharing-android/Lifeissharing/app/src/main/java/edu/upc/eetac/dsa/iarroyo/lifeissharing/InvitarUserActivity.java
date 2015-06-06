package edu.upc.eetac.dsa.iarroyo.lifeissharing;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;

import edu.upc.eetac.dsa.iarroyo.lifeissharing.api.AppException;
import edu.upc.eetac.dsa.iarroyo.lifeissharing.api.Editor;
import edu.upc.eetac.dsa.iarroyo.lifeissharing.api.LifeissharingAPI;
import edu.upc.eetac.dsa.iarroyo.lifeissharing.api.Lista;

/**
 * Created by nacho on 6/06/15.
 */
public class InvitarUserActivity extends Activity{



    private final static String TAG = InvitarUserActivity.class.getName();
    private String urlEditores = null;

    private class PostUserTask extends AsyncTask<String, Void, Editor> {
        private ProgressDialog pd;

        @Override
        protected Editor doInBackground(String... params) {
            Editor editor = null;
            try {
                editor = LifeissharingAPI.getInstance(InvitarUserActivity.this)
                        .createEditor(params[0], params[1]);
            } catch (AppException e) {
                Log.e(TAG, e.getMessage(), e);
            }
            return editor;
        }

        @Override
        protected void onPostExecute(Editor result) {
            showEditores(result);
            if (pd != null) {
                pd.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(InvitarUserActivity.this);

            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_user_layout);
        urlEditores = (String) getIntent().getExtras().get("url");

    }

    public void cancelar(View v) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void invitar(View v) {
        EditText etText = (EditText) findViewById(R.id.etText);

        String texto = etText.getText().toString();


        (new PostUserTask()).execute(texto, urlEditores);
    }

    private void showEditores(Editor result) {
        String json = new Gson().toJson(result);
        Bundle data = new Bundle();
        data.putString("json-editor", json);
        Intent intent = new Intent();
        intent.putExtras(data);
        setResult(RESULT_OK, intent);
        finish();
    }


}
