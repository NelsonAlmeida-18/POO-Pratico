import java.io.*;
import java.util.Scanner;
import java.lang.InterruptedException;
import java.lang.ProcessBuilder;

//import static com.intellij.openapi.util.text.Strings.toUpperCase;
//mudar o ui para um to string e passamos ao parser para construir tudo de uma vez, just an idea


public class UI {
    Scanner sc;

    public UI(SmartCity city, Scanner scanner){
        this.sc = scanner;
        try {
            clearConsole();
            menuInicial(city, sc);
        }catch(Exception e){
            System.out.print(e.toString());
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
    }

    public void printComercializadoresList(SmartCity city){

        System.out.println("Comercializadores de energia disponíveis:");
        for (ComercializadoresEnergia comercializadoresEnergia : city.getComercializadores()) {
            comercializadoresEnergia.toString();
        }
        System.out.println("Comercializadores de energia disponíveis");
        for (ComercializadoresEnergia comercializadoresEnergia : city.getComercializadores()) {
            System.out.print("\t");
            System.out.print(comercializadoresEnergia.toString());

        }
    }
    
    public void menuInicial(SmartCity city, Scanner sc) throws IOException {
        int res;
        System.out.print("\n");
        System.out.println("Selecione uma das opções abaixo:\n");
        System.out.println("1 - Criar uma cidade");
        System.out.println("2 - Carregar a partir de um estado de programa");
        System.out.println("3 - Carregar a partir de um ficheiro log");
        System.out.println("4 - Sair");
        res = sc.nextInt();
        sc.nextLine();
        switch (res) {
            case 1 -> {
                clearConsole();
                createMenu(city, sc);
            }
            case 2 -> {
                clearConsole();
                loadMenu(city, sc);
            }
            case 3 -> {
                clearConsole();
                Parser p = new Parser();
                city = p.parse(city.getHouseId(), city.getDeviceId());
                menuInicial(city, sc);
            }
            case 4 -> {
                clearConsole();
                System.out.println("Tem a certeza que quer sair?");
                if (response(sc) == 1) {
                    System.out.println("That's all, folks!.");
                } else {
                    createMenu(city, sc);
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
        String path = sc.nextLine();
        System.out.println("Insira o nome do ficheiro com a extensão (e.g. ficheiro.txt):");
        String file = sc.nextLine();
        path.concat(file);
        city = (SmartCity) ReadObjectFromFile(path); //createMenuLine devolve a cidade guardada no ficheiro
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
        sc.nextLine();

        switch(res){
            case 1:
                createSmartHouseMenu(city, sc);
                createMenu(city, sc);
            break;

            case 2:
                createComercializadorMenu(city, sc);
                createMenu(city, sc);
            break;

            case 3:
                createMarcaMenu(city, sc);
                createMenu(city, sc);
            break;

            case 4:
                createSmartDevicePresetMenu(city, sc);
                createMenu(city, sc);
            break;

            case 5:
                //deleteSmartDevicePresetMenu(city);
                createMenu(city, sc);
            break;

            case 6:
                city.listSmartHouses();
                //listSmartHousesMenu(city);
                createMenu(city, sc);
            break;

            case 7:
                city.listSmartDevicesPresets();
                //listSmartDevicesPresetsMenu(city);
                createMenu(city, sc);
            break;

            case 8:
                Parser p = new Parser();
                city.merge(p.parse(city.getHouseId(), city.getDeviceId())); //carregar faz gestão de conflitos para dar merge à cidade já existente e à cidade que se está a carregar do log
            break;

            case 9:
                saveState(city, sc); //função save, não tem menu, simplesmente guarda na pasta save
                System.out.println("Estado do programa guardado");
                createMenu(city, sc);
            break;

            case 10:
                //simulationMenu(city);
                try{menuInicial(city, sc);}
                catch(Exception e){System.out.println("Failed to load menuInicial");}
            break;
        }
    }

    public void createSmartHouseMenu(SmartCity city, Scanner sc){
        
        int house_id = city.giveHouseId();

        System.out.println("Insira o nome do proprietário:");
        String nome_prop = sc.nextLine();

        System.out.println("Insira o NIF do proprietário:");
        int nif = sc.nextInt();
        sc.nextLine();

        System.out.println("Insira a morada:");
        String morada = sc.nextLine();

        System.out.println("Insira o fornecedor:");
        printComercializadoresList(city);
        String fornecedor = sc.nextLine();

        city.createHouse(nome_prop, nif, morada, fornecedor); //não é o objeto mas sim o identificador acho

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
        printComercializadoresList(city);
        
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
        double consumo = sc.nextInt();
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

    } 
        
    public void simulationMenu(SmartCity city){
        //pedir data
        //calcula
        //mostra
    }
 */
}
