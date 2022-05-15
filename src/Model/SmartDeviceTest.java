package Model;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

public class SmartDeviceTest{

    @BeforeEach
    public void setUp(){
    }

    @AfterEach
    public void tearDown(){
    }

    @Test
    public void turnOnTest(){
        SmartBulb sb = new SmartBulb(1);
        SmartCamera sc = new SmartCamera(2, 1920, 1080, 0 , "off");
        Marca marca = new Marca("JBL");
        SmartSpeaker ss = new SmartSpeaker(3,10,"RFM",marca,0.0);
        assertEquals(SmartBulb.state.OFF,sb.getEstado(), "Não está desligado (L)");
        assertEquals(SmartCamera.state.OFF,sc.getState(), "Não está desligado (C)");
        assertEquals(SmartSpeaker.state.OFF,ss.getEstado(), "Não está desligado (S)");

        sb.turnOn();
        sc.turnOn();
        ss.turnOn();
        assertEquals(SmartBulb.state.ON,sb.getEstado(), "Não está ligado(L)");
        assertEquals(SmartCamera.state.ON,sc.getState(), "Não está ligado (C)");
        assertEquals(SmartSpeaker.state.ON,ss.getEstado(), "Não está ligado (S)");
    }

    @Test
    public void turnOffTest(){
        SmartBulb sb = new SmartBulb(1);
        SmartCamera sc = new SmartCamera(2, 1920, 1080, 0 , "off");
        Marca marca = new Marca("JBL");
        SmartSpeaker ss = new SmartSpeaker(3,10,"RFM",marca,0.0);
        assertEquals(SmartBulb.state.OFF,sb.getEstado(), "Não está desligado (L)");
        assertEquals(SmartCamera.state.OFF,sc.getState(), "Não está desligado (C)");
        assertEquals(SmartSpeaker.state.OFF,ss.getEstado(), "Não está desligado (S)");

        sb.turnOn();
        sc.turnOn();
        ss.turnOff();
        sb.turnOff();
        sc.turnOff();
        ss.turnOff();
        assertEquals(SmartBulb.state.OFF,sb.getEstado(), "Não está desligado (L)");
        assertEquals(SmartCamera.state.OFF,sc.getState(), "Não está desligado (C)");
        assertEquals(SmartSpeaker.state.OFF,ss.getEstado(), "Não está desligado (S)");

    }

    @Test
    public void consumoTest(){
        SmartBulb sb = new SmartBulb(1);
        SmartCamera sc = new SmartCamera(2, 1920, 1080, 0 , "off");
        Marca marca = new Marca("JBL");
        SmartSpeaker ss = new SmartSpeaker(3,10,"RFM",marca,0.0);

        sb.setConsumo(50.0f);
        sc.setConsumo(50.0f);
        ss.setConsumo(50.0f);
        assertEquals(50.0f,sb.getConsumo(),"Consumo não está correto");
        assertEquals(50.0f,sc.getConsumo(),"Consumo não está correto");
        assertEquals(50.0f,ss.getConsumo(),"Consumo não está correto");

    }

}