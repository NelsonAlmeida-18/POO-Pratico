//package src;// idea necessarry

import java.time.LocalDateTime;
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
    private LocalDateTime ligadoInit;
    private LocalDateTime dataFin;

    //CLASS CONSTRUCTORS

    public SmartSpeaker() {
        this.id = getId();
        this.volume = 10;
        this.estacao = "";
        this.estado = state.OFF;
        this.consumo = 0;
        this.ligadoInit = LocalDateTime.now();
        this.dataFin = LocalDateTime.now();
    }

    public SmartSpeaker(int id,int volume, String estacao, Marca marca, double consumo) {
        this.id = id;
        this.volume = volume;
        this.estacao = estacao;
        this.marca = marca;
        this.estado = state.OFF;
        this.consumo = consumo;
        this.ligadoInit = LocalDateTime.now();
        this.dataFin = LocalDateTime.now();
    }

    public SmartSpeaker(SmartSpeaker speaker) {
        this.id = speaker.getId();
        this.volume = speaker.getVolume();
        this.estacao = speaker.getEstacao();
        this.estado = speaker.getEstado();
        this.consumo = speaker.getConsumo();
        this.ligadoInit = speaker.getLigadoInit();
        this.dataFin = speaker.getDataFin();
    }

    //GETTERS & SETTERS

    @Override
    public int getId() {return this.id;}

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

    public LocalDateTime getLigadoInit() {
        return this.ligadoInit;
    }

    public void setLigadoInit(LocalDateTime ligadoInit) {
        this.ligadoInit = ligadoInit;
    }

    public LocalDateTime getDataFin() {
        return this.dataFin;
    }

    public void setDataFin(LocalDateTime dataFin) {
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

    @Override
    public String toString(){
        //"\nMarca: \n"+this.marca+
        return "\nEstado atual: \n" + this.estado + "Estação atual: \n" + this.estacao + "\nVolume: " + this.volume + "\n";
    }

    public void setState(SmartSpeaker.state est) {
        switch (est.toString()) {
            case ("ON"):
                this.ligadoInit = LocalDateTime.now();
                this.estado = est;
            case ("OFF"):
                this.consumo = ChronoUnit.MINUTES.between(this.ligadoInit, this.dataFin) * this.volume;
                this.estado = est;
        }
    }

    public void goToData(LocalDateTime data){
        this.dataFin=data;
    }

    //funções que nem sei se são realmente necessárias but why not

    public void volumeUp(){
        if(this.volume < 20) this.setVolume(this.volume+1);
    }

    public void volumeDown(){
        if(this.volume > 0) this.setVolume(this.volume-1);
    }

}