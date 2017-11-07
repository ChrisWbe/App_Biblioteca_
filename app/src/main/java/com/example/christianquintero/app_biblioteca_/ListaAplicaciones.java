package com.example.christianquintero.app_biblioteca_;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import static android.R.layout.simple_list_item_1;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListaAplicaciones extends Fragment {

    GridView lista;
    //ArrayAdapter<CharSequence> adapter;


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

        int[] imagesLista = {R.mipmap.ic_data_base,R.mipmap.ic_prestamos,R.mipmap.ic_prestamos,R.mipmap.ic_prestamos,R.mipmap.ic_computer,R.mipmap.ic_document};
        String[] titlesLista = {getString(R.string.dataBase),getString(R.string.biblioDig),getString(R.string.inVir),getString(R.string.regbiblio),getString(R.string.reserEquipos),getString(R.string.sumDoc)};

        lista = (GridView) view.findViewById(R.id.gridView);
        MyAdapterAplication adapter = new MyAdapterAplication(getActivity(), titlesLista, imagesLista);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent().setClass(getActivity(), Aplicaciones.class);
                switch(position){
                    case 0:
                        intent.putExtra("pagWeb", getString(R.string.pagBasesdeDatos));
                        break;

                    case 1:
                        intent.putExtra("pagWeb", getString(R.string.pagBibliotecaDigital));
                        break;

                    case 2:
                        intent.putExtra("pagWeb", getString(R.string.pagInduccionVirtual));
                        break;

                    case 3:
                        intent.putExtra("pagWeb", getString(R.string.pagMaterialBiblio));
                        break;

                    case 4:
                        intent.putExtra("pagWeb", getString(R.string.pagTurnos));
                        break;

                    case 5:
                        intent.putExtra("pagWeb", getString(R.string.pagSuministros));
                        break;

                }
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.left_in, R.anim.left_out);
            }
        });

    }

    public class MyAdapterAplication extends ArrayAdapter{
        int[] imgArray;
        String[] titleArray;

        public MyAdapterAplication(Context context, String[] title, int[] img) {
            super(context, R.layout.listview_row_aplicaciones, R.id.listTitleAplica, title);
            this.imgArray = img;
            this.titleArray = title;
        }

        public View getView(int position,  View convertView, ViewGroup parent){
            LayoutInflater inflater = (LayoutInflater)getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.listview_row_aplicaciones, parent, false);

            ImageView imageView = (ImageView)row.findViewById(R.id.listImageAplica);
            TextView textView = (TextView)row.findViewById(R.id.listTitleAplica);

            imageView.setImageResource(imgArray[position]);
            textView.setText(titleArray[position]);

            return row;
        }
    }
}
