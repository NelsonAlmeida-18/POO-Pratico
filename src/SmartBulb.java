//package src; // idea necessarry

import java.lang.StringBuilder;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Uma SmartBulb é uma lâmpada inteligente que além de ligar e desligar (já que
 * é subclasse de SmartDevice) também permite escolher a intensidade da iluminação
 * (a cor da mesma).
 *
 */
public class SmartBulb extends SmartDevice {
    
    public int id;
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
    private int dimensions;
    private LocalDate ligadoInit;
    private LocalDate dataFin;


    /**
     * Constructor for objects of class SmartBulb
     */
    public SmartBulb(int id) {
        // initialise instance variables
        this.id = id; //TODO meter id na superclass
        this.mode=modo.NEUTRAL;
        this.estado=state.OFF;
        this.consumo=0;
        this.dimensions=1;  // não existem lampadas "inexistentes"
        this.ligadoInit=LocalDate.now();
        this.dataFin=LocalDate.now();
    }

    public SmartBulb(int id,state estado) {
        // initialise instance variables
        this.id = id;
        this.mode=modo.NEUTRAL;
        this.estado=estado;
        this.consumo=0;
        this.dimensions=1;  // não existem lampadas "inexistentes"
        this.ligadoInit=LocalDate.now();
        this.dataFin=LocalDate.now();
    }

    public SmartBulb(int id, modo mode,int dimensions, double consumo) {
        // initialise instance variables
        this.id = id;
        this.mode = mode;
        this.estado = state.OFF;
        this.consumo=consumo;
        this.dimensions=dimensions;
        this.ligadoInit=LocalDate.now();
        this.dataFin=LocalDate.now();
    }

    public SmartBulb(int id, String mode,int dimensions, double consumo) {
        // initialise instance variables
        this.id = id;
        modo modos = modo.NEUTRAL;
        switch (mode.toUpperCase()) {
            case "COLD":
                modos = modo.COLD;
                break;
            case "NEUTRAL":
                modos = modo.NEUTRAL;
                break;
            case "WARM":
                modos = modo.WARM;
                break;
        }

        this.mode = modos;
        this.estado = state.OFF;
        this.consumo=consumo;
        this.dimensions=dimensions;
        this.ligadoInit=LocalDate.now();
        this.dataFin=LocalDate.now();
    }

    public SmartBulb(int id, String mode,int dimensions, double consumo, String estado) {
        // initialise instance variables
        this.id = id;
        modo modos = modo.NEUTRAL;
        switch (mode.toUpperCase()) {
            case "COLD":
                modos = modo.COLD;
                break;
            case "NEUTRAL":
                modos = modo.NEUTRAL;
                break;
            case "WARM":
                modos = modo.WARM;
                break;
        }

        this.mode = modos;
        this.estado = toState(estado);
        this.consumo=consumo;
        this.dimensions=dimensions;
        this.ligadoInit=LocalDate.now();
        this.dataFin=LocalDate.now();
    }

    public SmartBulb(int id, modo mode, state estado, double consumo, int dimensions, LocalDate ligadoInit) {
        // initialise instance variables
        this.id = id;
        this.mode = mode;
        this.estado = estado;
        this.consumo=consumo;
        this.dimensions=dimensions;
        this.ligadoInit=ligadoInit;
    }

    public SmartBulb(int id, modo mode, state estado, double consumo, int dimensions, LocalDate ligadoInit, LocalDate dataFinal) {
        // initialise instance variables
        this.id = id;
        this.mode = mode;
        this.estado = estado;
        this.consumo=consumo;
        this.dimensions=dimensions;
        this.ligadoInit=ligadoInit;
        this.dataFin=dataFinal;
    }

    public SmartBulb(SmartBulb sb) {
        // initialise instance variables
        this.id = sb.getID();
        this.mode = sb.getMode();
        this.estado = sb.getEstado();
        this.consumo= sb.getConsumo();
        this.dimensions= sb.getDimensions();
        this.ligadoInit= sb.getLigadoInit();
        this.dataFin=sb.getDataFin();

    }

