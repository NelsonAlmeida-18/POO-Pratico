import java.time.LocalDateTime;

public class Fatura {
    private LocalDateTime dataInit;
    private LocalDateTime dataFim;
    private double KwsConsumidos;
    private double valorDaFatura;


    //Não faz sentido criar uma fatura vazia
    public Fatura(LocalDateTime dataInit, LocalDateTime dataFim, double KwsConsumidos, double valorDaFatura){
        this.dataInit=dataInit;
        this.dataFim=dataFim;
        this.KwsConsumidos=KwsConsumidos;
        this.valorDaFatura=valorDaFatura;
    }

    public double getKwsConsumidos(){return this.KwsConsumidos;}

    public double getValorDaFatura(){return this.valorDaFatura;}

    public LocalDateTime getDataInicial(){return this.dataInit;}

    public LocalDateTime getDataFinal(){return this.dataFim;}

}
