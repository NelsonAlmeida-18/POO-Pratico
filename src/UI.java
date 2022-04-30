import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ClientInfoStatus;
import java.util.Scanner;
import java.lang.InterruptedException;
import java.time.*;
import java.util.Map;

//import static com.intellij.openapi.util.text.Strings.toUpperCase;

public class UI {

<<<<<<< HEAD
    public UI(SmartCity city){
        menuInicial(city);
    }
=======
    private SmartCity city;
>>>>>>> f764abeb0a702d6214fb23f22eb2610b80242cd2

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
    
    public void menuInicial(SmartCity city) throws IOException {
        int res;
        Scanner sc = new Scanner(System.in);
        System.out.println("Selecione uma das opções abaixo:\n");
        System.out.println("1 - Criar uma cidade\n");
        System.out.println("2 - Carregar a partir de um ficheiro\n");
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
        }
    }

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
                city.clone(createMenuLine(line)); //createMenuLine devolve a cidade guardada no ficheiro
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        /*
    // data , casa, dispositivo, ação
    // YYYY-MM-DD, casaX, acao (case 1.0)
    // YYYY-MM-DD, casaX, dispostivoY, acao (CASE 1.1)
    // YYYY-MM-DD, fornecedorX, alteraValorDesconto, novoValor (CASE 2)
    // YYYY-MM-DD, casaX, fornecedorN -> sendo fornecedorN o novo comercializador de energia (CASE 1.2)
        */
    public void createMenuLine(String line){
        String [] token = line.split(",\\s+"); // criar tokens
        String date = token[0];
        String [] data_token = date.split(".");

        // data
        int year = Integer.parseInt(data_token[0]);
        int mes = Integer.parseInt(data_token[1]);
        Month month = Month.of(mes);
        int day = Integer.parseInt(data_token[2]);
        LocalDate data = LocalDate.of(year,month,day);

        if(casas.constains(token[1])) {  // already created house related actions
           if(comercializadores.contains(token[2])){ // se for um comercializador, function trocar de comercializador
               // change comercializador
           } else { // é um device
                if(token[1].existsDevice(token[2])){ // already created device
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
        System.out.println("8 - Salvar para um ficheiro");
        System.out.println("9 - Continuar");
        res = sc.nextInt();
        sc.close();

        switch(res){
            case 1:
                createSmartHouseMenu(city);
            break;

            case 2:
                createComercializadorMenu(city);
            break;

            case 3:
                createMarcaMenu(city);
            break;

            case 4:
                createSmartDevicePresetMenu(city);
            break;

            case 5:
                deleteSmartDevicePresetMenu(city);
            break;

            case 6:
                listSmartHousesMenu(city);
            break;

            case 7:
                listSmartDevicesPresetsMenu(city);
            break;

            case 8:
                saveState(city); //função save, não tem menu, simplesmente guarda na pasta save
                System.out.println("Estado do programa guardado");
            break;

            case 9:
                simulationMenu(city);
            break;
        }
    }

<<<<<<< HEAD
    public void createSmartHouseMenu(SmartCity city){
=======
    private String morada;
    //private List<String> divisao;
    private ComercializadoresEnergia companhia_eletrica;
    //private Map<String, SmartDevice> devices; // identificador -> SmartDevice
    //private Map<String, List<String>> locations; // Espaço -> Lista codigo dos devices
    //private Map<String, Map<String, SmartDevice>> devices;
    private Map<String,Map<String, SmartDevice>> devices;
                //divisao, id,sd
    //Morada -> Map divisão -> devices) CONFIRMAR SE É ISTO


    public void createSmartHouseMenu(){
>>>>>>> f764abeb0a702d6214fb23f22eb2610b80242cd2
        // dar atributo hname
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Insira o nome do proprietário:");
        String nome_prop = sc.next();
        
        System.out.println("Insira o NIF do proprietário:");
        String nif = sc.next();
        
        System.out.println("Insira a morada:");
        String morada = sc.next();
        
        System.out.println("Insira o fornecedor:");
        System.out.println("Fornecedores disponiveis:");
        city.listComercializadores();
        String fornecedor = sc.next();

        //Nada disto faz sentido, temos de falar 
        if(city.listComercializadores().toString().contains(fornecedor)){

        }
        //if (hname!=null) SmartHouse hname = city.createHouse(nome_prop, nif, morada, fornecedor); else (o que está em baixo)
<<<<<<< HEAD
        SmartHouse house = createHouse(nome_prop, nif, morada, fornecedor); //não é o objeto mas sim o identificador acho
=======
        //SmartHouse house = city.createHouse(nome_prop, nif, morada, fornecedor); //não é o objeto mas sim o identificador acho
>>>>>>> f764abeb0a702d6214fb23f22eb2610b80242cd2
        
        String id = "";//adicionar forma de criar um id random ou por passagem
        SmartHouse house = new SmartHouse(id,nome_prop,nif, morada,fornecedor);

        System.out.println("Insira o número de divisões da casa:");
        int num_divisoes = sc.nextInt();

        for(int i = 0; i < num_divisoes; i++){
            System.out.println("Insira o nome da divisão "+ i +":");
                house.addDivisao(sc.next());
        }

        sc.close();

        System.out.println("Insira os dispositivos para cada divisão:");
        addDivisionDevicesMenu(house);

        city.addSmartHouse(house);
        
    }

    public void addDivisionDevicesMenu(SmartHouse house){
        
        Scanner sc = new Scanner(System.in);
        
        for(int i = 0; i < house.getTotalDivisions(); i++){
            System.out.println("Quantos dispositivos pretende adicionar em "+ house.getDivisaoByIndex(i) +"?");
            int num_disp = sc.nextInt();

            for(int j = 0; j < num_disp; num_disp++){
                addDeviceToDivisaoMenu(house, house.getDivisaoByIndex(i)); //adiciona um dispositivo a uma divisão
            }
        }

        sc.close();
    }

    public void addDeviceToDivisaoMenu(SmartHouse house, String division){
        //verifica se divisão existe em casa
        if(house.hasDivisao(division)){
            Scanner sc = new Scanner(System.in);
            
            System.out.println("Adicionar SmartDevice em "+ division);
            System.out.println();
            System.out.println("1 - Adicionar preset");
            System.out.println("2 - Criar SmartDevice");

            int res = sc.nextInt();
            sc.close();

            switch(res){
                case 1:
                    SmartDevice sd = addPresetMenu();
                break;
    
                case 2:
                    createSmartDeviceMenu();
                break;
            }
            
        }else{System.out.println("Erro: a divisão não existe");}

        
    }

    public SmartDevice addPresetMenu(SmartCity city){

        Scanner sc = new Scanner(System.in);
        
        //lista os presets

        System.out.println("Eis os presets existentes:");
        city.listSmartDevicesPresets();
        
        String selection = sc.next();
        sc.close();

        return sd(city.getPreset(selection)); //falta atribuir o id unico
        //sendo que na smartCity temos um map<nome,smartdevice> podemos clonar apartir daí
        
    }

    public SmartDevice createSmartDeviceMenu(SmartCity city){

        Scanner sc = new Scanner(System.in);
        
        System.out.println("Insira o tipo de SmartDevice:");
        System.out.println("1 - SmartSpeaker");
        System.out.println("2 - SmartCamera");
        System.out.println("3 - SmartBulb");
        System.out.println("4 - Cancelar");

        
        int res = sc.nextInt();
        sc.close();

        switch(res){
            case 1:
                SmartDevice sSpeaker = new SmartSpeaker(createSmartSpeakerMenu());
                return sSpeaker;
            break;

            case 2:
                SmartDevice sCamera = new SmartDevice(createSmartCameraMenu());
                return sCamera;
            break;

            case 3:
                SmartDevice sBulb = new SmartDevice(createSmartBulbMenu());
                return sBulb;
            break;

            case 4:
                System.out.println("Operação Cancelada");
                String s = "NULL";
                SmartDevice empty = new SmartDevice(s); //neste caso cria-se uma instância para retornar mas não entra na cidade porque o id diz NULL
                return empty;
            break;

            default:
                System.out.println("Opção inexistente");
                return createSmartDeviceMenu();
            break;
        }
        
   
    }

    public void createSmartSpeakerMenu(SmartCity city){

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

                city.createSmartSpeaker(volume, estacao, estado);

            break;
        }

        sc.close();
        System.out.println("SmartSpeaker adicionado com exito.");
        
    }

    public void createSmartCameraMenu(SmartCity city){

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

                city.createSmartCamera(resolucao, tamanho, estado);

            break;
        }

        sc.close();
        System.out.println("SmartCamera adicionada com exito.");
        
    }

    public void createSmartBulbMenu(SmartCity city){

        Scanner sc = new Scanner(System.in);
        System.out.println("Deseja inserir atributos no dispositivo?");
        System.out.println("0 - inicializa com as predefinições");
        System.out.println("1 - indicar as predefinições");

        int res = sc.nextInt();

        switch(res){
            case 0:
                city.createSmartBulb();
            break;

            case 1:
                System.out.println("Insira as dimensões da lâmpada:");
                int dimensions = sc.nextInt();
            
                System.out.println("Insira o modo em que se encontra a lâmpada (COLD, NEUTRAL, WARM):");
                String modo = sc.next());
            
                System.out.println("Insira o estado (ON/OFF):");
                String estado = sc.next();

                city.createSmartBulb(dimensions, modo, estado);

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
        
        ComercializadoresEnergia comercializador = city.createComercializadorEnergia(nome, preco, imposto);
        System.out.println("Comercializador "+ nome +" criado");
        
    }
    
    public void createMarcaMenu(SmartCity city){

        Scanner sc = new Scanner(System.in);
        
        System.out.println("Insira o nome da marca:");
        String nome = sc.next();
        
        System.out.println("Insira o consumo diário associado ao uso dos dispostivos:");
        double consumo = sc.nextInt();
        
        sc.close();
        
        Marca marca = city.createMarca(nome, consumo);
        System.out.println("Marca "+ nome +" criada. Consumo diário dos dispositivos: "+ consumo);
        
    }

    
    public void createSmartDevicePresetMenu(SmartCity city){

        city.createDevicePreset(createSmartDeviceMenu());
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

        city.listSmartDevicesPresets(); //lista das existentes
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Insira o nome do SmartDevice para ver mais detalhes:");
        String come = sc.next();
        sc.close();

        city.getSmartDevicePresetDetails(nome);

    }
    
    public void simulationMenu(SmartCity city){
        //pedir data
        //calcula
        //mostra
    }

}
