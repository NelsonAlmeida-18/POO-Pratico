

import java.io.IOException;
public class Main{
    public static void main(String[] args)throws IOException, ClassNotFoundException{

        // SmartCity city = new SmartCity();
        // Parser p = new Parser();
        // city = p.parse(city.getDeviceId(), city.getHouseId());
        // System.out.println(city.toString());
        // city.saveState("state");
        // // UI ui = new UI();
        // // ui.menuInicial(city);


        SmartCity city = new SmartCity();
        SmartCity nueva = city.loadState("state");
        System.out.println(nueva.toString());
        // //System.out.println(nueva.toString());
    }   
}
