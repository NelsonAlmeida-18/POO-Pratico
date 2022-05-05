//package src; // idea necessarry

import java.util.Map;
import java.io.Serializable;
import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A SmartHouse faz a gestão dos SmartDevices que existem e dos
 * espaços (as salas) que existem na casa.
 *
 */
public class SmartHouse implements Serializable{

    private int id;
    private String nome_prop;
    private int NIF_prop;
    private String morada;
    private ComercializadoresEnergia companhia_eletrica;
    private Map<String,Map<Integer, SmartDevice>> devices = new HashMap<>();
                //divisao, id,sd
    //Morada -> Map divisão -> devices) CONFIRMAR SE É ISTO


    /**
     * Constructor for objects of class SmartHouse
     */
    //public SmartHouse() {
        // initialise instance variables
        //this.nome_prop = "";
        //this.NIF_prop = "";
        //this.morada = "";
        //this.devices = new HashMap<String, Map<String, SmartDevice>>();
        //this.companhia_eletrica = new ComercializadoresEnergia();
    //}

    // casa sem luz
    public SmartHouse(int id, String nome, int NIF, String Morada){
        this.id = id;
        this.nome_prop = nome;
        this.NIF_prop = NIF;
        this.morada = Morada;
        this.companhia_eletrica = null;

    }
    public SmartHouse(int id,String nome, int NIF, String morada, ComercializadoresEnergia comp) {
        // initialise instance variables
        this.id=id;
        this.nome_prop = nome;
        this.NIF_prop = NIF;
        this.morada = morada;
        this.companhia_eletrica=comp;
        this.devices = new HashMap<>();
    }

    public SmartHouse(int id, SmartHouse sh){ //o id tem sempre de ser passado pela cidade para
        this.id = sh.getID();
        this.nome_prop=sh.getNome_prop();
        this.NIF_prop = sh.getNIF_prop();
        this.morada= sh.getMorada();
        this.companhia_eletrica=sh.getCompanhia_eletrica();
    }

    public SmartHouse(int houseID, String nome,int nif,ComercializadoresEnergia fornecedor){
        this.id = houseID;
        this.nome_prop = nome;
        this.NIF_prop = nif;
        this.morada = "";
        this.companhia_eletrica = fornecedor;
    }

    public SmartHouse(SmartHouse sh){
        this.id = sh.getID();
        this.nome_prop = sh.getNome_prop();
        this.NIF_prop = sh.getNIF_prop();
        this.morada = sh.getMorada();
        this.companhia_eletrica = sh.getCompanhia_eletrica();
    }

    public int getID(){
        return this.id;
    }

    public void setID(int id){
        this.id = id;
    }

    public void setNome_prop(String nome) { this.nome_prop = nome; }
    public String getNome_prop() { return this.nome_prop; }

    public void setNIF_prop(int NIF) { this.NIF_prop = NIF;}
    public int getNIF_prop(){ return this.NIF_prop; }

    public void setMorada(String morada){ this.morada = morada; }
    public String getMorada(){ return this.morada; }

    //nao sei se vale a pena clonar visto que é pertinente que se uma empresa muda precos mude automaticamente se bem que só no ciclo seguinte
    public void setCompanhia_eletrica(ComercializadoresEnergia s){this.companhia_eletrica=s;}
    public ComercializadoresEnergia getCompanhia_eletrica(){return this.companhia_eletrica.clone();}

    // public boolean hasDivisao(String divisao){ return this.divisao.contains(divisao);}
    // public void addDivisao(String divisao){ if(!hasDivisao(divisao)) this.divisao.add(divisao);}
    // public void delDivisao(String divisao){ if(hasDivisao(divisao)) this.divisao.remove(this.divisao.lastIndexOf(divisao));}

    
    public Map<String,Map<Integer, SmartDevice>> getHouse(){
        Map<String,Map<Integer, SmartDevice>> newHouse = new HashMap<>();
        for (String div:this.devices.keySet()){
            Map<Integer, SmartDevice> devi = new HashMap<>();
            for(SmartDevice sd:this.devices.get(div).values()){
                //falta clonar
                devi.put(sd.getID(), sd);
            }
            newHouse.put(div, devi);
        }
        return newHouse;
    }


    //ver se isto mantem o encapsulamento
    public Map<Integer, SmartDevice> getDivisao(String divisao){
        Map<Integer, SmartDevice> nova = new HashMap<>();
        nova.putAll(this.devices.get(divisao));
        return nova;
    }

    
    public void setDeviceOn(int devCode) {
        if(this.companhia_eletrica != null) {
            for (Map<Integer, SmartDevice> dispositivos : this.devices.values()) {
                if (dispositivos.containsKey(devCode)) {
                    dispositivos.get(devCode).turnOn();
                }
            }
        }
    }

