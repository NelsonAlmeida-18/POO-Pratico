package src;
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
public class smartCameraTemp extends SmartDevice {

    private String resolucao; // posteriormente definir resolução e utilizar 'final'
    private float tamanho_ficheiros; // tamanho da pasta com os ficheiros (guardar em MB? GB?)
    private float consumo;
    private LocalDateTime ultimaVezLigado;
    private boolean recording;

    
    private state state;

    public state getState() {
        return this.state;
    }


    public smartCameraTemp(){
        this.tamanho_ficheiros = 0;
        this.resolucao = ""; // mudar para resolução 'default'
        this.consumo = 0;
        this.ultimaVezLigado=LocalDateTime.now();
        this.state = state.OFF;
    }

    public smartCameraTemp(String resolucao,float ficheiros, float consumo, LocalDateTime ultimavezLigada, boolean recording, state state){
        this.resolucao = resolucao;
        this.tamanho_ficheiros = ficheiros;
        this.consumo = consumo;
        this.ultimaVezLigado=ultimavezLigada;
        this.recording=recording;
        this.state=state;
    }

    public smartCameraTemp(smartCameraTemp cam){
        this.resolucao=cam.getResolucao();
        this.tamanho_ficheiros=cam.getTamanho_ficheiros();
        this.consumo = cam.getConsumo();
        this.ultimaVezLigado=cam.getUltimavezLigada();
        this.recording=cam.isRecording();
        this.state=cam.getState();
    }

    public LocalDateTime getUltimavezLigada(){
        return this.ultimaVezLigado;
    }

    public boolean isRecording(){
        return this.recording;
    }



    public void setResolucao(String resolucao) {
        if (resolucao.equals(""))
            this.resolucao=resolucao;
    }

    public String getResolucao() {
        return resolucao;
    }

    public void setTamanho_ficheiros(float tamanho_ficheiros) {
        this.tamanho_ficheiros = tamanho_ficheiros;
    }

    public float getTamanho_ficheiros() {
        return tamanho_ficheiros;
    }

    public void add_Ficheiros(float tamanho_ficheiro_new){
        this.tamanho_ficheiros +=  tamanho_ficheiro_new;
    }

    public void remove_Ficheiros(float tamanho_ficheiros_del){
        this.tamanho_ficheiros -= tamanho_ficheiros_del;
    }

    public boolean equals(Object obj){
        if(this==obj)
            return true;
        
        if (obj==null||obj.getClass()!=this.getClass())
            return false;
        
        smartCameraTemp newC = (smartCameraTemp) obj;
        return (this.resolucao.equals(newC.getResolucao()) && this.tamanho_ficheiros==newC.getTamanho_ficheiros());
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Resolução da camera: \n");
        sb.append(this.resolucao);
        sb.append("\nTamanho dos ficheiros: \n");
        sb.append(this.tamanho_ficheiros);
        sb.append("\n");
        return sb.toString();
    }

    public smartCameraTemp clone(){
        return new smartCameraTemp(this);
    }



    //funcoes da ficha

    public void turnOn(){
        
    }

    public void startRecording(){
        if (this.recording==false){
            this.ultimaVezLigado = LocalDateTime.now();
            this.recording=true;
        }
    }


    //File Size = Bitrate x duration x compression ratio
    //compression ratio a 1 para standard
    public void stopRecording(){
        double bitRate = 0;
        switch(this.resolucao){
            case("4K"):
                bitRate=20;
            case("1080p"):
                bitRate=5;
            case("720p"):
                bitRate=1;
            case("480p"):
                bitRate=0.5;
        }
        this.tamanho_ficheiros+=bitRate*ChronoUnit.MINUTES.between(this.ultimaVezLigado, LocalDateTime.now());
        this.ultimaVezLigado=null;
    }

    //duvida quanto ao tamanho do ficheiro gerado, soma de todos os ficheiros ou do ficheiro de cada dia se tiver
    public float getConsumo(){
        return 0;
    }

}
