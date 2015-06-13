package edu.upc.eetac.dsa.iarroyo.lifeissharing;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.ArrayList;

import edu.upc.eetac.dsa.iarroyo.lifeissharing.api.AppException;
import edu.upc.eetac.dsa.iarroyo.lifeissharing.api.LifeissharingAPI;
import edu.upc.eetac.dsa.iarroyo.lifeissharing.api.Lista;
import edu.upc.eetac.dsa.iarroyo.lifeissharing.api.ListaCollection;


public class LifeMainActivity extends ListActivity {

    private final static String TAG = LifeMainActivity.class.toString();
    ArrayList<Lista> listaList;
    private ListaAdapter adapter;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_main);

        listaList = new ArrayList<Lista>();
        adapter = new ListaAdapter(this, listaList);
        setListAdapter(adapter);

        SharedPreferences prefs = getSharedPreferences("life-profile", Context.MODE_PRIVATE);
        final String username = prefs.getString("username",null);
        final String password = prefs.getString("password", null);

        Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password
                        .toCharArray());
            }
        });
        (new FetchListasTask()).execute();
    }


    private void addListas(ListaCollection listas){
        listaList.addAll(listas.getListas());
        adapter.notifyDataSetChanged();
    }

    private class FetchListasTask extends
            AsyncTask<Void, Void, ListaCollection> {
        private ProgressDialog pd;

        @Override
        protected ListaCollection doInBackground(Void... params) {
            ListaCollection listas = null;
            try {
                listas = LifeissharingAPI.getInstance(LifeMainActivity.this)
                        .getListas();
            } catch (AppException e) {
                e.printStackTrace();
            }
            return listas;
        }

        @Override
        protected void onPostExecute(ListaCollection result) {
            if(result == null){

                Toast toast = Toast.makeText(LifeMainActivity.this, "Has de introducir username y password", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.show();

            }else {
                addListas(result);
            }

            if (pd != null) {
                pd.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(LifeMainActivity.this);
            pd.setTitle("Searching...");
            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_life_main, menu);
        return true;
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Lista lista = listaList.get(position);
        Log.d(TAG, lista.getLinks().get("items").getTarget());

        Intent intent = new Intent(this, ItemActivity.class);
        intent.putExtra("url", lista.getLinks().get("items").getTarget());
        intent.putExtra("url2", lista.getLinks().get("editores").getTarget());



        startActivity(intent);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.post_lista:
                Intent intent = new Intent(this, WriteListaActivity.class);

                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
