<<<<<<< HEAD
=======
//package src; // idea necessarry
>>>>>>> a4197ec99de6bec2a98cf8beeed3c68ef9045a9f

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
