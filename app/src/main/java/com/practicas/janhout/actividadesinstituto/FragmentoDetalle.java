package com.practicas.janhout.actividadesinstituto;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentoDetalle extends Fragment {

    public FragmentoDetalle() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fragmento_detalle, container, false);
        LinearLayout lc = (LinearLayout) v.findViewById(R.id.detallesComplementaria);
        lc.setVisibility(View.GONE);
        return v;
    }

    public void mostrarDetalle(Actividad actividad) {
        LinearLayout lc = (LinearLayout) getView().findViewById(R.id.detallesComplementaria);
        LinearLayout le = (LinearLayout) getView().findViewById(R.id.detallesExtra);
        TextView tvProfesor = (TextView)getView().findViewById(R.id.profOrg);
        TextView tvGrupo = (TextView)getView().findViewById(R.id.grupPart);
        TextView tvDescripcion = (TextView)getView().findViewById(R.id.desc);
        TextView tvDepartamento = (TextView)getView().findViewById(R.id.depOrg);
        TextView tvtipo = (TextView)getView().findViewById(R.id.tipo);

        tvProfesor.setText(actividad.toString());
        tvProfesor.setText(actividad.getProfesorOrganizador());
        tvGrupo.setText(actividad.getGrupo());
        tvDescripcion.setText(actividad.getDescripcion());
        tvDepartamento.setText(actividad.getDepartamento());
        if (actividad.getClass().toString().contains("escolar")) {
            le.setVisibility(View.VISIBLE);
            lc.setVisibility(View.GONE);
            tvtipo.setText(getActivity().getString(R.string.extraT));
            mostrarDetalleExtra((ActividadExtraescolar) actividad);
        } else {
            lc.setVisibility(View.VISIBLE);
            le.setVisibility(View.GONE);
            tvtipo.setText(getActivity().getString(R.string.complementariaT));
            mostrarDetalleComplemetaria((ActividadComplementaria) actividad);
        }
    }

    private void mostrarDetalleExtra(ActividadExtraescolar act){
        TextView tvFechaLlegada = (TextView)getView().findViewById(R.id.fechLleg);
        TextView tvFechaSalida = (TextView)getView().findViewById(R.id.fechSal);
        TextView tvLugarSalida = (TextView)getView().findViewById(R.id.lugSal);
        TextView tvLugarLlegada = (TextView)getView().findViewById(R.id.lugLleg);
        TextView tvHoraLlegada = (TextView)getView().findViewById(R.id.hLleg);
        TextView tvHoraSalida = (TextView)getView().findViewById(R.id.hSal);
        tvFechaLlegada.setText(act.getFechaLLegada());
        tvFechaSalida.setText(act.getFechaSalida());
        tvLugarSalida.setText(act.getLugarSalida());
        tvLugarLlegada.setText(act.getLugarLlegada());
        tvHoraLlegada.setText(act.getHoraLlegada());
        tvHoraSalida.setText(act.getHoraSalida());
    }

    private void mostrarDetalleComplemetaria(ActividadComplementaria act){
        TextView tvFechaRel = (TextView)getView().findViewById(R.id.fechReal);
        TextView tvLugarRel = (TextView)getView().findViewById(R.id.lugReal);
        TextView tvHoraI = (TextView)getView().findViewById(R.id.hInicio);
        TextView tvHoraF = (TextView)getView().findViewById(R.id.hFinal);

        tvFechaRel.setText(act.getFecha());
        tvLugarRel.setText(act.getLugarRealizacion());
        tvHoraI.setText(act.getHoraInicio());
        tvHoraF.setText(act.getHoraFinal());
    }
}
