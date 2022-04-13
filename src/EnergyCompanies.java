package src;

public class EnergyCompanies{

    private String nome_companhia;
    private Float taxa;

    public EnergyCompanies(){
        this.nome_companhia = "";
        this.taxa = 0.0f; // insert default taxa
    }

    public EnergyCompanies(String nome){
        this.nome_companhia = nome;
        this.taxa = 0.0f; // insert default taxa
    }

    public EnergyCompanies(String nome, float taxa){
        this.nome_companhia = nome;
        this.taxa = taxa; // insert default taxa
    }

    public void setNome_companhia(String nome_companhia) {
        this.nome_companhia = nome_companhia;
    }

    public String getNome_companhia() {
        return nome_companhia;
    }

    public void setTaxa(Float taxa) {
        this.taxa = taxa;
    }

    public Float getTaxa() {
        return taxa;
    }

}