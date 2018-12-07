package com.example.bryan.minnanokanji;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class Curiosidades extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    public static ArrayList<String> lista_curiosidades;
    public int updown = 1; //up = 1; down =2
    public ListView listView;

    public String filtroGlobal = "Todas";
    public String ordenGlobal = "Sin Ordenar";
    public int updownGlobal = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curiosidades);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Curiosidades de Japón");

        final TextView filtroResult = findViewById(R.id.filtroResult);
        final TextView ordenResult = findViewById(R.id.ordenResult);

        listView = findViewById(R.id.listaCuriosidades);

        try {
            lista_curiosidades = (cargarCuriosidades());
        } catch (JSONException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mostrarMsj(position);
            }
        });



        Button btnFiltro = (Button) findViewById(R.id.btnFiltrar);
        btnFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* ACA SE HACE EL FILTRACION */
                Filtrar(filtroResult);
            }
        });


        Button btnOrden = findViewById(R.id.btnOrdenar);
        btnOrden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* ACA SE HACE EL ORDENAMIENTO */
                Ordenar(ordenResult);
            }
        });


        final Button UpDown = (Button) findViewById(R.id.UpDown);
        UpDown.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if (UpDown.getText().toString().equals("↑")){
                    UpDown.setText("↓");
                    updownGlobal=2;
                    try {
                        filtrarLista();
                    } catch (JSONException | ExecutionException | InterruptedException | ParseException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    UpDown.setText("↑");
                    updownGlobal=1;
                    try {
                        filtrarLista();
                    } catch (JSONException | ExecutionException | ParseException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        adapter = new ArrayAdapter<String>(this,R.layout.list_item,R.id.txtitem,lista_curiosidades);
        listView.setAdapter(adapter);



    }

    public void mostrarMsj(int pos){
        String msj = lista_curiosidades.get(pos);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msj)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                });

        // Create the AlertDialog object and return it
        builder.create();
        builder.show();


    }

    public void Filtrar(final TextView textView) {
        final String[] temas = {"Todos","General","Comida","Gente","Tradiciones","Lugares"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Filtrar Datos")
                .setItems(temas, new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.N)
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        textView.setText(temas[which]);
                        filtroGlobal = temas[which];
                        try {
                            filtrarLista();
                        } catch (JSONException | InterruptedException | ExecutionException | ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });
        builder.create();
        builder.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void filtrarLista() throws JSONException, ExecutionException, InterruptedException, ParseException {
        listView.setAdapter(null); //Borrar contenido de lista
        lista_curiosidades.clear();

        Conexion user_extendeds = new Conexion();
        String resultado_consulta_curiosidades = user_extendeds.execute("http://minnanokanjibackend.miwwk5bepd.us-east-1.elasticbeanstalk.com/curiosidades_japons.json", "GET").get();
        JSONArray datos_curiosidades = new JSONArray(resultado_consulta_curiosidades);
        List<String> curiosidades_finales = new ArrayList<>();

        JSONObject elemento;
        List<String> listaFechas = new ArrayList<>();

        for (int i = 0; i < datos_curiosidades.length(); i++) {

            elemento = datos_curiosidades.getJSONObject(i);

            String tipo = elemento.getString("tipo");
            String explicacion = elemento.getString("explicacion");
            String fecha = elemento.getString("fecha");

            if (tipo.equals(filtroGlobal) || filtroGlobal.equals("Todos")) {
                curiosidades_finales.add(tipo + ": " + explicacion + "\nFecha: " + fecha);
                listaFechas.add(fecha);
            }
        }



        /* Ordenar*/
        if (ordenGlobal.equals("Sin Ordenar")) {

        }
        else if (ordenGlobal.equals("Tipo")){

            Collections.sort(curiosidades_finales);
        }
        else if (ordenGlobal.equals("Fecha")){

                //curiosidades_finales = ordenarFecha(curiosidades_finales,listaFechas);

        }

        if(updownGlobal==2){
            Collections.reverse(curiosidades_finales);
        }

        lista_curiosidades= (ArrayList<String>) curiosidades_finales;
        adapter = new ArrayAdapter<String>(this,R.layout.list_item,R.id.txtitem,lista_curiosidades);
        listView.setAdapter(adapter);

    }


    public List<String> ordenarFecha(List<String> listaCuriosidades, List<String> listaFechas){

        List<String> result = new ArrayList<>();
        int indexMejorFecha;

        while(!listaCuriosidades.isEmpty()){
            indexMejorFecha = 0;

            for(int i = 0;i<listaCuriosidades.size();i++){
                String fecha = listaFechas.get(i);
                String mejorFecha = listaFechas.get(indexMejorFecha);
                if (i != 0) {
                    if(esAntes(mejorFecha,fecha)){//(mejorFecha.after(fecha)){ //si mejorFecha está después que la fecha en cuestión
                        indexMejorFecha = i;
                    }
                }
            }
            result.add(listaCuriosidades.get(indexMejorFecha));
            listaFechas.remove(listaFechas.get(indexMejorFecha));
            listaCuriosidades.remove(listaCuriosidades.get(indexMejorFecha));
        }

        return result;

    }

    public boolean esAntes(String fechaX, String fecha) {
        int diaX, mesX, anioX, dia, mes, anio;

        diaX = Integer.valueOf(slice_end(fechaX, fechaX.indexOf("/")));
        String tmp = slice_start(fechaX, fechaX.indexOf("/"));
        mesX = Integer.valueOf(slice_end(tmp, tmp.indexOf("/")));
        anioX = Integer.valueOf(slice_start(tmp, tmp.indexOf("/")));

        dia = Integer.valueOf(slice_end(fecha, fecha.indexOf("/")));
        String tmp2 = slice_start(fecha, fecha.indexOf("/"));
        mes = Integer.valueOf(slice_end(tmp2, tmp2.indexOf("/")));
        anio = Integer.valueOf(slice_start(tmp2, tmp2.indexOf("/")));

        return anioX < anio || anioX <= anio && (mesX < mes || mesX <= mes && diaX <= dia);
    }


    public String slice_start(String s, int startIndex) {
        if (startIndex < 0) startIndex = s.length() + startIndex;
        return s.substring(startIndex);
    }
    public String slice_end(String s, int endIndex) {
        if (endIndex < 0) endIndex = s.length() + endIndex;
        return s.substring(0, endIndex);
    }
    public String slice_range(String s, int startIndex, int endIndex) {
        if (startIndex < 0) startIndex = s.length() + startIndex;
        if (endIndex < 0) endIndex = s.length() + endIndex;
        return s.substring(startIndex, endIndex);
    }


    public void Ordenar(final TextView textView) {
        final String[] temas = {"Sin Ordenar","Fecha","Tipo"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ordenar Datos")
                .setItems(temas, new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.N)
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        textView.setText(temas[which]);
                        ordenGlobal = temas[which];
                        try {
                            filtrarLista();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });
        builder.create();
        builder.show();
    }


    public ArrayList<String> cargarCuriosidades() throws JSONException, ExecutionException, InterruptedException {

        Conexion user_extendeds = new Conexion();
        String resultado_consulta_curiosidades = user_extendeds.execute("http://minnanokanjibackend.miwwk5bepd.us-east-1.elasticbeanstalk.com/curiosidades_japons.json", "GET").get();
        JSONArray datos_curiosidades = new JSONArray(resultado_consulta_curiosidades);
        List<String> curiosidades_finales = new ArrayList<>();

        JSONObject elemento;

        for(int i = 0; i < datos_curiosidades.length(); i++){

            elemento = datos_curiosidades.getJSONObject(i);

            String tipo = elemento.getString("tipo");
            String explicacion = elemento.getString("explicacion");
            String fecha = elemento.getString("fecha");

            curiosidades_finales.add(tipo+": "+explicacion+"\nFecha: "+fecha);

        }

        return (ArrayList<String>) curiosidades_finales;



    }


}
