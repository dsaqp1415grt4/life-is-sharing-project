package edu.upc.eetac.dsa.iarroyo.lifeissharing.api;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

/**
 * Created by nacho on 30/05/15.
 */
public class LifeissharingAPI {

    private final static String TAG = LifeissharingAPI.class.getName();
    private static LifeissharingAPI instance = null;
    private URL url;
    private String id= null;

    private LifeissharingRootAPI rootAPI = null;

    private LifeissharingAPI(Context context) throws IOException, AppException {
        super();

        AssetManager assetManager = context.getAssets();
        Properties config = new Properties();
        config.load(assetManager.open("config.properties"));
        String urlHome = config.getProperty("life.home");
        url = new URL(urlHome);

        Log.d("LINKS", url.toString());
        getRootAPI();
    }

    public final static LifeissharingAPI getInstance(Context context) throws AppException {
        if (instance == null)
            try {
                instance = new LifeissharingAPI(context);
            } catch (IOException e) {
                throw new AppException(
                        "Can't load configuration file");
            }
        return instance;
    }

    private void getRootAPI() throws AppException {
        Log.d(TAG, "getRootAPI()");
        rootAPI = new LifeissharingRootAPI();
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();
        } catch (IOException e) {
            throw new AppException(
                    "Can't connect to Books API Web Service");
        }

        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONArray jsonLinks = jsonObject.getJSONArray("links");
            parseLinks(jsonLinks, rootAPI.getLinks());
        } catch (IOException e) {
            throw new AppException(
                    "Can't get response from Books API Web Service");
        } catch (JSONException e) {
            throw new AppException("Error parsing Book Root API");
        }

    }



    private void parseLinks(JSONArray jsonLinks, Map<String, Link> map)
            throws AppException, JSONException {
        for (int i = 0; i < jsonLinks.length(); i++) {
            Link link = null;
            try {
                link = SimpleLinkHeaderParser
                        .parseLink(jsonLinks.getString(i));
            } catch (Exception e) {
                throw new AppException(e.getMessage());
            }
            String rel = link.getParameters().get("rel");
            String rels[] = rel.split("\\s");
            for (String s : rels)
                map.put(s, link);
        }
    }




    public ListaCollection getListas() throws AppException {
        Log.d(TAG, "getListas()");
        ListaCollection listas = new ListaCollection();

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) new URL(rootAPI.getLinks()
                    .get("collection").getTarget()).openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();
        } catch (IOException e) {
            throw new AppException(
                    "Can't connect to Books API Web Service");
        }

        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONArray jsonLinks = jsonObject.getJSONArray("links");
            parseLinks(jsonLinks, listas.getLinks());


            JSONArray jsonListas = jsonObject.getJSONArray("listas");
            for (int i = 0; i < jsonListas.length(); i++) {
                Lista lista = new Lista();
                JSONObject jsonLista = jsonListas.getJSONObject(i);

                lista.setId(jsonLista.getInt("idlista"));
                lista.setNombre(jsonLista.getString("nombre"));
                lista.setCreador(jsonLista.getString("creador"));
                lista.setFecha_creacion(jsonLista.getLong("fecha_creacion"));
                lista.setUltima_modificacion(jsonLista.getLong("ultima_modificacion"));
                jsonLinks = jsonLista.getJSONArray("links");
                parseLinks(jsonLinks, lista.getLinks());
                listas.getListas().add(lista);


            }
        } catch (IOException e) {
            throw new AppException(
                    "Can't get response from Books API Web Service");
        } catch (JSONException e) {
            throw new AppException("Error parsing Books Root API");
        }

        return listas;
    }

    public ItemCollection getItems(String urlItems) throws AppException {
        Log.d(TAG, "getItems()");
        ItemCollection items = new ItemCollection();

        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(urlItems);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();
        } catch (IOException e) {
            throw new AppException(
                    "Can't connect to Books API Web Service");
        }

        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONObject jsonObject = new JSONObject(sb.toString());



            JSONArray jsonItems = jsonObject.getJSONArray("items");
            for (int i = 0; i < jsonItems.length(); i++) {

                Item item = new Item();
                JSONObject jsonItem = jsonItems.getJSONObject(i);

                item.setDescription(jsonItem.getString("description"));
                item.setId(jsonItem.getInt("id"));
                item.setIditem(jsonItem.getInt("iditem"));

                items.getItems().add(item);


            }
        } catch (IOException e) {
            throw new AppException(
                    "Can't get response from Books API Web Service");
        } catch (JSONException e) {
            throw new AppException("Error parsing Books Root API");
        }

        return items;
    }

    public Item createItem(String texto, String urlitems) throws AppException {

     //   String id;
        Item item = new Item();
       // String[] arrayUrlItem = urlitems.split("/");
       // id = arrayUrlItem[5];
        //System.out.println(id);
        item.setDescription(texto);
      //  item.setId(Integer.parseInt(id));


        HttpURLConnection urlConnection = null;
        try {

            JSONObject jsonItem = createJsonItem(item);
            URL url = new URL(urlitems);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept",
                    MediaType.LIFE_API_LISTA);
            urlConnection.setRequestProperty("Content-Type",
                    MediaType.LIFE_API_LISTA);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            PrintWriter writer = new PrintWriter(
                    urlConnection.getOutputStream());
            writer.println(jsonItem.toString());
            writer.flush();
            //writer.close();
            int rc = urlConnection.getResponseCode();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            jsonItem = new JSONObject(sb.toString());
            item.setDescription(jsonItem.getString("description"));
            item.setId(jsonItem.getInt("id"));
            item.setIditem(jsonItem.getInt("iditem"));

        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
            throw new AppException("Error parsing response");
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
            throw new AppException("Error getting response");
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
        return item;
    }

    private JSONObject createJsonItem(Item item) throws JSONException {
        JSONObject jsonItem = new JSONObject();
        jsonItem.put("description", item.getDescription());

        return jsonItem;
    }


    public Lista createLista(String texto) throws AppException {


        Lista lista = new Lista();
        lista.setNombre(texto);




        HttpURLConnection urlConnection = null;
        try {

            JSONObject jsonLista = createJsonLista(lista);
            urlConnection = (HttpURLConnection) new URL(rootAPI.getLinks()
                    .get("create-lista").getTarget()).openConnection();
            urlConnection.setRequestProperty("Accept",
                    MediaType.LIFE_API_LISTA);
            urlConnection.setRequestProperty("Content-Type",
                    MediaType.LIFE_API_LISTA);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            PrintWriter writer = new PrintWriter(
                    urlConnection.getOutputStream());
            writer.println(jsonLista.toString());
            writer.flush();
            //writer.close();
            int rc = urlConnection.getResponseCode();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            jsonLista = new JSONObject(sb.toString());
            lista.setId(jsonLista.getInt("idlista"));
            lista.setNombre(jsonLista.getString("nombre"));
            lista.setCreador(jsonLista.getString("creador"));
            lista.setFecha_creacion(jsonLista.getLong("fecha_creacion"));
            lista.setUltima_modificacion(jsonLista.getLong("ultima_modificacion"));
            JSONArray jsonLinks = jsonLista.getJSONArray("links");
            parseLinks(jsonLinks, lista.getLinks());

        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
            throw new AppException("Error parsing response");
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
            throw new AppException("Error getting response");
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
        return lista;
    }

    private JSONObject createJsonLista(Lista lista) throws JSONException {
        JSONObject jsonLista = new JSONObject();

        jsonLista.put("nombre", lista.getNombre());


        return jsonLista;
    }




    public EditorCollection getEditores(String urlEditores) throws AppException {
        Log.d(TAG, "getEditores()");
        EditorCollection editores = new EditorCollection();

        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(urlEditores);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();
        } catch (IOException e) {
            throw new AppException(
                    "Can't connect to Books API Web Service");
        }

        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONObject jsonObject = new JSONObject(sb.toString());



            JSONArray jsonEditores = jsonObject.getJSONArray("editores");
            for (int i = 0; i < jsonEditores.length(); i++) {

                Editor editor = new Editor();
                JSONObject jsonEditor = jsonEditores.getJSONObject(i);

                editor.setUsername(jsonEditor.getString("username"));

                editores.getEditores().add(editor);



            }
        } catch (IOException e) {
            throw new AppException(
                    "Can't get response from Lifeissharing API Web Service");
        } catch (JSONException e) {
            throw new AppException("Error parsing Lifeissharing Root API");
        }

        return editores;
    }



}
