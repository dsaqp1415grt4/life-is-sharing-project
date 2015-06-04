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
import edu.upc.eetac.dsa.iarroyo.lifeissharing.api.LifeissharingAPI;
import edu.upc.eetac.dsa.iarroyo.lifeissharing.api.Lista;

/**
 * Created by nacho on 31/05/15.
 */
public class WriteListaActivity extends Activity {

    private final static String TAG = WriteListaActivity.class.getName();

    private class PostListaTask extends AsyncTask<String, Void, Lista> {
        private ProgressDialog pd;

        @Override
        protected Lista doInBackground(String... params) {
            Lista lista = null;
            try {
                lista = LifeissharingAPI.getInstance(WriteListaActivity.this)
                        .createLista(params[0]);
            } catch (AppException e) {
                Log.e(TAG, e.getMessage(), e);
            }
            return lista;
        }

        @Override
        protected void onPostExecute(Lista result) {
            showListas(result);
            if (pd != null) {
                pd.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(WriteListaActivity.this);

            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_lista_layout);

    }

    public void cancelar(View v) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void publicar(View v) {
        EditText etText = (EditText) findViewById(R.id.etText);

        String texto = etText.getText().toString();


        (new PostListaTask()).execute(texto);
    }

    private void showListas(Lista result) {
        String json = new Gson().toJson(result);
        Bundle data = new Bundle();
        data.putString("json-lista", json);
        Intent intent = new Intent();
        intent.putExtras(data);
        setResult(RESULT_OK, intent);
        finish();
    }


}
