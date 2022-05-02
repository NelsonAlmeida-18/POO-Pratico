import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.lang.StringBuilder;
import java.io.*;

public class SmartCity {
    
    private Map<Integer,SmartHouse> casas=new HashMap<>();
    private List<ComercializadoresEnergia> comercializadores=new ArrayList<>();
    private List<Marca> marcas = new ArrayList<>(); 
    private Map<String,SmartDevice> presets= new HashMap<>();
    private int houseID; //diz quantas casas tem na cidade e atribui o seu numero
    private int deviceID; //diz quantos devices tem na cidade e atribui o seu numero

    public SmartCity(){
        this.deviceID = 0;
        this.houseID = 0;
        this.comercializadores=new ArrayList<ComercializadoresEnergia>();
        this.marcas = new ArrayList<>();
        this.presets=new HashMap<>();
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
            }
        }

        ListIterator<ComercializadoresEnergia> iter = toMerge.getComercializadores().listIterator();
        while(iter.hasNext()){
            ComercializadoresEnergia temp = iter.next();
            if (!this.comercializadores.contains(temp))
                this.comercializadores.add(temp);
        }

        ListIterator<Marca> iterMarcas = toMerge.getMarcas().listIterator();
        while(iterMarcas.hasNext()){
            Marca temp = iterMarcas.next();
            if (!this.marcas.contains(temp))
                this.marcas.add(temp);
        }

        for(String id: toMerge.getPresets().keySet()){
            if(!presets.containsKey(id)){
                this.presets.put(id,toMerge.getPreset(id));
            }
        }

        this.houseID=this.casas.size();
        //TODO fazer o do smartID


    }

    public Marca getMarca(String nome){
        for(Marca x : this.marcas){
            if(x.getNome().equals(nome)){Marca marca = x; return marca;}
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
        FileOutputStream fos = new FileOutputStream(nameOfFile);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.flush();
        oos.close();
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

    public void createComercializadorEnergia(String nome, double preco, double imposto){ 
        ComercializadoresEnergia com = new ComercializadoresEnergia(nome, preco, imposto);
        //System.out.println(com.toString());
        this.comercializadores.add(com);
    }

    public void createComercializadorEnergia(ComercializadoresEnergia com){ 
        this.comercializadores.add(com);
    }

    public SmartHouse getCasa(int id){
        return this.casas.get(id);
    }

    public void createHouse(int id, String nome, int nif, String morada, String comercializadorDeEnergia){
        if(getCasa(id)!=null){
            if (getComercializador(comercializadorDeEnergia)!=null){
                ComercializadoresEnergia comer = getComercializador(comercializadorDeEnergia);
                if(comer!=null){
                    SmartHouse casa = new SmartHouse(id,nome,nif,morada,comer);
                    this.casas.put(this.houseID,casa);
                    this.houseID++;
                }
            }
        }
    }

    public void createHouse(SmartHouse house){this.casas.put(this.houseID,house); this.houseID++;}

    public void criaDivisao(int id, String divisao){
        SmartHouse temp = getCasa(id);
        if(temp!=null){
            if(!temp.hasDivisao(divisao)){
                temp.addDivisao(divisao);
            }
        }
    }

    public void addDeviceToDivisao(int houseID, String divisao, SmartDevice sd){
        SmartHouse temp = getCasa(houseID);
        if(temp!=null){
            if(!temp.hasDivisao(divisao)){
                temp.addDevice(divisao, sd);
            }
        }
    }

    public void addDeviceToDivisao(int houseID, String divisao, String preset){
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

    public void listSmartDevicesPresets(){
        StringBuilder sb = new StringBuilder();
        for(String name : this.presets.keySet()){
            sb.append("Nome do preset: ");
            sb.append(name);
            sb.append("\n");
            sb.append(this.presets.get(name).toString());
        }
        System.out.println(sb.toString());
    }

    public void listSmartHouses(){
        StringBuilder sb = new StringBuilder();
        for(SmartHouse casa: this.casas.values()){
            sb.append(casa.toString());
        }
        System.out.println(sb.toString());
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
