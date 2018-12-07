package com.example.bryan.minnanokanji;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.crashlytics.android.Crashlytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import io.fabric.sdk.android.Fabric;

public class ClaseKatakana extends AppCompatActivity {

    private String numero_leccion;
    private ListView lista_view_leccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clase_katakana);

        Fabric.with(this, new Crashlytics());

        lista_view_leccion = findViewById(R.id.listViewKatakana);
        Intent i=getIntent();
        numero_leccion = i.getExtras().getString("leccion");

        getSupportActionBar().setTitle("KATAKANA, Clase No. "+numero_leccion);

        try {
            MostrarLeccion();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void MostrarLeccion() throws ExecutionException, InterruptedException, JSONException {
        Conexion user_extendeds = new Conexion();
        String resultado_consulta_hiragana = user_extendeds.execute("http://minnanokanjibackend.miwwk5bepd.us-east-1.elasticbeanstalk.com/leccion_katakanas.json", "GET").get();
        JSONArray datos_hiragana = new JSONArray(resultado_consulta_hiragana);
        List<String> simbolos_hiragana = new ArrayList<>();
        List<String> imagenes_simbolos = new ArrayList<>();

        JSONObject elemento;

        for(int i = 0; i < datos_hiragana.length(); i++){
            elemento = datos_hiragana.getJSONObject(i);

            if(elemento.getString("leccion").equals(this.numero_leccion)){
                simbolos_hiragana.add(elemento.getString("significado"));
                imagenes_simbolos.add(elemento.getString("url_imagen"));
            }
        }

        String[] simbolos_adapter = simbolos_hiragana.toArray(new String[0]);
        String[] imagenes_adapter = imagenes_simbolos.toArray(new String[0]);

        CustomListHiragana adapter = new CustomListHiragana(this,simbolos_adapter,imagenes_adapter);

        if(adapter != null){
            lista_view_leccion.setAdapter(adapter);
        }
    }
}
