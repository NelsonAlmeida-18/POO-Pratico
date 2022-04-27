
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
    // YYYY-MM-DD, casaX, dispostivoY, acao
    // YYYY-MM-DD, fornecedorX, alteraValorDesconto, novoValor
    // YYYY-MM-DD, casaX, fornecedorN -> sendo fornecedorN o novo comercializador de energia

    public void createLine(String line){
        String [] token = line.split(",\\s+"); // criar tokens
        String date = token[0];
        String [] data_token = date.split(".");

        int year = Integer.parseInt(data_token[0]);
        int mes = Integer.parseInt(data_token[1]);
        Month month = Month.of(mes);
        int day = Integer.parseInt(data_token[2]);
        LocalDate data = LocalDate.of(year,month,day);
        

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
