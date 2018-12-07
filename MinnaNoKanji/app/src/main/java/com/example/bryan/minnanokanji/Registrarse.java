package com.example.bryan.minnanokanji;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.crashlytics.android.Crashlytics;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.fabric.sdk.android.Fabric;

import static com.example.bryan.minnanokanji.GlobalClass.PATH_S3;
import static com.example.bryan.minnanokanji.GlobalClass.URL_HOST;
import static com.example.bryan.minnanokanji.GlobalClass.USER_LOGIN;
import static com.example.bryan.minnanokanji.GlobalClass.USER_REGISTER;

public class Registrarse extends AppCompatActivity {

    private EditText editTextCorreo;
    private EditText editTextNombre;
    private EditText editTextPseudo;
    private EditText editTextContra1;
    private EditText editTextContra2;
    private Button buttonGuardar;
    private Button buttonGaleria;
    private Button buttonCamara;
    private Conexion conexion;

    private Uri imagenUri;
    private int intentLlamada;
    private boolean cargoImagen = false;

    private String path_portada;
    private static final int SELECT_PICTURE=3513;
    private static final int PERMISSION_REQUEST_CODE=5468;

    private static  final int TOMADA_PICTURE=20;

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    private static final String CARPETA_PRINCIPAL = "/misImagenesApp/";
    private static final String CARPETA_IMAGEN = "imagenes/";
    private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL+CARPETA_IMAGEN;

    private MixpanelAPI mixpanel; //Objeto de mixpanel
    File fileImagen;
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        Fabric.with(this, new Crashlytics());

