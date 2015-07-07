package de.muximweb.javafx.testing_asynchonous_logic;

import de.saxsys.javafx.test.JfxRunner;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;


/**
 * A test for {@link ClassToTest} using the solution pattern given by http://blog.buildpath.de/how-to-test-javafx-services/.
 */
@RunWith(JfxRunner.class)
public class ClassToTestTest {

    private ClassToTest cut = new ClassToTest();

    @Test
    public void testLongLastingOperationCallingOnce() throws ExecutionException, InterruptedException {
        StringProperty resultProperty = new SimpleStringProperty();
        IntegerProperty progressProperty = new SimpleIntegerProperty(0);

        CompletableFuture longLastingOperationFuture = newFuture(progressProperty);

        cut.execLongLastingOperation(resultProperty, progressProperty);
        longLastingOperationFuture.get();

        assertEquals("An expensive result", resultProperty.get());
        assertEquals(1, progressProperty.get());
    }

    @Test
    public void testLongLastingOperationCallingTwice() throws ExecutionException, InterruptedException {
        StringProperty resultProperty = new SimpleStringProperty();
        IntegerProperty progressProperty = new SimpleIntegerProperty(0);

        // first onStart succeeds
        CompletableFuture longLastingOperationFuture1 = newFuture(progressProperty);
        cut.execLongLastingOperation(resultProperty, progressProperty);
        longLastingOperationFuture1.get();

        assertEquals("An expensive result", resultProperty.get());
        assertEquals(1, progressProperty.get());

        // second onStart fails as because of {@link Service#checkThread}
        CompletableFuture longLastingOperationFuture2 = newFuture(progressProperty);
        cut.execLongLastingOperation(resultProperty, progressProperty);
        longLastingOperationFuture2.get();

        assertEquals("An expensive result", resultProperty.get());
        assertEquals(1, progressProperty.get());
    }

    private CompletableFuture newFuture(IntegerProperty progressProperty) {
        CompletableFuture completableFuture = new CompletableFuture();
        progressProperty.addListener((b, o, n) -> {
            if (n.intValue() == 1) {
                completableFuture.complete(null);
            }
        });
        return completableFuture;
    }
}
