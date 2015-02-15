package com.practicas.janhout.actividadesinstituto;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Profesor implements Serializable{

    private int id;
    private String nombre;
    private String apellidos;
    private String departamento;

    public Profesor() {
    }

    public Profesor(int id, String nombre, String apellidos, String departamento) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.departamento = departamento;
    }

    public Profesor(JSONObject object){
        try{
            this.id = Integer.parseInt(object.getString("id"));
            this.nombre = object.getString("nombre");
            this.apellidos = object.getString("apellidos");
            this.departamento = object.getString("departamento");
        } catch (Exception e){}
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getNombreCompleto(){
        return nombre + " " + apellidos;
    }

    @Override
    public String toString() {
        return getNombreCompleto();
    }
}
