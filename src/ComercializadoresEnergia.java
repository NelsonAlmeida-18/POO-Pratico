import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;


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
        Map<SmartHouse,List<Fatura>> casinhasNovas=new HashMap<>();
        for(SmartHouse casa:casas.keySet()){
            List<Fatura> casinhas=new ArrayList<>();
            ListIterator<Fatura> iter = casas.get(casa).listIterator();
            while(iter.hasNext()){
                Fatura faturaTemp=iter.next();
                casinhas.add(faturaTemp);
            }
            casinhasNovas.put(casa.clone(), casinhas);
        }
        this.casas=casinhasNovas;
    }



    public ComercializadoresEnergia(ComercializadoresEnergia ce){
        this.nome=ce.getNome();
        this.precoBaseKW = ce.getPrecoBaseKW();
        this.fatorImposto= ce.getFatorImposto();
        this.casas = ce.getCasas();
    }

    public Map<SmartHouse,List<Fatura>> getCasas(){
        Map<SmartHouse,List<Fatura>> casinhasNovas=new HashMap<>();
        for(SmartHouse casa:casas.keySet()){
            List<Fatura> casinhas=new ArrayList<>();
            ListIterator<Fatura> iter = casas.get(casa).listIterator();
            while(iter.hasNext()){
                Fatura faturaTemp=iter.next();
                casinhas.add(faturaTemp);
            }
            casinhasNovas.put(casa.clone(), casinhas);
        }
        return casinhasNovas;
    }

    public void setCasas(Map<SmartHouse,List<Fatura>> casas){
        Map<SmartHouse,List<Fatura>> casinhasNovas=new HashMap<>();
        for(SmartHouse casa:casas.keySet()){
            casinhasNovas.put(casa.clone(), clonaFaturas(casas.get(casa)));
        }
        this.casas=casinhasNovas;
    }

    public void addCasa(SmartHouse casa){
        this.casas.put(casa.clone(),new ArrayList<Fatura>());
    }

    public void addCasa(SmartHouse casa, List<Fatura> faturas){
        this.casas.put(casa.clone(),clonaFaturas(faturas));
    }

    public List<Fatura> clonaFaturas(List<Fatura> faturas){
        List<Fatura> casinhas=new ArrayList<>();
        ListIterator<Fatura> iter = faturas.listIterator();
        while(iter.hasNext()){
            Fatura faturaTemp=iter.next();
            casinhas.add(faturaTemp);
        }
        return casinhas;
    }

    public void removeCasa(SmartHouse casa){
        this.casas.remove(casa);
    }

    public void removeCasa(String morada){
        for (SmartHouse casa:this.casas.keySet()){
            if(casa.getMorada().equals(morada)){
                this.casas.remove(casa);
            }
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


    //TODO ir buscar a primeira casa
    public SmartHouse getCasaMaisGastadora(){
        SmartHouse casaMaisGastadora=new SmartHouse();
        double maxConsumo=0;
        for(SmartHouse casa:this.casas.keySet()){
            double consumo=casa.getConsumoDaCasa();
            if (consumo>maxConsumo){
                maxConsumo=consumo;
                casaMaisGastadora=casa;
            }  
        }
        return casaMaisGastadora;
    }


    //REVER ESTA TRETA
    // public SmartHouse getCasaMaisGastadora(LocalDateTime init, LocalDateTime fim){
    //     ListIterator<SmartHouse> iter = this.casas.listIterator();
    //     SmartHouse casaMaisGastadora=this.casas.get(0);//vai buscar a primeira casa 
    //     double maxConsumo=0;
    //     while(iter.hasNext()){
    //         SmartHouse casaTemp = iter.next();
    //         double consumo=casaTemp.getConsumoDaCasa();
    //         if (consumo>maxConsumo ){
    //             maxConsumo=consumo;
    //             casaMaisGastadora=casaTemp;
    //         }
    //     }
    //     return casaMaisGastadora;
    // }

    //ver formula do valor de faturacao de cada casa
    public double getFaturacao(){
        double faturacao=0;
        for(List<Fatura> faturasDeCadaCasa:this.casas.values()){
            ListIterator<Fatura> iter = faturasDeCadaCasa.listIterator();
            while(iter.hasNext())
                faturacao+=iter.next().getValorDaFatura();
        }
        return faturacao;
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
