package layoutclasses;

import classes.BillItem;
import classes.BillItems;
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

import java.net.URISyntaxException;
import java.text.DecimalFormat;

public class ChangeBillPopup {
    public static void display(int index, BillItem billItem){
        Stage changeBill = new Stage();

        changeBill.initModality(Modality.APPLICATION_MODAL);
        changeBill.setTitle("Change Bill");

        Label billName = new Label();
        TextField billNameEntry = Utils.createEntryField(billName, "Bills", 16, billItem.getName());

        Label billPaymentLabel = new Label();
        TextField billPaymentField = Utils.createEntryField(billPaymentLabel, "Monthly Payment ($)", 16, billItem.getAmountPerMonth().toString());

        Label totalBillAmountLabel = new Label("Amount Remaining:");
        totalBillAmountLabel.setFont(new Font(16));
        TextField totalBillField = new TextField();
        totalBillField.setFont(new Font(16));
        if(billItem.getAmountRemaining() != null)
            totalBillField.setText(billItem.getAmountRemaining().toString());
        else
            totalBillField.setText("NaN");

        Label interestRateLabel = new Label("Interest Rate:");
        interestRateLabel.setFont(new Font(16));
        TextField interestField = new TextField();
        interestField.setFont(new Font(16));
        if(billItem.getInterest() != null)
            interestField.setText(billItem.getInterest().toString());
        else
            interestField.setText("NaN");

        DecimalFormat df = new DecimalFormat("#.##");
        Button changeButton = new Button("Update");
        changeButton.setFont(new Font(16));
        changeButton.setOnAction(event -> {
//            billItem.setName(billNameEntry.getText());
//            billItem.setAmountPerMonth(Double.valueOf(df.format(Double.parseDouble(billPaymentField.getText()))));
//            billItem.setAmountRemaining(Double.valueOf(df.format(Double.parseDouble(totalBillField.getText()))));
//            billItem.setInterest(Double.valueOf(df.format(Double.parseDouble(interestField.getText()))));
            try {
                BillItems.getInstance().changeBillItems(index, billNameEntry.getText(),
                        Double.valueOf(df.format(Double.parseDouble(billPaymentField.getText()))),
                        Double.valueOf(df.format(Double.parseDouble(totalBillField.getText()))),
                        Double.valueOf(df.format(Double.parseDouble(interestField.getText()))));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            changeBill.close();
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setFont(new Font(16));
        cancelButton.setOnAction(e ->changeBill.close());


        HBox buttonContainter = new HBox(0);
        VBox layout= new VBox(10);
        layout.paddingProperty().setValue(new Insets(10));

        buttonContainter.getChildren().addAll(changeButton, cancelButton);
        HBox.setHgrow(changeButton, Priority.ALWAYS);
        HBox.setHgrow(cancelButton, Priority.ALWAYS);
        changeButton.setMaxWidth(Double.MAX_VALUE);
        cancelButton.setMaxWidth(Double.MAX_VALUE);

        layout.getChildren().addAll(billName, billNameEntry,
                billPaymentLabel, billPaymentField,
                totalBillAmountLabel, totalBillField,
                interestRateLabel, interestField,
                buttonContainter);

        layout.setAlignment(Pos.CENTER);

        Scene scene1= new Scene(layout, 300, 400);

        changeBill.setScene(scene1);

        changeBill.showAndWait();

    }

}
