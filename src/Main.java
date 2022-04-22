package src;

public class Main{
    public static void main(String[] args){
        SmartDevice sd1 = new SmartCamera();
        sd1.turnOff();
        System.out.println(sd1.toString());
        sd1.turnOn();
        System.out.println(sd1.toString());
        System.out.println(sd1.getConsumo());
    }
}
