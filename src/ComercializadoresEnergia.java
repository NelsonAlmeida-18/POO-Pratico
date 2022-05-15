import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class ComercializadoresEnergia implements Serializable {

    private String nome;
    private double precoBaseKW;
    private double fatorImposto;
    private Map<SmartHouse,List<Fatura>> casas;
    private int numeroDeClientes;

    public ComercializadoresEnergia(String nome){
        this.nome=nome;
        this.precoBaseKW= 0.142;
        this.fatorImposto= 0.001;
        this.casas = new HashMap<>();
        this.numeroDeClientes=0;
    }

    public ComercializadoresEnergia(String nome,SmartHouse casa,List<Fatura> fat){
        this.nome=nome;
        this.precoBaseKW= 0.142;
        this.fatorImposto= 0.001;
        this.casas = new HashMap<>();
        this.casas.putIfAbsent(casa,fat);
        this.numeroDeClientes=0;
    }
    public ComercializadoresEnergia(String nome,double precoBaseKW, double fatorImposto, int numeroDeClientes){
        this.nome=nome;
        this.precoBaseKW=precoBaseKW;
        this.fatorImposto=fatorImposto;
        this.casas = new HashMap<>();
        this.numeroDeClientes=numeroDeClientes;
    }

    public ComercializadoresEnergia(String nome,double precoBaseKW, double fatorImposto){
        this.nome=nome;
        this.precoBaseKW=precoBaseKW;
        this.fatorImposto=fatorImposto;
        this.casas = new HashMap<>();
        this.numeroDeClientes=0;
    }

    public ComercializadoresEnergia(String nome,double precoBaseKW, double fatorImposto,Map<SmartHouse,List<Fatura>> casas){
        this.nome=nome;
        this.precoBaseKW=precoBaseKW;
        this.fatorImposto=fatorImposto;
        Map<SmartHouse,List<Fatura>> casinhasNovas=new HashMap<>();
        for(SmartHouse casa:casas.keySet()){
            List<Fatura> casinhas = new ArrayList<>(casas.get(casa));
            casinhasNovas.putIfAbsent(casa.clone(), casinhas);
            this.numeroDeClientes+=1;
        }
        this.casas=casinhasNovas;
    }



    public ComercializadoresEnergia(ComercializadoresEnergia ce){
        this.nome=ce.getNome();
        this.precoBaseKW = ce.getPrecoBaseKW();
        this.fatorImposto= ce.getFatorImposto();
        this.casas = ce.getCasas();
        this.numeroDeClientes=ce.getNumeroDeCliente();
    }

    public int getNumeroDeCliente(){return this.numeroDeClientes;}

    public Map<SmartHouse,List<Fatura>> getCasas(){
        Map<SmartHouse,List<Fatura>> casinhasNovas=new HashMap<>();
        for(SmartHouse casa:casas.keySet()){
            List<Fatura> casinhas = new ArrayList<>(casas.get(casa));
            casinhasNovas.put(casa.clone(), casinhas);
        }
        return casinhasNovas;
    }
    public void setCasas(Map<SmartHouse,List<Fatura>> casas){
        Map<SmartHouse,List<Fatura>> casinhasNovas=new HashMap<>();
        for(SmartHouse casa:casas.keySet()){
            casinhasNovas.put(casa.clone(), clonaFaturas(casas.get(casa)));
            this.numeroDeClientes+=1;
        }
        this.casas=casinhasNovas;
    }

    public void addCasa(SmartHouse casa){
        this.casas.put(casa, new ArrayList<>());
        this.numeroDeClientes++;
    }

    public void addCasa(SmartHouse casa, List<Fatura> faturas){
        this.casas.put(casa,clonaFaturas(faturas)); this.numeroDeClientes++;
    }

    public List<Fatura> clonaFaturas(List<Fatura> faturas){
        return new ArrayList<>(faturas);
    }

    public void removeCasa(SmartHouse casa){
        this.casas.remove(casa);
        // casa fica sem luz
        casa.setHouseOFF();
        casa.setCompanhia_eletrica(null);
        this.numeroDeClientes--;
    }

    public void removeCasa(String morada){
        for (SmartHouse casa:this.casas.keySet()){
            if(casa.getMorada().equals(morada)){
                this.casas.remove(casa);
                this.numeroDeClientes--;
            }
        }
    }

    public double getPrecoBaseKW(){return this.precoBaseKW;}

    public String getNome(){return this.nome;}

    public void setNome(String nome){ this.nome=nome;}
    public void setPrecoBaseKW(double preco){ this.precoBaseKW = preco; }
    public void setFatorImposto(double fator){ this.fatorImposto = fator; }
    public double getFatorImposto(){return this.fatorImposto;}



    public Fatura geraFatura(SmartHouse casa, LocalDate data_final){
        Fatura newFatura;
        if (this.casas.containsKey(casa)){
            List<Fatura> faturas = this.casas.get(casa);
            newFatura = new Fatura(LocalDate.now(), data_final, casa.getConsumoDaCasa(), casa.getConsumoDaCasa()*this.fatorImposto*this.precoBaseKW);
            faturas.add(newFatura);
        }
        else{
            List<Fatura> faturas = new ArrayList<>();
            newFatura = new Fatura(LocalDate.now(), data_final, casa.getConsumoDaCasa(), casa.getConsumoDaCasa()*this.fatorImposto*this.precoBaseKW);
            faturas.add(newFatura);
            this.casas.put(casa,faturas);
        }

        return newFatura;
    }

    public void addFatura(Fatura fat, SmartHouse casa){
        try{
            this.casas.get(casa).add(fat);
        } catch(Exception e){System.out.println("Não existe casa, erro "+e); }

    }
    public List<Fatura> getFaturas(SmartHouse casa){
        List<Fatura> faturas = new ArrayList<>();
        if(this.casas.containsKey(casa)){
            for (Fatura fatura : this.casas.get(casa)) faturas.add(fatura.clone());
        }
        return faturas;
    }

    public void terminaContrato(SmartHouse casa){
        removeCasa(casa);
        this.numeroDeClientes--;
        casa.setCompanhia_eletrica(null);
        casa.setHouseOFF();
    }

    public double getPrecoPerDevice(SmartDevice sd){
        return (this.precoBaseKW*sd.getConsumo()*(1+this.fatorImposto))*0.9;
    }

    public SmartHouse getCasaMaisGastadora(){
        SmartHouse casaMaisGastadora=null;
        double maxConsumo=0;
        for(SmartHouse casa:this.casas.keySet()) {
            double consumo = casa.getConsumoDaCasa();
            if ((consumo > maxConsumo) || (casaMaisGastadora == null)) {
                maxConsumo = consumo;
                casaMaisGastadora = casa;
            }
        }
        return casaMaisGastadora;
    }
    public void setNumerodeClientes(int numero){ this.numeroDeClientes = numero; }
    public int getNumeroDeClientes(){ return this.numeroDeClientes;}


    public SmartHouse getCasaMaisGastadora(LocalDate dataInit, LocalDate dataFin){
        SmartHouse casaMaisGastadora=null;
        double maxConsumo=0;
        for(SmartHouse casa:this.casas.keySet()){
            List<Fatura> faturas = this.casas.get(casa);
            for (Fatura faturaTemp : faturas) {
                if (faturaTemp.getDataInicial().isBefore(dataInit) && faturaTemp.getDataFinal().isAfter(dataFin)) {//falta ver se as datas coincidem
                    if (faturaTemp.getKwsConsumidos() > maxConsumo || casaMaisGastadora == null) {//será que é assim? assim estou a comparar consumos de kws e não em euros
                        casaMaisGastadora = casa;
                        maxConsumo = faturaTemp.getKwsConsumidos();
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
            for (Fatura fatura : faturasDeCadaCasa) faturacao += fatura.getValorDaFatura();
        }
        return faturacao;
    }

    public void faturacao(LocalDate data_final){
        for(SmartHouse casa:this.casas.keySet()) {
            geraFatura(casa, data_final);
            //casa.setConsumo(0);
        }
    }

    public String getListaFaturacao(){
        StringBuilder sb = new StringBuilder();
        for (SmartHouse casa:this.casas.keySet()){
            sb.append(casa.getID());
            sb.append("\n");
            for (Fatura fatura:this.casas.get(casa)){
                sb.append(fatura.toString());
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public String listOfClientes(){
        StringBuilder sb = new StringBuilder();
        
        for(SmartHouse casa:this.casas.keySet()){
            sb.append(casa.getID());  
            sb.append(this.casas.get(casa).toString());
        }
        
        return sb.toString();
    }

    public boolean equals(Object obj){
        if (this==obj) return true;
        if (obj==null||this.getClass()!=obj.getClass()) return false;
        ComercializadoresEnergia ce = (ComercializadoresEnergia) obj;
        return (this.nome.equals(ce.getNome())  &&  this.precoBaseKW == (ce.getPrecoBaseKW()) && this.fatorImposto == (ce.getFatorImposto()));
    }

    public ComercializadoresEnergia clone(){
        return new ComercializadoresEnergia(this);
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\nNOME: ");
        sb.append(this.nome);
        sb.append("\tPREÇO BASE: ");
        sb.append(this.precoBaseKW);
        sb.append("\tIMPOSTO ENERGÉTICO: ");
        sb.append(this.fatorImposto);
        sb.append("\tTOTAL CLIENTES: ");
        /*
        for(SmartHouse casa:this.casas.keySet()){
            sb.append(casa.getID());
            sb.append(this.casas.get(casa).toString());
        }*/
        sb.append(this.numeroDeClientes);
        //sb.append("\n");
        return sb.toString();
    }

}
