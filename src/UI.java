import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.lang.InterruptedException;
import java.time.*;
import java.util.Map;
import java.io.File;
import java.util.List;
import java.util.ListIterator;


//import static com.intellij.openapi.util.text.Strings.toUpperCase;

public class UI {

    public UI(){
    }

    public static int response(){

        Scanner sc = new Scanner(System.in);
        System.out.print("[y/n] ");
        String res = sc.next();
        sc.close();
        res = res.toLowerCase();
        int ret;


        if(res.equalsIgnoreCase("não") || res.equalsIgnoreCase("n") || res.equalsIgnoreCase("no")){
            ret = 0;
        }else if(res.equalsIgnoreCase("sim") || res.equalsIgnoreCase("s") || res.equalsIgnoreCase("y") || res.equalsIgnoreCase("yes")){
            ret = 1;
        }else{
            System.out.println("Resposta inválida");
            ret = response();
        }
        
        return ret;
    }

    public void saveState(SmartCity city, String nameOfFile){
        city.saveState(nameOfFile);
    }

    public static void clearConsole() throws IOException {
       try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else {
                System.out.print("\033\143");
            }
        } catch (IOException | InterruptedException ex) { System.out.println(ex.getMessage());}
    }

    public void printComercializadoresList(SmartCity city){
        System.out.println("Comercializadores de energia disponíveis:");
        ListIterator<ComercializadoresEnergia> iter = city.getComercializadores().listIterator();
        while(iter.hasNext()){
            iter.next().toString();
        }
    }

    public void printPresetsList(SmartCity city){
        System.out.println("Presets disponíveis:");
        ListIterator<ComercializadoresEnergia> iter = city.getComercializadores().listIterator();
        while(iter.hasNext()){
            iter.next().toString();
        }
    }
    
    public void menuInicial(SmartCity city) throws IOException {
        int res;
        Scanner sc = new Scanner(System.in);
        System.out.println("Selecione uma das opções abaixo:\n");
        System.out.println("1 - Criar uma cidade");
        System.out.println("2 - Carregar a partir de um estado de programa");
        System.out.println("3 - Carregar a partir de um estado de programa");
        System.out.println("4 - Sair");
        res = sc.nextInt();
        sc.close();
        switch(res){
            case 1:
             clearConsole();
                createMenu(city);
                break;

            case 2:
                clearConsole();
                loadMenu(city);
                break;
            
            case 2:
                clearConsole();
                city = parse(city.getHouseId(), city.getDeviceId());
                break;
            
            case 4:
                System.out.println("Tem a certeza que quer sair?");
                if(response() == 1){
                    System.out.println("Saindo");
                    try{
                        Thread.sleep(2000);
                    }catch(Exception e){
                        System.out.println("[DEBUG] erro: computador impaciente");
                    }
                }
                else{menuInicial(city);}
            break;

            default:
                System.out.println("Opção inválida.");
                menuInicial(city);
            break;
            
        }
    }
