package com.example.christianquintero.app_biblioteca_;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by christian.quintero on 09/10/2017.
 */

public class ExpandableListaData {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();


        List<String> medellin = new ArrayList<String>();
        medellin.add("Carlos Gavíria Diaz");
        medellin.add("Médica");
        medellin.add("Salud Pública");
        medellin.add("Odontología");
        medellin.add("Enfermería");
        medellin.add("Ciudadela Robledo");
        medellin.add("Bibliotecología");
        medellin.add("Bachillerato Nocturno");
        expandableListDetail.put("Medellín", medellin);

        List<String> seccionales = new ArrayList<String>();
        seccionales.add("Bajo Cauca - Caucasia");
        seccionales.add("Magdalena Medio - Puerto Berrío");
        seccionales.add("Norte - Yarumal");
        seccionales.add("Occidente - Santa Fe de Antioquia");
        seccionales.add("Oriente - Carmen de Viboral");
        seccionales.add("Oriente - Sonsón");
        seccionales.add("Suroeste - Andes");
        seccionales.add("Urabá - Turbo");
        expandableListDetail.put("Seccionales y Regionales", seccionales);

        List<String> centros_documentacion = new ArrayList<String>();
        centros_documentacion.add("CENDOI - Centro de Documentación de Ingeniería");
        centros_documentacion.add("Centro de Documentación de Artes");
        centros_documentacion.add("Centro de Documentación de Economía");
        centros_documentacion.add("Centro de Documentación de Educación");
        centros_documentacion.add("Centro de Documentación de Idiomas");
        centros_documentacion.add("Centro de Documentación de la Emisora Cultural");
        centros_documentacion.add("Centro de Documentación del Instituto de Estudios Políticos");
        centros_documentacion.add("Centro de documentacion Javiera Londoño");
        centros_documentacion.add("CIDUA - Química Farmacéutica");
        centros_documentacion.add("CIIC - Centro Internacional de Idiomas y Culturas");
        centros_documentacion.add("CISH - Ciencias Sociales y Humanas");
        centros_documentacion.add("Fondo de Investigación y Documentación de Músicas Regionales");
        centros_documentacion.add("INER - Instituto de  Estudios Regionales");
        expandableListDetail.put("Centros de Documentación", centros_documentacion);



        return expandableListDetail;
    }

}
