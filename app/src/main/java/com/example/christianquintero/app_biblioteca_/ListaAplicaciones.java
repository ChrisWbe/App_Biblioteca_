package com.example.christianquintero.app_biblioteca_;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import static android.R.layout.simple_list_item_1;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListaAplicaciones extends Fragment {

    ListView lista;
    ArrayAdapter<CharSequence> adapter;


    public ListaAplicaciones() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista_aplicaciones, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lista = (ListView)view.findViewById(R.id.listApli);
        adapter = ArrayAdapter.createFromResource(getContext(), R.array.listaApli, simple_list_item_1);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                switch(position){
                    case 0:
                        intent = new Intent().setClass(getActivity(), Aplicaciones.class);
                        intent.putExtra("pagWeb", getString(R.string.pagMaterialBiblio));
                        startActivity(intent);
                        break;

                    case 1:
                        intent = new Intent().setClass(getActivity(), Aplicaciones.class);
                        intent.putExtra("pagWeb", getString(R.string.pagTurnos));
                        startActivity(intent);
                        break;

                    case 2:
                        intent = new Intent().setClass(getActivity(), Aplicaciones.class);
                        intent.putExtra("pagWeb", getString(R.string.pagSuministros));
                        startActivity(intent);
                        break;

                }
                getActivity().overridePendingTransition(R.anim.left_in, R.anim.left_out);
            }
        });

    }
}
