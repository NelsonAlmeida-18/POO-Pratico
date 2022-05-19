package View;

import java.io.*;
import java.util.Scanner;

import javax.print.attribute.standard.PresentationDirection;

import java.lang.InterruptedException;
import java.lang.ProcessBuilder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Controller.Controller;


//import static com.intellij.openapi.util.text.Strings.toUpperCase;
//mudar o ui para um to string e passamos ao parser para construir tudo de uma vez, just an idea


public class View{
    private Scanner sc;
    private Controller c;

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
            menuInicial();
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


    public void saveState(){
       try{
           System.out.println("Dá um nome ao teu ficheiro de estado:");
           String nameOfFile = sc.nextLine();
           File f = new File("./output/"+nameOfFile);
           while(f.exists()){
                System.out.println("Dá um nome válido ao teu ficheiro");
                nameOfFile = sc.nextLine();
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
     * Menu Inicial da View
     * @throws IOException Exception de não encontrar ficheiro
     */
    public void menuInicial() throws IOException {

        System.out.print("\n");
        System.out.println("Selecione uma das opções abaixo:\n");
        System.out.println("1 - Editar cidade");
        System.out.println("2 - Consultar SmartHouses existentes");
        System.out.println("3 - Consultar comercializadores de energia existentes");
        System.out.println("4 - Editar comercializador existente");
        System.out.println("5 - Consultar marcas existentes");
        System.out.println("6 - Consultar SmartDevices presets");
        System.out.println("7 - Carregar um estado de programa");
        System.out.println("8 - Carregar um ficheiro log");
        System.out.println("9 - Guardar estado");
        System.out.println("10 - Começar Simulação");
        System.out.println("11 - Sair");
        int res = sc.nextInt();
        sc.nextLine();
        switch (res) {
            case 1 -> { //Editar cidade
                clearConsole();
                try {
                    createMenu();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            case 2 -> { //Consultar SmartHouses existentes
                clearConsole();
                this.c.listSmartHouses();
                menuInicial();
            }
            case 3 -> { //Consultar comercializadores de energia existentes
                clearConsole();
                System.out.println(this.c.listComercializadores());
                menuInicial();
            }
            case 4 -> { //Editar comercializadores de energia existentes
                clearConsole();
                editComercializadores();
                menuInicial();
            }
            case 5 -> { //Consultar marcas existentes
                clearConsole();
                this.c.listMarcas();
                menuInicial();
            }
            case 6 -> { //Consultar SmartDevices presets
                clearConsole();
                this.c.listSmartDevicesPresets();
                menuInicial();
            }
            case 7 -> { //Carregar um estado de programa
                clearConsole();
                loadMenu();
                menuInicial();
            }
            case 8 -> { //Carregar um ficheiro log
                clearConsole();
                Controller p = new Controller();

                p.parse();

                menuInicial();
            }
            case 9 -> { //Guardar estado
                clearConsole();
                saveState(); //função save, não tem menu, simplesmente guarda na pasta save
                System.out.println("Estado do programa guardado");
                menuInicial();
            }
            case 10 -> { //Começar Simulação
                clearConsole();
                simulationMenu();
                menuInicial();
            }
            case 11 -> { //Sair
                clearConsole();
                System.out.println("Tem a certeza que quer sair?");
                if (response(sc) == 1) {
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

    /**
     * Menu do load
     */
    public void loadMenu() {
        System.out.println("Insira o path para o ficheiro que pretende carregar:");
        StringBuilder path = new StringBuilder();
        path.append(sc.nextLine());
        System.out.println("Insira o nome do ficheiro com a extensão (e.g. ficheiro.txt):");
        path.append(sc.nextLine());
        try{this.c.merge(path.toString());}
        catch(Exception e){
            e.printStackTrace();
        }
        try {menuInicial();}
        catch(Exception e){
            System.out.println("Erro a carregar estado.");
        }

    }

    /**
     * Menu para editar os valores dos comercializadores de energia, nomeadamente o valor do KWh e o fator imposto
     * @param this.c Cidade a ser utilizada
     * @param sc Scannar a ser utilizada
     * @throws IOException
     */

    public void editComercializadores() throws IOException{
        clearConsole();
        System.out.println("Seleciona um comercializador para editar dos listados abaixo: ");
        System.out.println(this.c.listComercializadores());
        String nome = sc.nextLine();
        //while(this.c.getComercializador(choice)==null){
        while(!(this.c.existsComercializador(nome))){
            clearConsole();
            System.out.println("Seleciona um comercializador para editar dos listados abaixo: ");
            System.out.println(this.c.listComercializadores());
            nome = sc.nextLine();
        }
        System.out.println("1 - Editar o custo por KWh");
        System.out.println("2 - Editar o fator de imposto");
        System.out.println("3 - Retroceder");
        String choice = sc.nextLine();
        switch(choice){
            case("1"):
                clearConsole();
                System.out.println("Valor atual do KWh: "+this.c.getComercializadorPrecoBaseKW(nome)); 
                System.out.print("Selecione um novo preço por KWh:");
                this.c.setComercializadorPrecoBaseKW(nome,Double.parseDouble(sc.nextLine()));   
                clearConsole();
                System.out.println("Valor do KWh atualizado com sucesso!");
                try {
                    menuInicial();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            break;
            case("2"):
                clearConsole();
                System.out.println("Valor atual do imposto: "+this.c.getComercializadorFatorImposto(nome)); 
                System.out.print("Selecione um novo valor do imposto:");
                this.c.setComercializadorFatorImposto(nome,Double.parseDouble(sc.nextLine()));
                clearConsole();
                System.out.println("Valor do imposto atualizado com sucesso!");
                menuInicial();
            break;
            default:
                clearConsole();
                menuInicial();
            break;
        }
    }



    /**
     * Menu de criar cidades, dispositivos e fornecedores de energia
     * @throws IOException Exception ao voltar ao menu inicial
     */
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
                //city.merge(p.parse(city.getHouseId(), city.getDeviceId())); //carregar faz gestão de conflitos para dar merge à cidade já existente e à cidade que se está a carregar do log
                this.c.parse();

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


    /**
     * Menu criar casa
     */
    public void createSmartHouseMenu(){

        System.out.println("Insira o nome do proprietário:");
        String nome_prop = this.sc.nextLine();

        int nif = 0;
        System.out.println("Insira o NIF do proprietário:");
        try {
            nif = this.sc.nextInt();
        } catch (java.util.InputMismatchException e) {
            this.sc.nextLine();
            System.out.println("NIF inválido.");
            return;
        }
        this.sc.nextLine();


        System.out.println("Insira a morada:");
        String morada = sc.nextLine();

        System.out.println("Insira o fornecedor:");
        this.c.listComercializadores();
        String fornecedor = sc.nextLine();

        this.c.createHouse(nome_prop, nif, morada, fornecedor); //não é o objeto mas sim o identificador acho

        System.out.println("Pretende adicionar divisões na casa?");
        int res = response(this.sc);

        if(res == 1) addDivisoesToHouseMenu(this.c.getHouseId());

    }

    /**
     * Menu de adicionar divisões a casa
     * @param house_id id da casa que se pretende adicionar divisão
     */
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
    
    /**
     * Menu de adicionar dispositivos a divisões a casa
     * @param house_id id da casa que se pretende adicionar divisão
     * @param nome_divisao Nome da divisão a adicionar o dispositivo
     */
    public void addDivisionDevicesMenu(int house_id, String nome_divisao){ //reviewed
        
        addDeviceToDivisaoMenu(house_id, nome_divisao); //adiciona um dispositivo a uma divisão
        System.out.println("Pretende adicionar mais um dispositivo na divisão?");
        int res = response(sc);
        if(res == 1){
            addDivisionDevicesMenu(house_id, nome_divisao);
        }
        
    }

    /**
     * Menu de adicionar dispositivos a divisões da casa
     * @param house_id id da casa que se pretende adicionar divisão
     * @param nome_divisao Scanner a ser utilizado
     */
    public void addDeviceToDivisaoMenu(int house_id, String nome_divisao){

        System.out.println("Adicionar SmartDevice em "+ nome_divisao);
        System.out.println("(escolha uma das seguintes opções)\n");
        System.out.println("1 - Adicionar preset");
        System.out.println("2 - Criar SmartDevice");

        int res = this.sc.nextInt();
        this.sc.nextLine();

        switch (res) {
            case 1 -> addPresetToDivisaoMenu(house_id, nome_divisao);
            //case 2 -> this.c.addDeviceToDivisao(house_id, nome_divisao, createSmartDeviceMenu()); //adiciona um dispositivo criado no momento
            case 2 -> createSmartDeviceMenu(house_id, nome_divisao); //adiciona um dispositivo criado no momento
        }
                  
    }
    
    /**
     * Menu de adicionar presets de dispositivos a divisões da casa
     * @param house_id id da casa que se pretende adicionar divisão
     * @param nome_divisao Nome da divisão a ser adicionada o preset
     */
    public void addPresetToDivisaoMenu(int house_id, String nome_divisao){

        System.out.println("Eis os presets existentes:");
        this.c.listSmartDevicesPresets();
        System.out.println("0 - Para retroceder");
        
        String preset_selection = this.sc.nextLine();
        if (preset_selection.equals("0"))
            addDeviceToDivisaoMenu(house_id, nome_divisao);

        this.c.addDeviceToDivisao(house_id,nome_divisao, preset_selection);
        
    }

    

    /**
     * Menu de criar device
     * @param house_id id da casa
     * @param divisao divisao
     */
    public void createSmartDeviceMenu(int house_id, String divisao){

        System.out.println("Escolha o tipo de SmartDevice para criar:");
        System.out.println("1 - SmartSpeaker");
        System.out.println("2 - SmartCamera");
        System.out.println("3 - SmartBulb");

        int res = this.sc.nextInt();
        this.sc.nextLine();

        switch (res) {
            case 1 -> createSmartSpeakerMenu(house_id, divisao);
            case 2 -> createSmartCameraMenu(house_id, divisao);
            case 3 -> createSmartBulbMenu(house_id,divisao);
            default -> {
                System.out.println("Opção inexistente");
                createSmartDeviceMenu(house_id,divisao);
            }
        }
    
    }
    
    /**
     * Menu de criar coluna
     * @param house_id id da casa
     * @param divisao divisao
     */
    public void createSmartSpeakerMenu(int house_id, String divisao){

        System.out.println("Insira a marca:");
        this.c.marcasListToString();
        String nome_marca = sc.nextLine();

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

        this.c.addSmartSpeakerToDivisao(house_id, divisao, nome_marca, consumo, estacao, volume, estado.toUpperCase());
        if(house_id == -1){
            String nome_preset = divisao;
            this.c.addSmartSpeakerPreset(nome_preset, nome_marca, consumo, estacao, volume, estado.toUpperCase());
        }
    }

    /**
     * Menu de criar camara
     * @param house_id id da casa
     * @param divisao divisao
     */
    public void createSmartCameraMenu(int house_id, String divisao){

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

        this.c.addSmartCameraToDivisao(house_id,divisao, width, heigth, tamanho,0.0, estado);
        if(house_id == -1){
            String nome_preset = divisao;
            this.c.addSmartCameraPreset(nome_preset, width, heigth, tamanho, estado); //TODO add SmartCamera preset com polimorfismo
        }
    }

    /**
     * Menu de criar lampada
     * @param house_id id da casa
     * @param divisao divisao
     */
     public void createSmartBulbMenu(int house_id, String divisao){

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

        this.c.addSmartBulbToDivisao(house_id, divisao, modo, dimensions, consumo, estado);
        if(house_id == -1){
            String nome_preset = divisao;
            this.c.addSmartBulbPreset(nome_preset, modo, dimensions, consumo);
        }
    }
    
    /**
     * Menu de criar fornecedor
     */
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
    
    
    /**
     * Menu de criar marca
     */
    public void createMarcaMenu(){

        System.out.println("Insira o nome da marca:");
        String nome = this.sc.nextLine();

        System.out.println("Insira o consumo diário associado ao uso dos dispostivos:");
        double consumo = this.sc.nextDouble();
        this.sc.nextLine();
        
        this.c.createMarca(nome, consumo);
        System.out.println("Marca "+ nome +" criada. Consumo diário dos dispositivos: "+ consumo);
        
    }

    
    /**
     * Menu de criar preset de dispositivo
     */
    public void createSmartDevicePresetMenu(){

        System.out.println("Insira o nome do preset:");
        String nome = this.sc.nextLine();

        System.out.println("Escolha o tipo de SmartDevice para criar:");
        System.out.println("1 - SmartSpeaker");
        System.out.println("2 - SmartCamera");
        System.out.println("3 - SmartBulb");

        int res = this.sc.nextInt();
        this.sc.nextLine();

        switch (res) {
            case 1 -> createSmartSpeakerMenu(-1,nome);
            case 2 -> createSmartCameraMenu(-1,nome);
            case 3 -> createSmartBulbMenu(-1,nome);
            default -> {
                System.out.println("Opção inexistente");
                createSmartDeviceMenu(-1,nome);
            }
        }
    }

   /*  public void deleteSmartDevicePresetMenu(Smartthis.c this.c){ //falta adicionar opções de voltar atrás ou cancelar

        this.c.listSmartDevicesPresets(); //lista os presets existentes

        System.out.println("Insira o nome do preset que pretende eliminar:");
        String preset_name = sc.nextLine();

        this.c.deleteSmartDevicePreset(preset_name);

    }

    public void listSmartHousesMenu(Smartthis.c this.c){ //falta adicionar opções de voltar atrás ou cancelar

        this.c.listSmartHouses(); //lista das existentes

        System.out.println("Insira o id da casa para ver mais detalhes:");
        int id_casa = sc.nextInt();

        this.c.getSmartHouseDetails(id_casa);

    }

    public void listSmartDevicesPresetsMenu(Smartthis.c this.c){ //falta adicionar opções de voltar atrás ou cancelar

        printPresetsList(this.c); //lista das existentes

        System.out.println("Insira o nome do SmartDevice para ver mais detalhes:");
        String nome = sc.nextLine();
        sc.nextLine();

        this.c.getSmartDevicePresetDetails(nome);

    } */

    
    /**
     * Menu de simulações
     */  
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
    }

    
    /**
     * Menu de o
     * @param time Tempo da simulação
     */
    public void simulationOptions(String time){
        System.out.println("1 - Casa mais gastadora.");
        System.out.println("2 - Comercializador com maior faturação.");
        System.out.println("3 - Listar faturas de um Comercializador.");
        System.out.println("4 - Maiores consumidores de energia num intervalo de tempo.");
        System.out.println("5 - Retroceder.");
        String choice = this.sc.nextLine();
        switch (choice) {
            case ("1") -> {
                //falta fazer a faturacao ou ver se a mesma já foi feita

                System.out.println("A casa mais gastadora neste período de tempo é a casa: " + this.c.getCasaMaisGastadora(time).getID());
                System.out.println("KW's consumidos: " + this.c.getCasaMaisGastadora(time).getConsumo());
                System.out.println("1 - Mais dados da casa.");
                System.out.println("2 - Retroceder.");
                choice = this.sc.nextLine();
                switch (choice) {
                    case ("1") -> {
                        clearConsole();
                        System.out.println("Dados da casa: ");
                        System.out.println("KW's consumidos: " + this.c.getCasaMaisGastadora(time).getConsumo());
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
    
    /**
     * Menu que edita casas
     * @throws IOException
     */
    public void editSmartHouse() throws IOException{
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

    /**
     * Menu de interação com uma casa
     * @param id id da casa
     * @throws IOException
     */
    public void menuInteracaoCasas(int id) throws IOException{
    //Menu de edições possíveis
        System.out.println("0 - Mostrar casa.");
        System.out.println("1 - Listar divisões. ");
        System.out.println("2 - Editar divisões. "); 
        System.out.println("3 - Ligar/Desligar a Casa. ");
        System.out.println("4 - Ligar/Desligar uma divisão.");
        System.out.println("5 - Alterar Comercializador de energia.");
        System.out.println("6 - Retroceder");
        int choice = sc.nextInt();
        sc.nextLine();

        switch(choice){
            case(0):
                clearConsole();
                System.out.println(this.c.getCasa(id).toString());
                menuInteracaoCasas(id);
            break;
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
                createMenu();

            break;
        }
    }

    /**
     * Menu que edita divisões da casa
     * @param id id da casa
     * @throws IOException
     */
    public void editaDivisoes(int id) throws IOException{
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

//TODO: fazer método para eliminar smartHouse