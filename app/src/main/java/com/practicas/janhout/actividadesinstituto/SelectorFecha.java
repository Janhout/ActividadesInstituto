package com.practicas.janhout.actividadesinstituto;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

public class SelectorFecha extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private int mId;
    private EscuchadorSelectorFecha mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        mId = getArguments().getInt("picker_id");
        mListener = getActivity() instanceof EscuchadorSelectorFecha ? (EscuchadorSelectorFecha) getActivity() : null;

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public static SelectorFecha newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt("picker_id", id);
        SelectorFecha fragment = new SelectorFecha();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        if (mListener != null) mListener.onDateSet(mId, view, year, monthOfYear, dayOfMonth);
    }

    public static interface EscuchadorSelectorFecha {
        public void onDateSet(int id, DatePicker view, int year, int monthOfYear, int dayOfMonth);
    }
}