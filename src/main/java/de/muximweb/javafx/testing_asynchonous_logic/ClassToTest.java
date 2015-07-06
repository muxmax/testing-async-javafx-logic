package de.muximweb.javafx.testing_asynchonous_logic;

import de.muximweb.javafx.testing_asynchonous_logic.execution.AsyncExecution;
import de.muximweb.javafx.testing_asynchonous_logic.service.SomeService;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

/**
 * A business class to be tested.
 */
public class ClassToTest {

    private final AsyncExecution<String> asyncExecution;

    public ClassToTest(AsyncExecution<String> asyncExecution) {
        this.asyncExecution = asyncExecution;
        asyncExecution.onStart(() -> new SomeService().longLastingOperation());
    }

    public void execLongLastingOperation(StringProperty resultStringProperty, IntegerProperty progressProperty) {

        progressProperty.setValue(-1);

        asyncExecution.onSucceeded(resultString -> {
            resultStringProperty.setValue(resultString);
            progressProperty.setValue(1);
        });
        asyncExecution.onFailed(e -> {
            resultStringProperty.setValue("An error occurred: no result");
            progressProperty.setValue(1);
        });

        asyncExecution.start();
    }

}