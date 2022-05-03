

import java.io.IOException;
public class Main{
    public static void main(String[] args)throws IOException{

        SmartCity city = new SmartCity();
        Parser p = new Parser();
        city = p.parse(city.getDeviceId(), city.getHouseId());
       
       System.out.println(city.toString());
      // city.saveState("state");
    }
}
