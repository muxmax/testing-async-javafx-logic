package de.muximweb.javafx.testing_asynchonous_logic;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The starter class for the application.
 */
public class AppStarter extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {

        IntegerProperty progressProperty = new SimpleIntegerProperty(0);
        ProgressIndicator indicator = new ProgressIndicator();
        indicator.progressProperty().bind(progressProperty);

        StringProperty resultStringProperty = new SimpleStringProperty("...");
        Label resultLabel = new Label();
        resultLabel.textProperty().bind(Bindings.concat("Result of long lasting operation: ").concat
                (resultStringProperty));

        Button buttonStartOperation = new Button("Start long lasting operation");
        buttonStartOperation.setOnAction(e -> {
            resultStringProperty.set("...");
            new ClassToTest().execLongLastingOperation(resultStringProperty,
                    progressProperty);
        });

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(indicator, buttonStartOperation, resultLabel);

        primaryStage.setScene(new Scene(root, 400, 200));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
