package primenumberprinter.api;

import java.io.IOException;
import java.math.BigInteger;
import java.net.UnknownHostException;

/**
 * Interface for remote prime number service.
 */
public interface IRpns {

    /**
     * Checks if the given integer is prime/
     * @param n the integer to check for primeness.
     * @return <code>true</code> if the number is prime, <code>false</code> otherwise.
     * @throws UnknownHostException if the remote host could not be resolved.
     * @throws IOException if I/O error occurs.
     */
    boolean isPrime(BigInteger n) throws UnknownHostException, IOException;

}
