package src;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class SmartCity{

    private Map<Integer, List<String> > casas_Xproprietario;
    // NIF -> (lista de moradas)

    private Map<String, Map<String,Integer> > casas_Xcompanhia;
    // companhia -> (morada -> nº casa)

    public SmartCity(){
        this.casas_Xproprietario = new HashMap<>();
        this.casas_Xcompanhia = new HashMap<>();
    }

    // rest setters and getters to do after data structure defined

}