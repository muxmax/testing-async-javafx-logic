package de.muximweb.javafx.testing_asynchonous_logic.service;

/**
 * A Service that provides an operation which can last some time for calculating.
 */
public class SomeService {

    /**
     * A very long lasting operation blocking the current thread. So this operation should be executed asynchronously.
     *
     * @param onFinished The callback to be called when the execution is finished.
     * @return A String indicating that the operation has finished.
     * @throws InterruptedException Is thrown when an error occurs during execution.
     */
    public String longLastingOperation(Runnable onFinished) throws InterruptedException {
        Thread.sleep(5000);
        onFinished.run();
        return "An expensive result";
    }
}
