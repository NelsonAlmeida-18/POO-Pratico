package tester;

import java.util.Map;
import java.lang.StringBuilder;
import java.util.HashMap;

/**
 * A SmartHouse faz a gestão dos SmartDevices que existem e dos
 * espaços (as salas) que existem na casa.
 *
 */
public class SmartHouse{

    private String nome_prop;
    private String NIF_prop;
    private String morada;
    //private List<String> divisao;
    private ComercializadoresEnergia companhia_eletrica;
    //private Map<String, SmartDevice> devices; // identificador -> SmartDevice
    //private Map<String, List<String>> locations; // Espaço -> Lista codigo dos devices
    //private Map<String, Map<String, SmartDevice>> devices;
    private Map<String,Map<String, SmartDevice>> devices;
                //divisao, id,sd
    //Morada -> Map divisão -> devices) CONFIRMAR SE É ISTO

    /**
     * Constructor for objects of class SmartHouse
     */
    public SmartHouse() {
        // initialise instance variables
        this.nome_prop = "";
        this.NIF_prop = "";
        this.morada = "";
        this.devices = new HashMap<String, Map<String, SmartDevice>>();
        this.companhia_eletrica = new ComercializadoresEnergia();
    }

    public SmartHouse(String nome, String NIF, String morada, ComercializadoresEnergia comp) {
        // initialise instance variables
        this.nome_prop = nome;
        this.NIF_prop = NIF;
        this.morada = morada;
        //this.divisao = new ArrayList<String>();
        this.devices = new HashMap<>();
        this.companhia_eletrica=comp;
    }

    public SmartHouse(SmartHouse sh){
        this.nome_prop=sh.getNome_prop();
        this.NIF_prop = sh.getNIF_prop();
        this.morada= sh.getMorada();
        this.devices=sh.getHouse();
        this.companhia_eletrica=sh.getCompanhia_eletrica();
    }

    public void setNome_prop(String nome) { this.nome_prop = nome; }
    public String getNome_prop() { return this.nome_prop; }

    public void setNIF_prop(String NIF) { this.NIF_prop = NIF;}
    public String getNIF_prop(){ return this.NIF_prop; }

    public void setMorada(String morada){ this.morada = morada; }
    public String getMorada(){ return this.morada; }

    //nao sei se vale a pena clonar visto que é pertinente que se uma empresa muda precos mude automaticamente se bem que só no ciclo seguinte
    public void setCompanhia_eletrica(ComercializadoresEnergia s){this.companhia_eletrica=s;}
    public ComercializadoresEnergia getCompanhia_eletrica(){return this.companhia_eletrica.clone();}

    // public boolean hasDivisao(String divisao){ return this.divisao.contains(divisao);}
    // public void addDivisao(String divisao){ if(!hasDivisao(divisao)) this.divisao.add(divisao);}
    // public void delDivisao(String divisao){ if(hasDivisao(divisao)) this.divisao.remove(this.divisao.lastIndexOf(divisao));}
    public Map<String,Map<String, SmartDevice>> getHouse(){
        Map<String,Map<String, SmartDevice>> newHouse = new HashMap<>();
        for (String div:this.devices.keySet()){
            Map<String, SmartDevice> devi = new HashMap<>();
            for(SmartDevice sd:this.devices.get(div).values()){
                //falta clonar
                devi.put(sd.getID(), sd);
            }
            newHouse.put(div, devi);
        }
        return newHouse;
    }


    //ver se isto mantem o encapsulamento
    public Map<String, SmartDevice> getDivisao(String divisao){
        Map<String, SmartDevice> nova = new HashMap<>();
        nova.putAll(this.devices.get(divisao));
        return nova;
    }

    // public void setCompanhia_eletrica(String companhia){ this.companhia_eletrica = companhia; }
    // public String getCompanhia_eletrica() { return this.companhia_eletrica; }

    // to do after definition of data structure
    public void setDeviceOn(String devCode) {
        for (Map<String, SmartDevice> dispositivos: this.devices.values()){
            if (dispositivos.containsKey(devCode)){
                dispositivos.get(devCode).turnOn();
            }
        }
    }

    public void setDivisaoOn(String div){
        if (this.devices.containsKey(div)){
            Map <String, SmartDevice> divisao = this.devices.get(div);
            for (SmartDevice sd:divisao.values()){
                sd.turnOn();
            }
        }
    }

    public void setHouseOn(){
        for (String divisao:this.devices.keySet()){
            setDivisaoOn(divisao);
        }
    }
    
    public void setDeviceOFF(String devCode) {
        for (Map<String, SmartDevice> dispositivos: this.devices.values()){
            if (dispositivos.containsKey(devCode)){
                dispositivos.get(devCode).turnOff();
            }
        }
    }

    public void setDivisaoOFF(String div){
        if (this.devices.containsKey(div)){
            Map <String, SmartDevice> divisao = this.devices.get(div);
            for (SmartDevice sd:divisao.values()){
                sd.turnOff();
            }
        }
    }

    public void setHouseOFF(){
        for (String divisao:this.devices.keySet()){
            setDivisaoOFF(divisao);
        }
    }
    


    public boolean existsDevice(String id) {
        for(Map<String, SmartDevice> dispositivos:this.devices.values()){
            if (dispositivos.containsKey(id))
                return true;
        }
        return false;
    }

    public boolean existsDevice(String id, String divisao) {
        return this.devices.get(divisao).containsKey(id);
    }
    
    public void addDevice(String divisao,SmartDevice s) {
        if (this.devices.containsKey(divisao)){
            //falta clonar
            this.devices.get(divisao).put(s.getID(),s);
        }
        else{//criar a divisao 
            Map<String, SmartDevice> disp = new HashMap<String,SmartDevice>();
            //falta clonar
            disp.put(s.getID(),s);
            this.devices.put(divisao, disp);
        }
    }
    
    public SmartDevice getDevice(String s) {
        for(Map<String, SmartDevice> devices: this.devices.values()){
            if(devices.containsKey(s))
                //falta clonar
                return devices.get(s);
        }
        return null;
    }

    public SmartHouse clone(){
        return new SmartHouse(this);
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Nome do Proprietário: ");
        sb.append(this.nome_prop);
        sb.append("\nNIF do proprietário: ");
        sb.append(this.NIF_prop);
        sb.append("\nMorada: ");
        sb.append(this.morada);
        sb.append("\nDispositivos por divisão: \n");
        for(String divisao:this.devices.keySet()){
            sb.append(divisao);
            sb.append("\n");
            for(SmartDevice sd:this.devices.get(divisao).values()){
                sb.append("Id do dispositivo: ");
                sb.append(sd.getID());
                sb.append("\n");
                sb.append(sd.toString());
                sb.append("\n");
            }
            sb.append("\n");
        }
        sb.append("Companhia elétrica: \n");
        sb.append(this.companhia_eletrica.toString());
        sb.append("\n");
        return sb.toString();
    }
}
