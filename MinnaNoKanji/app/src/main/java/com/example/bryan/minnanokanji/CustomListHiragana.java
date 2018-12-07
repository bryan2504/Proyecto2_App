package com.example.bryan.minnanokanji;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by gerald on 09/06/18.
 */

public class CustomListHiragana extends ArrayAdapter{
    private final Activity context;
    private String[] Simbolos;
    private String[] Imagenes;

    public CustomListHiragana(Activity context,String[] Simbolos,String[] Imagenes){
        super(context, R.layout.item_lista_hiragana,Simbolos);
        this.context = context;
        this.Simbolos = Simbolos;
        this.Imagenes = Imagenes;

    }
    /*
    * Metodo que carga los datos al elemento del listview
    * */
    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.item_lista_hiragana, null, true);
        TextView lbRomaji = rowView.findViewById(R.id.textHiragana);
        ImageView imageViewSimbolo = rowView.findViewById(R.id.imageViewHiragana);

        lbRomaji.setText(Simbolos[position]);

        lbRomaji.setTag(position);
        imageViewSimbolo.setTag(position);

        if (!TextUtils.isEmpty(this.Imagenes[position])) {
            Picasso.with(this.getContext()).load(this.Imagenes[position]).into(imageViewSimbolo);
        }


        return rowView;
    }
}
