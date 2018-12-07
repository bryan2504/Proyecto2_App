package com.example.bryan.minnanokanji;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class menuKatakana extends AppCompatActivity {

    public static ArrayList<String> lista_lecciones;
    private ArrayAdapter<String> adapter;
    private ListView listView;
    public static final String POSICION  = "leccion";
    private MixpanelAPI mixpanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_katakana);
        getSupportActionBar().setTitle("Katakana カタカナ");

        mixpanel = MixpanelAPI.getInstance(this,"4b4d6b196066ba9a609c13fb5a11360c");
        mixpanel.track("Ventana de lecciones de Katakana",null); //Realiza la actividad de mixpanel
        mixpanel.flush();

        listView = findViewById(R.id.listaKatakanas);


        try {
            lista_lecciones = (cargarLecciones());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        adapter = new ArrayAdapter<String>(this,R.layout.list_item,R.id.txtitem,lista_lecciones);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                enviarLeccion(position);

            }

        });

    }

    public void enviarLeccion(int posicion){
        Intent intent = new Intent(menuKatakana.this,ClaseKatakana.class);
        intent.putExtra(POSICION,Integer.toString(++posicion));
        mixpanel.track("Entra a una leccion de Katakana",null); //Realiza la actividad de mixpanel
        mixpanel.flush();
        startActivity(intent);
    }



    public ArrayList<String> cargarLecciones() throws JSONException, ExecutionException, InterruptedException {

        Conexion user_extendeds = new Conexion();
        String resultado_consulta_katakana = user_extendeds.execute("http://minnanokanjibackend.miwwk5bepd.us-east-1.elasticbeanstalk.com/katakanas.json", "GET").get();
        JSONArray datos_hiragana = new JSONArray(resultado_consulta_katakana);
        List<String> leccion_katakana = new ArrayList<>();

        JSONObject elemento;

        for(int i = 0; i < datos_hiragana.length(); i++){

            elemento = datos_hiragana.getJSONObject(i);

            String msj = ": "+elemento.getString("explicacion");
            leccion_katakana.add("Lección No. "+ elemento.getString("leccion")+msj);
        }

        return (ArrayList<String>) leccion_katakana;


    }



}
