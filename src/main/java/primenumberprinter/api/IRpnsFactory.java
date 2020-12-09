package primenumberprinter.api;

/**
 * Interface for factories for remote prime number services.
 */
public interface IRpnsFactory {

    /**
     * Returns an instance of a prime number service.
     * @param host the host of the prime number service.
     * @param port the port of the prime number service.
     * @return instance of a prime number service.
     */
    IRpns getInstance(String host, int port);

}
