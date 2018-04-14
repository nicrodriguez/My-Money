import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class testFIle {
    public static void main(String[] args) throws URISyntaxException {
        List<String> info = readData("data/InvestmentInfo.txt");
        assert info != null;
        for(String data : info){
            System.out.println(data);
        }
        writeData("data/InvestmentInfo.txt", info);

    }

    private static List<String> readData(String filePath) throws URISyntaxException {

        try {
            List<String> info = new ArrayList<>();
            String str;
            BufferedReader in = new BufferedReader(new FileReader(new File(testFIle.class.getResource(filePath).toURI())));
            int i = 0;
            while ((str = in.readLine()) != null) {
                info.add(str);
            }
            in.close();
            return info;

        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;

        }


    }

    private static void writeData(String filePath, List<String> data) throws URISyntaxException {
        StringBuilder sb = new StringBuilder();
        for(String line : data){
            sb.append(line).append("\n");
        }

        System.out.println(sb.toString());
        try {

            String path = new FileReader(new File(testFIle.class.getResource(filePath).toURI())).toString();
            FileWriter writer = new FileWriter(path, false);
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }




}
