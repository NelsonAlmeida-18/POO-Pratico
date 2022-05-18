package View;

import java.io.*;
import java.util.Scanner;
import java.lang.InterruptedException;
import java.lang.ProcessBuilder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Controller.Controller;
import Model.SmartDevice;
import Model.SmartSpeaker;
import Model.SmartBulb;
import Model.SmartCamera;


//import static com.intellij.openapi.util.text.Strings.toUpperCase;
//mudar o ui para um to string e passamos ao parser para construir tudo de uma vez, just an idea


public class View {
    private Scanner sc;
    private Controller c;

    public View(Controller controller, Scanner scanner){
        this.sc = scanner;
        this.c = controller;
    }

    public void run(){
        clearConsole();
        try {
            menuInicial();
        } catch (Exception e) {System.out.println("Failed loading menuInicial");
        }
    }

    public static int response(Scanner sc){

        System.out.print("[y/n] ");
        String res = sc.nextLine();
        res = res.toLowerCase();
        int ret;


        if(res.equalsIgnoreCase("não") || res.equalsIgnoreCase("n") || res.equalsIgnoreCase("no")){
            ret = 0;
        }else if(res.equalsIgnoreCase("sim") || res.equalsIgnoreCase("s") || res.equalsIgnoreCase("y") || res.equalsIgnoreCase("yes")){
            ret = 1;
        }else{
            System.out.println("Resposta inválida");
            ret = response(sc);
        }
        
        return ret;
    }

    public void saveState(){
       try{
           System.out.println("Dá um nome ao teu ficheiro de estado:");
           String nameOfFile = this.sc.nextLine();
           File f = new File("./output/"+nameOfFile);
           while(f.exists()){
                System.out.println("Dá um nome válido ao teu ficheiro");
                nameOfFile = this.sc.nextLine();
                f = new File("./output/"+nameOfFile);
           }
           this.c.saveState(nameOfFile);
       } 
       catch(FileNotFoundException e){
           System.out.println("Error creating file with that name!");
       }
       catch(IOException e){
           System.out.println("Error saving state!");
       }
    }
/*
    public static void clearConsole(){
        try{
            String operatingSystem = System.getProperty("os.name") //Check the current operating system

            if(operatingSystem.contains("Windows")){
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
                Process startProcess = pb.inheritIO.start();
                startProcess.waitFor();
            } else {
                ProcessBuilder pb = new ProcessBuilder("clear");
                Process startProcess = pb.inheritIO.start();

                startProcess.waitFor();
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
*/

