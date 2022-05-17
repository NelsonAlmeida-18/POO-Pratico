package Controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Model.SmartCity;

public class Controller {
    private SmartCity city;

    public Controller(SmartCity city){
        this.city = city;
    }

    /**
     * Função que lê todas as linhas de um fichero
     * @param time Tempo dado na simulation
     */
    /*
    public int simulation(String time){

        String [] data = time.split("\\.", 3);  //separa a data dada
        int no_days = 0;
        LocalDate data_sim;
        double consumo_tmp;

        switch (data.length) {
            case 1 -> { //apenas dado um dia
                no_days = Integer.parseInt(data[0]);
                data_sim = LocalDate.now().plus(no_days,DAYS);
            }
            case 3 -> { //dada uma data em formato DD.MM.YYYY
                Parser p = new Parser();
                data_sim = p.parseData(time);
                if(data_sim.isBefore(this.data_atual)) return 1; //data anterior portanto inválida
            }
            default -> {
                //Data inválida
                return 1;
            }
        }
        System.out.println("\nDATA: "+data_sim.toString());
        //calcula consumos no intervalo de datas da data atual à data da simulação
        for (SmartHouse casa : this.casas.values()){
            consumo_tmp = casa.getConsumoDaCasa(this.data_atual, data_sim);
            System.out.println("\nCONSUMO CASA No."+ casa.getID() +": " + consumo_tmp);
        }
        /*percorre comercializadores para tirarem faturas
        guarda as faturas no seu map e manda para as casas guardarem
         *//*
        for (ComercializadoresEnergia comercializadore : this.comercializadores) {
            comercializadore.faturacao(data_sim);
        }
        this.data_atual = data_sim;
        return 0;
    } 
    */
    
    /**
     * Função que lê todas as linhas de um fichero
     * @param nomeFich nome do fichero
     * @return retorna uma Lista de Strings com todas as linhas do ficheiro
     */
    public List<String> readFile(String nomeFich) {
        try {
            return Files.readAllLines(Paths.get(nomeFich), StandardCharsets.UTF_8);

        }
        catch(IOException exc) {
            return new ArrayList<>();
        }

    }

    /**
     * Função que dá parse às logs dadas em ficheiro
     */
    public void parse() throws IOException{
        Path path = Path.of("./logs/logs.txt");
        //Path path = FileSystems.getDefault().getPath("logs", "logs.txt");
        String content = Files.readString(path);
        //List<String> linhas = readFile("dados.csv");
        String[] contentSplited = content.split("\n");
        String[] linhaPartida;
        String divisao = "";

        for (String linha : contentSplited) {

            linhaPartida = linha.split(":", 2);

            switch (linhaPartida[0]) {
                case "Casa" -> parseCasa(linhaPartida[1]);
                case "Divisao" -> {
                    //if (casaMaisRecente == null) System.out.println("Linha inválida.");
                    divisao = linhaPartida[1];
                    this.city.criaDivisao(divisao);
                }
                case "SmartBulb" -> {
                    if (divisao == null) System.out.println("Linha inválida.");
                    parseSmartBulb(divisao, linhaPartida[1]);
                }
                case "SmartCamera" -> {
                    if (divisao == null) System.out.println("Linha inválida.");
                    parseSmartCamera(divisao, linhaPartida[1]);
                }
                case "SmartSpeaker" -> {
                    if (divisao == null) System.out.println("Linha inválida.");
                    parseSmartSpeaker(divisao, linhaPartida[1]);
                   
                }
                case "Fornecedor" -> parseComercializadoresEnergia(linhaPartida[1]);
                case "Marca" -> parseMarca(linhaPartida[1]);
                default -> {
                }
                //System.out.println("Linha inválida.");
            }
        }
        System.out.println("Ficheiro carregado com sucesso.");
    }

    /**
     * Função que dá parse às casas em uma SmartCity
     * @param input String atual a ser lida das logs
     */
    public void parseCasa(String input){
        String[] campos = input.split(",");
        String nome = campos[0];
        int nif = Integer.parseInt(campos[1]);
        String fornecedor = campos[2];
        this.city.createHouse(nome, nif, "", fornecedor);
    }

