package Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
    private double consumo; //consumo em kWh
    private Map<String,Map<Integer, SmartDevice>> devices = new HashMap<>();
                //divisao, id,sd
    //Morada -> Map divisão -> devices) CONFIRMAR SE É ISTO


    /**
     * Constructor for objects of class SmartHouse
     */
    public SmartHouse() {
        this.nome_prop = "";
        this.NIF_prop = 0;
        this.morada = "";
        this.devices = new HashMap<>();
        this.companhia_eletrica = new ComercializadoresEnergia("");
    }

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
        this.devices = new HashMap<>();
        this.companhia_eletrica=comp;
        comp.addCasa(this);
    }

    public SmartHouse(int id, SmartHouse sh){ //o id tem sempre de ser passado pela cidade para
        this.id = id;
        this.nome_prop=sh.getNome_prop();
        this.NIF_prop = sh.getNIF_prop();
        this.morada= sh.getMorada();
        this.companhia_eletrica=sh.getCompanhia_eletrica();
        this.companhia_eletrica.addCasa(this);
    }

    public SmartHouse(int houseID, String nome,int nif,ComercializadoresEnergia fornecedor){
        this.id = houseID;
        this.nome_prop = nome;
        this.NIF_prop = nif;
        this.morada = "";
        this.companhia_eletrica = fornecedor;
        fornecedor.addCasa(this);
    }

    public SmartHouse(SmartHouse sh){
        this.id = sh.getID();
        this.nome_prop = sh.getNome_prop();
        this.NIF_prop = sh.getNIF_prop();
        this.morada = sh.getMorada();
        this.companhia_eletrica = sh.getCompanhia_eletrica();
        this.companhia_eletrica.addCasa(this);
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

    public void setConsumo(double consumo){this.consumo = consumo;}
    public double getConsumo(){return this.consumo;}

    //nao sei se vale a pena clonar visto que é pertinente que se uma empresa muda precos mude automaticamente se bem que só no ciclo seguinte
    public void setCompanhia_eletrica(ComercializadoresEnergia s){this.companhia_eletrica=s; s.addCasa(this);}
    public ComercializadoresEnergia getCompanhia_eletrica(){return this.companhia_eletrica;}

    // public boolean hasDivisao(String divisao){ return this.divisao.contains(divisao);}
    // public void addDivisao(String divisao){ if(!hasDivisao(divisao)) this.divisao.add(divisao);}
    // public void delDivisao(String divisao){ if(hasDivisao(divisao)) this.divisao.remove(this.divisao.lastIndexOf(divisao));}

    
    public Map<String,Map<Integer, SmartDevice>> getHouse(){
        Map<String,Map<Integer, SmartDevice>> newHouse = new HashMap<>();
        for (String div:this.devices.keySet()){
            Map<Integer, SmartDevice> devi = new HashMap<>();
            for(SmartDevice sd:this.devices.get(div).values()){
                devi.put(sd.getID(), sd.clone());
            }
            newHouse.put(div, devi);
        }
        return newHouse;
    }


    //ver se isto mantem o encapsulamento
    public Map<Integer, SmartDevice> getDivisao(String divisao){
        Map<Integer, SmartDevice> nova = new HashMap<>(this.devices.get(divisao));
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
        System.out.println(this.companhia_eletrica);
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
            this.devices.get(divisao).put(s.getID(),s.clone());
        }
        else{//criar a divisao 
            Map<Integer, SmartDevice> disp = new HashMap<>();
            disp.put(s.getID(),s.clone());
            this.devices.put(divisao, disp);
        }
    }

    public void addDevice(String divisao,int id) {
        if (this.devices.containsKey(divisao)){
            this.devices.get(divisao).put(id,getDevice(id).clone());
        }
        else{//criar a divisao
            Map<Integer, SmartDevice> disp = new HashMap<>();
            disp.put(id,getDevice(id).clone());
            this.devices.put(divisao, disp);
        }
    }
    public SmartDevice getDevice(Integer s) {
        for(Map<Integer, SmartDevice> devices: this.devices.values()){
            if(devices.containsKey(s))
                return devices.get(s).clone();
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

    public double getConsumoDivisao(Map<Integer,SmartDevice> div,LocalDate data_atual, LocalDate data_sim){
        double consumoDivisao=0;
        for(SmartDevice disp:div.values()){
            consumoDivisao+=disp.getConsumo(data_atual, data_sim);
        }
        return consumoDivisao;
    }

    public void addDivisao(String div){
        if (this.devices ==null){
            Map<Integer,SmartDevice> disp = new HashMap<>();
            assert false;
            this.devices.put(div,disp);
        }
        if(!this.devices.containsKey(div)){
            Map<Integer,SmartDevice> disp = new HashMap<>();
            this.devices.put(div,disp);
        }
    }

    public void removeDivisao(String div){
        if( this.devices!=null && this.devices.containsKey(div)){
            this.devices.remove(div);
        }
    }

    public boolean hasDivisao(String div){
        return this.devices.containsKey(div);
    }


    //isto é o consumo em KW's e não o consumo monetário
    public double getConsumoDaCasa(){
        this.consumo = 0;
        for(Map<Integer, SmartDevice> div: this.devices.values())
            this.consumo+=getConsumoDivisao(div);
        return this.consumo;
    }

    public double getConsumoDaCasa(LocalDate data_atual, LocalDate data_sim){
        this.consumo = 0;
        for(Map<Integer, SmartDevice> div: this.devices.values())
            this.consumo+=getConsumoDivisao(div, data_atual, data_sim);
        return this.consumo;
    }

    public SmartHouse clone(){
        return new SmartHouse(this);
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\nPROPRIETÁRIO: ");
        sb.append(this.nome_prop);
        sb.append("\tNIF: ");
        sb.append(this.NIF_prop);
        sb.append("\tMORADA: ");
        sb.append(this.morada);
        sb.append("\nCOMERCIALIZADOR DE ENERGIA:\n");
        sb.append(this.companhia_eletrica.toString());
        sb.append("\n");
        sb.append("TOTAL DIVISÕES: ");
        sb.append(this.devices.size());
        sb.append("\nDISPOSITIVOS POR DIVISÃO:\n");
        for(String divisao:this.devices.keySet()){
            sb.append("\n");
            sb.append(divisao);
            sb.append("\n");
            for(SmartDevice sd:this.devices.get(divisao).values()){
                //sb.append("Id do dispositivo: ");
                //sb.append(sd.getID());
                //sb.append("\n");
                sb.append(sd.toString());
                sb.append("\n");
            }
        }
        sb.append("\n");
        return sb.toString();
    }

    public void mudaDeFornecedor(ComercializadoresEnergia novoComercializadoresEnergia, LocalDate data_mudanca){
        this.companhia_eletrica.geraFatura(this, data_mudanca);
        this.companhia_eletrica.terminaContrato(this);//terminar contrato
        this.companhia_eletrica=novoComercializadoresEnergia;
        this.companhia_eletrica.addCasa(this);
    }

    public void terminaContrato(){
        this.companhia_eletrica.removeCasa(this);
        this.companhia_eletrica=null;
    }

    public Set<String> getNomeDivisoes(){
        return this.devices.keySet();
    }
    //funções sobre o map<divisions, devices>

    public int getTotalDivisions(){return this.devices.size();}


    public ArrayList<String> getDivisaoList(){
        
        ArrayList<String> divs_list = new ArrayList<>();
        int index = 0;

        for (Map.Entry<String,Map<Integer, SmartDevice>> devices : devices.entrySet()) {
            divs_list.add(index, devices.getKey());
            index++;
        }

        return divs_list;
    }

    public Map<Integer,SmartDevice> getDevicesDivisao(String divisao){
        return this.devices.get(divisao);

    }

    public String getDivisaoByIndex(int index){return this.getDivisaoList().get(index);}


}