        mixpanel = MixpanelAPI.getInstance(this,"4b4d6b196066ba9a609c13fb5a11360c");
        mixpanel.track("Ventana Registro",null); //Realiza la actividad de mixpanel
        mixpanel.flush();
        AWSMobileClient.getInstance().initialize(this).execute();
        editTextCorreo = findViewById(R.id.editTextREmail);
        editTextNombre = findViewById(R.id.editTextRNick);
        editTextPseudo = findViewById(R.id.editTextRNickname);
        editTextContra1 = findViewById(R.id.editTextRPass1);
        editTextContra2 = findViewById(R.id.editTextRPass2);
        buttonGuardar = findViewById(R.id.buttonRSave);
        buttonGaleria = findViewById(R.id.buttonRGallery);
        buttonCamara = findViewById(R.id.buttonRCamera);

        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    registrarUsuario();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        buttonGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarImagenPerfil();
            }
        });

        buttonCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    tomarFoto();
                }catch (Exception e){
                    infoMessageDialog(e.toString());
                }

            }
        });
    }

    public void registrarUsuario() throws JSONException, ExecutionException, InterruptedException {
        String correo = editTextCorreo.getText().toString();
        String nombre = editTextNombre.getText().toString();
        String pseudo = editTextPseudo.getText().toString();
        String contra1 = editTextContra1.getText().toString();
        String contra2 = editTextContra2.getText().toString();

        if(!correo.isEmpty() && !nombre.isEmpty() && !pseudo.isEmpty() && !contra1.isEmpty() && !contra2.isEmpty()){
            if(contra1.equals(contra2)){
                if(isEmailValid(correo)){
                    String path_total = correo+".jpg";
                    path_total = path_total.replace("@","_");

                    conexion = new Conexion();
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("email", correo);
                    jsonParam.put("name",nombre);
                    jsonParam.put("nickname",pseudo);
                    jsonParam.put("image",PATH_S3+path_total);
                    jsonParam.put("password", contra1);

                    String  result = conexion.execute(URL_HOST+USER_REGISTER,"POST",jsonParam.toString()).get();
                    if(result.equals("OK")){
                        mixpanel.track("Realiza registro existoso",null); //Realiza la actividad de mixpanel
                        mixpanel.flush();

                        uploadWithTransferUtility(path_total,path_portada);
                        infoMessageDialog("Se registro con exito.");
                        editTextCorreo.setText("");
                        editTextPseudo.setText("");
                        editTextContra1.setText("");
                        editTextNombre.setText("");
                        editTextContra2.setText("");
                        path_portada="";
                    }else{
                        infoMessageDialog("Problemas con el registro de usuario");
                    }
                }else
                    errorMessageDialog("Correo invalido.");
            }else
                errorMessageDialog("Las contraseñas no coinciden.");
        }else{
            errorMessageDialog("Para iniciar sesión debe de llenar todos los espacios.");
        }

    }

    // ------------------ Elegir imagen para mostrar portada ----------
    public void cargarImagenPerfil(){
        if(Build.VERSION.SDK_INT >=23) {
            if (checkPermission()){
                Intent intent = new Intent();
                intent.setType("*/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);

            }else{
                requestPermission();
            }
        }else{
            Intent intent = new Intent();
            intent.setType("*/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);

        }
    }

    public void tomarFoto() {
        String path_carpeta = Environment.getExternalStorageDirectory()+DIRECTORIO_IMAGEN;
        File miFile = new File(path_carpeta);
        //infoMessageDialog(path_carpeta);
        boolean isCreada=miFile.exists();
        if(isCreada==false){
            isCreada=miFile.mkdirs();
        }
        if(isCreada==true){
            Long consecutivo = System.currentTimeMillis()/1000;
            String nombre = consecutivo.toString()+".jpg";

            path_portada = path_carpeta+nombre;

            fileImagen=new File(path_portada);
            try {
                fileImagen.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Uri uri;
            //Abre la camara
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            //Validación de acuerdo al OS.
            if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.N) {
                uri = Uri.parse(path_portada);
            } else{
                uri = Uri.fromFile(new File(path_portada));
            }

            //Guarda la imagen
            intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
            //Retorna al activity
            startActivityForResult(intent,TOMADA_PICTURE);
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri filePath = data.getData();
                if (null != filePath) {
                    try {
                        //portada_pelicula.setImageURI(filePath);
                        path_portada = getFilePath(getApplicationContext(),filePath);
                        Log.d("PATH", filePath.getPath());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            /*
            if(requestCode==TOMADA_PICTURE){
                MediaScannerConnection.scanFile(this, new String[]{path_portada}, null,
                        new MediaScannerConnection.MediaScannerConnectionClient() {
                            @Override
                            public void onMediaScannerConnected() {

                            }

                            @Override
                            public void onScanCompleted(String s, Uri uri) {
                                Log.i("Path",s);
                            }
                        });
                bitmap= BitmapFactory.decodeFile(path_portada);
                //infoMessageDialog(path_portada);

            }*/
        }
    }

    @SuppressLint("NewApi")
    public static String getFilePath(Context context, Uri uri) throws URISyntaxException {
        String selection = null;
        String[] selectionArgs = null;
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(context.getApplicationContext(), uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("image".equals(type)) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                selection = "_id=?";
                selectionArgs = new String[]{
                        split[1]
                };
            }
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {
                    MediaStore.Images.Media.DATA
            };
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver()
                        .query(uri, projection, selection, selectionArgs, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE );
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestPermission() {
        requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    cargarImagenPerfil();
                } else {
                    if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        Toast.makeText(getApplicationContext(), "No se puede acceder a la imagenes sin permisos", Toast.LENGTH_SHORT).show();
                    }else{
                        new AlertDialog.Builder(getApplicationContext())
                                .setTitle("Acceso denegado")
                                .setMessage("No se puede acceder a la imagenes sin permisos. Por favor active los permisos en la configuración de la aplicación.")
                                .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // do nothing
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();//*/
                    }
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
            }
        }
    }

    public void uploadWithTransferUtility(String s3PathBucket, String filePathStorage) {

        TransferUtility transferUtility =
                TransferUtility.builder()
                        .context(getApplicationContext())
                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                        .s3Client(new AmazonS3Client(AWSMobileClient.getInstance().getCredentialsProvider()))
                        .build();

        TransferObserver uploadObserver =
                transferUtility.upload("uploads/" + s3PathBucket,new File(filePathStorage));

        // Attach a listener to the observer to get state update and progress notifications
        uploadObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    // Handle a completed upload.
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                int percentDone = (int)percentDonef;

                Log.d("YourActivity", "ID:" + id + " bytesCurrent: " + bytesCurrent
                        + " bytesTotal: " + bytesTotal + " " + percentDone + "%");
            }

            @Override
            public void onError(int id, Exception ex) {
                // Handle errors
            }

        });

        // If you prefer to poll for the data, instead of attaching a
        // listener, check for the state and progress in the observer.
        if (TransferState.COMPLETED == uploadObserver.getState()) {
            // Handle a completed upload.
        }

        Log.d("YourActivity", "Bytes Transferrred: " + uploadObserver.getBytesTransferred());
        Log.d("YourActivity", "Bytes Total: " + uploadObserver.getBytesTotal());
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

    //Validar formato de correo
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
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
