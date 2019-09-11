package com.example.gestordealarmas;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditarAlarmaFragment extends Fragment {
    private final String activ="Activada",desac="Desactivada";


    public EditarAlarmaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_editar_alarma, container, false);
        MainActivity activity= (MainActivity) getActivity();
        final Spinner spinner=view.findViewById(R.id.dropDownEditarAlarma);
        String[] items= new String[]{"Activada","Desactivada"};
        ArrayAdapter<String> adapter= new ArrayAdapter<>(getActivity(),android.R.layout.simple_selectable_list_item,items);
        // Inflate the layout for this fragment
        Spinner sp=view.findViewById(R.id.dropDownEditarAlarma);
        sp.setAdapter(adapter);

        return view;
    }

}
