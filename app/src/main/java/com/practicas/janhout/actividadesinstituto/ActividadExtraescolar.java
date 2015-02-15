package com.practicas.janhout.actividadesinstituto;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class ActividadExtraescolar extends Actividad implements Serializable{

    private String fechaSalida;
    private String fechaLLegada;
    private String horaSalida;
    private String horaLlegada;
    private String lugarSalida;
    private String lugarLlegada;

    public ActividadExtraescolar() {
        super();
    }

    public ActividadExtraescolar(int id, String profesorOrganizador, String descipcion, String departamento, String grupo, String fechaSalida, String fechaLLegada, String horaSalida, String horaLlegada, String lugarSalida, String lugarLlegada) {
        super(id, profesorOrganizador, descipcion, departamento, grupo);
        this.fechaSalida = fechaSalida;
        this.fechaLLegada = fechaLLegada;
        this.horaSalida = horaSalida;
        this.horaLlegada = horaLlegada;
        this.lugarSalida = lugarSalida;
        this.lugarLlegada = lugarLlegada;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(String fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getFechaLLegada() {
        return fechaLLegada;
    }

    public void setFechaLLegada(String fechaLLegada) {
        this.fechaLLegada = fechaLLegada;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getHoraLlegada() {
        return horaLlegada;
    }

    public void setHoraLlegada(String horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    public String getLugarSalida() {
        return lugarSalida;
    }

    public void setLugarSalida(String lugarSalida) {
        this.lugarSalida = lugarSalida;
    }

    public String getLugarLlegada() {
        return lugarLlegada;
    }

    public void setLugarLlegada(String lugarLlegada) {
        this.lugarLlegada = lugarLlegada;
    }

    @Override
    public String toString() {
        return "ActividadExtraescolar{" +
                "fechaSalida='" + fechaSalida + '\'' +
                ", fechaLLegada='" + fechaLLegada + '\'' +
                ", horaSalida='" + horaSalida + '\'' +
                ", horaLlegada='" + horaLlegada + '\'' +
                ", lugarSalida='" + lugarSalida + '\'' +
                ", lugarLlegada='" + lugarLlegada + '\'' + ", " +
                super.toString();
    }
}
