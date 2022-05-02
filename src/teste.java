import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;

public class teste {
    public static void main(String[] args) throws IOException {  
      Path path = Path.of("/Users/rkeat/Desktop/POO-Pratico/src/logs.txt");
      String content = Files.readString(path);
      System.out.println(content); // Prints file content
    }
}
