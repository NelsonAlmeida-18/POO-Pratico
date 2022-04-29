import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public abstract class SmartCity {
    
    private List<SmartHouse> casas;
    private List<ComercializadoresEnergia> comercializadores;
    private List<Marca> marcas;
    private List<SmartDevice> presets;
    private int deviceID;

    public SmartCity(){
        this.deviceID = 0;
    }

    public List<ComercializadoresEnergia> listComercializadores(){
        List<ComercializadoresEnergia> ret = new ArrayList<>();
        ListIterator<ComercializadoresEnergia> iter = this.comercializadores.listIterator();
        while(iter.hasNext())
            ret.add(iter.next().clone());
        return ret;
    }


    //city.listComercializadores();

    //city.createHouse(nome_prop, nif, morada, fornecedor)

    //city.listSmartDevicesPresets();

    //city.getPreset(selection)

    //city.createSmartSpeaker();
    
    //city.createSmartCamera();

    //city.createSmartBulb();
}
