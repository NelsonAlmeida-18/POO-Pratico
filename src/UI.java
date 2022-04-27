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
        Load(path); //no ficheiro parser
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

    public void createSmartHouseMenu(){
        String path;
        Scanner sc = new Scanner(System.in);
        System.out.println("Insira o path para o ficheiro que pretende carregar:");
        path = sc.next();
        sc.close();
        Load(path); //no ficheiro parser
    }
}
