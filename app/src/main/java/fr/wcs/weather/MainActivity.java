package fr.wcs.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Crée une file d'attente pour les requêtes vers l'API
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String Api_Key = "b2820323ad86c82a270be7407d715183";
        String url = "https://api.openweathermap.org/data/2.5/forecast?q=Toulouse&appid=" + Api_Key;

        // Création de la requête vers l'API, ajout des écouteurs pour les réponses et erreurs possibles
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray list = response.getJSONArray("list");

                            for (int j = 0; j < 40; j = j + 8) {
                                JSONObject listinfo = list.getJSONObject(j);
                                JSONArray tableWeather = listinfo.getJSONArray("weather");

                                for (int i = 0; i < tableWeather.length(); i++) {
                                    JSONObject elemtMeteo = tableWeather.getJSONObject(i);
                                    String meteo = elemtMeteo.getString("description");
                                    Toast.makeText(MainActivity.this, meteo, Toast.LENGTH_SHORT).show();
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Afficher l'erreur
                        Log.d("VOLLEY_ERROR", "onErrorResponse: " + error.getMessage());
                    }
                }
        );

        // On ajoute la requête à la file d'attente
        requestQueue.add(jsonObjectRequest);

    }
}
