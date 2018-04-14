package layoutclasses;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class Styles {
    //Button Styles
    public static String BillButtonStyle(){return "-fx-background-color: #D43F3F; -fx-border-color: #2a0c0c";}
    public static String BillButtonPressed(){return "-fx-background-color: #2a0c0c; -fx-border-color: #2a0c0c";}
    public static String InvestmentButtonStyle(){return "-fx-background-color: #00ACE9; -fx-border-color: #003345";}
    public static String InvestmentButtonPressed(){return "-fx-background-color: #003345; -fx-border-color: #003345";}
    public static String UpdateValueButtonStyle(){return "-fx-border-color: #8c8c8c; -fx-background-color: #ebebeb"; }
    public static String UpdateValueButtonPressed(){return "-fx-border-color: #8c8c8c; -fx-background-color: #8c8c8c"; }
    public static String SettingsChanged(){return "-fx-border-color: #8c8c8c; -fx-background-color: #f6f6e8"; }
    //Label Styles
    public static void SectionTitle(Label label){
        label.setUnderline(true);
        label.setFont(new Font(18));
    }
}
