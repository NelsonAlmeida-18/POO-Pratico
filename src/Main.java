

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
public class Main{
    public static void main(String[] args)throws IOException{
        /* ComercializadoresEnergia edp = new ComercializadoresEnergia("edp",10,10);
        SmartHouse sh = new SmartHouse("João da Silva", "258222222", "Rua da Bouça", edp);
        SmartDevice sd = new SmartCamera(1920,1080,0, SmartCamera.state.ON, 0, LocalDateTime.now());
        sh.addDevice("Quarto", sd);

        //sh.setHouseOFF();
        //sh.setDeviceOn("sd1");
        //sh.setDeviceOn("sd2");
        System.out.println(sh.toString());
        sd.goToData(LocalDateTime.now().plus(2, ChronoUnit.HOURS));
        edp.geraFatura(sh);
        //System.out.println(sd.getConsumo());
        System.out.println(edp.toString());
        //System.out.println(edp.getFaturacao()); */

        SmartCity city = new SmartCity();
        Parser p = new Parser();
        city = p.parse(city.getDeviceId(), city.getHouseId());
       
        System.out.println(city.toString());
        //UI ui = new UI();

        //city.toString();

        // try{
        //     ui.menuInicial(city);
        // }catch(Exception e){
        //     System.out.println("Erro a inicializar UI");
        // }
    }
}
