package com.example.bryan.minnanokanji;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.crashlytics.android.Crashlytics;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import io.fabric.sdk.android.Fabric;

public class menuJapon extends AppCompatActivity {
    private MixpanelAPI mixpanel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_japon);
        getSupportActionBar().setTitle("Acerca de Japón");
        Fabric.with(this, new Crashlytics());
        mixpanel = MixpanelAPI.getInstance(this,"4b4d6b196066ba9a609c13fb5a11360c");
        mixpanel.track("Ventana de Acerca de Japón",null); //Realiza la actividad de mixpanel
        mixpanel.flush();

        Button btnCuriosidades = (Button) findViewById(R.id.btnCuriosidad);
        Button btnConvertidor= (Button) findViewById(R.id.btnConvertidor);
        Button btnEmbajada= (Button) findViewById(R.id.btnEmbajada);

        btnCuriosidades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(menuJapon.this, Curiosidades.class);
                startActivity(intent);
            }
        });

        btnConvertidor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(menuJapon.this, Convertidor.class);
                startActivity(intent);
            }
        });

        btnEmbajada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(menuJapon.this, WebPage.class);
                startActivity(intent);
            }
        });
    }
}
