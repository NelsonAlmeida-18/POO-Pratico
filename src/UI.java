import java.util.Scanner;
import java.lang.InterruptedException;

public class UI {

    public static void clearConsole() {
       // try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else {
                System.out.print("\033\143");
            }
        //} catch (IOException | InterruptedException ex) {}
    }
    
    public void menuInicial(){
        int res;
        Scanner sc = new Scanner(System.in);
        System.out.println("Selecione uma das opções abaixo:\n");
        System.out.println("1 - Criar uma cidade\n");
        System.out.println("2 - Carregar a partir de um ficheiro\n");
        res = sc.nextInt();

        switch(res){
            case 1:
            clearConsole();
            createMenu();
            break;

            case 2:
                loadMenu();
            break;
        }
    }

    public void loadMenu(){
        String path;
        Scanner sc = new Scanner(System.in);
        System.out.println("Insira o path para o ficheiro que pretende carregar:");
        path = sc.next();
        sc.close();
        parser(path); //no ficheiro parser
    }

    public void createMenu(){
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
                createSmartHouseMenu();
            break;

            case 2:
                createComercializadorMenu();
            break;

            case 3:
                createMarcaMenu();
            break;

            case 4:
                createSmartDevicePresetMenu();
            break;

            case 5:
                deleteSmartDevicePresetMenu();
            break;

            case 6:
                listSmartHousesMenu();
            break;

            case 7:
                listSmartDevicesPresetsMenu();
            break;

            case 8:
                saveState();
            break;

            case 9:
                simulationMenu();
            break;
        }
    }

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

        SmartHouse house = city.createHouse(nome_prop, nif, morada, fornecedor); //não é o objeto mas sim o identificador acho
        
        System.out.println("Insira o número de divisões da casa:");
        int num_divisoes = sc.nextInt();

        for(int i = 0; i < num_divisoes; i++){
            System.out.println("Insira o nome da divisão "+ i +":");
                house.addDivisao(sc.next());
        }
        
        sc.close();

        System.out.println("Insira os dispositivos para cada divisão:");
        addDivisionDevicesMenu(house);
        
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
        if(house.hasDivisao){
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

    public SmartDevice addPresetMenu(){

        Scanner sc = new Scanner(System.in);
        
        //lista os presets

        System.out.println("Eis os presets existentes:");
        city.listSmartDevicesPresets();
        
        String selection = sc.next();
        sc.close();

        return sd(city.getPreset(selection)); //falta atribuir o id unico
        //sendo que na smartCity temos um map<nome,smartdevice> podemos clonar apartir daí
        
    }

    public void createSmartDeviceMenu(){

        Scanner sc = new Scanner(System.in);
        
        System.out.println("Insira o tipo de SmartDevice:");
        System.out.println("1 - SmartSpeaker");
        System.out.println("2 - SmartCamera");
        System.out.println("3 - SmartBulb");
        
        int res = sc.next();

        switch(res){
            case 1:
                createSmartSpeakerMenu();
            break;

            case 2:
                createSmartCameraMenu();
            break;

            case 3:
                createSmartBulbMenu();
            break;
        }
        
        sc.close();
        
    }

    public void createSmartSpeakerMenu(){

        Scanner sc = new Scanner(System.in);
        System.out.println("Deseja inserir atributos no dispositivo?");
        System.out.println("0 - inicializa com as predefinições");
        System.out.println("1 - indicar as predefinições");

        int res = sc.nextInt();

        switch(res){
            case 0:
                city.createSmartSpeaker();
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

    public void createSmartCameraMenu(){

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

    public void createSmartBulbMenu(){

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
}
