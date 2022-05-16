package Model;

public String readFromFile(String filename) {
    try{
        File f = new File(filename);
        BufferedReader br = new BufferedReader(new FileReader(f));
        StringBuilder sb = new StringBuilder();
        String st;
        while((st=br.readLine())!=null){
            sb.append(st);
            sb.append("\n");
        }
        br.close();
        return sb.toString();
    }
    catch(Exception e){
        System.out.println("File name/path are not reachable!");
    }

    return "";
}