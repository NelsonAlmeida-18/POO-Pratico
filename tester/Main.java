public class Main{
    public static void main(String[] args){
        SmartHouse sh = new SmartHouse();
        SmartDevice sd = new SmartCamera();
        SmartDevice sd2 = new SmartCamera();
        sd.setID("sd1");
        sd2.setID("sd2");
        sh.addDevice("Quarto", sd);
        sh.addDevice("Sala", sd2);
        sh.setHouseOFF();
        sh.setDeviceOn("sd1");
        sh.setDeviceOn("sd2");
        System.out.println(sh.toString());
    }
}
