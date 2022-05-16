package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

public class SmartHouseTest{

    @BeforeEach
    public void setUp(){
    }

    @AfterEach
    public void tearDown(){
    }

    @Test
    public void getDivisaoTest(){
        SmartHouse casa1 = new SmartHouse(1, "Artur",123123,"Rua da rua");
        casa1.addDivisao("sala");
        assertTrue(casa1.getDivisaoList().contains("sala"), "Não foi adicionada a divisão");
    }
    @Test
    public void setDeviceOnTest(){
        SmartBulb sb = new SmartBulb(1);
        SmartHouse casa1 = new SmartHouse(1, "Artur",123123,"Rua da rua");
        casa1.addDivisao("sala");
        casa1.addDevice("sala",sb);
        assertEquals(SmartBulb.state.OFF,sb.getEstado(),"Lampada deveria estar desligada");
        casa1.setDeviceOn(1);
        assertEquals(SmartBulb.state.ON,sb.getEstado(),"Lampada deveria estar ligada");
    }

    @Test
    public void setDeviceOffTest(){
        SmartBulb sb = new SmartBulb(1, SmartBulb.state.ON);
        SmartHouse casa1 = new SmartHouse(1, "Artur",123123,"Rua da rua");
        casa1.addDivisao("sala");
        casa1.addDevice("sala",sb);
        assertEquals(SmartBulb.state.ON,sb.getEstado(),"Lampada deveria estar ligada");
        casa1.setDeviceOFF(1);
        assertEquals(SmartBulb.state.OFF,sb.getEstado(),"Lampada deveria estar desligada");
    }

    @Test
    public void setDivisaoOnTest(){
        SmartBulb sb = new SmartBulb(1);
        SmartBulb sb2 = new SmartBulb(2);
        SmartHouse casa1 = new SmartHouse(1, "Artur",123123,"Rua da rua");
        casa1.addDivisao("sala");
        casa1.addDevice("sala",sb);
        casa1.addDevice("sala",sb2);
        casa1.setDivisaoOn("sala");
        for(SmartDevice sd : casa1.getDevicesDivisao("sala").values()){
            SmartBulb sd1 = (SmartBulb) sd.clone();
            assertEquals(SmartBulb.state.ON,sd1.getEstado(), "Lampada deveria estar ligada");
        }
    }

    @Test
    public void setDivisaoOffTest(){
        SmartBulb sb = new SmartBulb(1);
        SmartBulb sb2 = new SmartBulb(2);
        SmartHouse casa1 = new SmartHouse(1, "Artur",123123,"Rua da rua");
        casa1.addDivisao("sala");
        casa1.addDevice("sala",sb);
        casa1.addDevice("sala",sb2);
        casa1.setDivisaoOFF("sala");
        for(SmartDevice sd : casa1.getDevicesDivisao("sala").values()){
            SmartBulb sd1 = (SmartBulb) sd.clone();
            assertEquals(SmartBulb.state.OFF,sd1.getEstado(), "Lampada deveria estar desligada");
        }
    }

    @Test
    public void setHouseOnTest(){
        SmartBulb sb = new SmartBulb(1);
        SmartBulb sb2 = new SmartBulb(2);
        SmartBulb sb3 = new SmartBulb(3);
        SmartBulb sb4 = new SmartBulb(4);
        SmartHouse casa1 = new SmartHouse(1, "Artur",123123,"Rua da rua");
        casa1.addDivisao("sala");
        casa1.addDevice("sala",sb);
        casa1.addDevice("sala",sb2);
        casa1.addDivisao("cozinha");
        casa1.addDevice("cozinha",sb3);
        casa1.addDevice("cozinha",sb4);
        casa1.setHouseOn();
        for(String divisao : casa1.getDivisaoList()) {
            for (SmartDevice sd : casa1.getDevicesDivisao(divisao).values()) {
                SmartBulb sd1 = (SmartBulb) sd.clone();
                assertEquals(SmartBulb.state.ON, sd1.getEstado(), "Lampada deveria estar ligadas");
            }
        }
    }

    @Test
    public void setHouseOffTest(){
        SmartBulb sb = new SmartBulb(1);
        SmartBulb sb2 = new SmartBulb(2);
        SmartBulb sb3 = new SmartBulb(3);
        SmartBulb sb4 = new SmartBulb(4);
        SmartHouse casa1 = new SmartHouse(1, "Artur",123123,"Rua da rua");
        casa1.addDivisao("sala");
        casa1.addDevice("sala",sb);
        casa1.addDevice("sala",sb2);
        casa1.addDivisao("cozinha");
        casa1.addDevice("cozinha",sb3);
        casa1.addDevice("cozinha",sb4);
        casa1.setHouseOn();
        for(String divisao : casa1.getDivisaoList()) {
            for (SmartDevice sd : casa1.getDevicesDivisao(divisao).values()) {
                SmartBulb sd1 = (SmartBulb) sd.clone();
                assertEquals(SmartBulb.state.OFF, sd1.getEstado(), "Lampada deveria estar desligadas");
            }
        }
    }
}