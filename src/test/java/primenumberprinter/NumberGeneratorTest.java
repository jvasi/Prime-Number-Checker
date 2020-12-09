package primenumberprinter;

import java.math.BigInteger;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the number generator.
 */
public class NumberGeneratorTest {
    
    NumberGenerator numberGenerator;

    @Before
    public void setUp() {
        numberGenerator = new NumberGenerator();
    }

    @Test
    public void testProbablePrimesAreReturned() {
        assertEquals("Unexpected probable prime was returned.", 2, numberGenerator.next().intValue());
        assertEquals("Unexpected probable prime was returned.", 3, numberGenerator.next().intValue());
        assertEquals("Unexpected probable prime was returned.", 5, numberGenerator.next().intValue());
    }

    @Test
    public void testSetInitial() {
        numberGenerator.setInitial(BigInteger.valueOf(5));
        assertEquals("Unexpected probable prime was returned.", 7, numberGenerator.next().intValue());
    }

}
