<#include "/common/Copyright.ftl">
package ${project.basePackage}.math;

import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * This class produces secure random values. The {@code nextString()}
 * methods returns a text String; the {@code nextResourceId()} returns
 * a numeric String (all the characters are numbers; the {@code nextLong()}
 * returns a Long value. Methods {@code nextString()} and {@code nextResourceId()}
 * return values that have 160-bit entropy, making it difficult to guess
 * the next value.  These have better entropy than UUIDs, which only have
 * 122 bits of entropy. The {@code nextLong()} values only have 64-bits
 * of entropy since Long's by nature are only 64 bits long.
 *
 * Fun facts:
 * 160 bits = 2^160 ~= 1.46 x 10^48 possible values.
 * The number of liters of water on the Earth is about 1.26 x 10^21.
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
        // Some candidate algorithms:
		      // DRBG (deterministic random bit generator)
		      // SHA2-384
		      // Hash_DRBG
		      // The objectives are that resource IDs should be very hard to guess
		      // and that collisions are extremely rare. A FIPS-approved algorithm
		      // is suggested, as those are designed to meet our objectives.
		      // Just because the generated value is 160 bits long doesn't guarantee
		      // the entropy is that good; a FIPS-approved algorithm provides more
		      // assurance that the entropy objective will be met.
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
        return random.nextLong();
    }
    
    /**
	    * Returns a String that is between 48 and 49 characters in length.
	    * There are about 10^48 possible values, so expect at least 48 digits.
	    * The String contains is all digits. The String values have 160-bits of entropy,
	    * meeting the OAuth2 framework recommendation (OAuth recommends resource Ids 
	    * that have at least 128-bits of entropy and not more that 160-bits of entropy;
	    * see https://datatracker.ietf.org/doc/html/rfc6749#section-10.10)
	    */
	   public String nextResourceId() {
		      BigInteger bg = new BigInteger(160, 1, random);
		      return bg.toString();
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