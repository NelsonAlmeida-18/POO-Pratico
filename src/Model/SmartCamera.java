package Model;

import java.lang.StringBuilder;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


/**
 * Uma SmartCamera é um SmartDevice que além de ligar e desligar permite também
 * gravar videos de segurança.
 *
 * Obtém a resolução dos ficheiros
 * Tamanho do ficheiro de videos
 * Consumo Energético
 *
 */
public class SmartCamera extends SmartDevice {

    private int id;
    private float resolucao; 
    private int tamanho_ficheiros;
    public enum state{
        ON,
        OFF
    }
    private state estado;
    private double consumo;
    private LocalDate ligadoInit;
    private LocalDate dataFin;

    /**
     * Inicialização da camara
     * @param id id a definir
     * @param resolucao resolução a definir
     * @param tamanhoFicheiros tamanho dos ficheiros a definir
     * @param estado estado a definir
     * @param consumo consumo a definir
     * @param ligadoI data de ativar a camara a definir
     */
    public SmartCamera(int id,float resolucao, int tamanhoFicheiros, state estado, float consumo, LocalDate ligadoI){
        this.id = id;
        this.resolucao=resolucao;
        this.tamanho_ficheiros= tamanhoFicheiros;
        this.estado=estado;
        this.consumo=consumo;
        this.ligadoInit=ligadoI;
    }

    /**
     * Inicializador da camara
     * @param id id a definir
     * @param resolucao resolução a definir
     * @param tamanhoFicheiros tamanho de ficheiro a definir
     * @param consumo consumo a definir
     */
    public SmartCamera(int id, String resolucao, int tamanhoFicheiros, double consumo){
 
        String[] wxh = resolucao.split("x");
        float width = Float.parseFloat(wxh[0]);
        float height = Float.parseFloat(wxh[1]);
        this.id = id;
        this.resolucao=width*height/1000000;
        this.tamanho_ficheiros= tamanhoFicheiros;
        this.estado=state.OFF;
        this.consumo=consumo;
        this.ligadoInit=LocalDate.now();
    }

    /**
     * Inicializador da camera
     * @param id id a definir
     * @param width comprimento a definir
     * @param height largura a definir
     * @param tamanhoFicheiros tamanho de ficheiros a definir
     * @param estado estado a definir
     */
    public SmartCamera(int id, float width,float height, int tamanhoFicheiros, String estado){
        this.id = id;
        this.resolucao=width*height/1000000;
        this.tamanho_ficheiros= tamanhoFicheiros;
        this.estado=toState(estado);
        this.consumo=resolucao*tamanhoFicheiros;
        this.ligadoInit=LocalDate.now();
    }

    /**
     * Inicializador de camara
     * @param id id a definir
     * @param width compprimento a definir
     * @param height altura a definir
     * @param tamanhoFicheiros tamanho de ficheiros a definir
     * @param consumo consumo a definir
     */
    public SmartCamera(int id, float width,float height, int tamanhoFicheiros, double consumo){
        this.id = id;
        this.resolucao=width*height/1000000;
        this.tamanho_ficheiros= tamanhoFicheiros;
        this.estado=state.OFF;
        this.consumo=consumo;
        this.ligadoInit=LocalDate.now();
    }

    /**
     * Inicializador de camara
     * @param id id a definir
     * @param width compprimento a definir
     * @param height altura a definir
     * @param tamanhoFicheiros tamanho de ficheiros a definir
     * @param estado estado a definir
     * @param consumo consumo a definir
     */
    public SmartCamera(int id, float width,float height, int tamanhoFicheiros, state estado, float consumo){
        this.id = id;
        this.resolucao=width*height/1000000;
        this.tamanho_ficheiros= tamanhoFicheiros;
        this.estado=estado;
        this.consumo=consumo;
        this.ligadoInit=LocalDate.now();
    }

    /**
     * Camara a duplicar
     * @param sc camara a ser duplicado
     */
    public SmartCamera(SmartCamera sc){
        this.id=sc.getID();
        this.resolucao=sc.getResolucao();
        this.tamanho_ficheiros=sc.getFileSize();
        this.estado = sc.getState();
        this.consumo=sc.getConsumo();
        this.ligadoInit=sc.getLastLigado();
    }

