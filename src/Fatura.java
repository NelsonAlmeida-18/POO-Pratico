import java.time.LocalDate;

public class Fatura {
    private LocalDate dataInit;
    private LocalDate dataFim;
    private double KwsConsumidos;
    private double valorDaFatura;


    //Não faz sentido criar uma fatura vazia
    public Fatura(LocalDate dataInit, LocalDate dataFim, double KwsConsumidos, double valorDaFatura){
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

    public LocalDate getDataInicial(){return this.dataInit;}

    public LocalDate getDataFinal(){return this.dataFim;}

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
        sb.append("Início de contagem: ");
        sb.append(this.dataInit.toString());
        sb.append("\tTérmino de contagem: ");
        sb.append(this.dataFim.toString());
        sb.append("\tkWs Consumidos na casa: ");
        sb.append(this.KwsConsumidos);
        sb.append("\tTotal a pagar (IVA incl.): ");
        sb.append(this.valorDaFatura);
        sb.append("\n");
        return sb.toString();
    }
}
