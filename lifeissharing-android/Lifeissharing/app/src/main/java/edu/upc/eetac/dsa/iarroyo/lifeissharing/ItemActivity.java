package edu.upc.eetac.dsa.iarroyo.lifeissharing;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import edu.upc.eetac.dsa.iarroyo.lifeissharing.api.AppException;
import edu.upc.eetac.dsa.iarroyo.lifeissharing.api.Item;
import edu.upc.eetac.dsa.iarroyo.lifeissharing.api.ItemCollection;
import edu.upc.eetac.dsa.iarroyo.lifeissharing.api.LifeissharingAPI;
import edu.upc.eetac.dsa.iarroyo.lifeissharing.api.Lista;

/**
 * Created by nacho on 30/05/15.
 */
public class ItemActivity extends ListActivity{


    private final static String TAG = ItemActivity.class.toString();
    ArrayList<Item> itemList;
    private ItemAdapter adapter;
    String urlItems  = null;
    String urlEditores = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_activity);
        urlItems = (String) getIntent().getExtras().get("url");
        urlEditores = (String) getIntent().getExtras().get("url2");

        itemList = new ArrayList<Item>();
        adapter = new ItemAdapter(this, itemList);
        setListAdapter(adapter);
        (new FetchItemsTask()).execute(urlItems);
    }

    private void addItems(ItemCollection items){
        itemList.addAll(items.getItems());
        adapter.notifyDataSetChanged();

    }




    private class FetchItemsTask extends
            AsyncTask<String, Void, ItemCollection> {
        private ProgressDialog pd;

        @Override
        protected ItemCollection doInBackground(String... params) {
            ItemCollection items = null;
            try {
                items = LifeissharingAPI.getInstance(ItemActivity.this)
                        .getItems(params[0]);



            } catch (AppException e) {
                e.printStackTrace();
            }
            return items;
        }

        @Override
        protected void onPostExecute(ItemCollection result) {
            addItems(result);
            setListAdapter(adapter);
            if (pd != null) {
                pd.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(ItemActivity.this);
            pd.setTitle("Searching...");
            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.post_item:
                Intent intent = new Intent(this, WriteItemActivity.class);

                intent.putExtra("url", urlItems);

                startActivity(intent);
                return true;

            case R.id.list_editores:
                Intent intent2 = new Intent(this, EditorActivity.class);

                intent2.putExtra("url2", urlEditores);

                startActivity(intent2);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
