import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.io.Serializable;
import java.time.LocalDateTime;



public class ComercializadoresEnergia implements Serializable {

    private String nome;
    private double precoBaseKW;
    private double fatorImposto;
    private Map<SmartHouse,List<Fatura>> casas;
    //private List<SmartHouse> casas;

    public ComercializadoresEnergia(String nome){
        this.nome=nome;
        this.precoBaseKW= 0.142;
        this.fatorImposto= 0.001;
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

    public ComercializadoresEnergia clone(){
        return new ComercializadoresEnergia(this);
    }


    public void geraFatura(SmartHouse casa){
        if (this.casas.containsKey(casa)){
            List<Fatura> faturas = this.casas.get(casa);
            Fatura newFatura = new Fatura(LocalDateTime.now(), LocalDateTime.now(), casa.getConsumoDaCasa(), casa.getConsumoDaCasa()*this.fatorImposto*this.precoBaseKW);
            faturas.add(newFatura);
        }
        else{
            List<Fatura> faturas = new ArrayList<>();
            Fatura newFatura = new Fatura(LocalDateTime.now(), LocalDateTime.now(), casa.getConsumoDaCasa(), casa.getConsumoDaCasa()*this.fatorImposto*this.precoBaseKW);
            faturas.add(newFatura);
            this.casas.put(casa,faturas);
        }
    }

    public List<Fatura> getFaturas(SmartHouse casa){
        List<Fatura> faturas = new ArrayList<>();
        if(this.casas.containsKey(casa)){
            ListIterator<Fatura> iter = this.casas.get(casa).listIterator();
            while(iter.hasNext())
                faturas.add(iter.next().clone());
        }
        return faturas;
    }

    public void terminaContrato(SmartHouse casa){
        removeCasa(casa);
    }

    public double getPrecoPerDevice(SmartDevice sd){
        return (this.precoBaseKW*sd.getConsumo()*(1+this.fatorImposto))*0.9;
    }

    public SmartHouse getCasaMaisGastadora(){
        SmartHouse casaMaisGastadora=null;
        double maxConsumo=0;
        for(SmartHouse casa:this.casas.keySet()){
            double consumo=casa.getConsumoDaCasa();
            if (consumo>maxConsumo  ||  casaMaisGastadora==null){
                maxConsumo=consumo;
                casaMaisGastadora=casa;
            }  
        }
        return casaMaisGastadora;
    }

    public SmartHouse getCasaMaisGastadora(LocalDateTime dataInit, LocalDateTime dataFin){
        SmartHouse casaMaisGastadora=null;
        double maxConsumo=0;
        for(SmartHouse casa:this.casas.keySet()){
            List<Fatura> faturas = this.casas.get(casa);
            ListIterator<Fatura> iter = faturas.listIterator();
            while(iter.hasNext()){
                Fatura faturaTemp = iter.next();
                if(faturaTemp.getDataInicial().isBefore(dataInit)  &&  faturaTemp.getDataFinal().isAfter(dataFin)){//falta ver se as datas coincidem
                    if(faturaTemp.getKwsConsumidos()>maxConsumo ||  casaMaisGastadora==null){//será que é assim? assim estou a comparar consumos de kws e não em euros
                        casaMaisGastadora=casa;
                        maxConsumo=faturaTemp.getKwsConsumidos();
                    }
                }
            }
        }
        return casaMaisGastadora;
    }


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

    public String listOfClientes(){
        StringBuilder sb = new StringBuilder();
        
        for(SmartHouse casa:this.casas.keySet()){
            sb.append(casa.getMorada());  //TODO Mudar de get morada para get ID
            sb.append(this.casas.get(casa).toString());
        }
        
        return sb.toString();
    }

    public boolean equals(Object obj){
        if (this==obj) return true;
        if (obj==null||this.getClass()!=obj.getClass()) return false;
        ComercializadoresEnergia ce = (ComercializadoresEnergia) obj;
        return (this.nome==ce.getNome()  &&  this.precoBaseKW== ce.getPrecoBaseKW() && this.fatorImposto==ce.getFatorImposto());
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("NOME: ");
        sb.append(this.nome);
        sb.append("\tPREÇO BASE KWH: ");
        sb.append(this.precoBaseKW);
        sb.append("\tIMPOSTO ENERGÉTICO: ");
        sb.append(this.fatorImposto);
        sb.append("\tNo. CLIENTES: ");
        /* for(SmartHouse casa:this.casas.keySet()){
            sb.append(casa.getMorada());  //TODO Mudar de get morada para get ID
            sb.append(this.casas.get(casa).toString());
        } */
        sb.append(this.casas.size());
        return sb.toString();
    }

}
