package layoutclasses;

import classes.BillItem;
import classes.BillItems;
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
import layoutclasses.MainController;

import java.net.URISyntaxException;
import java.text.DecimalFormat;

public class NewBillPopup {

        public static void display()
        {
            Stage popupwindow=new Stage();

            popupwindow.initModality(Modality.APPLICATION_MODAL);
            popupwindow.setTitle("Add Bill");

            Label billNameLabel = new Label("Bill Name:");
            billNameLabel.setFont(new Font(16));
            TextField billNameField = new TextField();
            billNameField.setFont(new Font(16));
            billNameField.setPromptText("name");

            Label billPaymentLabel = new Label("Monthly Payment ($):");
            billPaymentLabel.setFont(new Font(16));
            TextField billPaymentField = new TextField();
            billPaymentField.setPromptText("payment");
            billPaymentField.setFont(new Font(16));

            Label totalBillAmountLabel = new Label("Amount Remaining:");
            totalBillAmountLabel.setFont(new Font(16));
            TextField totalBillField = new TextField();
            totalBillField.setFont(new Font(16));
            totalBillField.setPromptText("amount");

            Label interestRateLabel = new Label("Interest Rate:");
            interestRateLabel.setFont(new Font(16));
            TextField interestField = new TextField();
            interestField.setFont(new Font(16));
            interestField.setPromptText("interest");

            DecimalFormat df = new DecimalFormat("#.##");
            Button addButton = new Button("Add");
            addButton.setFont(new Font(16));
            addButton.setOnAction(event -> {
                try {
                    BillItems.getInstance().addBillItems(new BillItem(billNameField.getText(),
                            Double.valueOf(df.format(Double.parseDouble(billPaymentField.getText()))),
                            Double.valueOf(df.format(Double.parseDouble(totalBillField.getText()))),
                            Double.valueOf(df.format(Double.parseDouble(interestField.getText())))));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                popupwindow.close();
            });

            Button cancelButton = new Button("Cancel");
            cancelButton.setFont(new Font(16));
            cancelButton.setOnAction(e -> popupwindow.close());


            HBox buttonContainter = new HBox(0);
            VBox layout= new VBox(10);
            layout.paddingProperty().setValue(new Insets(10));

            buttonContainter.getChildren().addAll(addButton, cancelButton);
            HBox.setHgrow(addButton, Priority.ALWAYS);
            HBox.setHgrow(cancelButton, Priority.ALWAYS);
            addButton.setMaxWidth(Double.MAX_VALUE);
            cancelButton.setMaxWidth(Double.MAX_VALUE);

            layout.getChildren().addAll(billNameLabel, billNameField,
                    billPaymentLabel, billPaymentField,
                    totalBillAmountLabel, totalBillField,
                    interestRateLabel, interestField,
                    buttonContainter);

            layout.setAlignment(Pos.CENTER);

            Scene scene1= new Scene(layout, 300, 400);

            popupwindow.setScene(scene1);

            popupwindow.showAndWait();

        }


}

