package com.example.bryan.minnanokanji;

import android.app.Activity;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
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
//yourTextView.setMovementMethod(new ScrollingMovementMethod());
public class CustomListKanji extends ArrayAdapter{
    private final Activity context;
    private String[] Numeros;
    private String[] Significado;
    private String[] ImagenKanji;
    private String[] ImagenTrazos;
    private String[] Explicacion;
    private String[] Extra;

    public CustomListKanji(Activity context,String[] Numeros,String[] Significado,String[] ImagenKanji,String[] ImagenTrazos,String[] Explicacion,String[] Extra){
        super(context, R.layout.item_lista_kanji,Numeros);
        this.context = context;

        this.Numeros = Numeros;
        this.Significado = Significado;
        this.ImagenKanji = ImagenKanji;
        this.ImagenTrazos = ImagenTrazos;
        this.Explicacion = Explicacion;
        this.Extra = Extra;
    }
    /*
    * Metodo que carga los datos al elemento del listview
    * */
    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.item_lista_kanji, null, true);

        TextView lbNumero = rowView.findViewById(R.id.textNumeroKanji);
        TextView lbSignificado = rowView.findViewById(R.id.textKanji);
        TextView lbExplicacion = rowView.findViewById(R.id.textKanjiDescripcion);
        TextView lbExtra = rowView.findViewById(R.id.textExtra);

        ImageView imageViewSimbolo = rowView.findViewById(R.id.imageViewKanji);
        ImageView imageViewTrazos = rowView.findViewById(R.id.imageViewTrazos);

        lbNumero.setText(Numeros[position]);
        lbSignificado.setText(Significado[position]);
        lbExplicacion.setText(Explicacion[position]);
        lbExplicacion.setMovementMethod(new ScrollingMovementMethod());
        lbExtra.setText(Extra[position]);
        lbExtra.setMovementMethod(new ScrollingMovementMethod());

        lbNumero.setTag(position);
        lbSignificado.setTag(position);
        lbExplicacion.setTag(position);
        lbExtra.setTag(position);
        imageViewSimbolo.setTag(position);
        imageViewTrazos.setTag(position);

        if (!TextUtils.isEmpty(this.ImagenKanji[position])) {
            Picasso.with(this.getContext()).load(this.ImagenKanji[position]).into(imageViewSimbolo);
        }
        if (!TextUtils.isEmpty(this.ImagenTrazos[position])) {
            Picasso.with(this.getContext()).load(this.ImagenTrazos[position]).into(imageViewTrazos);
        }


        return rowView;
    }
}
