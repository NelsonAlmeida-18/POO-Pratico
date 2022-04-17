package src;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A SmartHouse faz a gestão dos SmartDevices que existem e dos
 * espaços (as salas) que existem na casa.
 *
 */
public class SmartHouse {

    private String nome_prop;
    private Integer NIF_prop;
    private String morada;
    private List<String> divisao;
    private String companhia_eletrica;
    //private Map<String, SmartDevice> devices; // identificador -> SmartDevice
    //private Map<String, List<String>> locations; // Espaço -> Lista codigo dos devices
    //private Map<String, Map<String, SmartDevice>> devices;
    private Map<String,Map<String, SmartDevice> > devices;
    //Morada -> Map divisão -> devices) CONFIRMAR SE É ISTO

    /**
     * Constructor for objects of class SmartHouse
     */
    public SmartHouse() {
        // initialise instance variables
        this.nome_prop = "";
        this.NIF_prop = 0;
        this.morada = "";
        this.divisao = new ArrayList<String>();
        this.devices = new HashMap<String,Map<String,SmartDevice>>();
        this.companhia_eletrica = "";
    }

    public SmartHouse(String nome, Integer NIF, String morada) {
        // initialise instance variables
        this.nome_prop = nome;
        this.NIF_prop = NIF;
        this.morada = morada;
        this.divisao = new ArrayList<String>();
        this.devices = new HashMap<>();
    }

    public void setNome_prop(String nome) { this.nome_prop = nome; }
    public String getNome_prop() { return this.nome_prop; }

    public void setNIF_prop(Integer NIF) { this.NIF_prop = NIF;}
    public Integer getNIF_prop(){ return this.NIF_prop; }

    public void setMorada(String morada){ this.morada = morada; }
    public String getMorada(){ return this.morada; }

    public boolean hasDivisao(String divisao){ return this.divisao.contains(divisao);}
    public void addDivisao(String divisao){ if(!hasDivisao(divisao)) this.divisao.add(divisao);}
    public void delDivisao(String divisao){ if(hasDivisao(divisao)) this.divisao.remove(this.divisao.lastIndexOf(divisao));}

    public void setCompanhia_eletrica(String companhia){ this.companhia_eletrica = companhia; }
    public String getCompanhia_eletrica() { return this.companhia_eletrica; }

    // to do after definition of data structure
    public void setDeviceOn(String devCode) {}
    
    public boolean existsDevice(String id) {return false;}
    
    public void addDevice(SmartDevice s) {}
    
    public SmartDevice getDevice(String s) {return null;}
    
    public void setOn(String s, boolean b) {}
    
    public void setAllOn(boolean b) {}
    
    public void addToRoom (String s1, String s2) {}
    
    public boolean roomHasDevice (String s1, String s2) {return false;}
    
}
