package primenumberprinter;

import primenumberprinter.api.INumberGenerator;
import java.math.BigInteger;

/**
 * Concurrent number generator used to generate numbers
 * that will be checked for primeness.
 *
 * This number generator implementation might be a bottleneck for the program because of the synchronized next() method.
 * A better implementation would be to have a separate thread to generate the next N prime candidates and push them
 * in an ArrayBlockingQueue with a capacity of N.
 */
public class NumberGenerator implements INumberGenerator {

    /**
     * Atomic long instance starting with the first prime number.
     */
    private BigInteger number = BigInteger.ONE;

    /**
     * @return the next candidate for prime number.
     */
    public synchronized BigInteger next() {
        number = number.nextProbablePrime();
        return number;
    }

    /**
     * Sets the initial number for sequence of prime numbers.
     * @param initial the number to set.
     */
    public synchronized void setInitial(BigInteger initial) {
        number = initial;
    }
}