    /**
     * Função que dá parse às lampadas em uma SmartCity
     * @param divisao Divisao em que o dispositivo vai ser adicionado
     * @param input String atual a ser lida das logs
     */
    public void parseSmartBulb(String divisao,String input){
        String[] campos = input.split(",");
        String mode = campos[0];
        int dimensions = Integer.parseInt(campos[1]);
        double consumo = Double.parseDouble(campos[2]);
        this.city.addDeviceToDivisaoL(divisao, this.city.giveDeviceId(), mode, dimensions,consumo);
    }

    /**
     * Função que dá parse às camaras desegurança em uma SmartCity
     * @param divisao Divisao em que o dispositivo vai ser adicionado
     * @param input String atual a ser lida das logs
     */
    public void parseSmartCamera(String divisao, String input){
        String[] campos = input.split(",");
        String resolucao = campos[0]; //resolução 0000x0000?
        resolucao = resolucao.replace("(","");
        resolucao = resolucao.replace(")","");
        String[] widthHeight = resolucao.split("x");
        int tamanho = Integer.parseInt(campos[1]);
        float width = Float.parseFloat(widthHeight[0]);
        float heigth = Float.parseFloat(widthHeight[1]);
        double consumo = Double.parseDouble(campos[2]);
		this.city.addDeviceToDivisaoC(divisao, this.city.giveDeviceId(),width,heigth,tamanho,consumo);
    }

    /**
     * Função que dá parse às colunas em uma SmartCity
     * @param divisao Divisao em que o dispositivo vai ser adicionado
     * @param input String atual a ser lida das logs
     */
    public void parseSmartSpeaker(String divisao, String input){
        String[] campos = input.split(",");
        int vol = Integer.parseInt(campos[0]);
        String estacao = campos[1];
        double consumo = Double.parseDouble(campos[3]);
        this.city.addDeviceToDivisaoS(divisao, this.city.getDeviceId(), vol, estacao, campos[2], consumo);
    }

    /**
     * Função que dá parse aos fornecedores em uma SmartCity
     * @param input String atual a ser lida das logs
     */
    public void parseComercializadoresEnergia(String input){
        String[] campos = input.split(",");
        String nome = campos[0];
        this.city.createComercializadorEnergia(nome);
    }

    /**
     * Função que dá parse às marcas em uma SmartCity
     * @param input String atual a ser lida das logs
     */
    public void parseMarca(String input){
        String[] campos = input.split(",");
        String nome = campos[0];
        double consumo = Double.parseDouble(campos[1]);
        this.city.createMarca(nome, consumo);
    }

    /**
     * Função que dá parse à data em uma SmartCity
     * @param data_string String atual a ser lida das logs
     */
    public LocalDate parseData(String data_string){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.parse(data_string, formatter);
    }
    
    /**
     * Função que trata da parte da simulação com o usuário
     * @param city SmartCity já existeste que vai sofrer a simulação
     * @param sc Scanner utilizado para ler do terminal
     */
    public void simulation(SmartCity city, Scanner sc) throws IOException {
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
                if (city.getCasa(Integer.parseInt(linha[1])).existsDevice(Integer.parseInt(linha[2]))) {
                    city.getCasa(Integer.parseInt(linha[1])).getDevice(Integer.parseInt(linha[2]));
                    switch (linha[3].toUpperCase()) {
                        case "SETON", "TURNON" -> city.getCasa(Integer.parseInt(linha[1])).getDevice(Integer.parseInt(linha[2])).turnOn();
                        case "SETOFF", "TURNOFF" -> city.getCasa(Integer.parseInt(linha[1])).getDevice(Integer.parseInt(linha[2])).turnOff();
                    }
                } else {
                    if (city.hasComercializador(linha[2])) { // try catch não pode ser utilizado dentro de ifs statements
                        city.getCasa(Integer.parseInt(linha[1])).mudaDeFornecedor(city.getComercializador(linha[2]), city.getDataAtual());
                        throw new IOException(linha[2]);
                    }
                }

            } else{
                if (city.hasComercializador(linha[1])) { // fornecedor related
                    switch (linha[2].toUpperCase()) {
                        case "ALTERAPRECOBASE" -> city.getComercializador(linha[1]).setPrecoBaseKW(Double.parseDouble(linha[3]));
                        case "ALTERAVALORDESCONTO", "ALTERAIMPOSTO", "ALTERAFATORIMPOSTO" -> city.getComercializador(linha[1]).setFatorImposto(Double.parseDouble(linha[3]));
                    }
                    throw new AccessDeniedException(linha[2]);
                }
            }
        }

    }


    
}
