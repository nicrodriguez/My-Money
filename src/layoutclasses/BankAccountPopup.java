package layoutclasses;

import classes.BankItem;
import classes.BankItems;
import classes.Utils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class BankAccountPopup {
    public static void display(BankItem checkingItem, BankItem savingsItem){
        Stage changeInv = new Stage();

        changeInv.initModality(Modality.APPLICATION_MODAL);
        changeInv.setTitle("Update Checking Account Value");

        Label checkingLabel = new Label();
        TextField cBalanceEntry = Utils.createEntryField(checkingLabel, "Checking Account", 16, checkingItem.getBalance().toString());

        Label savingsLabel = new Label();
        TextField sBalanceEntry = Utils.createEntryField(savingsLabel, "Savings Account", 16, savingsItem.getBalance().toString());


        Button changeButton = new Button("Update");
        changeButton.setFont(new Font(16));
        changeButton.setOnAction(event -> {
            BankItems.getInstance().changeItem(0, "Checking Account", Double.parseDouble(cBalanceEntry.getText()));
            BankItems.getInstance().changeItem(1, "Savings Account", Double.parseDouble(sBalanceEntry.getText()));
            changeInv.close();
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setFont(new Font(16));
        cancelButton.setOnAction(e ->changeInv.close());

        HBox buttonContainter = new HBox(0);
        VBox layout= new VBox(10);
        layout.paddingProperty().setValue(new Insets(10));

        buttonContainter.getChildren().addAll(changeButton, cancelButton);
        HBox.setHgrow(changeButton, Priority.ALWAYS);
        HBox.setHgrow(cancelButton, Priority.ALWAYS);
        changeButton.setMaxWidth(Double.MAX_VALUE);
        cancelButton.setMaxWidth(Double.MAX_VALUE);

        layout.getChildren().addAll(checkingLabel, cBalanceEntry,
                savingsLabel, sBalanceEntry,
                buttonContainter);

        layout.setAlignment(Pos.CENTER);
        Scene scene1= new Scene(layout, 300, 200);
        changeInv.setScene(scene1);
        changeInv.showAndWait();
    }
}
