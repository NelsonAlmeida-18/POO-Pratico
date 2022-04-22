<<<<<<< HEAD
package tester;
=======
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
>>>>>>> 062a5eb55ad5ca749982fbd8c190f8fe643d961d

public class Main{
    public static void main(String[] args){
        SmartHouse sh = new SmartHouse();
        SmartDevice sd = new SmartCamera(1920,1080,0, SmartCamera.state.ON, 0, LocalDateTime.now());
        SmartDevice sd2 = new SmartCamera();
        sd.setID("sd1");
        sd2.setID("sd2");
        sh.addDevice("Quarto", sd);
        sh.addDevice("Sala", sd2);
        //sh.setHouseOFF();
       // sh.setDeviceOn("sd1");
        //sh.setDeviceOn("sd2");
        System.out.println(sh.toString());
        sd.goToData(LocalDateTime.now().plus(2, ChronoUnit.HOURS));
        System.out.println(sd.getConsumo());
    }
}
