
import java.time.Month;
import java.util.Optional;
import java.util.Scanner;
import java.io.*;
import java.time.*;

public class Parser {

    public static void clearConsole() throws IOException {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else {
                System.out.print("\033\143");
            }
        } catch (IOException | InterruptedException ex) {}
    }

    public Optional<String> getExtension(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    public void menu() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Create/Edit Menu - 1");
        System.out.println("Load Menu - 2");
        String input = sc.next();
        sc.close();

        switch(input){

            case "1":
                create();
                break;

            case "2":
                load();
                break;

        }
        clearConsole();

    }

    // create
    public void create(){

    }


    // data , casa, dispositivo, ação
    // YYYY-MM-DD, casaX, acao (case 1.0)
    // YYYY-MM-DD, casaX, dispostivoY, acao (CASE 1.1)
    // YYYY-MM-DD, fornecedorX, alteraValorDesconto, novoValor (CASE 2)
    // YYYY-MM-DD, casaX, fornecedorN -> sendo fornecedorN o novo comercializador de energia (CASE 1.2)

    public void createLine(String line){
        String [] token = line.split(",\\s+"); // criar tokens
        String date = token[0];
        String [] data_token = date.split(".");

        // data
        int year = Integer.parseInt(data_token[0]);
        int mes = Integer.parseInt(data_token[1]);
        Month month = Month.of(mes);
        int day = Integer.parseInt(data_token[2]);
        LocalDate data = LocalDate.of(year,month,day);


        if(city.constainsHouse(token[2])) {  // CASE 1.0
            if(token[4]!=null) { // not case 1.0
                // do smth in the house
                if (house.constainsDevice(token[3])) { // CASE 1 . 1
                    // do smth to the device
                } else { // CASE 1 . 2
                    // change comercializador de energia
                }
            } else {
                // do action in the house (turn all lights, example)
            }
        }
        else if(city.constainsFornecedor(token[2])){ // CASE 2
            // altera valor de kW/h ou altera valor de Imposto
        }




    }

    public void createDevice(){

    }

    public void createMarca(){

    }

    public void createHouse(){

    }

    public void createDivisao(){

    }

    public void createComercializadores(){

    }

    public void editDevice(){

    }

    public void editMarca(){

    }

    public void editHouse(){

    }

    public void editDivisao(){

    }

    public void editComercializadores(){

    }

    // load
    public void load() throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nome do ficheiro de input (dentro da dir save): ");
        String fname = sc.next();
        sc.close();
        fname.concat(".");
        String ext = String.valueOf(getExtension(fname));
        fname.concat(ext);
        String dir = "../save/" + fname;
        BufferedReader reader;
        try{
            reader = new BufferedReader(new FileReader(dir));
            String line = reader.readLine();
            while(line!=null){
                createLine(line);
                line = reader.readLine();
            }
            reader.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

}
