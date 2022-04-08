public class Main{

    public static void main(String[] args){

        //codigo a funcionar mas falta fazer testes para ligar  e desligar multiplas vezes etc
        SmartBulb lampada = new SmartBulb();
        lampada.turnOn();
        lampada.turnOff();
        System.out.println(lampada.toString());
    }

}