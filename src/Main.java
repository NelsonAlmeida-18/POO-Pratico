//package src;

/* import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;S
 */
public class Main{
    public static void main(String[] args){
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
        UI ui = new UI();

        try{
            ui.menuInicial(city);
        }catch(Exception e){
            System.out.println("Erro a inicializar UI");
        }
    }
}
