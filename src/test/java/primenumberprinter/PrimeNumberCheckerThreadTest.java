package primenumberprinter;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigInteger;
import java.net.UnknownHostException;
import java.util.ArrayList;
import org.junit.Test;
import primenumberprinter.api.INumberGenerator;
import primenumberprinter.api.IPrimeNumberHandler;
import primenumberprinter.api.IRpns;

/**
 * Tests the prime number checker thread.
 */
public class PrimeNumberCheckerThreadTest {

    @Test
    public void test() {
        TestPrimeNumberHandler testPrimeNumberHandler = new TestPrimeNumberHandler();
        PrimeNumberCheckerThread thread = 
                new PrimeNumberCheckerThread(
                    new TrueFalseTrueRpns(), testPrimeNumberHandler,
                    new TestNumberGenerator());

        // Will test for 1
        thread.checkNextInteger();
        // Will test for 2
        thread.checkNextInteger();
        // Will test for 3
        thread.checkNextInteger();
        // Will test for 4
        thread.checkNextInteger();
        // Will test for 5
        thread.checkNextInteger();

        ArrayList<BigInteger> result = testPrimeNumberHandler.getResult();

        assertEquals("There should be 3 primes in the result.", 3, result.size());
        assertEquals("Only prime numbers should be persisted.", BigInteger.valueOf(1), result.get(0));
        assertEquals("Only prime numbers should be persisted.", BigInteger.valueOf(3), result.get(1));
        assertEquals("Only prime numbers should be persisted.", BigInteger.valueOf(5), result.get(2));

    }


    private static class TrueFalseTrueRpns implements IRpns {
        private int counter = 2;

        public boolean isPrime(BigInteger n) throws UnknownHostException, IOException {
            return (counter++) % 2 == 0;
        }
    }

    private static class TestPrimeNumberHandler implements IPrimeNumberHandler {

        ArrayList<BigInteger> arrayList = new ArrayList<BigInteger>();

        public void handle(BigInteger prime) throws IOException {
            arrayList.add(prime);
        }

        public ArrayList<BigInteger> getResult() {
            return arrayList;
        }

    }

    private static class TestNumberGenerator implements INumberGenerator {

        BigInteger n = BigInteger.ZERO;

        public BigInteger next() {
            n = n.add(BigInteger.ONE);
            return n;
        }

        public void setInitial(BigInteger initial) {}

    }

}
