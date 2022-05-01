
import java.util.ArrayList;
import java.util.List;

public class Marca {

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

}
