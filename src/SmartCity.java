import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class SmartCity {
    
    private List<SmartHouse> casas;
    private List<ComercializadoresEnergia> comercializadores;
    private List<Marca> marcas;
    private List<SmartDevice> presets;
    private int houseID; //diz quantas casas tem na cidade e atribui o seu numero
    private int deviceID; //diz quantos devices tem na cidade e atribui o seu numero

    public SmartCity(){
        this.deviceID = 0;
        this.houseID = 0;
    }

    public SmartCity(int houseID, int deviceID){
        this.deviceID = deviceID;
        this.houseID = houseID;
    }

    public int getHouseId(){return this.houseID;}
    
    public int getDeviceId(){return this.deviceID;}

    public int giveDeviceId(){return ++(this.deviceID);} //serve para dar o id e aumentar o numero de devices

    public int giveHouseId(){return ++(this.houseID);} //serve para dar o id e aumentar o numero de casas


    public List<ComercializadoresEnergia> listComercializadores(){
        List<ComercializadoresEnergia> ret = new ArrayList<>();
        ListIterator<ComercializadoresEnergia> iter = this.comercializadores.listIterator();
        while(iter.hasNext())
            ret.add(iter.next().clone());
        return ret;
    }

    public List<SmartHouse> getCasas(){return this.casas;}

    public List<ComercializadoresEnergia> getComercializadores(){return this.comercializadores;}

    //public List<ComercializadoresEnergia> getComercializadores(){return this.comercializadores;}

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

    public void createComercializadorEnergia(ComercializadoresEnergia com){ 
        this.comercializadores.add(com);
    }

    public SmartHouse getCasa(int id){
        ListIterator<SmartHouse> iter = this.casas.listIterator();
        while(iter.hasNext()){
            SmartHouse temp = iter.next();
            if (temp.getID().equals(id))
                return temp;
        }
        return null;
    }

    public void createHouse(String id, String nome, String nif, String morada, String comercializadorDeEnergia){
        if(getCasa(id)!=null){
            if (getComercializador(comercializadorDeEnergia)!=null){
                ComercializadoresEnergia comer = getComercializador(comercializadorDeEnergia);
                if(comer!=null){
                    SmartHouse casa = new SmartHouse(id,nome,nif,morada,comer);
                    this.casas.add(casa);
                }
            }
        }
    }

    public void createHouse(SmartHouse house){
        if(getCasa(house.getId)!=null){
            if (getComercializador(comercializadorDeEnergia)!=null){
                ComercializadoresEnergia comer = getComercializador(comercializadorDeEnergia);
                if(comer!=null){
                    SmartHouse casa = new SmartHouse(id,nome,nif,morada,comer);
                    this.casas.add(casa);
                }
            }
        }
    }

    public void criaDivisao(String id, String divisao){
        SmartHouse temp = getCasa(id);
        if(temp!=null){
            if(!temp.hasDivisao(divisao)){
                temp.addDivisao(divisao);
            }
        }
    }

    public void criaDivisao(int id, String divisao){
        SmartHouse temp = getCasa(id);
        if(temp!=null){
            if(!temp.hasDivisao(divisao)){
                temp.addDivisao(divisao);
            }
        }
    }

    public void addDeviceToDivisao(int houseID, String divisao, SmartDevice sd){

    }

    public void addDeviceToDivisao(int houseID, String divisao, String preset){
        
    }

    public void createMarca(Marca marca){
        
    }

    // public void criaDivisoes(String id, List<String> divisoes){
    //     SmartHouse temp = getCasa(id);
    //     if(temp!=null){
    //         if(!temp.hasDivisao(divisao)){
    //             temp.addDivisao(divisao);
    //         }
    //     }
    // }


    //createHouse(nome_prop, nif, morada, fornecedor)

    //listSmartDevicesPresets();

    //getPreset(selection)

    //createSmartSpeaker();
    
    //createSmartCamera();

    //createSmartBulb();
}
