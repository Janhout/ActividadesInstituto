package com.practicas.janhout.actividadesinstituto;

import org.json.JSONObject;

import java.io.Serializable;

public class Grupo implements Serializable{

    private int id;
    private String grupo;

    public Grupo() {
    }

    public Grupo(int id, String grupo) {
        this.id = id;
        this.grupo = grupo;
    }

    public Grupo(JSONObject object){
        try{
            this.id = Integer.parseInt(object.getString("id"));
            this.grupo = object.getString("grupo");
        } catch (Exception e){}
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    @Override
    public String toString() {
        return grupo;
    }
}
