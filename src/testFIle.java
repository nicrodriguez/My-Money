import classes.TimeSpan;
import classes.Utils;

import java.awt.event.KeyEvent;
import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class testFIle {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        TimeSpan timeSpan = new TimeSpan(21, 4, 2018);
        List<String> months = timeSpan.getNext12Months();
        for(String month : months){
            System.out.println(month);
        }

    }

    private static void hack() throws IOException, URISyntaxException, InterruptedException {
        System.out.println("Do you want to hack the FBI? (Y/N)");
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
        String inputLine = bufferReader.readLine();
        switch (inputLine) {
            case "Y": case "y":
                List<String> hackingData = Utils.readData("/data/hacked.rtf");
                assert hackingData != null;
                for(String line : hackingData){
                    System.out.println(line);
                    Thread.sleep(25);
                }

                System.out.println("FBI = HACKED!!!! ");
                Thread.sleep(2000);
                System.out.println("Incoming Message From FBI...");
                Thread.sleep(2000);
                System.out.println("DON'T FUCKING MOVE!!");
                break;
            case "N": case "n":
                System.out.println("FBI != HACKED");
                break;
            default:
                System.out.println("I said Y or N you dunce");
                break;
        }
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
