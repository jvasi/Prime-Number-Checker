package primenumberprinter.handlers;

import java.io.IOException;
import java.math.BigInteger;
import primenumberprinter.api.IPrimeNumberHandler;
import primenumberprinter.PrimeNumberPersistentStorage;

/**
 * Handler for persisting the prime numbers to disk.
 */
public class PersistPrimeHandler implements IPrimeNumberHandler {

    @Override
    public void handle(BigInteger prime) throws IOException {
        PrimeNumberPersistentStorage.getInstance().persistPrime(prime);
    }

}
