
import java.lang.StringBuilder;
import java.time.LocalDateTime;
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

    private float resolucao; 
    private double tamanho_ficheiros; 
    public enum state{
        ON,
        OFF
    }
    private state estado;
    private double consumo;
    private LocalDateTime ligadoInit;
    private LocalDateTime dataFin;

    public SmartCamera(){
        this.resolucao=0;
        this.tamanho_ficheiros=0;
        this.estado=state.ON;
        this.consumo=0;
        this.ligadoInit=LocalDateTime.now();
        this.dataFin=LocalDateTime.now();
    }

    public SmartCamera(float resolucao, float tamanhoFicheiros, state estado, float consumo, LocalDateTime ligadoI){
        this.resolucao=resolucao;
        this.tamanho_ficheiros= tamanhoFicheiros;
        this.estado=estado;
        this.consumo=consumo;
        this.ligadoInit=ligadoI;
    }

    public SmartCamera(float width,float height, float tamanhoFicheiros, state estado, float consumo, LocalDateTime ligadoI){
        this.resolucao=width*height/1000000;
        this.tamanho_ficheiros= tamanhoFicheiros;
        this.estado=estado;
        this.consumo=consumo;
        this.ligadoInit=ligadoI;
    }

    public SmartCamera(SmartCamera sc){
        this.resolucao=sc.getResolucao();
        this.tamanho_ficheiros=sc.getFileSize();
        this.estado = sc.getState();
        this.consumo=sc.getConsumo();
        this.ligadoInit=sc.getLastLigado();
    }

    public LocalDateTime getLastLigado(){
        return this.ligadoInit;
    }

    public float getResolucao(){
        return this.resolucao;
    }

    public double getFileSize(){
        return this.tamanho_ficheiros;
    }

    public state getState(){
        return this.estado;
    }

    public double getConsumo(){
        if (this.estado==state.OFF)
            return this.consumo;
        else{
            return ChronoUnit.MINUTES.between(this.ligadoInit, this.dataFin)*this.resolucao*((Math.random()*2)+1);
        }                                               //tempo de gravacao, resolucao, bitrate
    }

    public void setResolucao(float width, float height){
        this.resolucao=(width*height)/10000000;
    }

    public void setResolucao(float res){
        this.resolucao=res;
    }

    public void setFileSize(float file){
        this.tamanho_ficheiros=file;
    }

    public void setState(state est){
        switch(est.toString()){
            case("ON"):
                this.ligadoInit=LocalDateTime.now();
                this.estado = est;  
            case("OFF"):                                                                                      //bitrate encoder random pois pode depender da quantidade de movimento e densidade populacional da imagem
                this.consumo = ChronoUnit.MINUTES.between(this.ligadoInit, this.dataFin)*this.resolucao*Math.random();
                this.estado = est;
        }
    }

    //nao sei se é relevante poder alterar o consumo
    public void setConsumo(float con){
        this.consumo=con;
    }


    public boolean equals(Object obj){
        if(this==obj)
            return true;
        
        if (obj==null||obj.getClass()!=this.getClass())
            return false;
        
        SmartCamera newC = (SmartCamera) obj;
        return (this.resolucao == (newC.getResolucao()) && this.tamanho_ficheiros==newC.getFileSize()  && this.estado.toString().equals(newC.getState().toString())  &&  this.consumo == (newC.getConsumo()));
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Resolução da camera: \n");
        sb.append(this.resolucao);
        sb.append("\nTamanho dos ficheiros: \n");
        sb.append(this.tamanho_ficheiros);
        sb.append("\nEstado da camera: ");
        sb.append(this.estado);
        sb.append("\n");
        return sb.toString();
    }

    public SmartCamera clone(){
        return new SmartCamera(this);
    }

    //funcoes da ficha

    public void turnOn(){
        this.estado = state.ON;
        setState(state.ON);
    }

    public void turnOff(){
        this.estado = state.OFF;
        setState(state.OFF);
    }

    public void goToData(LocalDateTime data){
        this.dataFin=data;
    }
}
