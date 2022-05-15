package Model;

import java.io.Serializable;

public class Marca implements Serializable {

    private String nome;
    private double consumoDiario;

    public Marca(String nome, double consumo) {
        this.nome = nome;
        this.consumoDiario = consumo;
    }

    public Marca(String nome) {
        this.nome = nome;
        this.consumoDiario = 0.72; //coluna com consumo de 30W * 24h em kWh
    }

    public String getNome() {
        return this.nome;
    }

    public double getConsumoDiario() {
        return this.consumoDiario;
    }

    public void setConsumoDiario(double consumoDiario) {
        this.consumoDiario = consumoDiario;
    }

    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("NOME: ");
        sb.append(this.nome);
        sb.append("\tCONSUMO DIÁRIO: ");
        sb.append(this.consumoDiario);
        sb.append("\n");
        return sb.toString();
    }

}
