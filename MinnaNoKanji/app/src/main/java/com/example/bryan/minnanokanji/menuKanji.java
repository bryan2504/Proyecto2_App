package com.example.bryan.minnanokanji;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class menuKanji extends AppCompatActivity {

    private MixpanelAPI mixpanel; //Objeto de mixpanel
    public static final String POSICION  = "leccion";
    private ArrayList<String> lista_lecciones;
    private ArrayAdapter<String> adapter;
    private ListView lista_kanjis_parte1;

    public Button buttonLecc1;
    public Button buttonLecc2;
    public Button buttonLecc3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_kanji);
        getSupportActionBar().setTitle("Kanji 漢字");
        mixpanel = MixpanelAPI.getInstance(this,"4b4d6b196066ba9a609c13fb5a11360c");
        mixpanel.track("Ventana de lecciones de Kanji",null); //Realiza la actividad de mixpanel
        mixpanel.flush();

        lista_kanjis_parte1 = findViewById(R.id.listaParte1);



        try {
            lista_lecciones = cargarLecciones();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        adapter = new ArrayAdapter<String>(this,R.layout.list_item,R.id.txtitem,lista_lecciones);
        if(adapter != null){
            lista_kanjis_parte1.setAdapter(adapter);

            lista_kanjis_parte1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    enviarLeccion(position);

                }

            });
        }else{
            infoMessageDialog(Arrays.toString(new ArrayList[]{lista_lecciones}));
        }



        //creación del botón de acordeón
        //1
        final LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.listaLecciones1);
        final Button btnLec1 = (Button) findViewById(R.id.part1);
        btnLec1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (linearLayout1.getVisibility()==View.GONE){
                    linearLayout1.setVisibility(View.VISIBLE);
                    btnLec1.setText("Primera Parte ↑");

                }
                else{
                    linearLayout1.setVisibility(View.GONE);
                    btnLec1.setText("Primera Parte ↓");
                }
            }
        });


        //2

        final LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.listaLecciones2);
        final Button btnLec2 = (Button) findViewById(R.id.part2);
        btnLec2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (linearLayout2.getVisibility()==View.GONE){
                    linearLayout2.setVisibility(View.VISIBLE);
                    btnLec2.setText("Segunda Parte ↑");
                }
                else{
                    linearLayout2.setVisibility(View.GONE);
                    btnLec2.setText("Segunda Parte ↓");
                }
            }
        });



        //3

        final LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.listaLecciones3);
        final Button btnLec3 = (Button) findViewById(R.id.part3);
        btnLec3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (linearLayout3.getVisibility()==View.GONE){
                    linearLayout3.setVisibility(View.VISIBLE);
                    btnLec3.setText("Tercera Parte ↑");
                }
                else{
                    linearLayout3.setVisibility(View.GONE);
                    btnLec3.setText("Tercera Parte ↓");
                }
            }
        });


    }

    public void enviarLeccion(int posicion){
        Intent intent = new Intent(menuKanji.this,ClaseKanji.class);
        intent.putExtra(POSICION,Integer.toString(++posicion));
        mixpanel.track("Entra a una leccion de kanji",null); //Realiza la actividad de mixpanel
        mixpanel.flush();
        startActivity(intent);

    }

    public void leccion(View view){
        infoMessageDialog("Estas lecciones aun no se encuentran completadas.\nGracias por la comprensión.");
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

    public ArrayList<String> cargarLecciones() throws JSONException, ExecutionException, InterruptedException {

        Conexion user_extendeds = new Conexion();
        String resultado_consulta_hiragana = user_extendeds.execute("http://minnanokanjibackend.miwwk5bepd.us-east-1.elasticbeanstalk.com/kanjis.json", "GET").get();
        JSONArray datos_hiragana = new JSONArray(resultado_consulta_hiragana);
        List<String> leccion_hiragana = new ArrayList<>();

        JSONObject elemento;

        for(int i = 0; i < datos_hiragana.length(); i++){

            elemento = datos_hiragana.getJSONObject(i);

            String msj = ": "+elemento.getString("explicacion");
            leccion_hiragana.add("Lección No. "+ elemento.getString("leccion")+msj);
        }

        return (ArrayList<String>) leccion_hiragana;


    }
}