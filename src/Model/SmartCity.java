package Model;

import java.time.LocalDate;
import java.util.*;

import Controller.Controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.StringBuilder;
import java.io.*;

import static java.time.temporal.ChronoUnit.DAYS;

public class SmartCity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1732799934;
    private Map<Integer,SmartHouse> casas=new HashMap<>();
    private List<ComercializadoresEnergia> comercializadores=new ArrayList<>();
    private List<Marca> marcas = new ArrayList<>(); 
    private Map<String,SmartDevice> presets= new HashMap<>();
    private int houseID=0; //diz quantas casas tem na cidade e atribui o seu numero
    private int deviceID=0; //diz quantos devices tem na cidade e atribui o seu numero
    private LocalDate data_inicial;
    private LocalDate data_atual;

    public SmartCity(){
        this.data_inicial = LocalDate.now();
        this.data_atual = LocalDate.now();
        this.deviceID = 0;
        this.houseID = 0;
        this.comercializadores=new ArrayList<>();
        this.marcas = new ArrayList<>();
        this.presets=new HashMap<>();
        this.casas = new HashMap<>();
    }

    public SmartCity(SmartCity state){
        this.data_inicial = state.getDataAtual();
        this.data_atual = state.getDataAtual();
        this.deviceID = state.getDeviceId();
        this.houseID = state.getHouseId();
        this.comercializadores= state.getComercializadores();
        this.marcas = state.getMarcas();
        this.presets= state.getPresets();
        this.casas = state.getCasas();
    }

    public int simulation(String time){

        String [] data = time.split("\\.", 3);  //separa a data dada
        int no_days = 0;
        LocalDate data_sim;
        double consumo_tmp;

        switch (data.length) {
            case 1 -> { //apenas dado um dia
                no_days = Integer.parseInt(data[0]);
                data_sim = LocalDate.now().plus(no_days,DAYS);
            }
            case 3 -> { //dada uma data em formato DD.MM.YYYY
                Controller p = new Controller(this);
                data_sim = p.parseData(time);
                if(data_sim.isBefore(this.data_atual)) return 1; //data anterior portanto inválida
            }
            default -> {
                //Data inválida
                return 1;
            }
        }
        System.out.println("\nDATA: "+data_sim.toString());
        //calcula consumos no intervalo de datas da data atual à data da simulação
        for (SmartHouse casa : this.casas.values()){
            consumo_tmp = casa.getConsumoDaCasa(this.data_atual, data_sim);
            System.out.println("\nCONSUMO CASA No."+ casa.getID() +": " + consumo_tmp);
        }
        /*percorre comercializadores para tirarem faturas
        guarda as faturas no seu map e manda para as casas guardarem
         */
        for (ComercializadoresEnergia comercializadore : this.comercializadores) {
            comercializadore.faturacao(data_sim);
        }
        this.data_atual = data_sim;
        return 0;
    }

    public void merge(SmartCity toMerge){
        for(Integer id: toMerge.getCasas().keySet()){
            if(!casas.containsKey(id)){
                this.casas.put(id,toMerge.getCasa(id));
                //this.deviceID+=toMerge.getCasa(id).size();  incrementar o smartId;
            }
        }

        for (ComercializadoresEnergia temp : toMerge.getComercializadores()) {
            if (!this.comercializadores.contains(temp))
                this.comercializadores.add(temp);
        }

        for (Marca temp : toMerge.getMarcas()) {
            if (!this.marcas.contains(temp))
                this.marcas.add(temp);
        }

        for(String id: toMerge.getPresets().keySet()){
            if(!presets.containsKey(id)){
                this.presets.put(id,toMerge.getPreset(id));
            }
        }

        this.houseID=this.casas.size()+1; //passa para o id seguinte


    }

    public Marca getMarca(String nome){
        for(Marca x : this.marcas){
            if(x.getNome().equals(nome)){return x;}
        }
        return null;
    }

    public Map<String, SmartDevice> getPresets(){return this.presets;}

    public List<Marca> getMarcas(){return this.marcas;}

    public String marcasListToString(){
        StringBuffer sb = new StringBuffer();
        for(Marca x : this.marcas){
            sb.append(x.toString());
        }
        return sb.toString();
    }

    public void saveState(String nameOfFile) throws IOException {
        try{
            FileOutputStream fos = new FileOutputStream(nameOfFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.flush();
            oos.close();
        }
        catch(FileNotFoundException e){
            System.out.println("File not found! "+e);
        }
    }

    public SmartCity loadState(String nameOfFile) throws IOException, ClassNotFoundException{
        FileInputStream fis = new FileInputStream(nameOfFile);
        ObjectInputStream oos = new ObjectInputStream(fis);
        SmartCity cit =(SmartCity) oos.readObject();
        oos.close();
        return cit;
    }

    public SmartCity(int houseID, int deviceID){
        this.deviceID = deviceID;
        this.houseID = houseID;
    }

    public LocalDate getDataAtual(){return this.data_atual;}

    public int getHouseId(){return this.houseID;}
    
    public int getDeviceId(){return this.deviceID;}

    public int giveDeviceId(){return ++(this.deviceID);} //serve para dar o id e aumentar o numero de devices

    public int giveHouseId(){return ++(this.houseID);} //serve para dar o id e aumentar o numero de casas

    public SmartDevice getPreset(String preset){
        return presets.get(preset);
    }


    public Map<Integer,SmartHouse> getCasas(){return this.casas;}

    public boolean hasComercializador(String id){
        return this.comercializadores.stream().anyMatch(c->c.getNome().equals(id));
    }

    public String listComercializadores(){
        List<ComercializadoresEnergia> comercializadoresDaCidade = this.comercializadores;
        StringBuilder sb = new StringBuilder();
        ListIterator<ComercializadoresEnergia> iter = comercializadoresDaCidade.listIterator();
        sb.append("[");
        int pos =0;
        while(iter.hasNext()){
            ComercializadoresEnergia temp = iter.next();
            sb.append(temp.getNome());
            sb.append("     Preço p/KWh: ");
            sb.append(temp.getPrecoBaseKW());
            if(pos<this.comercializadores.size()-1){
                sb.append(",  ");
            }
            pos+=1;
        }
        sb.append("]");
        return sb.toString();

    }

    public SmartHouse getCasaMaisGastadora(){
        SmartHouse ret=null;
        for (ComercializadoresEnergia comer: this.comercializadores){
            SmartHouse temp = comer.getCasaMaisGastadora();
            if (ret == null || temp.getConsumo()>ret.getConsumo()){
                ret = temp;
            }
        }
        return ret;
    }

    public SmartHouse getCasaMaisGastadora(LocalDate dataInit, LocalDate dataFin){
        SmartHouse ret=null;
        for (ComercializadoresEnergia comer: this.comercializadores){
            SmartHouse temp = comer.getCasaMaisGastadora(dataInit, dataFin);
            if (ret == null || temp.getConsumo()>ret.getConsumo()){
                ret = temp;
            }
        }
        return ret;
    }

    public ComercializadoresEnergia getComercializadorMaiorFaturacao(){
        ComercializadoresEnergia ret=null;
        for (ComercializadoresEnergia comer: this.comercializadores){
            if (ret == null || comer.getFaturacao()>ret.getFaturacao()){
                ret = comer;
            }
        }
        return ret;
    }

    public List<ComercializadoresEnergia> getComercializadores(){return this.comercializadores;}

    // public ComercializadoresEnergia getComercializador(String id){
    //     ListIterator<ComercializadoresEnergia> iter = this.comercializadores.listIterator();
    //     while(iter.hasNext()){
    //         ComercializadoresEnergia temp = iter.next();
    //         if(temp.getNome().equals(id)){
    //             return temp;
    //         }
    //     }
    //     return null;
    // }

    public void createComercializadorEnergia(String nome){
        ComercializadoresEnergia com = new ComercializadoresEnergia(nome);
        createComercializadorEnergia(com);
    }

    public void createComercializadorEnergia(String nome, double preco, double imposto){ 
        ComercializadoresEnergia com = new ComercializadoresEnergia(nome, preco, imposto);
        createComercializadorEnergia(com);
    }

    public void createComercializadorEnergia(ComercializadoresEnergia com){
        this.comercializadores.add(com);
    }

    public SmartHouse getCasa(int id){
        return this.casas.get(id);
    }

    public int createHouse(String nome, int nif, String morada, String comercializadorDeEnergia){
        int id = this.houseID;
        //if(getCasa(id)!=null){
            if (getComercializador(comercializadorDeEnergia)==null){
                createComercializadorEnergia(comercializadorDeEnergia);
                SmartHouse house = new SmartHouse(id,nome,nif,morada,getComercializador(comercializadorDeEnergia));
                createHouse(house);
                //getComercializador(comercializadorDeEnergia).addCasa(house);
            }
            else{
                ComercializadoresEnergia comer = getComercializador(comercializadorDeEnergia);
                //createComercializadorEnergia(comer);
                SmartHouse house = new SmartHouse(id,nome,nif,morada,comer);
                createHouse(house);
                comer.addCasa(house);
            }
        //}
        return id;
    }

    public void createHouse(SmartHouse house){
        this.casas.put(this.houseID,house);
        this.houseID++;
    }

    public void criaDivisao(String divisao){
        SmartHouse temp = getCasa(this.houseID-1);
        //System.out.println(temp==null);
        if(temp!=null){
            temp.addDivisao(divisao);
        }
    }

    public void criaDivisao(int houseID, String divisao){
        SmartHouse temp = getCasa(houseID);
        //System.out.println(temp==null);
        if(temp!=null){
            temp.addDivisao(divisao);
        }
    }

    public void addDeviceToDivisao(String divisao, SmartDevice sd){
        int houseID = this.houseID-1;
        SmartHouse temp = getCasa(houseID);
        if(temp!=null){
            temp.addDevice(divisao, sd);
        }
    }

    public void addDeviceToDivisaoL(String divisao, int id, String mode , int dimensions, double consumo){
        SmartBulb sb = new SmartBulb(id,mode,dimensions,consumo);
        SmartDevice sd = (SmartDevice) sb;
        addDeviceToDivisao(divisao, sd);
    }

    public void addDeviceToDivisaoC(String divisao, int id, float width , float height, int tamanho, double consumo){
        SmartDevice sc = new SmartCamera(id,width,height,tamanho,consumo);
        addDeviceToDivisao(divisao,sc);
    }

    public void addDeviceToDivisaoS(String divisao, int id, int vol, String estacao , String marca, double consumo){
        SmartDevice sb = new SmartSpeaker(id,vol,estacao,this.createMarca(marca),consumo);
        addDeviceToDivisao(divisao,sb);
    }

    public void addDeviceToDivisao(String divisao, String preset){
        int houseID = this.houseID-1;
        SmartHouse temp = getCasa(houseID);
        if(temp!=null){
            if(!temp.hasDivisao(divisao)){
                temp.addDevice(divisao, getPreset(preset));
            }
        }
    }

    public Marca createMarca(String nome, double consumo){
        if(getMarca(nome) == null){
            createMarca(new Marca(nome, consumo));
        }
        return getMarca(nome);
    }

    public Marca createMarca(String nome){
        if(getMarca(nome) == null){
            createMarca(new Marca(nome));
        }
        return getMarca(nome);
    }

    public void createMarca(Marca marca){
        if(getMarca(marca.getNome()) == null){
            this.marcas.add(marca);
        }
    }

    public void addDevicePreset(String nome, SmartDevice device){
        if(getPreset(nome) == null){
            this.presets.put(nome,device);
        }
    }

    public String listFaturas(){
        List<String> listaDeFaturas = new ArrayList<>();
        for (ComercializadoresEnergia temp : this.comercializadores) {
            listaDeFaturas.add(temp.getNome());
            listaDeFaturas.add(temp.getListaFaturacao());
        }
        return listaDeFaturas.toString();
    }

    public String listFaturas(String nomeComercializador){
        return getComercializador(nomeComercializador).getListaFaturacao().toString();
    }

    public ComercializadoresEnergia getComercializador(String nomeComercializador){
        for (ComercializadoresEnergia comer:this.comercializadores){
            if (comer.getNome().equals(nomeComercializador)){
                return comer;
            }
        }
        return null;
    }


    public void listSmartDevicesPresets(){
        StringBuilder sb = new StringBuilder();
        for(String name : this.presets.keySet()){
            sb.append("\nNome do preset: ");
            sb.append(name);
            sb.append("\n");
            sb.append(this.presets.get(name).toString());
        }
        System.out.println(sb.toString());
    }

    public void listMarcas(){
        System.out.println(this.marcasListToString());
    }

    public void listSmartHouses(){
        StringBuilder sb = new StringBuilder();
        for(SmartHouse casa: this.casas.values()){
            sb.append(casa.toString());
        }
        System.out.println(sb.toString());
    }

    public boolean hasSmartHouse(int id){
        return this.casas.containsKey(id);
    }

    public String toString(){
        StringBuilder bd = new StringBuilder();
        bd.append("\nCASAS:\n");
        bd.append(this.casas.toString());
        bd.append("\nCOMERCIALIZADORES:\n");
        bd.append(this.comercializadores.toString());
        bd.append("\nMARCAS:\n");
        bd.append(this.marcas.toString());
        bd.append("\nPRESETS:\n");
        bd.append(this.presets.toString());
        bd.append("\n");
        return bd.toString();
    }

}
