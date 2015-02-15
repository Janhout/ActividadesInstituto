package com.practicas.janhout.actividadesinstituto;

import java.io.Serializable;

public class Actividad implements Serializable {

    private int id;
    private String profesorOrganizador;
    private String descripcion;
    private String departamento;
    private String grupo;

    public Actividad() {
    }

    public Actividad(int id, String profesorOrganizador, String descripcion, String departamento, String grupo) {
        this.id = id;
        this.profesorOrganizador = profesorOrganizador;
        this.descripcion = descripcion;
        this.departamento = departamento;
        this.grupo = grupo;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public String getProfesorOrganizador() {
        return profesorOrganizador;
    }

    public void setProfesorOrganizador(String profesorOrganizador) {
        this.profesorOrganizador = profesorOrganizador;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescipcion(String descipcion) {
        this.descripcion = descipcion;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    @Override
    public String toString() {
        return
                "id=" + id +
                ", profesorOrganizador='" + profesorOrganizador + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", departamento='" + departamento + '\'' +
                ", grupo=" + grupo +
                '}';
    }
}
