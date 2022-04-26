import java.util.ArrayList;
import java.util.List;
public class Marca {

    private final String nome;
    private final double consumoDiario;

    public Marca(String nome, double consumo) {
        this.nome = nome;
        this.consumoDiario = consumo;
    }

    public String getNome() {
        return this.nome;
    }

    public double getConsumoDiario() {
        return this.consumoDiario;
    }
}
