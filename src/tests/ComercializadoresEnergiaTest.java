public ComercializadoresEnergiaTest{

    @Test 
    public void ComercializadoresEnergiaTest(ComercializadoresEnergia ce){
        this.nome = ce.getNome();
        this.precoBaseKW = ce.getPrecoBaseKW();
        this.fatorImposto = ce.getFatorImposto();
        this.casas= ce.getCasas();
        assertSame("são iguais",ComercializadoresEnergia(ComercializadoresEnergia(ce),this));
    }

    @Test
    public void getCasasTest(){
        
    }


}