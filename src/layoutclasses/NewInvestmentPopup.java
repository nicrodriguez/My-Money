package layoutclasses;

import classes.InvestmentItem;
import classes.InvestmentItems;
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
import javafx.stage.Stage;

import java.net.URISyntaxException;
import java.text.DecimalFormat;

public class NewInvestmentPopup {
    public static void display(){
        Stage popupwindow = new Stage();
        popupwindow.setTitle("Add Investment");

        Label investmentNameLabel = new Label("Investment Name:");
        investmentNameLabel.setFont(new Font(16));
        TextField investmentNameField = new TextField();
        investmentNameField.setFont(new Font(16));
        investmentNameField.setPromptText("name");

        Label investmentAllocationLabel = new Label("Monthly Allocation:");
        investmentAllocationLabel.setFont(new Font(16));
        TextField investmentAllocationField = new TextField();
        investmentAllocationField.setFont(new Font(16));
        investmentAllocationField.setPromptText("allocation");

        Label totalInvestedLabel = new Label("Total Invested:");
        totalInvestedLabel.setFont(new Font(16));
        TextField totalInvestedField = new TextField();
        totalInvestedField.setFont(new Font(16));
        totalInvestedField.setPromptText("total");

        Label totalNetInvestedLabel = new Label("Total Net Investment Worth:");
        totalNetInvestedLabel.setFont(new Font(16));
        TextField totalNetInvestedField = new TextField();
        totalNetInvestedField.setFont(new Font(16));
        totalNetInvestedField.setPromptText("total net");


        DecimalFormat df = new DecimalFormat("#.##");
        Button addButton = new Button("Add");
        addButton.setFont(new Font(16));
        addButton.setOnAction(event -> {
//            BillItems.getInstance().addBillItems(new BillItem(investmentNameField.getText(), Double.valueOf(df.format(Double.parseDouble(invPaymentField.getText())))));
            InvestmentItems.getInstance().addItem(new InvestmentItem(investmentNameField.getText(),
                    Double.valueOf(df.format(Double.parseDouble(investmentAllocationField.getText()))),
                    Double.valueOf(df.format(Double.parseDouble(totalInvestedField.getText()))),
                    Double.valueOf(df.format(Double.parseDouble(totalNetInvestedField.getText())))));

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

        layout.getChildren().addAll(investmentNameLabel, investmentNameField,
                investmentAllocationLabel, investmentAllocationField,
                totalInvestedLabel, totalInvestedField,
                totalNetInvestedLabel, totalNetInvestedField,
                buttonContainter);

        layout.setAlignment(Pos.CENTER);

        Scene scene1= new Scene(layout, 300, 400);

        popupwindow.setScene(scene1);

        popupwindow.showAndWait();

    }
}