/* 
    public void loadMenu(SmartCity city) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Insira o path para o ficheiro que pretende carregar:");
        String path = sc.next();
        System.out.println("Insira o nome do ficheiro com a extensão (e.g. ficheiro.txt):");
        String file = sc.next();
        sc.close();
        path.concat(file);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();
            while (line != null) {
                createMenuLine(city,line); //createMenuLine devolve a cidade guardada no ficheiro
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    } */
        /*
    // data , casa, dispositivo, ação
    // YYYY-MM-DD, casaX, acao (case 1.0)
    // YYYY-MM-DD, casaX, dispostivoY, acao (CASE 1.1)
    // YYYY-MM-DD, fornecedorX, alteraValorDesconto, novoValor (CASE 2)
    // YYYY-MM-DD, casaX, fornecedorN -> sendo fornecedorN o novo comercializador de energia (CASE 1.2)
        */
    public void createMenuLine(SmartCity cidade, String line){
        String [] token = line.split(",\\s+"); // criar tokens
        String date = token[0];
        String [] data_token = date.split(".");

        // data
        int year = Integer.parseInt(data_token[0]);
        int mes = Integer.parseInt(data_token[1]);
        Month month = Month.of(mes);
        int day = Integer.parseInt(data_token[2]);
        LocalDate data = LocalDate.of(year,month,day);

        if(cidade.getCasas().contains(token[1])) {  // already created house related actions
           if(cidade.getComercializadores().contains(token[2])){ // se for um comercializador, function trocar de comercializador
               // change comercializador
           } else { // é um device

            //é capaz de dar erro quando retorna null fazer case para isso
                if(cidade.getCasa(token[1]).existsDevice(token[2])){ // already created device
                    switch((token[3]).toUpperCase()){
                        case "SETON":
                            // function ligar
                            break;
                        case "SETOFF":
                            // function desligar
                            break;
                        case "DELETE":
                            // function delete
                            break;
                    }
                } else{ // device not created
                    // token[1].createSmartDevicePresetMenu(token[2])// function criar dispositivo
                }
           }
        } else if(comercializadores.constains(token[1])){ // already created comercializadores related actions
            switch((token[2]).toUpperCase()){ // tipo de operação para comercializadores
                case "ALTERAIMPOSTO":
                    // function altera imposto de marca
                    break;
                case "ALTERAKWH":
                    // function altera kw/h de marca
                    break;
            }
        } else { // açao direta
            /* como distinguir que queremos criar uma casa, ou comercializador, ou marca ou device baseado no seu ID*/

            /*
            createSmartDevicePresetMenu();
            createSmartHouseMenu();
            createMarcaMenu();
            createComercizalidorMenu();
             */
        }
    }

    public void createMenu(SmartCity city){
        int res;
        Scanner sc = new Scanner(System.in);
        System.out.println("Selecione uma das opções abaixo:");
        System.out.println();
        System.out.println("1 - Criar SmartHouse");
        System.out.println("2 - Criar Comercializador de Energia");
        System.out.println("3 - Criar marca de SmartSpeaker");
        System.out.println("4 - Criar preset de SmartDevice");
        System.out.println("5 - Eliminar um preset de SmartDevice");
        System.out.println("6 - Consultar SmartHouses existentes");
        System.out.println("7 - Consultar SmartDevices presets");
        System.out.println("8 - Carregar de um ficheiro");
        System.out.println("9 - Salvar para um ficheiro");
        System.out.println("10 - Ir para a simulação");
        res = sc.nextInt();
        sc.close();

        switch(res){
            case 1:
                createSmartHouseMenu(city);
                createMenu(city);
            break;

            case 2:
                createComercializadorMenu(city);
                createMenu(city);
            break;

            case 3:
                createMarcaMenu(city);
                createMenu(city);
            break;

            case 4:
                createSmartDevicePresetMenu(city);
                createMenu(city);
            break;

            case 5:
                deleteSmartDevicePresetMenu(city);
                createMenu(city);
            break;

            case 6:
                listSmartHousesMenu(city);
                createMenu(city);
            break;

            case 7:
                listSmartDevicesPresetsMenu(city);
                createMenu(city);
            break;

            case 8:
                city = carregar(parse(city.getHouseId(), city.getDeviceId())); //carregar faz gestão de conflitos para dar merge à cidade já existente e à cidade que se está a carregar do log
            break;

            case 9:
                saveState(city); //função save, não tem menu, simplesmente guarda na pasta save
                System.out.println("Estado do programa guardado");
                createMenu(city);
            break;

            case 10:
                simulationMenu(city);
                menuInicial(city);
            break;
        }
    }

    public void createSmartHouseMenu(SmartCity city){
        Scanner sc = new Scanner(System.in);

        System.out.println("ID da casa:");
        String id = sc.next();
        
        System.out.println("Insira o nome do proprietário:");
        String nome_prop = sc.next();
        
        System.out.println("Insira o NIF do proprietário:");
        String nif = sc.next(); //TODO isto vai ser um int
        
        System.out.println("Insira a morada:");
        String morada = sc.next();
        
        System.out.println("Insira o fornecedor:");
        printComercializadoresList(city);
        String fornecedor = sc.next();

        System.out.println("Insira o nome da casa: [ID único]");
        String house_id = sc.next();

        //Nada disto faz sentido, temos de falar 
        //if(city.listComercializadores().toString().contains(fornecedor)){

        //}
        //if (hname!=null) SmartHouse hname = city.createHouse(nome_prop, nif, morada, fornecedor); else (o que está em baixo)

        city.createHouse(house_id, nome_prop, nif, morada, fornecedor); //não é o objeto mas sim o identificador acho

        System.out.println("Pretende adicionar divisões na casa?");
        int res = response();
        sc.close();

        if(res == 1) addDivisoesToHouseMenu(house_id, city);

    }

    public void addDivisoesToHouseMenu(String house_id, SmartCity city){ //reviewed
        
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Insira o nome da divisão:");
        String nome_divisao = sc.next();
        sc.close();
        city.criaDivisao(house_id, nome_divisao);

        System.out.println("Pretende adicionar dispositivos na divisão?");
        int res = response();
        if(res == 1){
            addDivisionDevicesMenu(city, house_id, nome_divisao);
        }

        System.out.println("Pretende adicionar mais divisões?");
        res = response();
        if(res == 1){
            addDivisoesToHouseMenu(house_id, city);
        }

        
    }
 
    public void addDivisionDevicesMenu(SmartCity city, String house_id, String nome_divisao){ //reviewed
        
        addDeviceToDivisaoMenu(city, house_id, nome_divisao); //adiciona um dispositivo a uma divisão
        System.out.println("Pretende adicionar mais um dispositivo na divisão?");
        int res = response();
        if(res == 1){
            addDivisionDevicesMenu(city, house_id, nome_divisao);
        }
        
    }

    public void addDeviceToDivisaoMenu(SmartCity city, String house_id, String nome_divisao){
 
            Scanner sc = new Scanner(System.in);
            
            System.out.println("Adicionar SmartDevice em "+ nome_divisao);
            System.out.println("(escolha uma das seguintes opções)\n");
            System.out.println("1 - Adicionar preset");
            System.out.println("2 - Criar SmartDevice");

        int res = sc.nextInt();
        sc.close();

            switch(res){
                case 1:
                    //neste menu aparece uma lista dos presets existentes e a pessoa escolhe qual quer para dar clone para a divisão da casa
                    addPresetToDivisaoMenu(city, house_id, nome_divisao);
                break;
    
                case 2:
                    //abrir o menu de criação de um smart device que no fim devolve um smartDevice que é colocado na divisão por uma função da smartCity
                    city.addDeviceToDivisao(house_id, nome_divisao, createSmartDeviceMenu(city)); //adiciona um dispositivo criado no momento
                break;
            }
                  
    }
    

    //TODO - injetar e mudar list devices na smartcity para map
    public void addPresetToDivisaoMenu(SmartCity city, String house_id, String nome_divisao){

        Scanner sc = new Scanner(System.in);
        
        //lista os presets

        System.out.println("Eis os presets existentes:");
        printPresetsList(city);
        
        String preset_selection = sc.next();
        sc.close();

        city.addDeviceToDivisao(house_id, nome_divisao, preset_selection);
        
    }

    public SmartDevice createSmartDeviceMenu(SmartCity city){

        Scanner sc = new Scanner(System.in);
        
        System.out.println("Escolha o tipo de SmartDevice para criar:");
        System.out.println("1 - SmartSpeaker");
        System.out.println("2 - SmartCamera");
        System.out.println("3 - SmartBulb");

        int res = sc.nextInt();
        sc.close();

        SmartDevice sd;
        
        switch(res){
            case 1:
                sd = createSmartSpeakerMenu(city);
            break;

            case 2:
                sd = createSmartCameraMenu(city);
            break;

            case 3:
                sd = createSmartBulbMenu(city);
            break;

            default:
                System.out.println("Opção inexistente");
                sd = createSmartDeviceMenu(city);
            break;
        }
        
        return sd;
    }

    public SmartSpeaker createSmartSpeakerMenu(SmartCity city){

        Scanner sc = new Scanner(System.in);
        System.out.println("Deseja inserir atributos no dispositivo?");
        System.out.println("0 - inicializa com as predefinições");
        System.out.println("1 - indicar as predefinições");

        int res = sc.nextInt();

        switch(res){
            case 0:
                //city.createSmartSpeaker();//não se podem criar smartDevices vazios
                break;

            case 1:
                System.out.println("Insira o volume (0 a 20):");
                int volume = sc.nextInt();
            
                System.out.println("Insira o nome da estação de rádio:");
                String estacao = sc.next();
            
                System.out.println("Insira o estado (ON/OFF):");
                String estado = sc.next();

                return city.createSmartSpeaker(volume, estacao, estado);


            break;
        }

        sc.close();
        System.out.println("SmartSpeaker adicionado com exito.");
        
    }

    public SmartCamera createSmartCameraMenu(SmartCity city){

        Scanner sc = new Scanner(System.in);
        System.out.println("Deseja inserir atributos no dispositivo?");
        System.out.println("0 - inicializa com as predefinições");
        System.out.println("1 - indicar as predefinições");

        int res = sc.nextInt();

        switch(res){
            case 0:
                city.createSmartCamera();
            break;

            case 1:
                System.out.println("Insira o resolução desejada:");
                int resolucao = sc.nextInt();
            
                System.out.println("Insira o tamanho desejado dos ficheiros:");
                int tamanho = sc.nextInt();
            
                System.out.println("Insira o estado (ON/OFF):");
                String estado = sc.next();

                return city.createSmartCamera(resolucao, tamanho, estado);

            break;
        }

        sc.close();
        System.out.println("SmartCamera adicionada com exito.");
        
    }

    public SmartBulb createSmartBulbMenu(SmartCity city){

        Scanner sc = new Scanner(System.in);
        System.out.println("Deseja inserir atributos no dispositivo?");
        System.out.println("0 - inicializa com as predefinições");
        System.out.println("1 - indicar as predefinições");

        int res = sc.nextInt();

        switch(res){
            case 0:
                return city.createSmartBulb();
            break;

            case 1:
                System.out.println("Insira as dimensões da lâmpada:");
                int dimensions = sc.nextInt();
            
                System.out.println("Insira o modo em que se encontra a lâmpada (COLD, NEUTRAL, WARM):");
                String modo = sc.next());
            
                System.out.println("Insira o estado (ON/OFF):");
                String estado = sc.next();

                return city.createSmartBulb(dimensions, modo, estado);

            break;
        }

        sc.close();
        System.out.println("SmartBulb adicionada com exito.");
        
    }

    public void createComercializadorMenu(SmartCity city){

        Scanner sc = new Scanner(System.in);
        
        System.out.println("Insira o nome do comercializador:");
        String nome = sc.next();
        
        System.out.println("Insira o preço base (KW):");
        int preco = sc.nextInt();
        
        System.out.println("Insira o fator imposto:");
        int imposto = sc.nextInt();
        sc.close();
        
        city.createComercializadorEnergia(nome, preco, imposto);
        System.out.println("Comercializador "+ nome +" criado");
        
    }
    
    public void createMarcaMenu(SmartCity city){

        Scanner sc = new Scanner(System.in);
        
        System.out.println("Insira o nome da marca:");
        String nome = sc.next();
        
        System.out.println("Insira o consumo diário associado ao uso dos dispostivos:");
        double consumo = sc.nextInt();
        
        sc.close();
        
        city.createMarca(nome, consumo);
        System.out.println("Marca "+ nome +" criada. Consumo diário dos dispositivos: "+ consumo);
        
    }

    
    public void createSmartDevicePresetMenu(SmartCity city){

        city.addDevicePreset(createSmartDeviceMenu());
    }

    public void deleteSmartDevicePresetMenu(SmartCity city){ //falta adicionar opções de voltar atrás ou cancelar

        city.listSmartDevicesPresets(); //lista os presets existentes
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Insira o nome do preset que pretende eliminar:");
        String preset_name = sc.next();
        sc.close();

        city.deleteSmartDevicePreset(preset_name);

    }

    public void listSmartHousesMenu(SmartCity city){ //falta adicionar opções de voltar atrás ou cancelar

        city.listSmartHouses(); //lista das existentes
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Insira o id da casa para ver mais detalhes:");
        int id_casa = sc.nextInt();
        sc.close();

        city.getSmartHouseDetails(id_casa);

    }

    public void listSmartDevicesPresetsMenu(SmartCity city){ //falta adicionar opções de voltar atrás ou cancelar

        printPresetsList(city); //lista das existentes
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Insira o nome do SmartDevice para ver mais detalhes:");
        String nome = sc.next();
        sc.close();

        city.getSmartDevicePresetDetails(nome);

    } 
        
    public void simulationMenu(SmartCity city){
        //pedir data
        //calcula
        //mostra
    }

}
