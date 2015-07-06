package de.muximweb.javafx.testing_asynchonous_logic.execution;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A mockable and testable wrapper for the execution of some operation.
 */
public class AsyncExecution<T> {

    private Service<T> service;

    /**
     * A Supplier that is called to calculate a value on started the execution.
     *
     * @param supplier A supplier calculating a value.
     * @return This {@link AsyncExecution} instance.
     */
    public AsyncExecution onStart(ErrorProneSupplier<T> supplier) {
        if (service != null) {
            throw new RuntimeException("This execution was already initialized with a supplier that calculates a " +
                    "value. Create a new instance to set new supplier.");
        }
        service = new Service<T>() {
            @Override
            protected Task<T> createTask() {
                return new Task<T>() {

                    @Override
                    protected T call() throws Exception {
                        return supplier.get();
                    }
                };
            }
        };
        return this;
    }

    /**
     * A {@link Consumer} that is called in the success case of calculation.
     *
     * @param resultConsumer A {@link Consumer} that is provided with a calculated value to be processed afterwards.
     * @return This {@link AsyncExecution} instance.
     */
    public AsyncExecution onSucceeded(Consumer<T> resultConsumer) {
        if (service.getOnSucceeded() != null) {
            throw new RuntimeException("This execution was already initialized with a consumer that does something " +
                    "with the result. Create a new instance to set new supplier.");
        }
        service.setOnSucceeded(e -> resultConsumer.accept(service.getValue()));
        return this;
    }

    /**
     * A {@link Consumer} that is called in the failure case of calculation.
     *
     * @param failureConsumer A {@link Consumer} that is provided with a {@link Throwable} when an error occurs during
     *                        execution.
     * @return This {@link AsyncExecution} instance.
     */
    public AsyncExecution onFailed(Consumer<Throwable> failureConsumer) {
        if (service.getOnFailed() != null) {
            throw new RuntimeException("This execution was already initialized with a consumer that does something " +
                    "with the error occurred during execution. Create a new instance to set new supplier.");
        }
        service.setOnFailed(e -> failureConsumer.accept(service.getException()));
        return this;
    }

    /**
     * Starts the execution with the set {@link Supplier} asynchronously using a {@link Service}.
     */
    public void start() {
        if (service == null) {
            throw new RuntimeException("First you should use 'onStart' to provide a Supplier that calculates a result " +
                    "value.");
        }
        service.restart();
    }

    /**
     * An adaption of {@link Supplier} throwing exceptions when errors occur while supplying the value.
     *
     * @param <T> The type of the value to be supplied.
     */
    public interface ErrorProneSupplier<T> {
        /**
         * @throws Exception Any error that occurs while calculating the value to be supplied.
         * @see Supplier#get()
         */
        T get() throws Exception;
    }
}