    public static void clearConsole() {
       try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                //System.out.print("\033\143");

            }
            else {
                //System.out.print("\033\143");
                System.out.print("\033\143");
            }
        } catch (IOException | InterruptedException ex) { System.out.println(ex.getMessage());}
       // System.out.println("limpo");
    }
    
    public void menuInicial() throws IOException {

        System.out.print("\n");
        System.out.println("Selecione uma das opções abaixo:\n");
        System.out.println("1 - Editar cidade");
        System.out.println("2 - Consultar SmartHouses existentes");
        System.out.println("3 - Consultar comercializadores de energia existentes");
        System.out.println("4 - Consultar marcas existentes");
        System.out.println("5 - Consultar SmartDevices presets");
        System.out.println("6 - Carregar um estado de programa");
        System.out.println("7 - Carregar um ficheiro log");
        System.out.println("8 - Guardar estado");
        System.out.println("9 - Começar Simulação");
        System.out.println("10 - Sair");
        int res = sc.nextInt();
        sc.nextLine();
        switch (res) {
            case 1 -> { //Editar cidade
                clearConsole();
                createMenu();
            }
            case 2 -> { //Consultar SmartHouses existentes
                clearConsole();
                this.c.listSmartHouses();
                menuInicial();
            }
            case 3 -> { //Consultar comercializadores de energia existentes
                clearConsole();
                System.out.println(this.c.listComercializadores());
                //city.listFaturas();
                menuInicial();
            }
            case 4 -> { //Consultar marcas existentes
                clearConsole();
                this.c.listMarcas();
                menuInicial();
            }
            case 5 -> { //Consultar SmartDevices presets
                clearConsole();
                this.c.listSmartDevicesPresets();
                menuInicial();
            }
            case 6 -> { //Carregar um estado de programa
                clearConsole();
                loadMenu();
                menuInicial();
            }
            case 7 -> { //Carregar um ficheiro log
                clearConsole();
                Controller p = new Controller();
                //city = p.parse(city.getHouseId(), city.getDeviceId());

                //não devia ser algo deste género?
                //SmartCity temp = p.parse(city.getHouseID(), city.getDeviceId());
                //city.merge(temp);
                //assim podiamos dar merge de vários files

                p.parse();
                menuInicial();
            }
            case 8 -> { //Guardar estado
                clearConsole();
                saveState(); //função save, não tem menu, simplesmente guarda na pasta save
                System.out.println("Estado do programa guardado");
                menuInicial();
            }
            case 9 -> { //Começar Simulação
                clearConsole();
                simulationMenu();
                menuInicial();
            }
            case 10 -> { //Sair
                clearConsole();
                System.out.println("Tem a certeza que quer sair?");
                if (response(this.sc) == 1) {
                    System.out.println("That's all, folks!.");
                } else {
                    menuInicial();
                }
            }
            default -> {
                clearConsole();
                System.out.println("Opção inválida.");
                menuInicial();
            }
        }
    }

    public void loadMenu() {
        System.out.println("Insira o path para o ficheiro que pretende carregar:");
        StringBuilder path = new StringBuilder();
        path.append(sc.nextLine());
        System.out.println("Insira o nome do ficheiro com a extensão (e.g. ficheiro.txt):");
        path.append(sc.nextLine());
        this.c.merge(ReadObjectFromFile(path.toString())); //createMenuLine devolve a cidade guardada no ficheiro
        try {menuInicial();}
        catch(Exception e){
            System.out.println("Erro a carregar estado.");
        }

    }

    public Object ReadObjectFromFile(String filepath) {

        try {

            FileInputStream fileIn = new FileInputStream(filepath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            Object obj = objectIn.readObject();

            System.out.println("Estado carregado com sucesso!");
            objectIn.close();
            return obj;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void createMenu() throws IOException{
        System.out.println("Selecione uma das opções abaixo:");
        System.out.println();
        System.out.println("1 - Criar SmartHouse");
        System.out.println("2 - Editar SmartHouse");
        System.out.println("3 - Criar Comercializador de Energia");
        System.out.println("4 - Criar marca de SmartSpeaker");
        System.out.println("5 - Criar preset de SmartDevice");
        System.out.println("6 - Eliminar um preset de SmartDevice");
        System.out.println("7 - Adicionar apartir de log");
        System.out.println("8 - Retroceder");
        int res = sc.nextInt();
        sc.nextLine();

        switch (res) {
            case 1 -> { //Criar SmartHouse
                createSmartHouseMenu();
                createMenu();
            }
            case 2 -> { //Editar uma smartHouse(Ligar e desligar todos os dispostivios/dispostivos individuais)

                editSmartHouse();
                createMenu();
            }
            case 3 -> { //Criar Comercializador de Energia
                createComercializadorMenu();
                createMenu();
            }
            case 4 -> { //Criar marca de SmartSpeaker
                createMarcaMenu();
                createMenu();
            }
            case 5 -> { //Criar preset de SmartDevice
                createSmartDevicePresetMenu();
                createMenu();
            }
            case 6 -> //Eliminar um preset de SmartDevice
                //deleteSmartDevicePresetMenu(city);
                    createMenu();
            case 7 -> { //Adicionar apartir de log
                clearConsole();
                Controller p = new Controller();
                //city.merge(p.parse(city.getHouseId(), city.getDeviceId())); //carregar faz gestão de conflitos para dar merge à cidade já existente e à cidade que se está a carregar do log
                p.parse();
                createMenu();
            }
            case 8 -> { //Retroceder
                clearConsole();
                try {
                    menuInicial();
                } catch (Exception e) {
                    System.out.println("Failed to load menuInicial");
                }
            }
        }
    }

    public void createSmartHouseMenu(){

        System.out.println("Insira o nome do proprietário:");
        String nome_prop = this.sc.nextLine();

        int nif = 0;
        System.out.println("Insira o NIF do proprietário:");
        try {
            nif = sc.nextInt();
        } catch (java.util.InputMismatchException e) {
            sc.nextLine();
            System.out.println("NIF inválido.");
            return;
        }
        sc.nextLine();


        System.out.println("Insira a morada:");
        String morada = sc.nextLine();

        System.out.println("Insira o fornecedor:");
        this.c.listComercializadores();
        String fornecedor = sc.nextLine();

        int house_id = this.c.createHouse(nome_prop, nif, morada, fornecedor); //não é o objeto mas sim o identificador acho

        System.out.println("Pretende adicionar divisões na casa?");
        int res = response(this.sc);

        if(res == 1) addDivisoesToHouseMenu(house_id);

    }

    public void addDivisoesToHouseMenu(int house_id){ //reviewed

        System.out.println("Insira o nome da divisão:");
        String nome_divisao = sc.nextLine();

        this.c.criaDivisao(nome_divisao);

        System.out.println("Pretende adicionar dispositivos na divisão?");
        int res = response(sc);
        if(res == 1){
            addDivisionDevicesMenu(house_id, nome_divisao);
        }

        System.out.println("Pretende adicionar mais divisões?");
        res = response(this.sc);
        if(res == 1){
            addDivisoesToHouseMenu(house_id);
        }

        
    }
 
    public void addDivisionDevicesMenu(int house_id, String nome_divisao){ //reviewed
        
        addDeviceToDivisaoMenu(house_id, nome_divisao); //adiciona um dispositivo a uma divisão
        System.out.println("Pretende adicionar mais um dispositivo na divisão?");
        int res = response(sc);
        if(res == 1){
            addDivisionDevicesMenu(house_id, nome_divisao);
        }
        
    }

    public void addDeviceToDivisaoMenu(int house_id, String nome_divisao){

        System.out.println("Adicionar SmartDevice em "+ nome_divisao);
        System.out.println("(escolha uma das seguintes opções)\n");
        System.out.println("1 - Adicionar preset");
        System.out.println("2 - Criar SmartDevice");

        int res = this.sc.nextInt();
        this.sc.nextLine();

        switch (res) {
            case 1 -> addPresetToDivisaoMenu(house_id, nome_divisao);
            case 2 -> this.c.addDeviceToDivisao(nome_divisao, createSmartDeviceMenu()); //adiciona um dispositivo criado no momento
        }
                  
    }
    
    public void addPresetToDivisaoMenu(int house_id, String nome_divisao){

        System.out.println("Eis os presets existentes:");
        this.c.listSmartDevicesPresets();
        
        String preset_selection = this.sc.nextLine();

        this.c.addDeviceToDivisao(house_id,nome_divisao, preset_selection);
        
    }

    public SmartDevice createSmartDeviceMenu(){

        System.out.println("Escolha o tipo de SmartDevice para criar:");
        System.out.println("1 - SmartSpeaker");
        System.out.println("2 - SmartCamera");
        System.out.println("3 - SmartBulb");

        int res = this.sc.nextInt();
        this.sc.nextLine();

        SmartDevice sd = null;

        switch (res) {
            case 1 -> sd = createSmartSpeakerMenu();
            case 2 -> sd = createSmartCameraMenu();
            case 3 -> sd = createSmartBulbMenu();
            default -> {
                System.out.println("Opção inexistente");
                sd = createSmartDeviceMenu();
            }
        }
        
        return sd;
    }

    public SmartDevice createSmartSpeakerMenu(){

        System.out.println("Insira a marca :");
        System.out.println(this.c.marcasListToString());
        String nome_marca = this.sc.nextLine();

        System.out.println("Insira o consumo diário: (kWh)");
        double consumo = this.sc.nextDouble();
        this.sc.nextLine();
        
        System.out.println("Insira o nome da estação de rádio:");
        String estacao = sc.nextLine();

        System.out.println("Insira o volume (0 a 20):");
        int volume = sc.nextInt();
        sc.nextLine();

        System.out.println("Insira o estado (ON/OFF):");
        String estado = sc.nextLine();

        return new SmartSpeaker(this.c.giveDeviceId(),volume, estacao, estado, this.c.getMarca(nome_marca), consumo);

    }

    public SmartDevice createSmartCameraMenu(){

        System.out.println("Insira a altura em pixeis desejada: ");
        float heigth = sc.nextFloat();
        sc.nextLine();

        System.out.println("Insira a largura em pixeis desejada: ");
        float width = sc.nextFloat();
        sc.nextLine();
    
        System.out.println("Insira o tamanho máximo dos ficheiros:");
        int tamanho = sc.nextInt();
        sc.nextLine();
    
        System.out.println("Insira o estado (ON/OFF):");
        String estado = sc.nextLine();

        return new SmartCamera(this.c.giveDeviceId(), width, heigth, tamanho, estado);
    }

    public SmartDevice createSmartBulbMenu(){

        System.out.println("Insira as dimensões da lâmpada:");
        int dimensions = sc.nextInt();
        sc.nextLine();

        System.out.println("Insira o consumo diário da lâmpada: (kWh)");
        double consumo = sc.nextDouble();
        sc.nextLine();
    
        System.out.println("Insira o modo em que se encontra a lâmpada (COLD, NEUTRAL, WARM):");
        String modo = sc.nextLine();

        System.out.println("Insira o estado (ON/OFF):");
        String estado = sc.nextLine();

        return new SmartBulb(this.c.giveDeviceId(), modo, dimensions, consumo, estado);
        
    }

    public void createComercializadorMenu(){

        System.out.println("Insira o nome do comercializador:");
        String nome = this.sc.nextLine();

        System.out.println("Insira o preço base (KW):");
        float preco = this.sc.nextFloat();
        this.sc.nextLine();
        
        System.out.println("Insira o fator imposto:");
        float imposto = this.sc.nextFloat();
        this.sc.nextLine();

        this.c.createComercializadorEnergia(nome, preco, imposto);
        System.out.println("Comercializador "+ nome +" criado");
        
    }
    
    public void createMarcaMenu(){

        System.out.println("Insira o nome da marca:");
        String nome = this.sc.nextLine();

        System.out.println("Insira o consumo diário associado ao uso dos dispostivos:");
        double consumo = this.sc.nextDouble();
        this.sc.nextLine();
        
        this.c.createMarca(nome, consumo);
        System.out.println("Marca "+ nome +" criada. Consumo diário dos dispositivos: "+ consumo);
        
    }

    
    public void createSmartDevicePresetMenu(){

        System.out.println("Insira o nome do preset:");
        String nome = this.sc.nextLine();

        this.c.addDevicePreset(nome, createSmartDeviceMenu());
    }

   /*  public void deleteSmartDevicePresetMenu(SmartCity city){ //falta adicionar opções de voltar atrás ou cancelar

        city.listSmartDevicesPresets(); //lista os presets existentes

        System.out.println("Insira o nome do preset que pretende eliminar:");
        String preset_name = sc.nextLine();

        city.deleteSmartDevicePreset(preset_name);

    }

    public void listSmartHousesMenu(SmartCity city){ //falta adicionar opções de voltar atrás ou cancelar

        city.listSmartHouses(); //lista das existentes

        System.out.println("Insira o id da casa para ver mais detalhes:");
        int id_casa = sc.nextInt();

        city.getSmartHouseDetails(id_casa);

    }

    public void listSmartDevicesPresetsMenu(SmartCity city){ //falta adicionar opções de voltar atrás ou cancelar

        printPresetsList(city); //lista das existentes

        System.out.println("Insira o nome do SmartDevice para ver mais detalhes:");
        String nome = sc.nextLine();
        sc.nextLine();

        city.getSmartDevicePresetDetails(nome);

    } */
        
    public void simulationMenu() {
        System.out.println("1 - Simulação Manual. ");
        System.out.println("2 - Simulação através de ficheiro") ;
        System.out.println("3 - Retroceder.");
        String choice = this.sc.nextLine();
        switch (choice) {
            case ("1") -> {
                System.out.println("Indique a data: (X dias ou DD.MM.YYYY)");
                String time = this.sc.nextLine();
                clearConsole();
                simulationOptions(time);
            }
            //fazer o resto dos métodos
            case ("2") -> System.out.println("Diretório do ficheiro: ");

            //fazer novo parser e tudo o resto.
            case ("3") -> {
                clearConsole();
                try {
                    menuInicial();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // if(city.simulation(time) == 0) {
        //     System.out.println(city.listFaturas());
        // } else {
        //     System.out.println("Data inválida");
        //     clearConsole();
        //     simulationMenu(city, sc);
        // }



    }

    public void simulationOptions( String time){
        System.out.println("1 - Casa mais gastadora.");
        System.out.println("2 - Comercializador com maior faturação.");
        System.out.println("3 - Listar faturas de um Comercializador.");
        System.out.println("4 - Maiores consumidores de energia num intervalo de tempo.");
        System.out.println("5 - Retroceder.");
        String choice = this.sc.nextLine();
        switch (choice) {
            case ("1") -> {

                //falta fazer a faturacao ou ver se a mesma já foi feita

                System.out.println("A casa mais gastadora neste período de tempo é a casa: " + this.c.getCasaMaisGastadoraID());
                System.out.println("KW's consumidos: " + this.c.getCasaMaisGastadoraConsumo());
                System.out.println("1 - Mais dados da casa.");
                System.out.println("2 - Retroceder.");
                choice = this.sc.nextLine();
                switch (choice) {
                    case ("1") -> {
                        clearConsole();
                        System.out.println("Dados da casa: ");
                        System.out.println("KW's consumidos: " + this.c.getCasaMaisGastadoraConsumo());
                        System.out.println(this.c.getCasaMaisGastadora());
                        simulationOptions(time);
                    }
                    case ("2") -> {
                        clearConsole();
                        simulationOptions(time);
                    }
                }
            }
            case ("2") -> {
                clearConsole();
                System.out.println("Comercializador de Energia com maior faturação: " + this.c.getComercializadorMaiorFaturacaoNome());
                System.out.println("1 - Mais dados do Comercializador.");
                System.out.println("2 - Retroceder.");
                choice = this.sc.nextLine();
                switch (choice) {
                    case ("1") -> {
                        clearConsole();
                        System.out.println("Dados do Comercializador: ");
                        System.out.println(this.c.getComercializadorMaiorFaturacao());
                        simulationOptions(time);
                    }
                    case ("2") -> {
                        clearConsole();
                        simulationOptions(time);
                    }
                }
            }
            case ("3") -> {
                clearConsole();
                System.out.println(this.c.listComercializadores());
                System.out.println("Indique o nome do Comercializador do qual pretende obter faturas.");
                choice = sc.nextLine();
                while (!this.c.hasComercializador(choice)) {
                    clearConsole();
                    System.out.println(this.c.listComercializadores());
                    System.out.println("\nIndique o nome do Comercializador do qual pretende obter faturas:");
                    choice = this.sc.nextLine();
                }
                clearConsole();
                this.c.ComercializadorFaturacao(choice,time); //ver isto com a data data a data
                System.out.println(this.c.getListaFaturacaoComercializador(choice)); //TODO:testar o listaFaturacao
                simulationOptions(time);
            }
            case ("4") -> {
                clearConsole();
                String formato = "dd.MM.yyyy";
                DateTimeFormatter formatador = DateTimeFormatter.ofPattern(formato);
                System.out.println("Data inicial (dd.MM.AAAA).");
                String dataInicialTexto = this.sc.nextLine();
                LocalDate dataInicial = LocalDate.from(formatador.parse(dataInicialTexto));
                System.out.println("Data Final (dd.MM.AAAA).");
                String dataFinalTexto = this.sc.nextLine();
                LocalDate dataFinal = LocalDate.from(formatador.parse(dataFinalTexto));
                LocalDate dataCriacao = this.c.getDataAtual();  //Mudar para data Inicial
                System.out.println(dataCriacao.isAfter(dataInicial));
                System.out.println(dataInicial.isAfter(dataFinal));
                System.out.println(dataCriacao);
                while (dataCriacao.isAfter(dataInicial) || dataInicial.isAfter(dataFinal)) {
                    clearConsole();
                    System.out.println("Datas inválidas!");
                    System.out.println("Data inicial mínima: " + dataCriacao);
                    System.out.println("Data inicial (dd.MM.AAAA).");
                    dataInicialTexto = this.sc.nextLine();
                    dataInicial = LocalDate.from(formatador.parse(dataInicialTexto));
                    System.out.println("Data Final (dd.MM.AAAA).");
                    dataFinalTexto = this.sc.nextLine();
                    dataFinal = LocalDate.from(formatador.parse(dataFinalTexto));
                }
                System.out.println("A casa mais gastadora entre " + dataInicialTexto + " e " + dataFinalTexto + " foi: " + this.c.getCasaMaisGastadora(dataInicial, dataFinal));
                simulationOptions(time);
            }
            case ("5") -> {
                clearConsole();
                simulationMenu();
            }
            default -> {
                clearConsole();
                simulationOptions(time);
            }
        }
    }

    public void editSmartHouse(){
        System.out.println("Indique o ID da casa que pertende editar(0-"+(this.c.getHouseId()-1)+"):");
        int id= this.sc.nextInt();
        sc.nextLine();
        while(id<0||id>this.c.getHouseId()-1){
            System.out.println("Por favor indique um ID válido(0-"+(this.c.getHouseId()-1)+"):");
            id=sc.nextInt();
            sc.nextLine();
        }
        clearConsole();
        menuInteracaoCasas(id);
    }

    public void menuInteracaoCasas(int id){
    //Menu de edições possíveis
        System.out.println("1 - Listar divisões. ");
        System.out.println("2 - Editar divisões. "); 
        System.out.println("3 - Ligar/Desligar a Casa. ");
        System.out.println("4 - Ligar/Desligar uma divisão.");
        System.out.println("5 - Alterar Comercializador de energia.");
        System.out.println("6 - Retroceder");
        int choice = sc.nextInt();
        sc.nextLine();

        switch(choice){
            case(1):
                System.out.println(this.c.getHouseDivisoes(id).toString());
                menuInteracaoCasas(id);
            break;
            case(2):
                clearConsole();
                editaDivisoes(id);
                menuInteracaoCasas(id);
            break;
            case(3):
                System.out.println("0 - Desligar");
                System.out.println("1 - Ligar");
                choice = this.sc.nextInt();
                this.sc.nextLine();
                switch (choice) {
                    case (0) -> {
                        this.c.setHouseOFF(id);
                        System.out.println("Energia da Casa desligada com sucesso.");
                    }
                    case (1) -> {
                        this.c.setHouseOn(id);
                        System.out.println("Energia da Casa ligada com sucesso.");
                    }
                    default -> menuInteracaoCasas(id);
                }
                menuInteracaoCasas(id);
            case(4):
                System.out.println(this.c.getHouseDivisoes(id).toString());
                System.out.println("Seleciona o nome da divisão: ");
                String escolha = sc.nextLine();
                while(this.c.getCasaDivisao(id, escolha)==null){
                    System.out.println("Seleciona uma divisão válida: ");
                    escolha = sc.nextLine();
                }
                clearConsole();
                System.out.println("0 - Desligar");
                System.out.println("1 - Ligar");
                choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case (0) -> {
                        this.c.setCasaDivisaoOFF(id,escolha);
                        clearConsole();
                        System.out.println("Energia da divisão desligada com sucesso.");
                        menuInteracaoCasas(id);
                    }
                    case (1) -> {
                        this.c.setCasaDivisaoOn(id,escolha);
                        clearConsole();
                        System.out.println("Energia da divisão ligada com sucesso.");
                        menuInteracaoCasas(id);
                    }
                    default -> menuInteracaoCasas(id);
                }
            case(5):
                System.out.println("Comercializador Atual: "+this.c.getComercializadoratual(id));
                System.out.println("Escolha um comercializador dos listados abaixo: ");
                System.out.println(this.c.listComercializadores());
                String nome = sc.nextLine();
                while(this.c.hasComercializador(nome)){
                    System.out.println("Comercializador inválido, por favor selecione um nome válido entre os listados.");
                    nome = sc.nextLine();
                }
                clearConsole();
                this.c.mudaDeFornecedorString(id,nome, this.c.getDataAtual());
                System.out.println("Comercializador de energia mudado com sucesso.");
                menuInteracaoCasas(id);
            break;
            case(6):
                clearConsole();
                menuInteracaoCasas(id);
            break;
        }
    }

    public void editaDivisoes(int id){
        System.out.println("1 - Adicionar divisão");
        System.out.println("2 - Remover divisão");
        System.out.println("3 - Adicionar dispositivo a divisão");
        System.out.println("4 - Retroceder");
        int option = sc.nextInt();
        this.sc.nextLine();
        switch(option){
            case(1):
                System.out.println("Nome da divisão a adicionar");
                String divisao = sc.nextLine();
                this.c.criaDivisaoCasa(id,divisao);
                System.out.println("Divisão adicionada com sucesso");
            break;
            case(2):
                System.out.println("Nome da divisão a remover");
                divisao =  sc.nextLine();
                this.c.removeDivisaoCasa(id,divisao);
                System.out.println("Divisão removida com sucesso");
            break;
            case(3):
                System.out.println("Divisão a adicionar dispositivo:");
                divisao =  sc.nextLine();
                addDeviceToDivisaoMenu(id, divisao);
                break;
            case(4):
                menuInteracaoCasas(id);
            break;
        }

    }

}
