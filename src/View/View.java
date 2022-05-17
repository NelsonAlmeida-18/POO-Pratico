package View;

import java.io.*;
import java.util.Scanner;
import java.lang.InterruptedException;
import java.lang.ProcessBuilder;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Controller.*;
import Model.*; // tratar disto

//import static com.intellij.openapi.util.text.Strings.toUpperCase;
//mudar o ui para um to string e passamos ao parser para construir tudo de uma vez, just an idea


public class View {
    private Scanner sc;
    private Controller c;
    private SmartCity city=new SmartCity();

    /**
     * Inicializador do construtor da View
     * @param controller Controller a ser utilizado
     * @param scanner Scanner a utilizar
     */
    public View(Controller controller, Scanner scanner){
        this.sc = scanner;
        this.c = controller;
    }

    /**
     * Função que corre visualmente o projeto
     */
    public void run(){
        clearConsole();
        try {
            menuInicial(this.city,sc);
        } catch (Exception e) {System.out.println("Failed loading menuInicial");
        }
    }

    /**
     * Leitor de respostas de sim ou não.
     * @param sc Scanner a utilizar
     * @return opção escolhida
     */
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

    /**
     * @param city Cidade a ser utilizada
     * @param sc Scanner a ser utilizador
     */
    public void saveState(SmartCity city, Scanner sc){
       try{
           System.out.println("Dá um nome ao teu ficheiro de estado:");
           String nameOfFile = sc.nextLine();
           File f = new File("./output/"+nameOfFile);
           while(f.exists()){
                System.out.println("Dá um nome válido ao teu ficheiro");
                nameOfFile = sc.nextLine();
                f = new File("./output/"+nameOfFile);
           }
           city.saveState(nameOfFile);
       } 
       catch(FileNotFoundException e){
           System.out.println("Error creating file with that name!");
       }
       catch(IOException e){
           System.out.println("Error saving state!");
       }
    }

    /**
     * Limpa a consola
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
    
    /**
     * Menu inicial gráfico
     * @param city Cidade a ser utilizada
     * @param sc Scanner a ser utilizado
     * 
     */
    public void menuInicial(SmartCity city, Scanner sc) throws IOException {
        int res;
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
        res = sc.nextInt();
        sc.nextLine();
        switch (res) {
            case 1 -> { //Editar cidade
                clearConsole();
                createMenu(city, sc);
            }
            case 2 -> { //Consultar SmartHouses existentes
                clearConsole();
                city.listSmartHouses();
                menuInicial(city, sc);
            }
            case 3 -> { //Consultar comercializadores de energia existentes
                clearConsole();
                System.out.println(city.listComercializadores());
                //city.listFaturas();
                menuInicial(city, sc);
            }
            case 4 -> { //Consultar marcas existentes
                clearConsole();
                city.listMarcas();
                menuInicial(city, sc);
            }
            case 5 -> { //Consultar SmartDevices presets
                clearConsole();
                city.listSmartDevicesPresets();
                menuInicial(city, sc);
            }
            case 6 -> { //Carregar um estado de programa
                clearConsole();
                loadMenu(city,sc);
                menuInicial(city, sc);
            }
            case 7 -> { //Carregar um ficheiro log
                clearConsole();
                Controller p = new Controller(city);
                //city = p.parse(city.getHouseId(), city.getDeviceId());

                //não devia ser algo deste género?
                //SmartCity temp = p.parse(city.getHouseID(), city.getDeviceId());
                //city.merge(temp);
                //assim podiamos dar merge de vários files

                p.parse();
                menuInicial(city, sc);
            }
            case 8 -> { //Guardar estado
                clearConsole();
                saveState(city, sc); //função save, não tem menu, simplesmente guarda na pasta save
                System.out.println("Estado do programa guardado");
                menuInicial(city, sc);
            }
            case 9 -> { //Começar Simulação
                clearConsole();
                simulationMenu(city, sc);
                menuInicial(city, sc);
            }
            case 10 -> { //Sair
                clearConsole();
                System.out.println("Tem a certeza que quer sair?");
                if (response(sc) == 1) {
                    System.out.println("That's all, folks!.");
                } else {
                    menuInicial(city, sc);
                }
            }
            default -> {
                clearConsole();
                System.out.println("Opção inválida.");
                menuInicial(city, sc);
            }
        }
    }

