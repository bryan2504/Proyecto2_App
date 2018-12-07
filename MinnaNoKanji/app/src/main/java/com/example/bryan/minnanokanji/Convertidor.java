package com.example.bryan.minnanokanji;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import io.fabric.sdk.android.Fabric;

public class Convertidor extends AppCompatActivity {
    private SoapPrimitive resultadoXML;
    double valor_dolar;
    double valor_yen;
    private Conexion conexion;
    private String resultadoJson;
    private MixpanelAPI mixpanel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convertidor);
        getSupportActionBar().setTitle("Convertidor de Monedas");

        Fabric.with(this, new Crashlytics());

        mixpanel = MixpanelAPI.getInstance(this,"4b4d6b196066ba9a609c13fb5a11360c");
        mixpanel.track("Ventana de Convertidor de monedas",null); //Realiza la actividad de mixpanel
        mixpanel.flush();

        valor_dolar = 0;
        valor_yen = 0;
        SegundoPlano segundoPlanoTareas = new SegundoPlano();
        segundoPlanoTareas.execute();
        conexion = new Conexion();
        try {
            resultadoJson = conexion.execute("https://forex.1forge.com/1.0.3/quotes?pairs=USDJPY&api_key=xfD7IYbz6YRZ6ORBQHoCMjv4ituPcNt7","GET").get();
            JSONArray datos_hiragana = new JSONArray(resultadoJson);
            JSONObject elemento = datos_hiragana.getJSONObject(0);
            valor_yen = elemento.getDouble("price");
        } catch (InterruptedException e) {
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (ExecutionException e) {
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (JSONException e) {
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void obtenerValorDolarWS() {
        String soapAction = "http://ws.sdde.bccr.fi.cr/ObtenerIndicadoresEconomicosXML";
        String namespace = "http://ws.sdde.bccr.fi.cr";
        String url = "http://indicadoreseconomicos.bccr.fi.cr/indicadoreseconomicos/WebServices/wsIndicadoresEconomicos.asmx";
        String metodo_nombre = "ObtenerIndicadoresEconomicosXML";
        String fecha;
        DateFormat formato_fecha = new SimpleDateFormat("dd/MM/yyyy");
        Date fecha_actual = new Date();
        fecha = formato_fecha.format(fecha_actual);
        try {
            SoapObject request = new SoapObject(namespace, metodo_nombre);
            request.addProperty("tcIndicador", "317");
            request.addProperty("tcFechaInicio",fecha);
            request.addProperty("tcFechaFinal",fecha);
            request.addProperty("tcNombre", "Gerald Morales");
            request.addProperty("tnSubNiveles", "N");
            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;
            soapEnvelope.setOutputSoapObject(request);

            HttpTransportSE transport = new HttpTransportSE(url);
            transport.call(soapAction, soapEnvelope);

            resultadoXML = (SoapPrimitive) soapEnvelope.getResponse();

        } catch (Exception ex){

        }

    }

    private class SegundoPlano extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute(){

        }
        @Override
        protected  Void doInBackground(Void ... params){
            obtenerValorDolarWS();
            return null;
        }
        @Override
        protected void onPostExecute(Void result){
            valor_dolar = obtener_valor_dolar(resultadoXML.toString());
        }
    }
    public double obtener_valor_dolar(String texto){
        String resultado = "";

        Document documento = null;
        DocumentBuilder constructor_documento;
        DocumentBuilderFactory instancia_nueva;

        try {
            InputStream resultadoStream = new ByteArrayInputStream(texto.getBytes("UTF-8"));
            instancia_nueva = DocumentBuilderFactory.newInstance();
            constructor_documento = instancia_nueva.newDocumentBuilder();
            documento = constructor_documento.parse(resultadoStream);
        } catch (UnsupportedEncodingException e) {
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (SAXException e) {
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        if(documento != null) {
            resultado = documento.getElementsByTagName("NUM_VALOR").item(0).getTextContent();
        }
        return Double.parseDouble(resultado);
    }

    public void onTextClicked (View view) throws IOException, XmlPullParserException {

        EditText entrada_textfield = findViewById(R.id.valoresText);

        String monedaConvertir = entrada_textfield.getText().toString();

        RadioButton dolarestocolones = findViewById(R.id.radioButtonY2C);
        RadioButton colonestodolares = findViewById(R.id.radioButtonC2Y);

        InputMethodManager input_controlador = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        input_controlador.hideSoftInputFromWindow(view.getWindowToken(), 0);

        if(!monedaConvertir.isEmpty()){
            if(dolarestocolones.isChecked()){
                mixpanel.track("Realiza una conversion de moneda de Yenes a Colones",null); //Realiza la actividad de mixpanel
                mixpanel.flush();
                double valor_grados_numerico = Double.parseDouble(monedaConvertir) / valor_yen * valor_dolar;
                TextView salida_textview = findViewById(R.id.resultadoText);
                String mostrar = String.format("₡%.4f", valor_grados_numerico);
                salida_textview.setText(mostrar);
                //Toast.makeText(this,mostrar,Toast.LENGTH_SHORT).show();

            }else if(colonestodolares.isChecked()){
                mixpanel.track("Realiza una conversion de moneda de Colones a Yenes",null); //Realiza la actividad de mixpanel
                mixpanel.flush();
                double valor_grados_numerico = (Double.parseDouble(monedaConvertir)) / valor_dolar * valor_yen;
                TextView salida_textview = findViewById(R.id.resultadoText);
                String mostrar = String.format("¥%.4f", valor_grados_numerico);
                salida_textview.setText(mostrar);
                //Toast.makeText(this,mostrar,Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"Seleccione el tipo de conversión",Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this,"Introduzca un valor",Toast.LENGTH_SHORT).show();
        }



        //Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }
}
