

import java.io.IOException;
import java.util.Scanner;

public class Main{
    public static void main(String[] args)throws IOException, ClassNotFoundException{

        SmartCity city = new SmartCity();

        //city = p.parse(city.getDeviceId(), city.getHouseId());
        //System.out.println(city.toString());
        //city.saveState("state");

        Parser p = new Parser();
        Scanner scanner = new Scanner(System.in);
        new UI(city, scanner);
        scanner.close();
        System.out.println(city.toString());



        //SmartCity city = new SmartCity();
        //SmartCity nueva = city.loadState("state");
        //System.out.println(nueva.toString());
        // //System.out.println(nueva.toString());
    }   
}
