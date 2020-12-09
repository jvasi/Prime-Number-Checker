package primenumberprinter.api;

import java.math.BigInteger;

/**
 * Number generator interface.
 */
public interface INumberGenerator {

    /**
     * @return the next candidate for prime number.
     */
    BigInteger next();

    /**
     * Sets the initial number for sequence of prime numbers.
     * @param initial the number to set.
     */
    void setInitial(BigInteger initial);

}
