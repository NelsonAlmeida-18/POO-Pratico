package tester;

public class ComercializadoresEnergia {
    private double precoBaseKW;
    private double fatorImposto;

    public ComercializadoresEnergia(double precoBaseKW, double fatorImposto){
        this.precoBaseKW=precoBaseKW;
        this.fatorImposto=fatorImposto;
    }

    public ComercializadoresEnergia(ComercializadoresEnergia ce){
        this.precoBaseKW = ce.getPrecoBaseKW();
        this.fatorImposto= ce.getFatorImposto();
    }

    public double getPrecoBaseKW(){return this.precoBaseKW;}

    public double getFatorImposto(){return this.fatorImposto;}

   /*
   public double getPrecoDiaPorDispositivo(String id){
        
    }
    */
}
