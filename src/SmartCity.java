import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.StringBuilder;
import java.io.*;

import static java.lang.Integer.valueOf;
import static java.time.temporal.ChronoUnit.DAYS;

public class SmartCity implements Serializable {
    
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
    }

    public int simulation(String time){

        String [] data = time.split("\\.", 3);  //separa a data dada
        int no_days = 0;
        LocalDate data_objetivo;

        switch (data.length) {
            case 1 -> { //apenas dado um dia
                no_days = Integer.parseInt(data[0]);
                data_objetivo = LocalDate.now().plus(no_days,DAYS);
            }
            case 3 -> { //dada uma data em formato DD.MM.YYYY
                Parser p = new Parser();
                data_objetivo = p.parseData(time);
            }
            default -> {
                System.out.println("Data inválida.");
                return 0;
            }
        }
        for (SmartHouse casa : this.casas.values()){
            casa.calculaConsumoDaCasa(data_objetivo);
            casa.getConsumoDaCasa();
        }


        return 0;
    }

    public String readFromFile(String filename)throws Exception{
        try{
            File f = new File(filename);
            BufferedReader br = new BufferedReader(new FileReader(f));
            StringBuilder sb = new StringBuilder();
            String st;
            while((st=br.readLine())!=null){
                sb.append(st);
                sb.append("\n");
            }
            br.close();
            return sb.toString();
        }
        catch(Exception e){
            System.out.println("File name/path are not reachable!\n");
        }

        return "";
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

    public void saveState(String nameOfFile) throws FileNotFoundException,IOException{
        try{
            FileOutputStream fos = new FileOutputStream(nameOfFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.flush();
            oos.close();
        }
        catch(FileNotFoundException e){
            System.out.println("File not found!\n");
        }
        // catch(IOException e){
        //     System.out.println("Something went wrong while saving the state!\n");
        //     e.printStackTrace();
        // }
    }

    public SmartCity loadState(String nameOfFile) throws FileNotFoundException,IOException, ClassNotFoundException{
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

    public int getHouseId(){return this.houseID;}
    
    public int getDeviceId(){return this.deviceID;}

    public int giveDeviceId(){return ++(this.deviceID);} //serve para dar o id e aumentar o numero de devices

    public int giveHouseId(){return ++(this.houseID);} //serve para dar o id e aumentar o numero de casas

    public SmartDevice getPreset(String preset){
        return presets.get(preset);
    }

    public List<ComercializadoresEnergia> listComercializadores(){
        List<ComercializadoresEnergia> ret = new ArrayList<>();
        ListIterator<ComercializadoresEnergia> iter = this.comercializadores.listIterator();
        while(iter.hasNext())
            ret.add(iter.next().clone());
        return ret;
    }

    public Map<Integer,SmartHouse> getCasas(){return this.casas;}

    public boolean hasComercializador(String id){
        List<ComercializadoresEnergia> ret = listComercializadores();
        return ret.stream().map(ComercializadoresEnergia::clone).anyMatch(c->c.getNome().equals(id));
    }

    public List<ComercializadoresEnergia> getComercializadores(){return this.comercializadores;}

    public ComercializadoresEnergia getComercializador(String id){
        ListIterator<ComercializadoresEnergia> iter = this.comercializadores.listIterator();
        while(iter.hasNext()){
            ComercializadoresEnergia temp = iter.next();
            if(temp.getNome().equals(id)){
                return temp;
            }
        }
        return null;
    }

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
                getComercializador(comercializadorDeEnergia).addCasa(house);
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

    public void addDeviceToDivisao(String divisao, String preset){
        int houseID = this.houseID-1;
        SmartHouse temp = getCasa(houseID);
        if(temp!=null){
            if(!temp.hasDivisao(divisao)){
                temp.addDevice(divisao, getPreset(preset));
            }
        }
    }

    public void createMarca(String nome, double consumo){
        if(getMarca(nome) == null){
            createMarca(new Marca(nome, consumo));
        }
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

    public void listComercializadoresEnergia(){
        System.out.println(this.comercializadores.toString());
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
