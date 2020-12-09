package primenumberprinter.api;

/**
 * Interface for prime number handler factory.
 */
public interface IPrimeNumberHandlerFactory {

    /**
     * @return an instance of a prime number handler implementation.
     */
    IPrimeNumberHandler createInstance();

}
