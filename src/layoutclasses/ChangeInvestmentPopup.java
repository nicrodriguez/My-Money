package layoutclasses;

import classes.InvestmentItem;
import classes.InvestmentItems;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.text.DecimalFormat;

class ChangeInvestmentPopup {

//    private static TextField createEntryField(Label label, String text, int fontSize, String prompt){
//        label.setText(text);
//        label.setFont(new Font(fontSize));
//        TextField Entry = new TextField();
//        Entry.setFont(new Font(fontSize));
//        Entry.setText(prompt);
//        return Entry;
//    }

    static void display(int index, InvestmentItem investmentItem){
        Stage changeInv = new Stage();

        changeInv.initModality(Modality.APPLICATION_MODAL);
        changeInv.setTitle("Change Bill");

        Label investmentName = new Label();
        TextField investmentNameEntry = Utils.createChangeEntryField(investmentName, "InvestmentAcount", 16, investmentItem.getName());

        Label monthlyAllocationLabel = new Label();
        TextField allocationField = Utils.createChangeEntryField(monthlyAllocationLabel, "Monthly Allocation ($):", 16, investmentItem.getAmountPerMonth().toString());

        Label investmentSumLabel = new Label();
        TextField totalInvField = Utils.createChangeEntryField(investmentSumLabel, "Total Invested", 16, investmentItem.getTotalAmount().toString());

        Label netSumLabel = new Label();
        TextField netSumField = Utils.createChangeEntryField(netSumLabel, "Net Sum: ", 16, investmentItem.getNetSum().toString());


        DecimalFormat df = new DecimalFormat("#.##");
        Button changeButton = new Button("Update");
        changeButton.setFont(new Font(16));
        changeButton.setOnAction(event -> {
            InvestmentItems.getInstance().changeItem(index, investmentNameEntry.getText(),
                    Double.valueOf(df.format(Double.parseDouble(allocationField.getText()))),
                    Double.valueOf(df.format(Double.parseDouble(totalInvField.getText()))),
                    Double.valueOf(df.format(Double.parseDouble(netSumField.getText()))));
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

        layout.getChildren().addAll(investmentName, investmentNameEntry,
                monthlyAllocationLabel, allocationField,
                investmentSumLabel, totalInvField,
                netSumLabel, netSumField,
                buttonContainter);

        layout.setAlignment(Pos.CENTER);
        Scene scene1= new Scene(layout, 300, 400);
        changeInv.setScene(scene1);
        changeInv.showAndWait();
    }
}
