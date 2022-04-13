package src;

/**
 * Um SmartSpeaker é um SmartDevice que além de ligar e desligar permite também
 * reproduzir som.
 * Consegue ligar-se a um canal (por simplificação uma rádio online) e permite
 * a regulação do seu nível de volume.
 *
 */
public class SmartSpeaker extends SmartDevice {
    public static final int MAX = 20; //volume máximo
    
    private int volume;
    private String channel;
    private float consumo;


    /**
     * Constructor for objects of class SmartSpeaker
     */
    public SmartSpeaker() {
        // initialise instance variables
        this.volume = 10;
    }

    public SmartSpeaker(String s) {
        // initialise instance variables
        this.volume = 10;
        this.channel = s;
    }

    public SmartSpeaker(String cod, String channel, int i) {
        // initialise instance variables
        this.volume = 10;
    }

    public void volumeUp() {
        if (this.volume<MAX) this.volume++;
    }

    public void volumeDown() {
        if (this.volume>0) this.volume--;
    }

    public int getVolume() {return 0;}
    
    public String getChannel() {return "";}
    
    public void setChannel(String c) {this.channel = c;}

    public void setConsumo(float consumo) {
        this.consumo = consumo;
    }
    public float getConsumo() {
        return consumo;
    }

}