    @Override
    public int getID() {return this.id;}

    public modo getMode(){return this.mode;}

    public void setMode(modo mode){this.mode = mode;}

    public state getEstado(){return this.estado;}

    public LocalDate getDataFin(){return this.dataFin;}

    public void setLigadoInit(LocalDate ult){this.ligadoInit=ult;}

    public LocalDate getLigadoInit(){return this.ligadoInit; }

    public void setDimensions(int dimensions){this.dimensions=dimensions;}

    public int getDimensions(){return this.dimensions;}

    public void setState(state est){
        switch (est.toString()) {
            case ("ON") -> {
                this.ligadoInit = LocalDate.now();
                this.estado = est;
            }
            case ("OFF") -> {
                this.consumo = ChronoUnit.DAYS.between(this.ligadoInit, this.dataFin) * this.dimensions * Math.random();
                this.estado = est;
            }
        }
    }
    /* possibly useful */
    public void setConsumo(float con){
        this.consumo=con;
    }

    public double getConsumo() {return this.consumo;}


    public double getConsumo(LocalDate data_atual, LocalDate dataSimulacao){
        switch (this.estado.toString()) {
            case ("ON") ->
                    this.consumo = ChronoUnit.DAYS.between(data_atual, dataSimulacao) * this.dimensions * Math.random();
            case ("OFF") -> this.consumo = 0;
        }
        return this.consumo;
    }

    /*  esta vai calcular o consumo caso o dipositivo tenha sido
    public double getConsumo(LocalDate data_atual, LocalDate dataSimulacao){

        switch(this.estado.toString()){
            case("ON"):
                if(this.ligadoInit.isBefore(data_atual) && this.dataFin.isAfter(dataSimulacao)){
                    this.consumo = ChronoUnit.DAYS.between(data_atual, dataSimulacao)*this.dimensions*Math.random();
                }
                else if(this.ligadoInit.isBefore(data_atual) && this.dataFin.isBefore(dataSimulacao)){
                    this.consumo = ChronoUnit.DAYS.between(data_atual, this.dataFin)*this.dimensions*Math.random();
                }
                else if(this.ligadoInit.isAfter(data_atual) && this.dataFin.isAfter(dataSimulacao)){
                    this.consumo = ChronoUnit.DAYS.between(this.ligadoInit, dataSimulacao)*this.dimensions*Math.random();
                }
                else{ this.consumo = 0;}

            case("OFF"):
                this.consumo = ChronoUnit.DAYS.between(this.ligadoInit, this.dataFin)*this.dimensions*Math.random();
                this.estado = est;
        }
        return this.consumo;
    }*/

    public boolean equals(Object obj){
        if (this==obj)
            return true;

        if (this.getClass()!=obj.getClass() || obj==null)
            return false;

        SmartBulb lampada = (SmartBulb) obj;
        return (this.id == lampada.getID() && this.mode==lampada.getMode() && this.estado==lampada.getEstado() &&
                this.dimensions==lampada.getDimensions() &&
                this.ligadoInit.equals(lampada.getLigadoInit()));
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n[SmartBulb]");
        sb.append("\nID: ");
        sb.append(this.id);
        sb.append("\nEstado: ");
        sb.append(this.estado);
        sb.append("\nModo: ");
        sb.append(this.mode);
        //sb.append(super(getOn())); // help on this
        sb.append("\nDimensões: ");
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
                this.ligadoInit=LocalDate.now();
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

    public void goToData(LocalDate data){
        this.dataFin=data;
    }

    public SmartBulb.state toState(String state){
        SmartBulb.state ret = SmartBulb.state.OFF;
        
        switch(state){
            case ("ON"):
                ret = SmartBulb.state.ON;
            break;

            case ("OFF"):
                ret = SmartBulb.state.OFF;
            break;
            
        }

        return ret;
    }
}


