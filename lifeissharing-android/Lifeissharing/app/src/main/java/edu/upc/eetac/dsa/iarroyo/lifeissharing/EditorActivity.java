package edu.upc.eetac.dsa.iarroyo.lifeissharing;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

import edu.upc.eetac.dsa.iarroyo.lifeissharing.api.AppException;
import edu.upc.eetac.dsa.iarroyo.lifeissharing.api.Editor;
import edu.upc.eetac.dsa.iarroyo.lifeissharing.api.EditorCollection;
import edu.upc.eetac.dsa.iarroyo.lifeissharing.api.Item;
import edu.upc.eetac.dsa.iarroyo.lifeissharing.api.ItemCollection;
import edu.upc.eetac.dsa.iarroyo.lifeissharing.api.LifeissharingAPI;

/**
 * Created by nacho on 4/06/15.
 */
public class EditorActivity extends ListActivity{

    private final static String TAG = EditorActivity.class.toString();
    ArrayList<Editor> editorList;
    private EditorAdapter adapter;
    String urlEditores  = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_activity);
        urlEditores = (String) getIntent().getExtras().get("url2");

        editorList = new ArrayList<Editor>();
        adapter = new EditorAdapter(this, editorList);
        setListAdapter(adapter);
        (new FetchEditoresTask()).execute(urlEditores);
    }

    private void addItems(EditorCollection editores){
        editorList.addAll(editores.getEditores());
        adapter.notifyDataSetChanged();

    }




    private class FetchEditoresTask extends
            AsyncTask<String, Void, EditorCollection> {
        private ProgressDialog pd;

        @Override
        protected EditorCollection doInBackground(String... params) {
            EditorCollection editores = null;
            try {
                editores = LifeissharingAPI.getInstance(EditorActivity.this)
                        .getEditores(params[0]);


            } catch (AppException e) {
                e.printStackTrace();
            }
            return editores;
        }

        @Override
        protected void onPostExecute(EditorCollection result) {
            addItems(result);
            if (pd != null) {
                pd.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(EditorActivity.this);
            pd.setTitle("Searching...");
            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user, menu);
        return true;
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.post_user:
                Intent intent = new Intent(this, InvitarUserActivity.class);

                intent.putExtra("url", urlEditores);

                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
