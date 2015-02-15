package com.practicas.janhout.actividadesinstituto;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class FragmentoLista extends Fragment {

    private ArrayList<Actividad> listaActividades;
    private ArrayList<Profesor> listaProfesores;
    private ArrayList<Grupo> listaGrupos;
    private ArrayList<ActividadGrupo> listaActGrup;
    private ExpandableListView elvActividades;
    private AdaptadorLista elvad;

    private static final int NUEVA_ACTIVIDAD = 1;
    private final static String URLBASE = "http://ieszv.x10.bz/restful/api/";
    private final static String USUARIO = "rafa";

    private EscuchadorLista escuchador;

    /**********************************************************************************/
    /****CONSTRUCTOR****/
    /**********************************************************************************/

    public FragmentoLista() {
    }

    /**********************************************************************************/
    /****MÉTODOS ON....****/
    /**********************************************************************************/

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        elvActividades.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (escuchador != null) {
                    escuchador.onActividadSeleccionada((Actividad) elvad.getChild(groupPosition, childPosition));
                }
                return true;
            }
        });
        registerForContextMenu(elvActividades);
        cargarDatos();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == NUEVA_ACTIVIDAD){
                Actividad actividad = (Actividad)data.getSerializableExtra("actividad");
                agregarActividad(actividad);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        ExpandableListView.ExpandableListContextMenuInfo info =
                (ExpandableListView.ExpandableListContextMenuInfo) item.getMenuInfo();

        int groupPosition = ExpandableListView.getPackedPositionGroup(info.packedPosition);
        int childPosition = ExpandableListView.getPackedPositionChild(info.packedPosition);

        if (id == R.id.contextual_borrar) {
            borrarActividad(listaActividades.indexOf(elvad.getChild(groupPosition, childPosition)));
        } /*else if (id == R.id.contextual_editar) {
            editarActividad(listaActividades.indexOf(elvad.getChild(groupPosition, childPosition)));
        }*/

        return super.onContextItemSelected(item);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        ExpandableListView.ExpandableListContextMenuInfo info =
                (ExpandableListView.ExpandableListContextMenuInfo) menuInfo;

        int type = ExpandableListView.getPackedPositionType(info.packedPosition);
        if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.menu_contextual,menu);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_fragmento_lista, container, false);
        listaActividades = new ArrayList<>();
        listaProfesores = new ArrayList<>();
        listaActGrup = new ArrayList<>();
        listaGrupos = new ArrayList<>();
        elvActividades = (ExpandableListView)v.findViewById(R.id.elvActividades);
        elvad = new AdaptadorLista(getActivity(), listaActividades);
        elvActividades.setAdapter(elvad);
        return v;
    }

    /**********************************************************************************/
    /****METODOS CRUD****/
    /**********************************************************************************/

    private void agregarActividad(Actividad actividad){
        ParametrosPost pp = new ParametrosPost();
        pp.url = URLBASE + "actividad";
        pp.json = getJSON(actividad);
        if(pp.json != null) {
            PostRestfull post = new PostRestfull(actividad, null);
            post.execute(pp);
        }
    }

    public void borrarActividad(int index){
        String[] peticiones = new String[3];
        int id = listaActividades.get(index).getId();
        peticiones[0] = "actividad/" + id;
        peticiones[1] = "actividadprofesor/" + id;
        peticiones[2] = "actividadgrupo/" + id;

        DeleteRestfull del = new DeleteRestfull(index);
        del.execute(peticiones);
    }

    private void cargarDatos(){
        String[] peticiones = new String[4];
        peticiones[0] = "actividad/" + USUARIO;
        peticiones[1] = "profesor";
        peticiones[2] = "grupo";
        peticiones[3] = "actividadgrupo";

        GetRestfull get = new GetRestfull();
        get.execute(peticiones);
    }

    public void editarActividad(int index){

    }

    /**********************************************************************************/
    /****AUXILIARES****/
    /**********************************************************************************/

    private void actualizarLista(){
        Collections.sort(listaActividades, new OrdenarActividad());
        elvad.notifyDataSetChanged();
    }

    public JSONObject getJSON(Actividad actividad){
        JSONObject objetoJSON = new JSONObject();
        String profesor = "-1";
        String tipo;
        String fechai;
        String fechaf;
        String lugari;
        String lugarf;
        for(int i = 0; i<listaProfesores.size() && profesor.equals("-1"); i++){
            if(listaProfesores.get(i).getNombreCompleto().equals(actividad.getProfesorOrganizador())){
                profesor = listaProfesores.get(i).getId()+"";
            }
        }
        if(profesor.equals(-1) && listaProfesores.size()>0){
            listaProfesores.get(listaProfesores.size()-1).getId();
        }
        if(actividad.getClass().toString().contains("escolar")){
            tipo = "extraescolar";
        }else{
            tipo = "complementaria";
        }
        if(tipo.equals("complementaria")){
            ActividadComplementaria tmp = (ActividadComplementaria)actividad;
            String[] f = tmp.getFecha().split("/");
            String fecha = f[2] + "-" + f[1] + "-" + f[0];
            fechaf = fecha + " " + tmp.getHoraFinal();
            fechai = fecha + " " + tmp.getHoraInicio();
            lugari = tmp.getLugarRealizacion();
            lugarf = tmp.getLugarRealizacion();
        } else{
            ActividadExtraescolar tmp2 = (ActividadExtraescolar)actividad;
            String[] f = tmp2.getFechaLLegada().split("/");
            String fecha = f[2] + "-" + f[1] + "-" + f[0];
            f = tmp2.getFechaSalida().split("/");
            String fecha2 = f[2] + "-" + f[1] + "-" + f[0];
            fechaf = fecha + " " + tmp2.getHoraLlegada();
            fechai = fecha2+ " " + tmp2.getHoraSalida();
            lugari = tmp2.getLugarSalida();
            lugarf = tmp2.getLugarLlegada();
        }

        try{
            objetoJSON.put("id", actividad.getId()+"");
            objetoJSON.put("idprofesor", profesor);
            objetoJSON.put("tipo", tipo);
            objetoJSON.put("fechai", fechai);
            objetoJSON.put("fechaf", fechaf);
            objetoJSON.put("lugari", lugari);
            objetoJSON.put("lugarf", lugarf);
            objetoJSON.put("descripcion", actividad.getDescripcion());
            objetoJSON.put("alumno", USUARIO);
            return objetoJSON;
        }catch (JSONException e){
            return null;
        }
    }

    public void nuevaActividad(){
        Intent i = new Intent(getActivity(), NuevaActividad.class);
        Bundle b = new Bundle();
        b.putSerializable("listaGrupo", listaGrupos);
        b.putSerializable("listaProf", listaProfesores);
        i.putExtras(b);
        startActivityForResult(i, NUEVA_ACTIVIDAD);
    }

    public void setEscuchadorLista(EscuchadorLista escuchador) {
        this.escuchador=escuchador;
    }

    /**********************************************************************************/
    /****Interfaz Escuchador****/
    /**********************************************************************************/

    public interface EscuchadorLista{
        public void onActividadSeleccionada(Actividad i);
    }


    /**********************************************************************************/
    /****Clase Comparator****/
    /**********************************************************************************/

    private class OrdenarActividad implements Comparator<Actividad> {
        public int compare(Actividad o1, Actividad o2) {
            Date fecha1 = null;
            Date fecha2 = null;
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            try{
                if(o1.getClass().getName().equals(ActividadComplementaria.class.getName())){
                    fecha1 = formato.parse(((ActividadComplementaria) o1).getFecha());
                }else{
                    fecha1 = formato.parse(((ActividadExtraescolar) o1).getFechaSalida());
                }

                if(o2.getClass().getName().equals(ActividadComplementaria.class.getName())){
                    fecha2 = formato.parse(((ActividadComplementaria) o2).getFecha());
                }else{
                    fecha2 = formato.parse(((ActividadExtraescolar) o2).getFechaSalida());
                }
            }catch (ParseException e){
            }
            return fecha1.compareTo(fecha2);
        }
    }


    /**********************************************************************************/
    /****Hilos REST GET****/
    /**********************************************************************************/

    private class GetRestfull extends AsyncTask<String, Void, String[]> {

        public GetRestfull(){
        }

        @Override
        protected String[] doInBackground(String[] params) {
            String[] r = new String[params.length];
            int cont = 0;
            for(String s:params){
                r[cont] = ClienteRestFul.get(URLBASE + s);
                cont++;
            }
            return r;
        }

        @Override
        protected void onPostExecute(String[] r) {
            super.onPostExecute(r);
            cargarActividadGrupo(new JSONTokener(r[3]));
            cargarGrupos(new JSONTokener(r[2]));
            cargarProfesores(new JSONTokener(r[1]));
            cargarActividades(new JSONTokener(r[0]));
            actualizarLista();
        }

        public void cargarActividades(JSONTokener token){
            try{
                JSONArray array = new JSONArray(token);
                for(int i = 0; i<array.length(); i++){
                    JSONObject object = array.getJSONObject(i);
                    try{
                        int id = Integer.parseInt(object.getString("id"));
                        int idProfesor = Integer.parseInt(object.getString("idprofesor"));
                        String tipo = object.getString("tipo");
                        String fechaI = object.getString("fechai");
                        String fechaF = object.getString("fechaf");
                        String lugarI = object.getString("lugari");
                        String lugarF = object.getString("lugarf");
                        String descripcion = object.getString("descripcion");
                        String alumno = object.getString("alumno");


                        Actividad a;
                        if(tipo.equals("complementaria")){
                            a = new ActividadComplementaria(id, buscarProfesor(idProfesor),
                                    descripcion, buscarDepartamento(idProfesor),
                                    buscarGrupo(id), buscarFecha(fechaI),
                                    lugarI, buscarHora(fechaI), buscarHora(fechaF));
                        } else{
                            a = new ActividadExtraescolar(id, buscarProfesor(idProfesor),
                                    descripcion, buscarDepartamento(idProfesor),
                                    buscarGrupo(id), buscarFecha(fechaI), buscarFecha(fechaF),
                                    buscarHora(fechaI), buscarHora(fechaF), lugarI, lugarF);
                        }
                        listaActividades.add(a);
                    } catch (Exception e){}
                }
                actualizarLista();
            }catch (JSONException e){
            }
        }

        public void cargarGrupos(JSONTokener token){
            try{
                JSONArray array = new JSONArray(token);
                for(int i = 0; i<array.length(); i++){
                    JSONObject objeto = array.getJSONObject(i);
                    Grupo grupo = new Grupo(objeto);
                    listaGrupos.add(grupo);
                }
            }catch (JSONException e){
            }
        }

        public void cargarProfesores(JSONTokener token){
            try{
                JSONArray array = new JSONArray(token);
                for(int i = 0; i<array.length(); i++){
                    JSONObject objeto = array.getJSONObject(i);
                    Profesor profesor = new Profesor(objeto);
                    listaProfesores.add(profesor);
                }
            }catch (JSONException e){
            }
        }

        public void cargarActividadGrupo(JSONTokener token){
            try{
                JSONArray array = new JSONArray(token);
                for(int i = 0; i<array.length(); i++){
                    JSONObject objeto = array.getJSONObject(i);
                    ActividadGrupo actGrup = new ActividadGrupo(objeto);
                    listaActGrup.add(actGrup);
                }
            }catch (JSONException e){
            }
        }

        public String buscarProfesor(int id){
            for (int i = 0; i < listaProfesores.size(); i++) {
                if(listaProfesores.get(i).getId()==id){
                    return listaProfesores.get(i).getNombreCompleto();
                }
            }
            return "";
        }

        public String buscarDepartamento(int id){
            for (int i = 0; i < listaProfesores.size(); i++) {
                if(listaProfesores.get(i).getId()==id){
                    return listaProfesores.get(i).getDepartamento();
                }
            }
            return "";
        }

        public String buscarFecha(String cadena){
            String c = cadena.substring(0, cadena.indexOf(" "));
            String[] res = c.split("-");
            return res[2]+"/"+res[1]+"/"+res[0];
        }

        public String buscarHora(String cadena){
            String c = cadena.substring(cadena.indexOf(" ")+1);
            return c;
        }

        public String buscarGrupo(int id){
            for (int i = 0; i < listaActGrup.size(); i++) {
                if(listaActGrup.get(i).getIdactividad()==id){
                    for (int j = 0; j < listaGrupos.size(); j++) {
                        if(listaGrupos.get(j).getId() == listaActGrup.get(i).getIdgrupo()){
                            return listaGrupos.get(j).getGrupo();
                        }
                    }
                    return "";
                }
            }
            return "";
        }
    }

    /**********************************************************************************/
    /****Hilos REST POST****/
    /**********************************************************************************/

    static class ParametrosPost{
        String url;
        JSONObject json;
    }

    private class PostRestfull extends AsyncTask<ParametrosPost, Void, String> {

        private Actividad a;
        private ActividadGrupo actgr;

        public PostRestfull(Actividad a, ActividadGrupo actgr){
            this.actgr = actgr;
            this.a = a;
        }

        @Override
        protected String doInBackground(ParametrosPost[] params) {
            String r = ClienteRestFul.post(params[0].url, params[0].json);
            return r;
        }

        @Override
        protected void onPostExecute(String r) {
            super.onPostExecute(r);
            if(a != null) {
                try {
                    JSONObject ob = new JSONObject(r);
                    String id = ob.getString("r");
                    if(Integer.valueOf(id)>0) {
                        a.setId(Integer.valueOf(id));
                        listaActividades.add(a);
                        agregarActividadGrupo(a.getId(), a.getGrupo());
                        actualizarLista();
                    }
                } catch (JSONException e) {
                }
            }else{
                listaActGrup.add(actgr);
            }
        }

        public void agregarActividadGrupo(int idAct, String grupo){
            ParametrosPost pp = new ParametrosPost();
            int idGrupo = -1;
            for (int i = 0; i < listaGrupos.size() && idGrupo==-1; i++) {
                if(listaGrupos.get(i).getGrupo().equals(grupo)){
                    idGrupo = listaGrupos.get(i).getId();
                }
            }
            if (idGrupo == -1){
                idGrupo =1;
            }
            pp.url = URLBASE + "actividadgrupo";
            ActividadGrupo acgr = new ActividadGrupo(1, idAct, idGrupo);
            pp.json = acgr.getJSON();
            if(pp.json != null) {
                PostRestfull post = new PostRestfull(null, acgr);
                post.execute(pp);
            }
        }
    }

    /**********************************************************************************/
    /****Hilos REST DELETE****/
    /**********************************************************************************/

    private class DeleteRestfull extends AsyncTask<String, Void, String> {

        private int index;

        public DeleteRestfull(int index){
            this.index = index;
        }

        @Override
        protected String doInBackground(String... params) {
            String r = ClienteRestFul.delete(URLBASE + params[0]);
            return r;
        }

        @Override
        protected void onPostExecute(String r) {
            super.onPostExecute(r);
            listaActividades.remove(index);
            actualizarLista();
        }
    }

}