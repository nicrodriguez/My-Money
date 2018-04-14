package classes;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    // File Handling
    public static List<String> readData(String filePath) throws URISyntaxException {

        try {
            List<String> info = new ArrayList<>();
            String str;
            BufferedReader in = new BufferedReader(new FileReader(new File(Utils.class.getResource(filePath).toURI())));
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
    public static void writeData(String filePath, List<String> data){
        StringBuilder sb = new StringBuilder();
        for(String line : data){
            sb.append(line).append("\n");
        }

        try {

            FileWriter writer = new FileWriter(filePath, false);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            bufferedWriter.write(sb.toString());

            bufferedWriter.close();

//            System.out.println("It is done");
        } catch (IOException e) {
            e.printStackTrace();
        }




    }

    //Layouts
    public  static TextField createEntryField(Label label, String text, int fontSize, String prompt){
        label.setText(text);
        label.setFont(new Font(fontSize));
        TextField Entry = new TextField();
        Entry.setFont(new Font(fontSize));
        Entry.setText(prompt);
        return Entry;
    }
}
