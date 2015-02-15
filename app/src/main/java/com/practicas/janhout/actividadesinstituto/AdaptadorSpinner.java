package com.practicas.janhout.actividadesinstituto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AdaptadorSpinner extends ArrayAdapter {

    private Context c;
    private List lista;

    public AdaptadorSpinner(Context context, int resource, List lista) {
        super(context, resource, lista);
        this.c = context;
        this.lista = lista;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        View fila = View.inflate(c, R.layout.detalle_hijo, null);
        TextView texto = (TextView) fila.findViewById(R.id.textoHijo);
        texto.setText(lista.get(position).toString());
        return fila;
    }
}