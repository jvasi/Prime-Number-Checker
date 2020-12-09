package primenumberprinter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.Before;
import primenumberprinter.rpns.streams.BigIntegerInputStream;
import static org.junit.Assert.assertEquals;

/**
 * Tests for the prime number persistent storage.
 */
public class PrimeNumberPersistentStorageTest {
    
    private PrimeNumberPersistentStorage primeNumberPersistentStorage;

    @Before
    public void setUpBeforeTest() {
        primeNumberPersistentStorage = PrimeNumberPersistentStorage.getInstance();
    }

    @After
    public void tearDownAfterTest() throws FileNotFoundException, IOException {
        PrimeNumberPersistentStorage.resetStorage();
    }

    @AfterClass
    public static void afterClass() {
        new File(PrimeNumberPersistentStorage.STORAGE_FILE).delete();
    }

    @Test
    public void testLastPersistedPrime() throws IOException {
        primeNumberPersistentStorage.persistPrime(BigInteger.ONE);

        assertEquals(BigInteger.ONE, primeNumberPersistentStorage.getLastPersistedPrime());
    }

    @Test
    public void testPersistingPrimes() throws IOException {
        primeNumberPersistentStorage.persistPrime(BigInteger.ZERO);
        primeNumberPersistentStorage.persistPrime(BigInteger.ONE);
        primeNumberPersistentStorage.persistPrime(BigInteger.TEN);

        BigIntegerInputStream in = new BigIntegerInputStream(
                new FileInputStream(PrimeNumberPersistentStorage.STORAGE_FILE));

        assertEquals("Integer 0 not persisted.", BigInteger.ZERO, in.readBigInteger());
        assertEquals("Integer 1 not persisted.", BigInteger.ONE, in.readBigInteger());
        assertEquals("Integer 10 not persisted.", BigInteger.TEN, in.readBigInteger());

        in.close();
    }

}
