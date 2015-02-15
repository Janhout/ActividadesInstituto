package com.practicas.janhout.actividadesinstituto;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TimePicker;

import java.util.Calendar;

public class SelectorHora extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    private int mId;
    private EscuchadorSelectorHora mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        mId = getArguments().getInt("picker_id");
        mListener = getActivity() instanceof EscuchadorSelectorHora ? (EscuchadorSelectorHora) getActivity() : null;

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute, false);
    }

    public static SelectorHora newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt("picker_id", id);
        SelectorHora fragment = new SelectorHora();
        fragment.setArguments(args);
        return fragment;
    }

    @Override public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (mListener != null) mListener.onTimeSet(mId, view, hourOfDay, minute);
    }

    public static interface EscuchadorSelectorHora {
        public void onTimeSet(int id, TimePicker view, int hourOfDay, int minute);
    }
}