    public void loadMenu(SmartCity city, Scanner sc) {
        System.out.println("Insira o path para o ficheiro que pretende carregar:");
        StringBuilder path = new StringBuilder();
        path.append(sc.nextLine());
        System.out.println("Insira o nome do ficheiro com a extensão (e.g. ficheiro.txt):");
        path.append(sc.nextLine());
        city.merge((SmartCity)ReadObjectFromFile(path.toString())); //createMenuLine devolve a cidade guardada no ficheiro
        /*try {menuInicial(city,sc);}
        catch(Exception e){
            System.out.println("Erro a carregar estado.");
        }*/

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

    public void createMenu(SmartCity city, Scanner sc) throws IOException{
        int res;
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
        res = sc.nextInt();
        sc.nextLine();

        switch (res) {
            case 1 -> { //Criar SmartHouse
                createSmartHouseMenu(city, sc);
                createMenu(city, sc);
            }
            case 2 -> { //Editar uma smartHouse(Ligar e desligar todos os dispostivios/dispostivos individuais)

                editSmartHouse(city, sc);
                createMenu(city, sc);
            }
            case 3 -> { //Criar Comercializador de Energia
                createComercializadorMenu(city, sc);
                createMenu(city, sc);
            }
            case 4 -> { //Criar marca de SmartSpeaker
                createMarcaMenu(city, sc);
                createMenu(city, sc);
            }
            case 5 -> { //Criar preset de SmartDevice
                createSmartDevicePresetMenu(city, sc);
                createMenu(city, sc);
            }
            case 6 -> //Eliminar um preset de SmartDevice
                //deleteSmartDevicePresetMenu(city);
                    createMenu(city, sc);
            case 7 -> { //Adicionar apartir de log
                clearConsole();
                Controller p = new Controller(city);
                //city.merge(p.parse(city.getHouseId(), city.getDeviceId())); //carregar faz gestão de conflitos para dar merge à cidade já existente e à cidade que se está a carregar do log
                p.parse();
                createMenu(city, sc);
            }
            case 8 -> { //Retroceder
                clearConsole();
                try {
                    menuInicial(city, sc);
                } catch (Exception e) {
                    System.out.println("Failed to load menuInicial");
                }
            }
        }
    }

    public void createSmartHouseMenu(SmartCity city, Scanner sc){

        System.out.println("Insira o nome do proprietário:");
        String nome_prop = sc.nextLine();

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
        city.listComercializadores();
        String fornecedor = sc.nextLine();

        int house_id = city.createHouse(nome_prop, nif, morada, fornecedor); //não é o objeto mas sim o identificador acho

        System.out.println("Pretende adicionar divisões na casa?");
        int res = response(sc);

        if(res == 1) addDivisoesToHouseMenu(house_id, city, sc);

    }

    public void addDivisoesToHouseMenu(int house_id, SmartCity city, Scanner sc){ //reviewed

        System.out.println("Insira o nome da divisão:");
        String nome_divisao = sc.nextLine();

        city.criaDivisao(nome_divisao);

        System.out.println("Pretende adicionar dispositivos na divisão?");
        int res = response(sc);
        if(res == 1){
            addDivisionDevicesMenu(city, house_id, nome_divisao);
        }

        System.out.println("Pretende adicionar mais divisões?");
        res = response(sc);
        if(res == 1){
            addDivisoesToHouseMenu(house_id, city, sc);
        }

        
    }
 
    public void addDivisionDevicesMenu(SmartCity city, int house_id, String nome_divisao){ //reviewed
        
        addDeviceToDivisaoMenu(city, house_id, nome_divisao, sc); //adiciona um dispositivo a uma divisão
        System.out.println("Pretende adicionar mais um dispositivo na divisão?");
        int res = response(sc);
        if(res == 1){
            addDivisionDevicesMenu(city, house_id, nome_divisao);
        }
        
    }

    public void addDeviceToDivisaoMenu(SmartCity city, int house_id, String nome_divisao, Scanner sc){

        System.out.println("Adicionar SmartDevice em "+ nome_divisao);
        System.out.println("(escolha uma das seguintes opções)\n");
        System.out.println("1 - Adicionar preset");
        System.out.println("2 - Criar SmartDevice");

        int res = sc.nextInt();
        sc.nextLine();

        switch (res) {
            case 1 -> addPresetToDivisaoMenu(city, house_id, nome_divisao, sc);
            case 2 ->
                    city.addDeviceToDivisao(nome_divisao, createSmartDeviceMenu(city, sc)); //adiciona um dispositivo criado no momento
        }
                  
    }
    
    public void addPresetToDivisaoMenu(SmartCity city, int house_id, String nome_divisao, Scanner sc){

        System.out.println("Eis os presets existentes:");
        city.listSmartDevicesPresets();
        
        String preset_selection = sc.nextLine();

        city.addDeviceToDivisao(nome_divisao, preset_selection);
        
    }

    public SmartDevice createSmartDeviceMenu(SmartCity city, Scanner sc){

        System.out.println("Escolha o tipo de SmartDevice para criar:");
        System.out.println("1 - SmartSpeaker");
        System.out.println("2 - SmartCamera");
        System.out.println("3 - SmartBulb");

        int res = sc.nextInt();
        sc.nextLine();

        SmartDevice sd;

        switch (res) {
            case 1 -> sd = createSmartSpeakerMenu(city, sc);
            case 2 -> sd = createSmartCameraMenu(city, sc);
            case 3 -> sd = createSmartBulbMenu(city, sc);
            default -> {
                System.out.println("Opção inexistente");
                sd = createSmartDeviceMenu(city, sc);
            }
        }
        
        return sd;
    }

    public SmartSpeaker createSmartSpeakerMenu(SmartCity city, Scanner sc){

        System.out.println("Insira a marca:");
        city.marcasListToString();
        String nome_marca = sc.nextLine();
        Marca marca = city.getMarca(nome_marca);

        System.out.println("Insira o consumo diário: (kWh)");
        double consumo = sc.nextDouble();
        sc.nextLine();
        
        System.out.println("Insira o nome da estação de rádio:");
        String estacao = sc.nextLine();

        System.out.println("Insira o volume (0 a 20):");
        int volume = sc.nextInt();
        sc.nextLine();

        System.out.println("Insira o estado (ON/OFF):");
        String estado = sc.nextLine();

        return new SmartSpeaker(city.giveDeviceId(),volume, estacao, estado, marca, consumo);

    }

    public SmartCamera createSmartCameraMenu(SmartCity city, Scanner sc){

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

        return new SmartCamera(city.giveDeviceId(), width, heigth, tamanho, estado);
    }

    public SmartBulb createSmartBulbMenu(SmartCity city, Scanner sc){

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

        return new SmartBulb(city.giveDeviceId(), modo, dimensions, consumo, estado);
        
    }

    public void createComercializadorMenu(SmartCity city, Scanner sc){

        System.out.println("Insira o nome do comercializador:");
        String nome = sc.nextLine();

        System.out.println("Insira o preço base (KW):");
        float preco = sc.nextFloat();
        sc.nextLine();
        
        System.out.println("Insira o fator imposto:");
        float imposto = sc.nextFloat();
        sc.nextLine();

        city.createComercializadorEnergia(nome, preco, imposto);
        System.out.println("Comercializador "+ nome +" criado");
        
    }
    
    public void createMarcaMenu(SmartCity city, Scanner sc){

        System.out.println("Insira o nome da marca:");
        String nome = sc.nextLine();

        System.out.println("Insira o consumo diário associado ao uso dos dispostivos:");
        double consumo = sc.nextDouble();
        sc.nextLine();
        
        city.createMarca(nome, consumo);
        System.out.println("Marca "+ nome +" criada. Consumo diário dos dispositivos: "+ consumo);
        
    }

    
    public void createSmartDevicePresetMenu(SmartCity city,Scanner sc){

        System.out.println("Insira o nome do preset:");
        String nome = sc.nextLine();

        city.addDevicePreset(nome, createSmartDeviceMenu(city, sc));
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
        
    public void simulationMenu(SmartCity city, Scanner sc) {
        System.out.println("1 - Simulação Manual. ");
        System.out.println("2 - Simulação através de ficheiro") ;
        System.out.println("3 - Retroceder.");
        String choice = sc.nextLine();
        switch(choice){
            case("1"):
                System.out.println("Indique a data: (X dias ou DD.MM.YYYY)");
                String time = sc.nextLine();
                clearConsole();
                simulationOptions(city, sc, time);
                //fazer o resto dos métodos
            break;
            case("2"):
                System.out.println("Diretório do ficheiro: ");
                //fazer novo parser e tudo o resto.
            break;
            case("3"):
                clearConsole();
                try {
                    menuInicial(city, sc);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            break;
        }
    }

    public void simulationOptions(SmartCity city, Scanner sc, String time){
        System.out.println("1 - Casa mais gastadora.");
        System.out.println("2 - Comercializador com maior faturação.");
        System.out.println("3 - Listar faturas de um Comercializador.");
        System.out.println("4 - Maiores consumidores de energia num intervalo de tempo.");
        System.out.println("5 - Retroceder.");
        String choice = sc.nextLine();
        switch(choice){
            case("1"):

            //falta fazer a faturacao ou ver se a mesma já foi feita

                System.out.println("A casa mais gastadora neste período de tempo é a casa: " + city.getCasaMaisGastadora().getID());
                System.out.println("KW's consumidos: "+city.getCasaMaisGastadora().getConsumoDaCasa());
                System.out.println("1 - Mais dados da casa.");
                System.out.println("2 - Retroceder.");
                choice = sc.nextLine();
                switch(choice){
                    case("1"):
                        clearConsole();
                        System.out.println("Dados da casa: ");
                        System.out.println("KW's consumidos: "+city.getCasaMaisGastadora().getConsumoDaCasa());
                        System.out.println(city.getCasaMaisGastadora().toString());
                        simulationOptions(city, sc, time);
                        break;
                    case("2"):
                        clearConsole();
                        simulationOptions(city, sc, time);
                        break;
                }
            break;     
            case("2"):
                clearConsole();
                System.out.println("Comercializador de Energia com maior faturação: "+ city.getComercializadorMaiorFaturacao().getNome());
                System.out.println("Total faturado: "+ city.getComercializadorMaiorFaturacao().getFaturacao());
                System.out.println("1 - Mais dados do Comercializador.");
                System.out.println("2 - Retroceder.");
                choice = sc.nextLine();
                switch(choice){
                    case("1"):
                        clearConsole();
                        System.out.println("Dados do Comercializador: ");
                        System.out.println(city.getComercializadorMaiorFaturacao().toString());
                        simulationOptions(city, sc, time);
                        break;
                    case("2"):
                        clearConsole();
                        simulationOptions(city, sc, time);
                        break;
                }
            break;
            case("3"):
                clearConsole();
                System.out.println(city.listComercializadores());
                System.out.println("Indique o nome do Comercializador do qual pretende obter faturas.");
                choice = sc.nextLine();
                while(city.hasComercializador(choice)==false){
                    clearConsole();
                    System.out.println(city.listComercializadores());
                    System.out.println("\nIndique o nome do Comercializador do qual pretende obter faturas:");
                    choice = sc.nextLine();
                }
                clearConsole();
                ComercializadoresEnergia temp = city.getComercializador(choice);
                temp.faturacao(time); //ver isto com a data data a data
                System.out.println(temp.getListaFaturacao()); //TODO:testar o listaFaturacao
                simulationOptions(city, sc, time);
            break;
            case("4"):
                clearConsole();
                String formato = "dd.MM.yyyy";
                DateTimeFormatter formatador = DateTimeFormatter.ofPattern(formato);
                System.out.println("Data inicial (dd.MM.AAAA).");
                String dataInicialTexto = sc.nextLine();
                LocalDate dataInicial = LocalDate.from(formatador.parse(dataInicialTexto));
                System.out.println("Data Final (dd.MM.AAAA).");
                String dataFinalTexto = sc.nextLine();
                LocalDate dataFinal = LocalDate.from(formatador.parse(dataFinalTexto));
                LocalDate dataCriacao = city.getDataAtual();  //Mudar para data Inicial
                System.out.println(dataCriacao.isAfter(dataInicial));
                System.out.println(dataInicial.isAfter(dataFinal));
                System.out.println(dataCriacao);
                while(dataCriacao.isAfter(dataInicial) || dataInicial.isAfter(dataFinal)){
                    clearConsole();
                    System.out.println("Datas inválidas!");
                    System.out.println("Data inicial mínima: "+dataCriacao);
                    System.out.println("Data inicial (dd.MM.AAAA).");
                    dataInicialTexto = sc.nextLine();
                    dataInicial = LocalDate.from(formatador.parse(dataInicialTexto));
                    System.out.println("Data Final (dd.MM.AAAA).");
                    dataFinalTexto = sc.nextLine();
                    dataFinal = LocalDate.from(formatador.parse(dataFinalTexto));
                }
                System.out.println("A casa mais gastadora entre "+dataInicialTexto+" e "+dataFinalTexto+ " foi: "+city.getCasaMaisGastadora(dataInicial, dataFinal));
                simulationOptions(city, sc, time);
                break;
            case("5"):
                clearConsole();
                simulationMenu(city, sc);
            break;
            default:
                clearConsole();
                simulationOptions(city, sc, time);
            break;
        }
    }

    public void editSmartHouse(SmartCity city, Scanner sc){
        System.out.println("Indique o ID da casa que pertende editar(0-"+(city.getHouseId()-1)+"):");
        int id=sc.nextInt();
        sc.nextLine();
        SmartHouse casa = city.getCasa(id);
        while(casa==null){
            System.out.println("Por favor indique um ID válido(0-"+(city.getHouseId()-1)+"):"); 
            id=sc.nextInt();
            sc.nextLine();
            casa = city.getCasa(id);
        }
        clearConsole();
        menuInteracaoCasas(city, casa, sc);
    }

    public void menuInteracaoCasas(SmartCity city, SmartHouse casa, Scanner sc){
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
                System.out.println(casa.getNomeDivisoes().toString());
                menuInteracaoCasas(city, casa, sc);
            break;
            case(2):
                clearConsole();
                editaDivisoes(city, casa, sc);
                menuInteracaoCasas(city, casa, sc);
            break;
            case(3):
                System.out.println("0 - Desligar");
                System.out.println("1 - Ligar");
                choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case (0) -> {
                        casa.setHouseOFF();
                        clearConsole();
                        System.out.println("Energia da Casa desligada com sucesso.");
                    }
                    case (1) -> {
                        casa.setHouseOn();
                        clearConsole();
                        System.out.println("Energia da Casa ligada com sucesso.");
                    }
                    default -> menuInteracaoCasas(city, casa, sc);
                }
                menuInteracaoCasas(city, casa, sc);
            case(4):
                System.out.println(casa.getDivisaoList());
                System.out.println("Seleciona o nome da divisão: ");
                String escolha = sc.nextLine();
                while(casa.getDivisao(escolha)==null){
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
                        casa.setDivisaoOFF(escolha);
                        clearConsole();
                        System.out.println("Energia da divisão desligada com sucesso.");
                        menuInteracaoCasas(city, casa, sc);
                    }
                    case (1) -> {
                        casa.setDivisaoOn(escolha);
                        clearConsole();
                        System.out.println("Energia da divisão ligada com sucesso.");
                        menuInteracaoCasas(city, casa, sc);
                    }
                    default -> menuInteracaoCasas(city, casa, sc);
                }
            case(5):
                System.out.println("Comercializador Atual: "+casa.getCompanhia_eletrica());
                System.out.println("Escolha um comercializador dos listados abaixo: ");
                System.out.println(city.listComercializadores());
                String nome = sc.nextLine();
                while(city.getComercializador(nome)==null){
                    System.out.println("Comercializador inválido, por favor selecione um nome válido entre os listados.");
                    nome = sc.nextLine();
                }
                clearConsole();
                casa.mudaDeFornecedor(city.getComercializador(nome), city.getDataAtual());
                System.out.println("Comercializador de energia mudado com sucesso.");
                menuInteracaoCasas(city, casa, sc);
            break;
            case(6):
                clearConsole();
                try {
                    createMenu(city, sc);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            break;
        }
    }

    public void editaDivisoes(SmartCity city, SmartHouse casa, Scanner sc){
        System.out.println("1 - Adicionar divisão");
        System.out.println("2 - Remover divisão");
        System.out.println("3 - Adicionar dispositivo a divisão");
        System.out.println("4 - Retroceder");
        int option = sc.nextInt();
        sc.nextLine();
        switch(option){
            case(1):
                System.out.println("Nome da divisão a adicionar");
                String divisao = sc.nextLine();
                casa.addDivisao(divisao);
                System.out.println("Divisão adicionada com sucesso");
            break;
            case(2):
                System.out.println("Nome da divisão a remover");
                divisao =  sc.nextLine();
                casa.removeDivisao(divisao);
                System.out.println("Divisão removida com sucesso");
            break;
            case(3):
                System.out.println("Divisão a adicionar dispositivo:");
                divisao =  sc.nextLine();
                addDeviceToDivisaoMenu(city, casa.getID(), divisao, sc);
                break;
            case(4):
                menuInteracaoCasas(city, casa, sc);
            break;
        }

    }

}
