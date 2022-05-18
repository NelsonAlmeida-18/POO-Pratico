package Model;

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

    /**
     * Inicializador de coluna
     */
    public SmartSpeaker() {
        this.id = getID();
        this.volume = 10;
        this.estacao = "";
        this.estado = state.OFF;
        this.consumo = 0;
        this.ligadoInit = LocalDate.now();
        this.dataFin = LocalDate.now();
    }

    /**
     * Inicializador de coluna
     * @param id id a definir
     * @param volume volume a definir
     * @param estacao estação a definir
     * @param marca marca a definir
     * @param consumo consumo a definir
     */
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

    /**
     * Inicializador de coluna
     * @param id id a definir
     * @param volume volume a definir
     * @param estacao estação a definir
     * @param estado estado a definir
     * @param marca marca a definir
     * @param consumo consumon a definir
     */
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

    /**
     * Duplicador de coluna
     * @param speaker coluna a duplicar
     */
    public SmartSpeaker(SmartSpeaker speaker) {
        this.id = speaker.getID();
        this.volume = speaker.getVolume();
        this.estacao = speaker.getEstacao();
        this.estado = speaker.getEstado();
        this.consumo = speaker.getConsumo();
        this.ligadoInit = speaker.getLigadoInit();
        this.dataFin = speaker.getDataFin();
    }


    /**
     * Getter do id
     * @return id da coluna
     */
    @Override
    public int getID() {return this.id;}

    /**
     * Getter da marca
     * @return marca
     */
    public Marca getMarca() {
        return this.marca;
    }

    /**
     * Setter da marca
     * @param marca marca a definir
     */
    public void setMarca(Marca marca) {
         this.marca = marca;
    }

    /**
     * Getter do volume
     * @return volume da coluna
     */
    public int getVolume() {
        return this.volume;
    }

    /**
     * Setter do volume da coluna
     * @param volume volume a definir
     */
    public void setVolume(int volume) {
        if((volume <= 20) && (volume >= 0)) this.volume = volume;
    }

    /**
     * Getter de estação
     * @return estação
     */
    public String getEstacao() {
        return this.estacao;
    }

    /**
     * Setter de estação
     * @param estacao estação a definir
     */
    public void setEstacao(String estacao) {
        this.estacao = estacao;
    }

    /**
     * Getter do estado
     * @return estado
     */
    public state getEstado() {
        return this.estado;
    }

    /**
     * Setter estado
     * @param estado estado a definir
     */
    public void setEstado(state estado) {
        this.estado = estado;
    }

    /**
     * Setter do consumo
     * @param consumo consumo a definir
     */
    public void setConsumo(double consumo) {
        this.consumo = consumo;
    }

    /**
     * Getter da data de ativação
     * @return data de ativação
     */
    public LocalDate getLigadoInit() {
        return this.ligadoInit;
    }

    /**
     * Setter da data de ativação
     * @param ligadoInit data de ativação a definir
     */
    public void setLigadoInit(LocalDate ligadoInit) {
        this.ligadoInit = ligadoInit;
    }

    /**
     * Getter data de desligar a coluna
     * @return
     */
    public LocalDate getDataFin() {
        return this.dataFin;
    }

    /**
     * Setter da data de desligar a coluna
     * @param dataFin data de desativação da coluna
     */
    public void setDataFin(LocalDate dataFin) {
        this.dataFin = dataFin;
    }

    /**
     * Getter do consumo
     * @return consumo
     */
    @Override
    public double getConsumo() {
        double result;
        if (this.estado == state.OFF) {
            result = 0;
        } else {
            //result = ChronoUnit.MINUTES.between(this.ligadoInit, this.dataFin) * this.volume;//função do consumo por definir
            result = this.marca.getConsumoDiario()+0.3*this.volume;
        }
        return result;
    }

    /**
     * Getter de consumo dentro de determinado periodo do tempo
     * @param data_atual Data atual
     * @param dataSimulacao data de inicial da cortina de tempo
     * @return
     */
    public double getConsumo(LocalDate data_atual, LocalDate dataSimulacao){

        switch (this.estado.toString()) {
            case ("ON") ->
                    this.consumo = ChronoUnit.DAYS.between(data_atual, dataSimulacao) * this.volume * this.marca.getConsumoDiario() * ((Math.random() * 2) + 1);
            case ("OFF") -> this.consumo = 0;
        }
        return this.consumo;
    }

    /**
     * Ligar coluna
     */
    @Override
    public void turnOn() {
        this.setState(SmartSpeaker.state.ON);
    }

    /**
     * Desligar coluna
     */
    @Override
    public void turnOff() {
        this.setState(SmartSpeaker.state.OFF);
    }

    /**
     * Clone a coluna
     * @return coluna clonada
     */
    @Override
    public SmartDevice clone() {
        return new SmartSpeaker(this);
    }

    /**
     * Verificação entre duas colunas
     * @param obj objeto a verificar
     * @return boolean se objeto é a mesma coluna
     */
    public boolean equals(Object obj){
        if(this==obj)
            return true;

        if (obj==null||obj.getClass()!=this.getClass())
            return false;

        SmartSpeaker newC = (SmartSpeaker) obj;
        return (this.id == newC.getID() && this.volume == (newC.getVolume()) && this.estacao==newC.getEstacao()  && this.estado.toString().equals(newC.getEstado().toString())
                &&  this.consumo == (newC.getConsumo()));
    }

    /**
     * toString da coluna
     * @return String com as especificcações da coluna
     */
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

    /**
     * Setter de estado
     * @param est estado a definir
     */
    public void setState(SmartSpeaker.state est) {
        switch (est.toString()) {
            case ("ON"):
                //this.ligadoInit = LocalDate.now();
                this.estado = est;
            case ("OFF"):
                //this.consumo = ChronoUnit.MINUTES.between(this.ligadoInit, this.dataFin) * this.volume;
                this.estado = est;
        }
    }

    /**
     * Avançar data
     * @param data data para qual avançar
     */
    public void goToData(LocalDate data){
        this.dataFin=data;
    }

    /**
     * Aumentar o volume uma unidade
     */
    public void volumeUp(){
        if(this.volume < 20) this.setVolume(this.volume+1);
    }

    /**
     * Diminuir o volume uma unidade
     */
    public void volumeDown(){
        if(this.volume > 0) this.setVolume(this.volume-1);
    }

    /**
     * Modificar de estado
     * @param state novo estado para modificar
     * @return novo estado
     */
    public SmartSpeaker.state toState(String state){
        SmartSpeaker.state ret = null;

        switch (state.toUpperCase()) {
            case ("ON") -> ret = SmartSpeaker.state.ON;
            case ("OFF") -> ret = SmartSpeaker.state.OFF;
            default -> {
            }
        }

        return ret;
    }
}   