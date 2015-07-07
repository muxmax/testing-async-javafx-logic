package de.muximweb.javafx.testing_asynchonous_logic;

import de.saxsys.javafx.test.JfxRunner;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;


/**
 * A test for {@link ClassToTest}.
 */
@RunWith(JfxRunner.class)
public class ClassToTestTest {

    private ClassToTest cut = new ClassToTest();

    @Test
    public void testLongLastingOperation() {
        StringProperty resultProperty = new SimpleStringProperty();
        IntegerProperty progressProperty = new SimpleIntegerProperty(1);

        cut.execLongLastingOperation(resultProperty, progressProperty);

        assertEquals("An expensive result", resultProperty.get());
        assertEquals(1, progressProperty.get());
    }
}
