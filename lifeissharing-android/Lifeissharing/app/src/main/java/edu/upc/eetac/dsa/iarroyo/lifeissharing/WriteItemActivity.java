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
import edu.upc.eetac.dsa.iarroyo.lifeissharing.api.Item;
import edu.upc.eetac.dsa.iarroyo.lifeissharing.api.LifeissharingAPI;

/**
 * Created by nacho on 31/05/15.
 */
public class WriteItemActivity  extends Activity{

    private final static String TAG = WriteItemActivity.class.getName();
    private String urlItems;

    private class PostItemTask extends AsyncTask<String, Void, Item> {
        private ProgressDialog pd;

        @Override
        protected Item doInBackground(String... params) {
            Item item = null;
            try {
                item = LifeissharingAPI.getInstance(WriteItemActivity.this)
                        .createItem(params[0], params[1]);
            } catch (AppException e) {
                Log.e(TAG, e.getMessage(), e);
            }
            return item;
        }

        @Override
        protected void onPostExecute(Item result) {
            showItems(result);
            if (pd != null) {
                pd.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(WriteItemActivity.this);

            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_item_layout);
        urlItems = (String) getIntent().getExtras().get("url");

    }

    public void cancelar(View v) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void publicar(View v) {
        EditText etText = (EditText) findViewById(R.id.etText);

        String texto = etText.getText().toString();


        (new PostItemTask()).execute(texto, urlItems);
    }

    private void showItems(Item result) {
        String json = new Gson().toJson(result);
        Bundle data = new Bundle();
        data.putString("json-item", json);
        Intent intent = new Intent();
        intent.putExtras(data);
        setResult(RESULT_OK, intent);
        finish();
    }

}
