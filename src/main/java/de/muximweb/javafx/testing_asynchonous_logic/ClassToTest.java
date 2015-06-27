package de.muximweb.javafx.testing_asynchonous_logic;

import de.muximweb.javafx.testing_asynchonous_logic.service.SomeService;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

/**
 * A business class to be tested.
 */
public class ClassToTest {

    public void execLongLastingOperation(StringProperty resultStringProperty, IntegerProperty progressProperty) {
        try {
            resultStringProperty.setValue(//
                    new SomeService().longLastingOperation(//
                            () -> progressProperty.setValue(1)));
        } catch (InterruptedException e) {
            resultStringProperty.setValue("An error occurred during calculation");
        }
    }
}