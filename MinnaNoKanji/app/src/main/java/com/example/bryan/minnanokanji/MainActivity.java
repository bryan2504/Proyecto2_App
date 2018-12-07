package com.example.bryan.minnanokanji;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.crashlytics.android.Crashlytics;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import io.fabric.sdk.android.Fabric;

import static com.example.bryan.minnanokanji.GlobalClass.URL_HOST;
import static com.example.bryan.minnanokanji.GlobalClass.USER_LOGIN;

public class MainActivity extends AppCompatActivity {

    public EditText editTextEmail;
    public EditText editTextPass;
    public Button buttonInit;
    public Button butttonRegister;
    public Conexion conexion;
    private MixpanelAPI mixpanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Iniciar Sesión");

        Fabric.with(this, new Crashlytics());

        // TODO: Move this to where you establish a user session
        logUser();


        editTextEmail = findViewById(R.id.editTextMEmail);
        editTextPass  = findViewById(R.id.editTextMPass);
        buttonInit = findViewById(R.id.buttonMinit);
        butttonRegister = findViewById(R.id.buttonMRegister);

        mixpanel = MixpanelAPI.getInstance(this,"4b4d6b196066ba9a609c13fb5a11360c");
        mixpanel.track("Ventana Login",null);
        mixpanel.flush();

        butttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, Registrarse.class);
                startActivity(intent);
            }
        });

        buttonInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    iniciarSesion();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Metodo para iniciar sesión
     * @throws JSONException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void iniciarSesion() throws JSONException, ExecutionException, InterruptedException {
        String correo= editTextEmail.getText().toString();
        String pass= editTextPass.getText().toString();
        if(!correo.equals("")&&!pass.equals("")){
            conexion = new Conexion();
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("email", correo);
            jsonParam.put("password", pass);

            String  result = conexion.execute(URL_HOST+USER_LOGIN,"POST",jsonParam.toString()).get();

            if(result.equals("OK")) {
                mixpanel.track("Realiza login existoso",null);
                mixpanel.flush();
                DownLoadTask downLoadTask = new DownLoadTask();
                String json_user = downLoadTask.execute(correo,pass).get();
                Intent intent= new Intent(MainActivity.this, MenuPrincipal.class);
                intent.putExtra("datos",json_user);
                startActivity(intent);
            }else{
                errorMessageDialog("Credenciales incorrectos.");
           }
        }else{
            errorMessageDialog("Para iniciar sesión debe de llenar todos los espacios.");
        }
    }

    private JSONObject ObtenerJSon(String JSonDatos, String correo) throws JSONException{
        JSONArray datos = new JSONArray(JSonDatos);
        for(int i = 0; i < datos.length(); i++){
            JSONObject elemento = datos.getJSONObject(i);
            if(elemento.getString("correo").equals(correo)){
                return elemento;
            }
        }
        return null;
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



    /**
     * Cuadro de diálogo para mensajes de error.
     * @param message
     */
    private void errorMessageDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setIcon(R.drawable.ic_img_diag_error_icon)
                .setMessage(message).setTitle("Error").setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) { return; }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public class DownLoadTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... strings) {
            String xmlString;
            HttpURLConnection urlConnection = null;
            URL url = null;

            try {
                url = new URL("http://minnanokanjibackend.miwwk5bepd.us-east-1.elasticbeanstalk.com/auth/sign_in");
                urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.setRequestProperty("email",strings[0]);
                urlConnection.setRequestProperty("password",strings[1]);
                urlConnection.setRequestProperty("Content-Type","application/json");
                urlConnection.setRequestMethod("POST");
                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    StringBuilder xmlResponse = new StringBuilder();
                    BufferedReader input = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String strLine = null;
                    while ((strLine = input.readLine()) != null) {
                        xmlResponse.append(strLine);
                    }
                    xmlString = xmlResponse.toString();
                    //xmlString += urlConnection.getHeaderField("access-token");
                    input.close();
                    return xmlString;

                }else{
                    return "Usuario Incorrecto";
                }
            }
            catch (Exception e) {
                return e.toString();
            }
            finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        }
    }

    public void forceCrash(View view) {
        throw new RuntimeException("This is a crash");
    }

    private void logUser() {
        // TODO: Use the current user's information
        // You can call any combination of these three methods
        //Crashlytics.setUserIdentifier("12345");
        //Crashlytics.setUserEmail("user@fabric.io");
        Crashlytics.setUserName("Ventana principal");
    }


}
