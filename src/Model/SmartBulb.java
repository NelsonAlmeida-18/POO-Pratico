package Model;

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
     * Inicializador de lampada
     * @param id id a definir
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

    /**
     * Inicializador de lampada
     * @param id id a definir
     * @param estado estado a definir
     */
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

    /**
     * Inicializador de lampada
     * @param id id a definir
     * @param mode modo a definir
     * @param dimensions dimensões a definir
     * @param consumo consumo a definir
     */
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

    /**
     * Inicializador de lampada
     * @param id id a definir
     * @param mode modo a definir
     * @param dimensions dimensões a definir
     * @param consumo consumo a definir
     */
    public SmartBulb(int id, String mode,int dimensions, double consumo) {
        // initialise instance variables
        this.id = id;
        modo modos = switch (mode.toUpperCase()) {
            case "COLD" -> modo.COLD;
            case "NEUTRAL" -> modo.NEUTRAL;
            case "WARM" -> modo.WARM;
            default -> modo.NEUTRAL;
        };

        this.mode = modos;
        this.estado = state.OFF;
        this.consumo=consumo;
        this.dimensions=dimensions;
        this.ligadoInit=LocalDate.now();
        this.dataFin=LocalDate.now();
    }

    /**
     * Inicializador da lampada
     * @param id id a definir
     * @param mode modo a definir
     * @param dimensions dimensões a definir
     * @param consumo consumo a definir
     * @param estado estado a definir
     */
    public SmartBulb(int id, String mode,int dimensions, double consumo, String estado) {
        // initialise instance variables
        this.id = id;
        modo modos = switch (mode.toUpperCase()) {
            case "COLD" -> modo.COLD;
            case "NEUTRAL" -> modo.NEUTRAL;
            case "WARM" -> modo.WARM;
            default -> modo.NEUTRAL;
        };

        this.mode = modos;
        this.estado = toState(estado);
        this.consumo=consumo;
        this.dimensions=dimensions;
        this.ligadoInit=LocalDate.now();
        this.dataFin=LocalDate.now();
    }

    /**
     * Inicializador de lampada
     * @param id id a definir
     * @param mode modo a definir
     * @param estado estado a definir
     * @param consumo consumo a definir
     * @param dimensions dimensões a definir
     * @param ligadoInit data de ativar a lampada a definir
     */
    public SmartBulb(int id, modo mode, state estado, double consumo, int dimensions, LocalDate ligadoInit) {
        // initialise instance variables
        this.id = id;
        this.mode = mode;
        this.estado = estado;
        this.consumo=consumo;
        this.dimensions=dimensions;
        this.ligadoInit=ligadoInit;
    }

    /**
     * Inicializar lampada
     * @param id id a definir
     * @param mode modo a definir
     * @param estado estado a definir
     * @param consumo consumo a definir
     * @param dimensions dimensões a definir
     * @param ligadoInit ativação a definir
     * @param dataFinal data de desligar a definir
     */
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

    /**
     * Duplicar lampada
     * @param sb Lampada a duplicar
     */
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

    /**
     * Getter do id da lampada
     * @return id da lampada
     */
    @Override
    public int getID() {return this.id;}

    /**
     * Getter do modo da lampada
     * @return modo da lamapda
     */
    public modo getMode(){return this.mode;}

    /**
     * Setter do modo da lampada
     * @param mode modo a definar a lampada
     */
    public void setMode(modo mode){this.mode = mode;}

    /**
     * Getter de estado da lamapda
     * @return estado da lamapda
     */
    public state getEstado(){return this.estado;}

    /**
     * Getter da data de desligar
     * @return data de desligar o dispositivo
     */
    public LocalDate getDataFin(){return this.dataFin;}

    /**
     * Setter da data de ativação
     * @param ult data de ativação a definir
     */
    public void setLigadoInit(LocalDate ult){this.ligadoInit=ult;}

    /**
     * Getter da data de ligar
     * @return data de ligar o dispositivo
     */
    public LocalDate getLigadoInit(){return this.ligadoInit; }

    /**
     * Setter de dimensões
     * @param dimensions dimensões a dar set
     */
    public void setDimensions(int dimensions){this.dimensions=dimensions;}

    /**
     * Getter de dimensões
     * @return dimensões
     */
    public int getDimensions(){return this.dimensions;}

    /**
     * Setter do estado de uma lampada
     * @param est Estado a dar set
     */
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

    /**
     * Setter de consumo
     * @param con consumo a definir
     */
    public void setConsumo(float con){
        this.consumo=con;
    }

    /**
     * Getter de consumo
     * @return consumo
     */
    public double getConsumo() {
        if (this.estado == state.ON){
            return this.consumo;
        }
        else{
            return 0;
        }
    }

    /**
     * Calcula o consumo da lampada
     * @param data_atual data que foi desligada a lampada
     * @param dataSimulacao data que foi ligada a lampada
     * @return consumo da lampada
     */
    public double getConsumo(LocalDate data_atual, LocalDate dataSimulacao){
        switch (this.estado.toString()) {
            case ("ON") ->
                    this.consumo = ChronoUnit.DAYS.between(data_atual, dataSimulacao) * this.dimensions * Math.random();
            case ("OFF") -> this.consumo = 0;
        }
        return this.consumo;
    }


    /*
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

    /**
     * Compara a lampada a um objeto
     * @param obj
     * @return Bool de validação da comparação entre objetos
     */
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

    /**
     * toString de uma lampada
     * @return String com todos os dados de uma lampada
     */
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

    /**
     * Dar clone a uma lampada
     * @return Lampada clonada
     */
    public SmartBulb clone(){
        return new SmartBulb(this);
    }

    /**
     * Ligar uma lampada
     */
    public void turnOn(){
        if ("OFF".equals(this.estado.toString())) {
            this.estado = state.ON;
            this.ligadoInit = LocalDate.now();
        }
    }

    /**
     * Desligar uma lampada
     */
    public void turnOff(){
        if ("ON".equals(this.estado.toString())) {
            this.estado = state.OFF;
            double factor = 0;
            switch (this.mode.toString()) {
                case ("COLD"):
                    factor = 10;
                case ("NEUTRAL"):
                    factor = 20;
                case ("WARM"):
                    factor = 30;
            }
            this.consumo = 0.5 + factor;
        }
    }

    /**
     * Trocar a data de fim
     * @param data Nova data
     */
    public void goToData(LocalDate data){
        this.dataFin=data;
    }

    /**
     * Modificar estado de uma lampada
     * @param state Estado a colocar numa lâmpada
     * @return estado da lampada
     */
    public SmartBulb.state toState(String state){
        SmartBulb.state ret = switch (state.toUpperCase()) {
            case ("ON") -> SmartBulb.state.ON;
            case ("OFF") -> SmartBulb.state.OFF;
            default -> SmartBulb.state.OFF;
        };

        return ret;
    }
}


