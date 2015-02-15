package com.practicas.janhout.actividadesinstituto;

import android.content.Context;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdaptadorLista extends BaseExpandableListAdapter {

    private ArrayList<Actividad> lista;
    private ArrayList<Date> padres; //fecha 1, 2, 3
    private ArrayList<ArrayList<Actividad>> hijos; //en la posicion 1, todas las actividades de fecha 1
    private Context c;

    public AdaptadorLista(Context c, ArrayList<Actividad> lista) {
        this.c = c;
        this.lista = lista;
        buscarCambios();
    }

    @Override
    public int getGroupCount() {
        return padres.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int i = 0;
        try {
            i = hijos.get(groupPosition).size();
        } catch (Exception e) {
        }
        return i;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return padres.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return hijos.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void notifyDataSetChanged() {
        buscarCambios();
        super.notifyDataSetChanged();

    }

    private void buscarCambios() {
        this.padres = new ArrayList<Date>();
        this.hijos = new ArrayList<ArrayList<Actividad>>();
        for (int i = 0; i < lista.size(); i++) {
            Date fecha = null;
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formato2 = new SimpleDateFormat("MM/yyyy");
            try {
                if (lista.get(i).getClass().getName().equals(ActividadComplementaria.class.getName())) {
                    fecha = formato.parse(((ActividadComplementaria) lista.get(i)).getFecha());
                } else {
                    fecha = formato.parse(((ActividadExtraescolar) lista.get(i)).getFechaSalida());
                }
            } catch (ParseException e) {
            }
            int pos = -1;
            boolean encontrado = false;
            String b = formato2.format(fecha);
            for (int j = 0; j < padres.size() && !encontrado; j++) {
                String a = formato2.format(padres.get(j));
                if (a.equals(b)) {
                    encontrado = true;
                    pos = j;
                }
            }
            if (!encontrado) {
                padres.add(fecha);
                hijos.add(new ArrayList<Actividad>());
                pos = padres.size() - 1;
            }
            hijos.get(pos).add(lista.get(i));
        }
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View padre = View.inflate(c, R.layout.detalle_padre, null);
        TextView textView = (TextView) padre.findViewById(R.id.textoPadre);
        SimpleDateFormat formato = new SimpleDateFormat("MMMM (yyyy)");

        Date a = (Date) getGroup(groupPosition);
        textView.setText(formato.format(a).toUpperCase());

        return padre;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View hijo = View.inflate(c, R.layout.detalle_hijo, null);
        TextView textView = (TextView) hijo.findViewById(R.id.textoHijo);
        textView.setPadding(20, 0, 0, 0);
        textView.setText(((Actividad) getChild(groupPosition, childPosition)).getDescripcion());

        return hijo;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
