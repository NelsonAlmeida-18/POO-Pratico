import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.time.LocalDateTime;

public class ComercializadoresEnergia {

    private String nome;
    private double precoBaseKW;
    private double fatorImposto;
    private Map<SmartHouse,List<Fatura>> casas;
    //private List<SmartHouse> casas;

    public ComercializadoresEnergia(){
        this.nome="";
        this.precoBaseKW=0;
        this.fatorImposto=0;
        this.casas = new HashMap<>();
    }
    public ComercializadoresEnergia(String nome,double precoBaseKW, double fatorImposto){
        this.nome=nome;
        this.precoBaseKW=precoBaseKW;
        this.fatorImposto=fatorImposto;
        this.casas = new HashMap<>();
    }

    public ComercializadoresEnergia(String nome,double precoBaseKW, double fatorImposto,Map<SmartHouse,List<Fatura>> casas){
        this.nome=nome;
        this.precoBaseKW=precoBaseKW;
        this.fatorImposto=fatorImposto;
        List<SmartHouse> casinhas=new ArrayList<>();
        ListIterator<SmartHouse> iter = casas.listIterator();
        while(iter.hasNext()){
            SmartHouse casaTemp=iter.next();
            casinhas.add(casaTemp.clone());
        }
        this.casas=casinhas;
    }



    public ComercializadoresEnergia(ComercializadoresEnergia ce){
        this.nome=ce.getNome();
        this.precoBaseKW = ce.getPrecoBaseKW();
        this.fatorImposto= ce.getFatorImposto();
        this.casas = ce.getCasas();
    }

    public List<SmartHouse> getCasas(){
        List<SmartHouse> casinhas=new ArrayList<>();
        ListIterator<SmartHouse> iter = this.casas.listIterator();
        while(iter.hasNext()){
            SmartHouse casaTemp=iter.next();
            casinhas.add(casaTemp.clone());
        }
        return casinhas;
    }

    public void setCasas(List<SmartHouse> casas){
        List<SmartHouse> casinhas=new ArrayList<>();
        ListIterator<SmartHouse> iter = casas.listIterator();
        while(iter.hasNext()){
            SmartHouse casaTemp=iter.next();
            casinhas.add(casaTemp.clone());
        }
        this.casas=casinhas;
    }

    public void addCasa(SmartHouse casa){
        this.casas.add(casa.clone());
    }

    public void removeCasa(SmartHouse casa){
        this.casas.remove(casa);
    }

    public void removeCasa(String morada){
        ListIterator<SmartHouse> iter = this.casas.listIterator();
        while(iter.hasNext()){
            SmartHouse casaTemp = iter.next();
            if (casaTemp.getMorada().equals(morada))
                this.casas.remove(casaTemp);
        }
    }

    public double getPrecoBaseKW(){return this.precoBaseKW;}

    public String getNome(){return this.nome;}

    public void setNome(String nome){ this.nome=nome;}

    public double getFatorImposto(){return this.fatorImposto;}

    /**/
    //public double getPrecoDiaPorDispositivo(String id){return 1.0;}

    public ComercializadoresEnergia clone(){
        return new ComercializadoresEnergia(this);
    }

    public double getPrecoPerDevice(SmartDevice sd){
        return (this.precoBaseKW*sd.getConsumo()*(1+this.fatorImposto))*0.9;
    }

    public SmartHouse getCasaMaisGastadora(){
        ListIterator<SmartHouse> iter = this.casas.listIterator();
        SmartHouse casaMaisGastadora=this.casas.get(0);//vai buscar a primeira casa 
        double maxConsumo=0;
        while(iter.hasNext()){
            SmartHouse casaTemp = iter.next();
            double consumo=casaTemp.getConsumoDaCasa();
            if (consumo>maxConsumo){
                maxConsumo=consumo;
                casaMaisGastadora=casaTemp;
            }
        }
        return casaMaisGastadora;
    }


    //REVER ESTA TRETA
    public SmartHouse getCasaMaisGastadora(LocalDateTime init, LocalDateTime fim){
        ListIterator<SmartHouse> iter = this.casas.listIterator();
        SmartHouse casaMaisGastadora=this.casas.get(0);//vai buscar a primeira casa 
        double maxConsumo=0;
        while(iter.hasNext()){
            SmartHouse casaTemp = iter.next();
            double consumo=casaTemp.getConsumoDaCasa();
            if (consumo>maxConsumo ){
                maxConsumo=consumo;
                casaMaisGastadora=casaTemp;
            }
        }
        return casaMaisGastadora;
    }

    //ver formula do valor de faturacao de cada casa
    public double getFaturacao(){
        ListIterator<SmartHouse> iter = this.casas.listIterator();
        double consumo=0;
        while(iter.hasNext()){
            consumo+=iter.next().getConsumoDaCasa()*precoBaseKW*fatorImposto;
        }
        return consumo;
    }

    public boolean equals(Object obj){
        if (this==obj) return true;
        if (obj==null||this.getClass()!=obj.getClass()) return false;
        ComercializadoresEnergia ce = (ComercializadoresEnergia) obj;
        return (this.nome.equals(ce.getNome())  &&  this.precoBaseKW== ce.getPrecoBaseKW() && this.fatorImposto==ce.getFatorImposto());
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Nome da Empresa: ");
        sb.append(this.nome);
        sb.append("\nPreço base do KW: ");
        sb.append(this.precoBaseKW);
        sb.append("\nFator do Imposto energético: ");
        sb.append(this.fatorImposto);
        sb.append("\n");
        return sb.toString();
    }

}
