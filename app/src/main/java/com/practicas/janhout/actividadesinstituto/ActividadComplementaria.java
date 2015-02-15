package com.practicas.janhout.actividadesinstituto;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class ActividadComplementaria extends Actividad implements Serializable {

    private String fecha;
    private String lugarRealizacion;
    private String horaInicio;
    private String horaFinal;

    public ActividadComplementaria() {
        super();
    }

    public ActividadComplementaria(int id, String profesorOrganizador, String descripcion, String departamento, String grupo, String fecha, String lugarRealizacion, String horaInicio, String horaFinal) {
        super(id, profesorOrganizador, descripcion, departamento, grupo);
        this.fecha = fecha;
        this.lugarRealizacion = lugarRealizacion;
        this.horaInicio = horaInicio;
        this.horaFinal = horaFinal;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getLugarRealizacion() {
        return lugarRealizacion;
    }

    public void setLugarRealizacion(String lugarRealizacion) {
        this.lugarRealizacion = lugarRealizacion;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(String horaFinal) {
        this.horaFinal = horaFinal;
    }

    @Override
    public String toString() {
        return "ActividadComplementaria{" +
                "fecha='" + fecha + '\'' +
                ", lugarRealizacion='" + lugarRealizacion + '\'' +
                ", horaInicio='" + horaInicio + '\'' +
                ", horaFinal='" + horaFinal + '\'' + ", " +
                super.toString();
    }
}
