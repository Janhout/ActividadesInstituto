package com.practicas.janhout.actividadesinstituto;

import org.json.JSONException;
import org.json.JSONObject;

public class ActividadGrupo {

    private int id;
    private int idactividad;
    private int idgrupo;

    public ActividadGrupo(int id, int idactividad, int idgrupo) {
        this.id = id;
        this.idactividad = idactividad;
        this.idgrupo = idgrupo;
    }

    public ActividadGrupo(JSONObject object){
        try{
            this.id = Integer.parseInt(object.getString("id"));
            this.idactividad = Integer.parseInt(object.getString("idactividad"));
            this.idgrupo = Integer.parseInt(object.getString("idgrupo"));
        } catch (Exception e){}
    }

    public ActividadGrupo() {
    }

    public JSONObject getJSON(){
        JSONObject objetoJSON = new JSONObject();
        try{
            objetoJSON.put("id", id+"");
            objetoJSON.put("idactividad", idactividad+"");
            objetoJSON.put("idgrupo", idgrupo+"");
            return objetoJSON;
        }catch (JSONException e){
            return null;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdactividad() {
        return idactividad;
    }

    public void setIdactividad(int idactividad) {
        this.idactividad = idactividad;
    }

    public int getIdgrupo() {
        return idgrupo;
    }

    public void setIdgrupo(int idgrupo) {
        this.idgrupo = idgrupo;
    }
}
