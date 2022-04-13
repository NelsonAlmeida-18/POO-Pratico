package src;
import java.lang.StringBuilder;


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

    private String resolucao; // posteriormente definir resolução e utilizar 'final'
    private float tamanho_ficheiros; // tamanho da pasta com os ficheiros (guardar em MB? GB?)

    public SmartCamera(){
        this.tamanho_ficheiros = 0;
        this.resolucao = ""; // mudar para resolução 'default'
    }

    public SmartCamera(String resolucao,float ficheiros){
        this.resolucao = resolucao;
        this.tamanho_ficheiros = ficheiros;
    }

    public SmartCamera(SmartCamera cam){
        this.resolucao=cam.getResolucao();
        this.tamanho_ficheiros=cam.getTamanho_ficheiros();
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
        
        SmartCamera newC = (SmartCamera) obj;
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

    public SmartCamera clone(){
        return new SmartCamera(this);
    }
}
