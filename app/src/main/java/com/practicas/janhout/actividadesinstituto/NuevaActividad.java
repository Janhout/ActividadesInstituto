package com.practicas.janhout.actividadesinstituto;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v4.app.NavUtils;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;


public class NuevaActividad extends Activity implements SelectorHora.EscuchadorSelectorHora,
        SelectorFecha.EscuchadorSelectorFecha {

    private static final int HORA_SALIDA = 1;
    private static final int FECHA_SALIDA = 2;
    private static final int HORA_LLEGADA = 3;
    private static final int FECHA_LLEGADA = 4;
    private static final int HORA_INICIO = 5;
    private static final int HORA_FINAL = 6;
    private static final int FECHA_ACTIVIDAD = 7;

    private Button botonHoraSalida;
    private Button botonFechaSalida;
    private Button botonHoraLlegada;
    private Button botonFechaLlegada;
    private Button botonHoraInicio;
    private Button botonHoraFinal;
    private Button botonFechaActividad;

    private RadioButton rbComplementaria;

    private EditText etLugarSalida;
    private EditText etLugarLlegada;
    private EditText etDescripcion;

    private Spinner spGrupo;
    private Spinner spProfesor;
    private Spinner spLugar;

    private ArrayList<Profesor> profesores;
    private ArrayList<Grupo> grupos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nueva_actividad);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setIcon(R.drawable.ic_launcher);
        int sw = this.getResources().getConfiguration().smallestScreenWidthDp;
        if(sw<600){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else{
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        inicializarBotones();
        inicializarTexto();
        inicializarSpinner();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("rb", rbComplementaria.isChecked());
        outState.putString("bhs", botonHoraSalida.getText().toString());
        outState.putString("bfs", botonFechaSalida.getText().toString());
        outState.putString("bfl", botonFechaLlegada.getText().toString());
        outState.putString("bhl", botonHoraLlegada.getText().toString());
        outState.putString("bfa", botonFechaActividad.getText().toString());
        outState.putString("bhi", botonHoraInicio.getText().toString());
        outState.putString("bhf", botonHoraFinal.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        rbComplementaria.setChecked(savedInstanceState.getBoolean("rb"));
        cambioActividad(null);
        botonHoraSalida.setText(savedInstanceState.getString("bhs"));
        botonFechaSalida.setText(savedInstanceState.getString("bfs"));
        botonFechaLlegada.setText(savedInstanceState.getString("bfl"));
        botonHoraLlegada.setText(savedInstanceState.getString("bhl"));
        botonFechaActividad.setText(savedInstanceState.getString("bfa"));
        botonHoraInicio.setText(savedInstanceState.getString("bhi"));
        botonHoraFinal.setText(savedInstanceState.getString("bhf"));
    }

    private void inicializarBotones() {
        botonHoraSalida = (Button) findViewById(R.id.botonHoraSalida);
        botonFechaSalida = (Button) findViewById(R.id.botonFechaSalida);
        botonFechaLlegada = (Button) findViewById(R.id.botonFechaLlegada);
        botonHoraLlegada = (Button) findViewById(R.id.botonHoraLlegada);
        botonFechaActividad = (Button) findViewById(R.id.botonFechaActividad);
        botonHoraInicio = (Button) findViewById(R.id.botonHoraInicio);
        botonHoraFinal = (Button) findViewById(R.id.botonHoraFinal);
        rbComplementaria = (RadioButton) findViewById(R.id.rbComplementaria);
    }

    private void inicializarTexto() {
        etDescripcion = (EditText) findViewById(R.id.etDescripcion);
        etLugarSalida = (EditText) findViewById(R.id.etSalida);
        etLugarLlegada = (EditText) findViewById(R.id.etLlegada);
    }

    private void inicializarSpinner() {
        spProfesor = (Spinner) findViewById(R.id.spinnerProfesor);
        spGrupo = (Spinner) findViewById(R.id.spinnerGrupo);
        spLugar = (Spinner) findViewById(R.id.spinnerLugar);

        ArrayAdapter<CharSequence> adaptadorG;
        ArrayAdapter<CharSequence> adaptadorL;
        ArrayAdapter<CharSequence> adaptadorP;
        Bundle b = getIntent().getExtras();
        profesores = (ArrayList<Profesor>)b.getSerializable("listaProf");
        grupos = (ArrayList<Grupo>)b.getSerializable("listaGrupo");
        int sw = this.getResources().getConfiguration().smallestScreenWidthDp;
        if(sw<600){
            adaptadorL = ArrayAdapter.createFromResource(this, R.array.lugares, android.R.layout.simple_spinner_item);
        }else{
            adaptadorL = ArrayAdapter.createFromResource(this, R.array.lugaresG, android.R.layout.simple_spinner_item);
        }
        if(profesores != null && grupos != null && profesores.size()>0 && grupos.size()>0) {
            adaptadorP = new AdaptadorSpinner(this, android.R.layout.simple_spinner_item, profesores);
            adaptadorG = new AdaptadorSpinner(this, android.R.layout.simple_spinner_item, grupos);
            spLugar.setAdapter(adaptadorL);
            spProfesor.setAdapter(adaptadorP);
            spGrupo.setAdapter(adaptadorG);
        } else {
            tostada(getString(R.string.error_datos));
            finish();
        }
    }

    public void dialogoHora(View v) {
        DialogFragment fragmento = null;
        switch (v.getId()) {
            case R.id.botonHoraSalida:
                fragmento = SelectorHora.newInstance(HORA_SALIDA);
                break;
            case R.id.botonHoraLlegada:
                fragmento = SelectorHora.newInstance(HORA_LLEGADA);
                break;
            case R.id.botonHoraInicio:
                fragmento = SelectorHora.newInstance(HORA_INICIO);
                break;
            case R.id.botonHoraFinal:
                fragmento = SelectorHora.newInstance(HORA_FINAL);
                break;
        }
        if (fragmento != null) {
            fragmento.show(getFragmentManager(), "timePicker");
        }
    }

    public void dialogoFecha(View v) {
        DialogFragment fragmento = null;
        switch (v.getId()) {
            case R.id.botonFechaSalida:
                fragmento = SelectorFecha.newInstance(FECHA_SALIDA);
                break;
            case R.id.botonFechaLlegada:
                fragmento = SelectorFecha.newInstance(FECHA_LLEGADA);
                break;
            case R.id.botonFechaActividad:
                fragmento = SelectorFecha.newInstance(FECHA_ACTIVIDAD);
                break;
        }
        if (fragmento != null) {
            fragmento.show(getFragmentManager(), "datePicker");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_nueva_actividad, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTimeSet(int id, TimePicker view, int hourOfDay, int minute) {
        String h = String.valueOf(hourOfDay);
        String m = String.valueOf(minute);
        if(h.length()==1){
            h="0"+h;
        }
        if(m.length()==1){
            m="0"+m;
        }
        String hora = h + ":" + m;
        switch (id) {
            case HORA_SALIDA:
                botonHoraSalida.setText(hora);
                break;
            case HORA_LLEGADA:
                botonHoraLlegada.setText(hora);
                break;
            case HORA_INICIO:
                botonHoraInicio.setText(hora);
                break;
            case HORA_FINAL:
                botonHoraFinal.setText(hora);
                break;
        }
    }

    @Override
    public void onDateSet(int id, DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String fecha;
        String an = String.valueOf(year);
        fecha = dayOfMonth + "/" + (monthOfYear+1) + "/" + an;

        switch (id) {
            case FECHA_SALIDA:
                botonFechaSalida.setText(fecha);
                break;
            case FECHA_LLEGADA:
                botonFechaLlegada.setText(fecha);
                break;
            case FECHA_ACTIVIDAD:
                botonFechaActividad.setText(fecha);
                break;
        }
    }

    public void cambioActividad(View v) {
        LinearLayout lc = (LinearLayout) findViewById(R.id.layoutComplementaria);
        LinearLayout le = (LinearLayout) findViewById(R.id.layoutExtra);
        if (rbComplementaria.isChecked()) {
            lc.setVisibility(View.VISIBLE);
            le.setVisibility(View.GONE);
        } else {
            le.setVisibility(View.VISIBLE);
            lc.setVisibility(View.GONE);
        }
    }

    public void devolverActividad(View v) {
        Actividad a;
        if (rbComplementaria.isChecked()) {
            a = creaComplementaria();
        } else {
            a = creaExtraescolar();
        }
        if(a!=null) {
            Intent i = new Intent();
            i.putExtra("actividad", a);
            setResult(Activity.RESULT_OK, i);
            finish();
        }
    }

    private void tostada(String s){
        Toast.makeText(getApplicationContext(), s,Toast.LENGTH_SHORT).show();
    }

    private ActividadComplementaria creaComplementaria() {
        String profesor;
        String grupo;
        String departamento = null;
        String descripcion;
        String lugar;
        String horaInicio;
        String horaFin;
        String fechaActividad;

        profesor = (spProfesor.getSelectedItem().toString());
        for(int i = 0; i<profesores.size() && departamento == null; i++){
            if(profesores.get(i).getNombreCompleto().equals(profesor)){
                departamento = profesores.get(i).getDepartamento();
            }
        }

        grupo = (spGrupo.getSelectedItem().toString());

        if(!etDescripcion.getText().toString().equals("")){
            descripcion = etDescripcion.getText().toString();
        }else{
            tostada(getString(R.string.error_descripcion));
            return null;
        }
        if (!((String) spLugar.getSelectedItem()).equals("")
                && !((String) spLugar.getSelectedItem()).equals(getString(R.string.lugar_realizacion))) {
            lugar = ((String) spLugar.getSelectedItem());
        } else {
            tostada(getString(R.string.error_lugar));
            return null;
        }
        if(botonHoraInicio.getText().toString().contains(":")){
            horaInicio = botonHoraInicio.getText().toString();
        }else{
            tostada(getString(R.string.error_hora_inicio));
            return null;
        }
        if(!botonFechaActividad.getText().toString().contains("a")){
            fechaActividad = botonFechaActividad.getText().toString();
        }else{
            tostada(getString(R.string.error_fecha_actividad));
            return null;
        }
        if(!botonHoraFinal.getText().toString().contains("a")){
            horaFin = botonHoraFinal.getText().toString();
        }else{
            horaFin = "";
        }
        return new ActividadComplementaria(1, profesor, descripcion, departamento, grupo, fechaActividad, lugar, horaInicio, horaFin);
    }

    private ActividadExtraescolar creaExtraescolar(){
        String profesor;
        String grupo;
        String departamento = null;
        String descripcion;
        String lugarSalida;
        String lugarLlegada;
        String horaSalida;
        String horaLlegada;
        String fechaSalida;
        String fechaLlegada;

        profesor = (spProfesor.getSelectedItem().toString());
        for(int i = 0; i<profesores.size() && departamento == null; i++){
            if(profesores.get(i).getNombreCompleto().equals(profesor)){
                departamento = profesores.get(i).getDepartamento();
            }
        }

        grupo = (spGrupo.getSelectedItem().toString());
        if(!etDescripcion.getText().toString().equals("")){
            descripcion = etDescripcion.getText().toString();
        }else{
            tostada(getString(R.string.error_descripcion));
            return null;
        }
        if(!etLugarSalida.getText().toString().equals("")){
            lugarSalida = etLugarSalida.getText().toString();
        }else{
            tostada(getString(R.string.error_lugar_salida));
            return null;
        }
        if(!etLugarLlegada.getText().toString().equals("")){
            lugarLlegada = etLugarLlegada.getText().toString();
        }else{
            tostada(getString(R.string.error_lugar_llegada));
            return null;
        }
        if(!botonHoraSalida.getText().toString().contains("a")){
            horaSalida = botonHoraSalida.getText().toString();
        }else{
            tostada(getString(R.string.error_hora_salida));
            return null;
        }
        if(!botonHoraLlegada.getText().toString().contains("a")){
            horaLlegada = botonHoraLlegada.getText().toString();
        }else{
            tostada(getString(R.string.error_hora_llegada));
            return null;
        }
        if(!botonFechaSalida.getText().toString().contains("a")){
            fechaSalida = botonFechaSalida.getText().toString();
        }else{
            tostada(getString(R.string.error_fecha_salida));
            return null;
        }
        if(!botonFechaLlegada.getText().toString().contains("a")){
            fechaLlegada = botonFechaLlegada.getText().toString();
        }else{
            tostada(getString(R.string.error_fecha_llegada));
            return null;
        }

        return new ActividadExtraescolar(1, profesor, descripcion, departamento, grupo, fechaSalida, fechaLlegada,
                horaSalida, horaLlegada, lugarSalida, lugarLlegada);
    }
}