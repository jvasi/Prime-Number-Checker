package primenumberprinter;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Singleton representing storage for prime numbers.
 *
 * Later on this storage can be implemented to persist the primes in a database for example.
 *
 * TODO: Shutdown persistent storage.
 */
public class PrimeNumberPersistentStorage {

    /**
     * Default logger.
     */
    private static final Logger sLogger = Logger.getLogger(PrimeNumberPersistentStorage.class.getName());

    /**
     * The file name of the storage file.
     */
    public static final String STORAGE_FILE = "primes.dat";

    /**
     * The storage file.
     */
    private static RandomAccessFile storage;

    static {

        try {
            // Initialize the storage file in write-through mode, so if the program crashes no data is lost.
            storage = new RandomAccessFile(STORAGE_FILE, "rwd");
        } catch (FileNotFoundException ex) {
            throw new RuntimeException("Could not initialize prime numbers storage.", ex);
        }

    }

    /**
     * Variable holding the last primer that was recovered.
     */
    private static BigInteger lastPrime = recoverLastPersistedPrime();

    /**
     * The single instance of this class.
     */
    private static final PrimeNumberPersistentStorage sInstance = new PrimeNumberPersistentStorage();

    /**
     * @return the single instance of this class.
     */
    public static PrimeNumberPersistentStorage getInstance() {
        return sInstance;
    }

    /**
     * Private constructor to prevent instantiation.
     */
    private PrimeNumberPersistentStorage() {
    }

    /**
     * Persists a given prime number to disk.
     * @param prime the prime to be persisted.
     * @throws IOException if I/O error occurs while persisting the prime.
     */
    public synchronized void persistPrime(BigInteger prime) throws IOException {
        byte[] asByteArray = prime.toByteArray();
        // Store the BigInteger as length of bytes + the bytes
        storage.writeInt(asByteArray.length);
        storage.write(asByteArray);
        lastPrime = prime;
    }

    /**
     * @return the last persisted prime.
     */
    public synchronized BigInteger getLastPersistedPrime() {
        return lastPrime;
    }

    /**
     * Recovers the last persisted prime.
     * @return the last persisted prime or 1 if the storage could not be read.
     */
    private static BigInteger recoverLastPersistedPrime() {
        BigInteger result = BigInteger.ONE;
        try {
            while (true) {
                result = readBigInteger();
            }
        } catch (EOFException e) {
            // Swallow, reached EOF.
        } catch (IOException e) {
            sLogger.log(Level.WARNING,
                    "Could not recover last persisted prime. Starting from " + result.toString(), e);
            resetStorage();
        }
        
        // In case we couldn't retrieve the prime, start over from the first prime.
        return result;
    }

    /**
     * Reads a BigInteger from the storage.
     * @return the read big integer.
     * @throws IOException if I/O error occurs.
     */
    private static BigInteger readBigInteger() throws EOFException, IOException {
        // Reads the length of the big integer.
        byte biLenBuf[] = new byte[4];

        int read_number_length = storage.read(biLenBuf); //storage.readInt();

        if (read_number_length <= 0) {
            throw new EOFException();
        }

        if (read_number_length < 4) {
            // Length was not fully written, back up few bytes to avoid data corruption.
            storage.seek(storage.getFilePointer() - read_number_length);
            throw new EOFException();
        }

        int biLength = new BigInteger(biLenBuf).intValue();

        byte biBuf[] = new byte[biLength];

        int read = storage.read(biBuf);

        if (read <= 0) {
            // Length was not fully written, back up few bytes to avoid data corruption.
            storage.seek(storage.getFilePointer() - read_number_length);
            throw new EOFException();
        }

        if (read != biLength) {
            // Integer was not fully written, back up few bytes to avoid data corruption.
            storage.seek(storage.getFilePointer() - read - read_number_length);
            throw new EOFException("Last integer was not fully persisted.");
        }

        return new BigInteger(biBuf);
    }

    /**
     * Truncates the storage.
     */
    public static void resetStorage() {
        try {
            storage.seek(0);
            storage.setLength(0);
        } catch (IOException ex) {
            sLogger.log(Level.SEVERE, "Could not trucate storage.", ex);
        }
    }
}