    public void setDivisaoOn(String div){
        if(this.companhia_eletrica != null) {
            if (this.devices.containsKey(div)) {
                Map<Integer, SmartDevice> divisao = this.devices.get(div);
                for (SmartDevice sd : divisao.values()) {
                    sd.turnOn();
                }
            }
        }
    }

    public void setHouseOn(){
        if(this.companhia_eletrica != null) {
            for (String divisao : this.devices.keySet()) {
                setDivisaoOn(divisao);
            }
        }
    }
    
    public void setDeviceOFF(int devCode) {
        for (Map<Integer, SmartDevice> dispositivos: this.devices.values()){
            if (dispositivos.containsKey(devCode)){
                dispositivos.get(devCode).turnOff();
            }
        }
    }

    public void setDivisaoOFF(String div){
        if (this.devices.containsKey(div)){
            Map <Integer, SmartDevice> divisao = this.devices.get(div);
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

    public boolean existsDevice(Integer id) {
        for(Map<Integer, SmartDevice> dispositivos:this.devices.values()){
            if (dispositivos.containsKey(id))
                return true;
        }
        return false;
    }

    public boolean existsDevice(Integer id, String divisao) {
        return this.devices.get(divisao).containsKey(id);
    }
    
    public void addDevice(String divisao,SmartDevice s) {
        if (this.devices.containsKey(divisao)){
            //falta clonar
            this.devices.get(divisao).put(s.getID(),s);
        }
        else{//criar a divisao 
            Map<Integer, SmartDevice> disp = new HashMap<>();
            //falta clonar
            disp.put(s.getID(),s);
            this.devices.put(divisao, disp);
        }
    }
    
    public SmartDevice getDevice(Integer s) {
        for(Map<Integer, SmartDevice> devices: this.devices.values()){
            if(devices.containsKey(s))
                //falta clonar
                return devices.get(s);
        }
        return null;
    }


    //rever esta
    public double getConsumoDivisao(Map<Integer,SmartDevice> div){
        double consumoDivisao=0;
        for(SmartDevice disp:div.values()){
            consumoDivisao+=disp.getConsumo();
        }
        return consumoDivisao;
    }

    public void addDivisao(String div){
        if (this.devices ==null){
            Map<Integer,SmartDevice> disp = new HashMap<>();
            this.devices.put(div,disp);
        }
        if(!this.devices.containsKey(div)){
            Map<Integer,SmartDevice> disp = new HashMap<>();
            this.devices.put(div,disp);
        }
    }

    public boolean hasDivisao(String div){
        return this.devices.containsKey(div);
    }


    //isto é o consumo em KW's e não o consumo monetário
    public double getConsumoDaCasa(){
        double consumoDaCasa=0;
        for(Map<Integer, SmartDevice> div: this.devices.values())
            consumoDaCasa+=getConsumoDivisao(div);
        return consumoDaCasa;
    }

    public SmartHouse clone(){
        return new SmartHouse(this);
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("PROPIETÁRTIO: ");
        sb.append(this.nome_prop);
        sb.append("\tNIF: ");
        sb.append(this.NIF_prop);
        sb.append("\tMORADA: ");
        sb.append(this.morada);
        sb.append("Numero de divisãoes: ");
        sb.append(this.devices.size());
        sb.append("\tDispositivos por divisão: \n");
        if (this.devices!=null){
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
        }
        sb.append("\tCompanhia elétrica: ");
        sb.append(this.companhia_eletrica.toString());
        //sb.append("\n");
        return sb.toString();
    }

    public void mudaDeFornecedor(ComercializadoresEnergia novoComercializadoresEnergia){
        this.companhia_eletrica.geraFatura(this);
        this.companhia_eletrica.terminaContrato(this);//terminar contrato
        this.companhia_eletrica=novoComercializadoresEnergia;
        this.companhia_eletrica.addCasa(this);

    }

    //funções sobre o map<divisions, devices>

    public int getTotalDivisions(){return this.devices.size();}

    public ArrayList<String> getDivisaoList(){
        
        ArrayList<String> divs_list = new ArrayList<String>();
        int index = 0;

        for (Map.Entry<String,Map<Integer, SmartDevice>> devices : devices.entrySet()) {
            divs_list.add(index, devices.getKey());
            index++;
        }

        return divs_list;
    }

    public String getDivisaoByIndex(int index){return this.getDivisaoList().get(index);}


}
