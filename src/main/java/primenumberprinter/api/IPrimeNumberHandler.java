package primenumberprinter.api;

import java.io.IOException;
import java.math.BigInteger;

/**
 * Interface for handling prime numbers.
 */
public interface IPrimeNumberHandler {

    /**
     * Handles the prime number.
     * @param prime the prie number to handler.
     * @throws IOException if I/O error occurs.
     */
    void handle(BigInteger prime) throws IOException;

}
