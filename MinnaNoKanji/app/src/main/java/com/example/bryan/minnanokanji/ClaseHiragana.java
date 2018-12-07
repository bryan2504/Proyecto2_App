package com.example.bryan.minnanokanji;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import io.fabric.sdk.android.Fabric;

public class ClaseHiragana extends AppCompatActivity {

    private String numero_leccion;
    private ListView lista_view_leccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clase_hiragana);
        getSupportActionBar().setTitle("HIRAGANA, Clase No. ");

        Fabric.with(this, new Crashlytics());

        lista_view_leccion = findViewById(R.id.list_view_clase_hiragana);
        Intent i=getIntent();
        numero_leccion = i.getExtras().getString("leccion");

        getSupportActionBar().setTitle("HIRAGANA, Clase No. "+numero_leccion);


        try {
            MostrarLeccion();
        } catch (ExecutionException e) {
            infoMessageDialog(e.toString());
        } catch (InterruptedException e) {
            infoMessageDialog(e.toString());
        } catch (JSONException e) {
            infoMessageDialog(e.toString());
        }
    }

    /**
     * Este metodo muestra la leccion en el listview
     */
    //http://minnanokanjibackend.miwwk5bepd.us-east-1.elasticbeanstalk.com/leccion_hiraganas

    private void MostrarLeccion() throws ExecutionException, InterruptedException, JSONException {
        Conexion user_extendeds = new Conexion();
        String resultado_consulta_hiragana = user_extendeds.execute("http://minnanokanjibackend.miwwk5bepd.us-east-1.elasticbeanstalk.com/leccion_hiraganas.json", "GET").get();
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
            //infoMessageDialog("Sirve");
        }else{
            infoMessageDialog("No sirve");
        }
    }

    /**
     * Cuadro de diálogo para mensajes de información.
     * @param message
     */
    private void infoMessageDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setIcon(R.drawable.ic_img_diag_info_icon)
                .setMessage(message).setTitle("Información").setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) { return; }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
