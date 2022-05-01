//import com.intellij.util.containers.hash.HashMap;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.Map;
import java.nio.file.*;
import java.nio.charset.*;

public class Parser {

    public SmartCity parse(int houseID, int deviceID){
        
        SmartCity city = new SmartCity(houseID, deviceID);
        List<String> linhas = readFile("dados.csv");
        String[] linhaPartida;
        String divisao = null;
        int id_house = 0;
        
        for (String linha : linhas) {
            
            linhaPartida = linha.split(":", 2);
            
            switch(linhaPartida[0]){
                case "Casa":
                    city.createHouse(parseCasa(linhaPartida[1], city.giveHouseId()));
                    id_house = city.getHouseId();

                    break;
                case "Divisao":
                    //if (casaMaisRecente == null) System.out.println("Linha inválida.");
                    divisao = linhaPartida[1];
                    city.criaDivisao(id_house, divisao);
                    break;
                case "SmartBulb":
                    if (divisao == null) System.out.println("Linha inválida.");
                    SmartBulb sd1 = parseSmartBulb(linhaPartida[1], city.giveDeviceId());
                    city.addDeviceToDivisao(id_house, divisao, sd1); // FAZER FUNÇÃO
                    //casaMaisRecente.addDevice(divisao, sd1.getId()); // change para addToDevice

                    break;
                case "SmartCamera":
                    if (divisao == null) System.out.println("Linha inválida.");
                    SmartCamera sd2 = parseSmartCamera(linhaPartida[1], city.giveDeviceId());
                    city.addDeviceToDivisao(id_house, divisao, sd2);
                    break;
                case "SmartSpeaker":
                    if (divisao == null) System.out.println("Linha inválida.");
                    SmartSpeaker sd3 = parseSmartSpeaker(linhaPartida[1], city.giveDeviceId());
                    city.addDeviceToDivisao(id_house, divisao, sd3);
                    break;
                case "Fornecedor":
                    city.createComercializadorEnergia(parseComercializadoresEnergia(linhaPartida[1]));
                    break;
                case "Marca":
                    city.createMarca(parseMarca(linhaPartida[1]));
                    break;
                default:
                    System.out.println("Linha inválida.");
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

    public SmartHouse parseCasa(String input, int houseID){
        String[] campos = input.split(",");
        String nome = campos[0];
        int nif = Integer.parseInt(campos[1]);
        ComercializadoresEnergia fornecedor = new ComercializadoresEnergia(campos[2]);
        return new SmartHouse(houseID, nome,nif,fornecedor); // fazer metodo
    }

    public SmartBulb parseSmartBulb(String input, int sd_id){
        String[] campos = input.split(",");
        String mode = campos[0];
        int dimensions = Integer.parseInt(campos[1]);
        double consumo = Double.parseDouble(campos[2]);
        return new SmartBulb(sd_id, mode,dimensions,consumo); // fazer metodo

    }
    public SmartCamera parseSmartCamera(String input, int sd_id){
        String[] campos = input.split(",");
        String resolucao = campos[0]; //resolução 0000x0000?
        resolucao.replace("(","");
        resolucao.replace(")","");
        int tamanho = Integer.parseInt(campos[1]);
        double consumo = Double.parseDouble(campos[2]);
        return new SmartCamera(sd_id, resolucao,tamanho,consumo); // fazer metodo
    }

    public SmartSpeaker parseSmartSpeaker(String input, int sd_id){
        String[] campos = input.split(",");
        int vol = Integer.parseInt(campos[0]);
        String estacao = campos[1];
        Marca marca = new Marca(campos[2]);
        double consumo = Double.parseDouble(campos[3]);
        return new SmartSpeaker(sd_id, vol,estacao,marca,consumo);

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