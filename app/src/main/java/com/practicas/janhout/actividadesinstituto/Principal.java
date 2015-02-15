package com.practicas.janhout.actividadesinstituto;


import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;


public class Principal extends Activity implements FragmentoLista.EscuchadorLista {

    private boolean hayDetalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayUseLogoEnabled(true);
        int sw = this.getResources().getConfiguration().smallestScreenWidthDp;
        if(sw<600){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else{
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        FragmentoLista fragmentoLista =(FragmentoLista)getFragmentManager().findFragmentById(R.id.fragmentoLista);
        fragmentoLista.setEscuchadorLista(this);
        FragmentoDetalle f2 = (FragmentoDetalle)getFragmentManager().findFragmentById(R.id.fragmentoDetalle);
        hayDetalle = (f2 != null && f2.isInLayout());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.agregar:
                FragmentoLista f = (FragmentoLista)getFragmentManager().findFragmentById(R.id.fragmentoLista);
                f.nuevaActividad();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActividadSeleccionada(Actividad i) {
        if(hayDetalle) {
            ((FragmentoDetalle)getFragmentManager().findFragmentById(R.id.fragmentoDetalle)).mostrarDetalle(i);
        }
        else {
            Intent intent = new Intent(this, DatosDetalle.class);
            intent.putExtra("actividad", i);
            startActivity(intent);
        }
    }
}
