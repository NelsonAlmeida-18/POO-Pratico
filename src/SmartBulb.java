

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
    public enum modo{
        COLD,
        NEUTRAL,
        WARM
    }
    public modo mode;
    public enum state{
        ON,
        OFF
    }
    private state estado;
    private double consumo;
   // private int tone;
    private double dimensions;
    private LocalDateTime ligadoInit;
    private LocalDateTime dataFin;


    /**
     * Constructor for objects of class SmartBulb
     */
    public SmartBulb() {
        // initialise instance variables
        this.mode=modo.NEUTRAL;
        this.estado=state.ON;
        this.consumo=0;
        this.dimensions=1;  // não existem lampadas "inexistentes"
        this.ligadoInit=LocalDateTime.now();
        this.dataFin=LocalDateTime.now();
    }

    public SmartBulb(modo mode, state estado, double consumo, double dimensions, LocalDateTime ligadoInit) {
        // initialise instance variables
        this.mode = mode;
        this.estado = estado;
        this.consumo=consumo;
        this.dimensions=dimensions;
        this.ligadoInit=ligadoInit;
    }

    public SmartBulb(modo mode, state estado, double consumo, double dimensions, LocalDateTime ligadoInit, LocalDateTime dataFinal) {
        // initialise instance variables
        this.mode = mode;
        this.estado = estado;
        this.consumo=consumo;
        this.dimensions=dimensions;
        this.ligadoInit=ligadoInit;
        this.dataFin=dataFinal;
    }

    public SmartBulb(SmartBulb sb) {
        // initialise instance variables
        this.mode = sb.getMode();
        this.estado = sb.getEstado();
        this.consumo= sb.getConsumo();
        this.dimensions= sb.getDimensions();
        this.ligadoInit= sb.getLigadoInit();
        this.dataFin=sb.getDataFin();

    }

    public modo getMode(){return this.mode;}

    public void setMode(modo mode){this.mode = mode;}

    public state getEstado(){return this.estado;}

    public LocalDateTime getDataFin(){return this.dataFin;}

    public void setLigadoInit(LocalDateTime ult){this.ligadoInit=ult;}

    public LocalDateTime getLigadoInit(){return this.ligadoInit; }

    public void setDimensions(double dimensions){this.dimensions=dimensions;}

    public double getDimensions(){return this.dimensions;}

    public void setState(state est){
        switch(est.toString()){
            case("ON"):
                this.ligadoInit=LocalDateTime.now();
                this.estado = est;
            case("OFF"):
                this.consumo = ChronoUnit.MINUTES.between(this.ligadoInit, this.dataFin)*this.dimensions*Math.random();
                this.estado = est;
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
        return (this.mode==lampada.getMode() && this.estado==lampada.getEstado() &&
                this.dimensions==lampada.getDimensions() &&
                this.ligadoInit.equals(lampada.getLigadoInit()));
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Estado da lâmpada: ");
        sb.append(this.estado);
        sb.append("\nModo de Funcionamento da lâmpada: ");
        sb.append(this.mode);
        //sb.append(super(getOn())); // help on this
        sb.append("\nDimensões da lâmpada: ");
        sb.append(this.dimensions);
        sb.append(" cm");
        sb.append("\nÚltima vez ligada: ");
        sb.append(this.ligadoInit);
        sb.append("\n");
        return sb.toString();
    }

    public SmartBulb clone(){
        return new SmartBulb(this);
    }

    public void turnOn(){
        switch(this.estado.toString()){
            case("OFF"):
                this.estado=state.ON;
                this.ligadoInit=LocalDateTime.now();
        }
    }

    public void turnOff(){
        switch(this.estado.toString()){
            case("ON"):
                this.estado=state.OFF;
                double factor=0;
                switch(this.mode.toString()){
                    case("COLD"):
                        factor=10;
                    case("NEUTRAL"):
                        factor=20;
                    case("WARM"):
                        factor=30;
                }
                this.consumo=0.5+factor;
        }
    }

    public void goToData(LocalDateTime data){
        this.dataFin=data;
    }
}


