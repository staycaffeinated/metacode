<#include "/common/Copyright.ftl">
package ${project.basePackage}.math;

import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * This class produces secure random values with a 160-bit entropy, which improves upon
 * the 122-bit entropy of UUIDs.
 *
 * The strength of the secure random generator can be configured in the java.security file.
 * See https://metebalci.com/blog/everything-about-javas-securerandom/
 *
 */
public class SecureRandomSeries {
    private static SecureRandom random;
    private static final Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();

    static {
        try { random = SecureRandom.getInstance("DRBG"); }
        catch (NoSuchAlgorithmException e) {
            random = new SecureRandom();
        }
    }

    // A private constructor to prevent instantiation
    private SecureRandomSeries() {}

    /**
     * Returns a secure random value with at least 160 bits of entropy.
     * The returned value is Base64 encoded to make it suitable for URLs.
     * The returned string happens to be 27 characters long (that length is not guaranteed).
     * @return a secure random value
     */
    public static String nextString() {
        var buffer = randomBytes();
        return encoder.encodeToString(buffer);
    }

    /**
     * Returns a secure random Long value with at least 160 bits of entropy
     * @return a secure random value
     */
    public static Long nextLong() {
        var buffer = randomBytes();
        return ByteBuffer.wrap(buffer).getLong();
    }

    /**
     * Build a buffer with random byte values. The returned value has an
     * entropy of 160 bits (improving on the 122 bit entropy of a UUID).
     * Of course, the entropy can be increased by adding more bytes to the array.
     * @return a random series of bytes
     */
    private static byte[] randomBytes() {
        var buffer = new byte[20]; // 20x8 = 160 = bits of entropy
        random.nextBytes(buffer);
        return buffer;
    }
}