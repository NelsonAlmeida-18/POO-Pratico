<<<<<<< HEAD:src/Marca.java
import java.util.ArrayList;
import java.util.List;
=======
//package tester; // idea necessarry

>>>>>>> ba6681d761c618bb1adebd63177e6dc5299872fe:tester/Marca.java
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
