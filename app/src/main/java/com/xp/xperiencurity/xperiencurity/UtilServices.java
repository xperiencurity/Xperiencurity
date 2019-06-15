package com.xp.xperiencurity.xperiencurity;

/**
 * <p>
 * A collection of utility methods used throughout this project.
 * </p>
 */
public class UtilServices {

    // Constants and variables
    // -------------------------------------------------------------------------

    // Hex charset
    private static final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();

    // Constructor(s)
    // -------------------------------------------------------------------------

    /**
     * Trivial constructor to enforce Singleton pattern.
     */
    private UtilServices() {
        super();
    }

    // Class methods
    // -------------------------------------------------------------------------

    /**
     * <p>
     * Returns a string of hexadecimal digits from a byte array. Each byte is
     * converted to 2 hex symbols; zero(es) included.
     * </p>
     *
     * <p>
     * This method calls the method with same name and three arguments as:
     * </p>
     *
     * <pre>
     * toString(ba, 0, ba.length);
     * </pre>
     *
     * @param ba the byte array to convert.
     * @return a string of hexadecimal characters (two for each byte)
     * representing the designated input byte array.
     */
    public static String toString(byte[] ba) {
        return toString(ba, 0, ba.length);
    }

    /**
     * <p>
     * Returns a string of hexadecimal digits from a byte array, starting at
     * <code>offset</code> and consisting of <code>length</code> bytes. Each
     * byte is converted to 2 hex symbols; zero(es) included.
     * </p>
     *
     * @param ba     the byte array to convert.
     * @param offset the index from which to start considering the bytes to
     *               convert.
     * @param length the count of bytes, starting from the designated offset to
     *               convert.
     * @return a string of hexadecimal characters (two for each byte)
     * representing the designated input byte sub-array.
     */
    public static final String toString(byte[] ba, int offset, int length) {
        char[] buf = new char[length * 2];
        for (int i = 0, j = 0, k; i < length; ) {
            k = ba[offset + i++];
            buf[j++] = HEX_DIGITS[(k >>> 4) & 0x0F];
            buf[j++] = HEX_DIGITS[k & 0x0F];
        }
        return new String(buf);
    }
}
