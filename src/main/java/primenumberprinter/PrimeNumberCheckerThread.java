package primenumberprinter;

import primenumberprinter.api.IPrimeNumberHandler;
import primenumberprinter.api.IRpns;
import java.io.IOException;
import java.math.BigInteger;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import primenumberprinter.api.INumberGenerator;

/**
 * Class representing single thread that will check for prime numbers.
 */
public class PrimeNumberCheckerThread extends Thread {
    /**
     * Default logger.
     */
    private static final Logger sLogger = Logger.getLogger(PrimeNumberCheckerThread.class.getName());

    /**
     * Remote prime number service.
     */
    private final IRpns rpns;

    /**
     * Handler for the prime numbers (e.g. persisting, printing on the console).
     */
    private final IPrimeNumberHandler primeNumberHandler;

    /**
     * The number generator that this thread will use for generating prime number candidates.
     */
    private final INumberGenerator numberGenerator;

    /**
     * Creates instance of this thread.
     * @param rpns the remote prime number service that this thread will use.
     * @param primeNumberHandler the handler for prime numbers.
     * @param numberGenerator the number generator that this thread should use.
     */
    public PrimeNumberCheckerThread(IRpns rpns, IPrimeNumberHandler primeNumberHandler,
            INumberGenerator numberGenerator) {
        super();
        // Make non-daemon thread, so the JVM won't exit on us while working ...
        setDaemon(false);
        this.rpns = rpns;
        this.primeNumberHandler = primeNumberHandler;
        this.numberGenerator = numberGenerator;
    }

    @Override
    public void run() {
        while (!interrupted()) {
            checkNextInteger();
        }
    }

    /**
     * Gets and checks the next integer for primeness.
     */
    public void checkNextInteger() {
        // Generate  next number.
        BigInteger nextNumber = numberGenerator.next();
        try {
            // Check for prime.
            if (rpns.isPrime(nextNumber)) {
                // Persist and print.
                primeNumberHandler.handle(nextNumber);
            }
        } catch (UnknownHostException ex) {
            interrupt();
            sLogger.log(Level.SEVERE, "Remote host unknown, stopping thread " + getName(), ex);
        } catch (IOException ex) {
            // TODO: Make retry attempts for the same number instead of interrupting the thread.
            interrupt();
            sLogger.log(Level.SEVERE, "Error occurred while checking if number " + nextNumber + " is prime, stopping thread " + getName(), ex);
        }
    }

}
