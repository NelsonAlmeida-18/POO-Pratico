

import java.util.Map;

public class ComercializadoresEnergia {

    private String nome;
    private double precoBaseKW;
    private double fatorImposto;

    public ComercializadoresEnergia(){
        this.nome="";
        this.precoBaseKW=0;
        this.fatorImposto=0;
    }

    public ComercializadoresEnergia(String nome,double precoBaseKW, double fatorImposto){
        this.nome=nome;
        this.precoBaseKW=precoBaseKW;
        this.fatorImposto=fatorImposto;
    }

    public ComercializadoresEnergia(ComercializadoresEnergia ce){
        this.nome=ce.getNome();
        this.precoBaseKW = ce.getPrecoBaseKW();
        this.fatorImposto= ce.getFatorImposto();
    }

    public double getPrecoBaseKW(){return this.precoBaseKW;}

    public String getNome(){return this.nome;}

    public void setNome(String nome){ this.nome=nome;}

    public double getFatorImposto(){return this.fatorImposto;}

    /**/
    public double getPrecoDiaPorDispositivo(String id){return 1.0;}

    public ComercializadoresEnergia clone(){
        return new ComercializadoresEnergia(this);
    }

    public double getPrecoPerDevice(SmartDevice sd){
        return (this.precoBaseKW*sd.getConsumo()*(1+this.fatorImposto))*0.9;
    }

    public double getConsumoDivisao(Map<String,SmartDevice> div){
        double consumoDivisao=0;
        for(SmartDevice disp:div.values()){
            consumoDivisao+=disp.getConsumo();
        }
        return consumoDivisao;
    }

    public double getConsumoDaCasa(SmartHouse sh){
        double consumoDaCasa=0;
        for(Map<String, SmartDevice> div: sh.getHouse().values())
            consumoDaCasa+=getConsumoDivisao(div);
        return consumoDaCasa;
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
