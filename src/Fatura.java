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

    public Fatura(Fatura fat){
        this.dataInit=fat.getDataInicial();
        this.dataFim=fat.getDataFinal();
        this.KwsConsumidos=fat.getKwsConsumidos();
        this.valorDaFatura=fat.getValorDaFatura();
    }

    public double getKwsConsumidos(){return this.KwsConsumidos;}

    public double getValorDaFatura(){return this.valorDaFatura;}

    public LocalDateTime getDataInicial(){return this.dataInit;}

    public LocalDateTime getDataFinal(){return this.dataFim;}

    public boolean equals(Object obj){
        if (this==obj)
            return true;
        
        if(obj==null||this.getClass()!=obj.getClass())
            return false;

        Fatura nF = (Fatura) obj;
        return(this.dataFim.equals(nF.getDataFinal())  &&  
            this.dataInit.equals(nF.getDataInicial())  &&
            this.KwsConsumidos==nF.getKwsConsumidos()  &&
            this.valorDaFatura==nF.getValorDaFatura());
    }

    public Fatura clone(){
        return new Fatura(this);
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Data Inicial da Faturação: ");
        sb.append(this.dataInit.toString());
        sb.append("\nData Final da Faturação: ");
        sb.append(this.dataFim.toString());
        sb.append("\nKws Consumidos nesta casa: ");
        sb.append(this.KwsConsumidos);
        sb.append("\nTotal a pagar (IVA incl.): ");
        sb.append(this.valorDaFatura);
        sb.append("\n");
        return sb.toString();
    }
}