    /**
     * Getter de consumo da lampada
     * @param data_atual data atual da camara
     * @param dataSimulacao data de inicialização da camara
     * @return consumo
     */
    public double getConsumo(LocalDate data_atual, LocalDate dataSimulacao){
        switch(this.estado.toString()){
            case("ON"):
                this.consumo = ChronoUnit.DAYS.between(data_atual, dataSimulacao)*this.resolucao*((Math.random()*2)+1);
            case("OFF"):
                this.consumo = 0;
        }
        return this.consumo;
    }

    /**
     * Getter do id
     * @return id da camara
     */
    @Override
    public int getID() {return this.id;}

    /**
     * Getter da última vez ligada
     * @return Data da última vez ligada
     */
    public LocalDate getLastLigado(){
        return this.ligadoInit;
    }

    /**
     * Getter da resolução
     * @return resolução
     */
    public float getResolucao(){
        return this.resolucao;
    }

    /**
     * Getter do tamanho do ficheiros
     * @return tamanho dos ficheros
     */
    public int getFileSize(){
        return this.tamanho_ficheiros;
    }

    /**
     * Getter do estado da camara
     * @return estado
     */
    public state getState(){
        return this.estado;
    }

    /**
     * Getter do consumo da camara
     * @return consumo
     */
    public double getConsumo(){
        if (this.estado.equals(state.ON))
            return this.tamanho_ficheiros*resolucao;
        else
            return 0;
    }

    /**
     * Setter de resolução
     * @param width comprimento da resolução a definir
     * @param height largura da resolução a definir
     */
    public void setResolucao(float width, float height){
        this.resolucao=(width*height)/10000000;
    }

    /**
     * Setter da resolução
     * @param res resolução a definir
     */
    public void setResolucao(float res){
        this.resolucao=res;
    }

    /**
     * Setter do tamanho de ficheiros
     * @param file tamanho de ficheiros a definir'«
     */
    public void setFileSize(int file){
        this.tamanho_ficheiros=file;
    }

    /**
     * Setter do estado a definir
     * @param est estado a definir
     */
    public void setState(state est){this.estado = est;}

    /**
     * Trocar o estado
     */
    public void switchState(){
        switch (this.estado.toString()) {
            case ("ON") -> this.estado = state.OFF;
            case ("OFF") -> this.estado = state.ON;
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
     * Comparar camara a um objeto
     * @param obj Objeto a comparar
     * @return boolean de comparação
     */
    public boolean equals(Object obj){
        if(this==obj)
            return true;
        
        if (obj==null||obj.getClass()!=this.getClass())
            return false;
        
        SmartCamera newC = (SmartCamera) obj;
        return (this.id == newC.getID() && this.resolucao == (newC.getResolucao()) && this.tamanho_ficheiros==newC.getFileSize()  && this.estado.toString().equals(newC.getState().toString())  &&  this.consumo == (newC.getConsumo()));
    }

    /**
     * toString de uma camara
     * @return String com as especificações de uma camara
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n[SmartCamera]");
        sb.append("\nID: ");
        sb.append(this.id);
        sb.append("\nResolução: ");
        sb.append(this.resolucao);
        sb.append("\nTamanho dos ficheiros:");
        sb.append(this.tamanho_ficheiros);
        sb.append("\nEstado: ");
        sb.append(this.estado);
        sb.append("\n");
        return sb.toString();
    }

    /**
     * Clone de camara
     * @return camara clonada
     */
    public SmartCamera clone(){
        return new SmartCamera(this);
    }

    /**
     * Ligar dispositivo
     */
    public void turnOn(){
        this.estado = state.ON;
        setState(state.ON);
    }

    /**
     * Desligar dispositivo
     */
    public void turnOff(){
        this.estado = state.OFF;
        setState(state.OFF);
    }

    /**
     * Trocar para determinada data
     * @param data nova data
     */
    public void goToData(LocalDate data){
        this.dataFin=data;
    }

    /**
     * Trocar para determinado estado
     * @param state estado a definir
     * @return estado da camara
     */
    public SmartCamera.state toState(String state){
        SmartCamera.state ret = SmartCamera.state.OFF;

        switch (state) {
            case ("ON") -> ret = SmartCamera.state.ON;
            case ("OFF") -> ret = SmartCamera.state.OFF;
            default -> {
            }
        }

        return ret;
    }
}   
