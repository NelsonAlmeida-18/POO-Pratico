package tester;

import java.lang.StringBuilder;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Uma SmartBulb é uma lâmpada inteligente que além de ligar e desligar (já que
 * é subclasse de SmartDevice) também permite escolher a intensidade da iluminação
 * (a cor da mesma).
 *
 */
public class SmartBulb extends SmartDevice {
    public static final int WARM = 1;
    public static final int NEUTRAL = 0;
    public static final int COLD = -1;
    public enum state{
        ON,
        OFF
    }
    private state estado;
    private double consumo;
    private int tone;
    private double dimensions;
    private LocalDateTime ligadoInit;
    private LocalDateTime dataFin;


    /**
     * Constructor for objects of class SmartBulb
     */
    public SmartBulb() {
        // initialise instance variables
        super();
        this.tone = NEUTRAL;
    }

    public SmartBulb(String id, int tone) {
        // initialise instance variables
        super(id);
        this.tone = tone;
    }

    public SmartBulb(SmartBulb id) {
        // initialise instance variables
        super(id);
        this.tone = NEUTRAL;
    }

    public void setTone(int t) {
        if (t>=WARM) this.tone = WARM;
        else if (t<=COLD) this.tone = COLD;
        else this.tone = t;
    }

    public int getTone() { return this.tone; }

    public void setLigadoInit(LocalDateTime ult){this.ligadoInit=ult;}

    public LocalDateTime getLigadoInit(){
        return this.ligadoInit;
    }

    public void setDimensions(double dimensions){this.dimensions=dimensions;}

    public double getDimensions(){return this.dimensions;}

    public void setState(SmartBulb.state est){
        switch(est.toString()){
            case("ON"):
                this.ligadoInit=LocalDateTime.now();
                this.estado = est;
                this.tone = NEUTRAL;
            case("OFF"):
                this.consumo = ChronoUnit.MINUTES.between(this.ligadoInit, this.dataFin)*Math.random();
                this.estado = est;
                this.tone = NEUTRAL;
        }
    }
    /* possibly useful */
    public void setConsumo(float con){
        this.consumo=con;
    }

    public double getConsumo() {return this.consumo;}

    public boolean equals(Object obj){
        if (this==obj)
            return true;

        if (this.getClass()!=obj.getClass() || obj==null)
            return false;

        SmartBulb lampada = (SmartBulb) obj;
        return (this.tone==lampada.getTone() &&
                this.dimensions==lampada.getDimensions() &&
                this.ligadoInit.equals(lampada.getLigadoInit()));
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Estado da lâmpada: ");
        sb.append(this.tone);
        sb.append("\nModo de Funcionamento da lâmpada: ");
        //sb.append(super(getOn())); // help on this
        sb.append("\nDimensões da lâmpada: ");
        sb.append(this.dimensions);
        sb.append(" cm");
        sb.append("\nÚltima vez ligada: ");
        sb.append(this.ligadoInit.toString());
        sb.append("\n");
        return sb.toString();
    }

    public SmartBulb clone(){
        return new SmartBulb(this);
    }

    public void turnOn(){
        this.estado = SmartBulb.state.ON;
        setState(SmartBulb.state.ON);
        setTone(NEUTRAL);
    }

    public void turnOff(){
        this.estado = SmartBulb.state.OFF;
        setState(SmartBulb.state.OFF);
    }

    public void goToData(LocalDateTime data){
        this.dataFin=data;
    }



}


