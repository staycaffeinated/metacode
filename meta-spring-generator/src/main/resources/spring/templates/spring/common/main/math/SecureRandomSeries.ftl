<#include "/common/Copyright.ftl">
package ${project.basePackage}.math;

import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

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

    // If you want the resource Id to use alphanumeric, this constant
    // defines the cipher alphabet that will be used. Add other characters as desired.
    // Be mindful that some characters are not URL-friendly. Refer to https://www.ietf.org/rfc/rfc1738.txt,
    // which lists the unsafe characters.
    private static final String CIPHER_ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    // This is the length of the string returned by the nextString() method.
    // Longer strings will, of course, have higher entropy that shorter ones.
    // With an alphabet of 62 characters and a string length of 24, or entropy is
    // 62^24 = 1.04e43, which is within the 128-to-160 bits of entropy
    // recommended by the OAuth2 standards.
    // Recall that 128 bits of entropy is about 3.4e38 combinations; 160 bits of entropy is 1.4e48 combinations.
    // For a shorter string, say, length of 22, the entropy is
    // 20 characters long = 62^20 = 7.04e35 (7.04 x 10^35) (below the preferred threshold)
    // 22 characters long = 62^22 = 2.7e39 (2.7 x 10^39)
    public static final int ENTROPY_STRING_LENGTH = 24;

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
     * Returns a secure random value with about 143 bits of entropy.
     * The returned value is suitable for usage within URLs.
     * @return a URL-safe, secure, random String
     */
    public String nextString() {
        // This technique allows a custom cipher alphabet and a fixed length, which are defined above.
        return random.ints(ENTROPY_STRING_LENGTH, 0, CIPHER_ALPHABET.length()).mapToObj(CIPHER_ALPHABET::charAt)
            .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
    }

    public String nextResourceId() {
        return nextString();
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
	   public String nextNumericResourceId() {
		      BigInteger bg = new BigInteger(160, 1, random);
		      return bg.toString();
	   }
}