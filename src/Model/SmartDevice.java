package Model;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class SmartDevice implements Serializable{

    /**
     * Getter abstrato do id do dispositivo
     * @return id do dispositivo
     */
    public abstract int getID(); //para definir nas subclasses

    /**
     * Avançar data do dispositivo
     * @param data data a vançar
     */
    public abstract void goToData(LocalDate data);

    /**
     * Getter de consumo
     * @return consumo
     */
    public abstract double getConsumo();

    /**
     * Getter de consumo em determinada cortina do cosnumo
     * @param data_atual data atual da simulação
     * @param data_sim data inicial da simulação
     * @return consumo dentro de determinado tempo
     */
    public abstract double getConsumo(LocalDate data_atual, LocalDate data_sim);

    /**
     * Ligar dispositivo
     */
    public abstract void turnOn();

    /**
     * Desligar dispositivo
     */
    public abstract void turnOff();

    /**
     * Clone de dispositvo
     * @return dispositivo clonado
     */
    public abstract SmartDevice clone();

    /**
     * toString de dispositivo
     * @return String com os componentes do dispositivo
     */
    public abstract String toString();

}
