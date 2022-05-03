//import com.intellij.util.containers.hash.HashMap;

import java.util.ArrayList;
import java.util.List;

//import apple.laf.JRSUIConstants.WindowTitleBarSeparator;

import java.nio.file.*;
import java.nio.charset.*;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;

public class Parser {

    public Parser(){
    }

    public SmartCity parse(int houseID, int deviceID) throws IOException{
        Path path = Path.of("/Users/rkeat/Desktop/POO-Pratico/src/logs.txt");
        String content = Files.readString(path);
        SmartCity city = new SmartCity(houseID, deviceID);
        //List<String> linhas = readFile("dados.csv");
        String[] contentSplited = content.split("\n");
        String[] linhaPartida;
        String divisao = "";
        
        for (String linha : contentSplited) {
            
            linhaPartida = linha.split(":", 2);
            
            switch(linhaPartida[0]){
                case "Casa":
                    city.createHouse(parseCasa(linhaPartida[1],city));

                    break;
                case "Divisao":
                    //if (casaMaisRecente == null) System.out.println("Linha inválida.");
                    divisao = linhaPartida[1];
                    city.criaDivisao(divisao);
                    break;
                case "SmartBulb":
                    if (divisao == null) System.out.println("Linha inválida.");
                    SmartBulb sd1 = parseSmartBulb(linhaPartida[1], city);
                    city.addDeviceToDivisao(divisao, sd1); // FAZER FUNÇÃO
                    break;
                case "SmartCamera":
                    if (divisao == null) System.out.println("Linha inválida.");
                    SmartCamera sd2 = parseSmartCamera(linhaPartida[1], city);
                    city.addDeviceToDivisao(divisao, sd2);
                    break;
                case "SmartSpeaker":
                    if (divisao == null) System.out.println("Linha inválida.");
                    SmartSpeaker sd3 = parseSmartSpeaker(linhaPartida[1], city);
                    city.addDeviceToDivisao(divisao, sd3);
                    break;
                case "Fornecedor":
                    city.createComercializadorEnergia(parseComercializadoresEnergia(linhaPartida[1]));
                    break;
                case "Marca":
                    city.createMarca(parseMarca(linhaPartida[1]));
                    break;
                default:
                    //System.out.println("Linha inválida.");
                    break;
            }
        }
        System.out.println("done!");
        return city;
    }

    public List<String> readFile(String nomeFich) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(nomeFich), StandardCharsets.UTF_8);
            return lines;
        }
        catch(IOException exc) {
            List<String> lines = new ArrayList<>();
            return lines;
        }
    }

    public SmartHouse parseCasa(String input, SmartCity city){
        String[] campos = input.split(",");
        String nome = campos[0];
        int nif = Integer.parseInt(campos[1]);
        String fornecedor = campos[2];
        ComercializadoresEnergia comercializador = city.getComercializador(fornecedor);
        if(comercializador == null){comercializador = new ComercializadoresEnergia(fornecedor);}
        return new SmartHouse(city.getHouseId(),nome,nif,comercializador);
    }

    public SmartBulb parseSmartBulb(String input, SmartCity city){
        String[] campos = input.split(",");
        String mode = campos[0];
        int dimensions = Integer.parseInt(campos[1]);
        double consumo = Double.parseDouble(campos[2]);
        return new SmartBulb(city.giveDeviceId(), mode,dimensions,consumo);
    }

    public SmartCamera parseSmartCamera(String input, SmartCity city){
        String[] campos = input.split(",");
        String resolucao = campos[0]; //resolução 0000x0000?
        resolucao = resolucao.replace("(","");
        resolucao = resolucao.replace(")","");
        String[] widthHeight = resolucao.split("x");
        int tamanho = Integer.parseInt(campos[1]);
        float width = Float.parseFloat(widthHeight[0]);
        float heigth = Float.parseFloat(widthHeight[1]);
        float consumo = Float.parseFloat(campos[2]);
        //inicializei a off para testes
        return new SmartCamera(city.giveDeviceId(), width,heigth,tamanho,SmartCamera.state.OFF, consumo);
    }

    public SmartSpeaker parseSmartSpeaker(String input, SmartCity city){
        String[] campos = input.split(",");
        int vol = Integer.parseInt(campos[0]);
        String estacao = campos[1];
        Marca marca = new Marca(campos[2]);
        city.createMarca(marca);
        double consumo = Double.parseDouble(campos[3]);
        return new SmartSpeaker(city.giveDeviceId(), vol,estacao,marca,consumo);

    }

    public ComercializadoresEnergia parseComercializadoresEnergia(String input){
        String[] campos = input.split(",");
        String nome = campos[0];
        return new ComercializadoresEnergia(nome);
    }

    public Marca parseMarca(String input){
        String[] campos = input.split(",");
        String nome = campos[0];
        return new Marca(nome);
    }

}