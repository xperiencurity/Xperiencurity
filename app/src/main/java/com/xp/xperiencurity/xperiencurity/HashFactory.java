package com.xp.xperiencurity.xperiencurity;

/**
 * <p>
 * A <i>Factory</i> to instantiate message digest algorithm instances.
 * </p>
 */
public class HashFactory {

    // Constants and variables
    // -------------------------------------------------------------------------

    // Constructor(s)
    // -------------------------------------------------------------------------

    /**
     * Trivial constructor to enforce <i>Singleton</i> pattern.
     */
    private HashFactory() {
        super();
    }

    // Class methods
    // -------------------------------------------------------------------------

    /**
     * <p>
     * Return an instance of a hash algorithm given its name.
     * </p>
     *
     * @param name the name of the hash algorithm.
     * @return an instance of the hash algorithm, or null if none found.
     * @throws InternalError if the implementation does not pass its self- test.
     */
    public static IMessageDigest getInstance(String name) {
        if (name == null) {
            return null;
        }

        name = name.trim();
        IMessageDigest result = null;
        if (name.equalsIgnoreCase("haval"))
            result = new Haval();
        else if (name.equalsIgnoreCase("md2"))
            result = new MD2();
        else if (name.equalsIgnoreCase("md4"))
            result = new MD4();
        else if (name.equalsIgnoreCase("md5"))
            result = new MD5();
        else if (name.equalsIgnoreCase("ripemd-128"))
            result = new RipeMD128();
        else if (name.equalsIgnoreCase("ripemd-160"))
            result = new RipeMD160();
        else if (name.equalsIgnoreCase("sha-1"))
            result = new Sha160();
        else if (name.equalsIgnoreCase("sha-256"))
            result = new Sha256();
        else if (name.equalsIgnoreCase("sha-384"))
            result = new Sha384();
        else if (name.equalsIgnoreCase("sha-512"))
            result = new Sha512();
        else if (name.equalsIgnoreCase("tiger"))
            result = new Tiger();
        else if (name.equalsIgnoreCase("whirlpool"))
            result = new Whirlpool();

        return result;
    }
}

