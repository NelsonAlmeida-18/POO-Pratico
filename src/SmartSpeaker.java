//package src;// idea necessarry

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Um SmartSpeaker é um SmartDevice que além de ligar e desligar permite também ouvir estações de rádio
 * aumentando e descendo o volume.
 *
 * Obtém a estação atual
 * Obtém o volume
 * Pertence a uma dada marca
 * Consumo Energético
 *
 */

public class SmartSpeaker extends SmartDevice {

    private int id;
    private int volume;
    private String estacao;
    public enum state {
        ON,
        OFF
    }
    private SmartSpeaker.state estado;
    private Marca marca;
    private double consumo;
    private LocalDate ligadoInit;
    private LocalDate dataFin;

    //CLASS CONSTRUCTORS

    public SmartSpeaker() {
        this.id = getID();
        this.volume = 10;
        this.estacao = "";
        this.estado = state.OFF;
        this.consumo = 0;
        this.ligadoInit = LocalDate.now();
        this.dataFin = LocalDate.now();
    }

    public SmartSpeaker(int id,int volume, String estacao, Marca marca, double consumo) {
        this.id = id;
        this.volume = volume;
        this.estacao = estacao;
        this.marca = marca;
        this.estado = state.OFF;
        this.consumo = consumo;
        this.ligadoInit = LocalDate.now();
        this.dataFin = LocalDate.now();
    }

    public SmartSpeaker(int id,int volume, String estacao, String estado, Marca marca, double consumo) {
        this.id = id;
        this.volume = volume;
        this.estacao = estacao;
        this.estado = toState(estado);
        this.marca = marca;
        this.estado = state.OFF;
        this.consumo = consumo;
        this.ligadoInit = LocalDate.now();
        this.dataFin = LocalDate.now();
    }

    public SmartSpeaker(SmartSpeaker speaker) {
        this.id = speaker.getID();
        this.volume = speaker.getVolume();
        this.estacao = speaker.getEstacao();
        this.estado = speaker.getEstado();
        this.consumo = speaker.getConsumo();
        this.ligadoInit = speaker.getLigadoInit();
        this.dataFin = speaker.getDataFin();
    }

    //GETTERS & SETTERS

    @Override
    public int getID() {return this.id;}

    public Marca getMarca() {
        return this.marca;
    }

    public Marca setMarca() {
        return this.marca;
    }

    public int getVolume() {
        return this.volume;
    }

    public void setVolume(int volume) {
        if((volume <= 20) && (volume >= 0)) this.volume = volume;
    }

    public String getEstacao() {
        return this.estacao;
    }

    public void setEstacao(String estacao) {
        this.estacao = estacao;
    }

    public state getEstado() {
        return this.estado;
    }

    public void setEstado(state estado) {
        this.estado = estado;
    }

    public void setConsumo(double consumo) {
        this.consumo = consumo;
    }

    public LocalDate getLigadoInit() {
        return this.ligadoInit;
    }

    public void setLigadoInit(LocalDate ligadoInit) {
        this.ligadoInit = ligadoInit;
    }

    public LocalDate getDataFin() {
        return this.dataFin;
    }

    public void setDataFin(LocalDate dataFin) {
        this.dataFin = dataFin;
    }

    @Override
    public double getConsumo() {
        double result;
        if (this.estado == state.OFF) {
            result = this.consumo;
        } else {
            result = ChronoUnit.MINUTES.between(this.ligadoInit, this.dataFin) * this.volume;//função do consumo por definir
        }
        return result;
    }

    public double getConsumo(LocalDate data_atual, LocalDate dataSimulacao){

        switch(this.estado.toString()){
            case("ON"):
                this.consumo = ChronoUnit.DAYS.between(data_atual, dataSimulacao)*this.volume*this.marca.getConsumoDiario()*((Math.random()*2)+1);

            case("OFF"):
                this.consumo = 0;
        }
        return this.consumo;
    }

    @Override
    public void turnOn() {
        this.setState(SmartSpeaker.state.ON);
    }

    @Override
    public void turnOff() {
        this.setState(SmartSpeaker.state.OFF);
    }

    @Override
    public SmartDevice clone() {
        return new SmartSpeaker(this);
    }

    public boolean equals(Object obj){
        if(this==obj)
            return true;

        if (obj==null||obj.getClass()!=this.getClass())
            return false;

        SmartSpeaker newC = (SmartSpeaker) obj;
        return (this.id == newC.getID() && this.volume == (newC.getVolume()) && this.estacao==newC.getEstacao()  && this.estado.toString().equals(newC.getEstado().toString())
                &&  this.consumo == (newC.getConsumo()));
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n[SmartSpeaker]");
        sb.append("\nID: ");
        sb.append(this.id);
        sb.append("\nEstado: ");
        sb.append(this.estado);
        sb.append("\nEstação: ");
        sb.append(this.estacao);
        sb.append("\nVolume: ");
        sb.append(this.volume);
        sb.append("\n");

        return sb.toString();
    }

    public void setState(SmartSpeaker.state est) {
        switch (est.toString()) {
            case ("ON"):
                this.ligadoInit = LocalDate.now();
                this.estado = est;
            case ("OFF"):
                this.consumo = ChronoUnit.MINUTES.between(this.ligadoInit, this.dataFin) * this.volume;
                this.estado = est;
        }
    }

    public void goToData(LocalDate data){
        this.dataFin=data;
    }

    //funções que nem sei se são realmente necessárias but why not

    public void volumeUp(){
        if(this.volume < 20) this.setVolume(this.volume+1);
    }

    public void volumeDown(){
        if(this.volume > 0) this.setVolume(this.volume-1);
    }

    public SmartSpeaker.state toState(String state){
        SmartSpeaker.state ret = SmartSpeaker.state.OFF;
        
        switch(state){
            case ("ON"):
                ret = SmartSpeaker.state.ON;
            break;

            case ("OFF"):
                ret = SmartSpeaker.state.OFF;
            break;

            default:
            
        }

        return ret;
    }
}   