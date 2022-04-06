<#include "/common/Copyright.ftl">
package ${project.basePackage}.math;

import lombok.NonNull;
import org.springframework.stereotype.Component;

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
@Component
public class SecureRandomSeries {
    private SecureRandom random;
    private static final Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();


    /**
     * Default constructor, which selects a default algorithm
     */
    public SecureRandomSeries() {
        this("DRBG");
    }

    /**
     * Constructor with a preferred algorithm
     */
    public SecureRandomSeries(@NonNull String algorithm) {
        try {
            random = SecureRandom.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            random = new SecureRandom();
        }
    }

    /**
     * Returns a secure random value with at least 160 bits of entropy.
     * The returned value is Base64 encoded to make it suitable for URLs.
     * The returned string happens to be 27 characters long (that length is not guaranteed).
     * @return a secure random value
     */
    public String nextString() {
        byte[] buffer = randomBytes();
        return encoder.encodeToString(buffer);
    }

    /**
     * Returns a secure random Long value.
     * These only have 64-bit entropy since Java long's are 64 bits long.
     * @return a secure random value
     */
    public Long nextLong() {
        byte[] buffer = randomBytes();
        return ByteBuffer.wrap(buffer).getLong();
    }

    /**
     * Build a buffer with random byte values. The returned value has an
     * entropy of 160 bits (improving on the 122 bit entropy of a UUID).
     * Of course, the entropy can be increased by adding more bytes to the array.
     * @return a random series of bytes
     */
    private byte[] randomBytes() {
        byte[] buffer = new byte[20]; // 20x8 = 160 = bits of entropy
        random.nextBytes(buffer);
        return buffer;
    }
}