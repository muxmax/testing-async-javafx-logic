package de.muximweb.javafx.testing_asynchonous_logic;

import de.muximweb.javafx.testing_asynchonous_logic.service.SomeService;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * A business class to be tested.
 */
public class ClassToTest {

    private Service<String> service = new Service<String>() {
        @Override
        protected Task<String> createTask() {
            return new Task<String>() {

                @Override
                protected String call() throws Exception {
                    return new SomeService().longLastingOperation();
                }
            };
        }
    };

    public void execLongLastingOperation(StringProperty resultStringProperty, IntegerProperty progressProperty) {

        progressProperty.setValue(-1);

        service.setOnSucceeded(e -> {
            resultStringProperty.setValue(service.getValue());
            progressProperty.setValue(1);
        });
        service.setOnFailed(e -> {
            resultStringProperty.setValue("An error occurred: no result");
            progressProperty.setValue(1);
        });

        service.restart();
    }
}