package com.example.bryan.minnanokanji;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class MenuPrincipal extends AppCompatActivity {

    public String resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Fabric.with(this, new Crashlytics());

        Button btnKanji = (Button) findViewById(R.id.btnKanji);
        Button btnKatakana = (Button) findViewById(R.id.btnKatakana);
        Button btnHiragana = (Button) findViewById(R.id.btnHiragana);
        Button btnJapon = (Button) findViewById(R.id.btnJapon);


        btnKanji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MenuPrincipal.this, menuKanji.class);
                startActivity(intent);
            }
        });

        btnKatakana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MenuPrincipal.this, menuKatakana.class);
                startActivity(intent);
            }
        });

        btnHiragana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MenuPrincipal.this, menuHiragana.class);
                startActivity(intent);
            }
        });

        btnJapon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MenuPrincipal.this, menuJapon.class);
                startActivity(intent);
            }
        });

        resultado = getIntent().getExtras().getString("datos");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Intent intent;
        switch (item.getItemId()) {
            case R.id.Ajustes:
                intent= new Intent(this, ajustesCuenta.class);
                intent.putExtra("datos",resultado);
                startActivity(intent);
                return true;
            case R.id.Acerca:
                intent = new Intent(this, acercaDe.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
