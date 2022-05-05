import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComercializadoresEnergiaTest{


    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public static void tearDown(){

    }
    @Test
    public void getFaturasTest(){
        ComercializadoresEnergia ce = new ComercializadoresEnergia("EDP");
        SmartHouse casa1 = new SmartHouse(1,"joão", 123123,"Rua da rua", ce);
        Fatura fatura1 = new Fatura(LocalDateTime.now(),LocalDateTime.of(2022,6,5,11,36), 51.4f, 60.53);
        Fatura fatura2 = new Fatura(LocalDateTime.now(),LocalDateTime.of(2022,7,5,11,36), 53.4f, 63.53);
        Fatura fatura3 = new Fatura(LocalDateTime.now(),LocalDateTime.of(2022,8,5,11,36), 48.4f, 55.53);
        List<Fatura> faturas = new ArrayList<>();
        faturas.add(1,fatura1);
        faturas.add(2,fatura2);
        faturas.add(3,fatura3);
        assertEquals(faturas,ce.getFaturas(casa1),"As faturas não coincidem");
    }

    @Test
    public void terminaContratoTest(){
        ComercializadoresEnergia ce = new ComercializadoresEnergia("EDP");
        SmartHouse casa1 = new SmartHouse(1,"joão", 123123,"Rua da rua", ce);
        ce.addCasa(casa1,null);
        SmartHouse casa2 = new SmartHouse(1,"joão", 123123,"Rua da rua");
        ComercializadoresEnergia nce = new ComercializadoresEnergia("ELETRO",casa2,null);
        assertEquals(ce,casa1.getCompanhia_eletrica(),"Companhia não é igual");
        ce.terminaContrato(casa1);
        assertNull(casa1.getCompanhia_eletrica(), "A casa não deveria ter fornecedor de energia");
        List<Fatura> faturas= new ArrayList<>();
        Fatura fatura1 = new Fatura(LocalDateTime.now(),LocalDateTime.of(2022,6,5,11,36), 51.4f, 60.53);
        faturas.add(1,fatura1);
        casa1.mudaDeFornecedor(nce);
        assertEquals(nce,casa1.getCompanhia_eletrica(),"Deveria ter trocador de fornecedor (!=null)");
        nce.terminaContrato(casa1);
        nce.addCasa(casa1,faturas);
        assertEquals(nce,casa1.getCompanhia_eletrica(),"Deveria ter como fornecedor ELETRO");
        assertTrue(nce.getCasas().containsKey(casa1),"Casa ainda não está associada ao fornecedor");
    }

    @Test
    public void getFaturacaoTest(){
        ComercializadoresEnergia ce = new ComercializadoresEnergia("EDP");
        SmartHouse casa1 = new SmartHouse(1,"joão", 123123,"Rua da rua", ce);
        List<Fatura> faturas= new ArrayList<>();
        Fatura fatura1 = new Fatura(LocalDateTime.now(),LocalDateTime.of(2022,6,5,11,36), 51.4f, 60.53);
        faturas.add(1,fatura1);
        ce.addCasa(casa1,faturas);
        ce.addFatura(fatura1,casa1);
        assertEquals(60.53,ce.getFaturacao());
    }

    @Test
    public void casaMaisGastadoraTest() {
        ComercializadoresEnergia ce = new ComercializadoresEnergia("EDP");
        SmartHouse casa1 = new SmartHouse(1, "joão", 123123, "Rua da rua", ce);
        SmartHouse casa2 = new SmartHouse(2, "rui", 231231, "Rua sem rua", ce);
        SmartHouse casa3 = new SmartHouse(3, "adalberto", 321321, "Rua com rua", ce);
        SmartHouse casa4 = new SmartHouse(4, "joaquim", 312312, "Rua na rua", ce);
        SmartBulb ds = new SmartBulb(11);
        ds.setConsumo(11.11f);
        casa1.addDevice("sala", ds);
        Map<SmartHouse, List<Fatura>> casas = new HashMap<>();
        List<Fatura> faturas = new ArrayList<>();
        Fatura fatura1 = new Fatura(LocalDateTime.now(), LocalDateTime.of(2022, 6, 5, 11, 36), 51.4f, 60.53);
        faturas.add(1, fatura1);
        casas.put(casa1.clone(), faturas);
        casas.put(casa2.clone(), null);
        casas.put(casa3.clone(), null);
        casas.put(casa4.clone(), null);
        ce.setCasas(casas);
        assertEquals(casa1, ce.getCasaMaisGastadora(), "Esta não é a casa mais gastadora");
    }
}