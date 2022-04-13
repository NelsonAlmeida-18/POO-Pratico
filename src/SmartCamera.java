package src;

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

    public void setResolucao(String resolucao) {
        this.resolucao = resolucao;
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

}
