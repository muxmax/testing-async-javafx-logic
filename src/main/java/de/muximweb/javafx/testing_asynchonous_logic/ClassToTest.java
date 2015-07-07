package de.muximweb.javafx.testing_asynchonous_logic;

import de.muximweb.javafx.testing_asynchonous_logic.service.SomeService;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

import java.util.concurrent.CompletableFuture;

/**
 * A business class to be tested.
 */
public class ClassToTest {

    public void execLongLastingOperation(StringProperty resultStringProperty, IntegerProperty progressProperty) {

        progressProperty.setValue(-1);

        CompletableFuture.supplyAsync(() -> {
            try {
                return new SomeService().longLastingOperation();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).whenComplete((resultString, exception) -> Platform.runLater(() -> {
            if (exception != null) {
                resultStringProperty.setValue("An error occurred: no result");
            } else {
                resultStringProperty.setValue(resultString);
            }
            progressProperty.setValue(1);
        }));
    }

}