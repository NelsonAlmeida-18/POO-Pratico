//import com.intellij.util.containers.hash.HashMap;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

//import apple.laf.JRSUIConstants.WindowTitleBarSeparator;

import java.nio.file.*;
import java.nio.charset.*;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.Scanner;

public class Parser {
    /*
    public SmartCity parse(int houseID, int deviceID) throws IOException{
        try {
            //Path path = Path.of("./src/logs.txt");
            Path path = FileSystems.getDefault().getPath("logs", "logs.txt");
            String content = Files.readString(path);
            SmartCity city = new SmartCity(houseID, deviceID);
            //List<String> linhas = readFile("dados.csv");
            String[] contentSplited = content.split("\n");
            String[] linhaPartida;
            String divisao = "";

            for (String linha : contentSplited) {

                linhaPartida = linha.split(":", 2);

                switch (linhaPartida[0]) {
                    case "Casa" -> city.createHouse(parseCasa(linhaPartida[1], city));
                    case "Divisao" -> {
                        //if (casaMaisRecente == null) System.out.println("Linha inválida.");
                        divisao = linhaPartida[1];
                        city.criaDivisao(city.getHouseId() - 1, divisao);
                    }
                    case "SmartBulb" -> {
                        if (divisao == null) System.out.println("Linha inválida.");
                        SmartBulb sd1 = parseSmartBulb(linhaPartida[1], city);
                        city.addDeviceToDivisao(divisao, sd1); // FAZER FUNÇÃO
                    }
                    case "SmartCamera" -> {
                        if (divisao == null) System.out.println("Linha inválida.");
                        SmartCamera sd2 = parseSmartCamera(linhaPartida[1], city);
                        city.addDeviceToDivisao(divisao, sd2);
                    }
                    case "SmartSpeaker" -> {
                        if (divisao == null) System.out.println("Linha inválida.");
                        SmartSpeaker sd3 = parseSmartSpeaker(linhaPartida[1], city);
                        city.addDeviceToDivisao(divisao, sd3);
                    }
                    case "Fornecedor" -> city.createComercializadorEnergia(parseComercializadoresEnergia(linhaPartida[1]));
                    case "Marca" -> city.createMarca(parseMarca(linhaPartida[1]));
                    default -> {
                    }
                    //System.out.println("Linha inválida.");
                }
            }
            System.out.println("done!");
        }
        catch(Exception e){
            System.out.println("Something went wrong!");
        }
    }*/

    public void parse(SmartCity city) throws IOException{
        Path path = Path.of("./src/logs/logs.txt");
        //Path path = FileSystems.getDefault().getPath("logs", "logs.txt");
        String content = Files.readString(path);
        //List<String> linhas = readFile("dados.csv");
        String[] contentSplited = content.split("\n");
        String[] linhaPartida;
        String divisao = "";

        for (String linha : contentSplited) {

            linhaPartida = linha.split(":", 2);

            switch (linhaPartida[0]) {
                case "Casa" -> city.createHouse(parseCasa(linhaPartida[1], city));
                case "Divisao" -> {
                    //if (casaMaisRecente == null) System.out.println("Linha inválida.");
                    divisao = linhaPartida[1];
                    city.criaDivisao(divisao);
                }
                case "SmartBulb" -> {
                    if (divisao == null) System.out.println("Linha inválida.");
                    SmartBulb sd1 = parseSmartBulb(linhaPartida[1], city);
                    city.addDeviceToDivisao(divisao, sd1); // FAZER FUNÇÃO
                }
                case "SmartCamera" -> {
                    if (divisao == null) System.out.println("Linha inválida.");
                    SmartCamera sd2 = parseSmartCamera(linhaPartida[1], city);
                    city.addDeviceToDivisao(divisao, sd2);
                }
                case "SmartSpeaker" -> {
                    if (divisao == null) System.out.println("Linha inválida.");
                    SmartSpeaker sd3 = parseSmartSpeaker(linhaPartida[1], city);
                    city.addDeviceToDivisao(divisao, sd3);
                }
                case "Fornecedor" -> city.createComercializadorEnergia(linhaPartida[1]);
                case "Marca" -> city.createMarca(parseMarca(linhaPartida[1]));
                default -> {
                }
                //System.out.println("Linha inválida.");
            }
        }
        System.out.println("done!");
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

    public LocalDate parseData(String data_string){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.parse(data_string, formatter);
    }

    public void simulation(SmartCity city, Scanner sc) throws IOException, ClassNotFoundException {
        System.out.println("Especifique o número de linhas que pretende adicionar na simulação: ");
        int loop= sc.nextInt();
        System.out.println("Insira os comandos: ");
        for(int i = 0;i<loop;i++){
            String line = sc.nextLine();
            String [] linha = line.split(",", 4);
        /*
         linha[0] data
         linha[1] id_casa/id_fornecedor
         linha[2] id_dispositivo/id_fornecedor/altera_atributo_fornecedor
         linha[3] ação/novo_valor_atributo/null
         */
            // fazer com que o tempo avance e só depois pode efetuar ação
            // FALTA TRATAR DOS FATORES QUE SÃO DEPENDENTES DA DATA
            //SmartCity city = new SmartCity();
            //city.loadState("path");
            if(city.hasSmartHouse(Integer.parseInt(linha[1]))) { // house related
                SmartHouse clonedhouse = city.getCasa(Integer.parseInt(linha[1])).clone();
                if (clonedhouse.existsDevice(Integer.parseInt(linha[2]))) {
                    SmartDevice sd = clonedhouse.getDevice(Integer.parseInt(linha[2]));
                    switch (linha[3].toUpperCase()) {
                        case "SETON", "TURNON" -> sd.turnOn();
                        case "SETOFF", "TURNOFF" -> sd.turnOff();
                    }
                } else {
                    if (city.hasComercializador(linha[2])) { // try catch não pode ser utilizado dentro de ifs statements
                        clonedhouse.mudaDeFornecedor(city.getComercializador(linha[2]));
                        throw new IOException(linha[2]);
                    }
                }

            } else{
                if (city.hasComercializador(linha[1])) { // fornecedor related
                    ComercializadoresEnergia c = city.getComercializador(linha[1]);
                        switch (linha[2].toUpperCase()) {
                            case "ALTERAPRECOBASE" -> c.setPrecoBaseKW(Double.parseDouble(linha[3]));
                            case "ALTERAVALORDESCONTO", "ALTERAIMPOSTO", "ALTERAFATORIMPOSTO" -> c.setFatorImposto(Double.parseDouble(linha[3]));
                        }
                    throw new AccessDeniedException(linha[2]);
                    }
                }
            }

        }

}