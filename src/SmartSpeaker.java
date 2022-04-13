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
    
    private int id;
    private int volume;
    private String radio; 
    private Boolean state; // define the speaker state (on/off)
    private String brand;

    /**
     * Constructor for objects of class SmartSpeaker
     */
    public SmartSpeaker() {
        // create smartspeaker - on by default
        this.volume = 10;
        this.state = true; //on
    }

    public SmartSpeaker(String station) {
        // create smartspeaker mentioning radio station makes it on by default
        this.volume = 10;
        this.state = true; //on
        this.radio = station;
    }

    public SmartSpeaker(String brand, String station, int i) {
        // create smartspeaker mentioning radio station and brand makes it on by default
        this.id = i;
        this.volume = 10;
        this.radio = station;
        this.state = true; //on
        this.brand = brand;
    }

    //SETTERS

    public void setStation(String station) {this.radio = station;}

    public void setBrand(String brand) {this.brand = brand;}

    //GETTERS

    public String getBrand() {return this.brand;}

    public String getStation() {return this.radio;}

    public int getVolume() {return this.volume;}

    public Boolean getState() {return this.state;}


    //SMARTSPEAKER METHODS

    public void turnOn() {this.state = true;}

    public void turnOff() {this.state = false;}

    public void volumeUp() {if (this.volume<MAX) this.volume++;}

    public void volumeDown() {if (this.volume>0) this.volume--;}

    

    


}
