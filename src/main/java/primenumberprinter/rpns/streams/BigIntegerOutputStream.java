package primenumberprinter.rpns.streams;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;

/**
 * Decorator for output streams which supports writing big integers.
 */
public class BigIntegerOutputStream extends OutputStream {

    /**
     * The wrapped underlying output stream.
     */
    private final DataOutputStream mOut;

    /**
     * Creates intance of this class.
     * @param out the underlying output stream this instance will wrap.
     */
    public BigIntegerOutputStream(OutputStream out) {
        super();
        mOut = new DataOutputStream(out);
    }

    @Override
    public void write(int b) throws IOException {
        mOut.write(b);
    }

    /**
     * Writes a big integer to the output stream.
     * @param n the big integer.
     * @throws IOException if I/O error occurs.
     */
    public void writeBigInteger(BigInteger n) throws IOException {
        byte[] asByteArray = n.toByteArray();
        mOut.writeInt(asByteArray.length);
        mOut.write(asByteArray);
        mOut.flush();
    }

    @Override
    public void close() throws IOException {
        mOut.close();
        super.close();
    }

}
