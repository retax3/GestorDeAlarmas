package com.example.gestordealarmas;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlarmasFragment extends Fragment {


    public AlarmasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_alarmas, container, false);
        ListView lv= view.findViewById(R.id.listaAlarmas);

        ArrayList<Alarma> arrayList= new ArrayList<>();

        arrayList.add(new Alarma(1,"asdqwe","Cocina",false,"1231234","12315"));
        arrayList.add(new Alarma(2,"1245","casa",true,"1231212434","12315"));
        arrayList.add(new Alarma(3,"asd12451qwe","oficina",true,"23123","1213"));

        MyAdapter myadapter= new MyAdapter(getActivity(),arrayList);

        lv.setAdapter(myadapter);

        // Inflate the layout for this fragment
        return view;
    }

}
