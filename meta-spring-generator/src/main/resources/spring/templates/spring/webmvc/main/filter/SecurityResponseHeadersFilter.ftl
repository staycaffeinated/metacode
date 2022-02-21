<#include "/common/Copyright.ftl">
package  ${project.basePackage}.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This filter adds HTTP security headers to _all_ responses.
 * These are important for public-facing APIs.
 *
 * References:
 * https://infosec.mozilla.org/guidelines/web_security
 * https://owasp.org/www-project-secure-headers/
 */
@Component
public class SecurityResponseHeadersFilter implements Filter {

    // Content-Security-Policy setting
    final static String CSP_VALUE =
              "default-src *; "
            + "style-src * 'unsafe-inline'; "
            + "script-src * 'unsafe-inline' 'unsafe-eval'; "
            + "img-src * data: 'unsafe-inline'; "
            + "connect-src * 'unsafe-inline'; "
            + "frame-src *;";


    final static Map<String,String> SECURITY_HEADERS = new HashMap<>();

    static {
        // Enforce HTTPS.
        // Disabled by default to ease development work.
        // Should be enabled for production
        // See https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Strict-Transport-Security
        // REQUIRED_HEADERS.put ("Strict-Transport-Security", "max-age=3153600; includeSubDomains; preload" );

        // Prevent iframe busting by attackers
        SECURITY_HEADERS.put ("X-Frame-Options", "deny" );

        // Allows sites to opt-in to reporting and enforcement of Certificate Transparency requirements
        // Protects against certificates issued by rogue certificate authorities
        // See https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Expect-CT )
        SECURITY_HEADERS.put ("Expect-CT", "enforce, max-age=604800" );

        // Define a whitelist of sources of approved content for this site, thereby
        // helping the browser avoid loading malicious assets.
        // When this header is implemented well, the X-Frame-Options header can be dropped
        // (this header definition can be configured to encompass the X-Frame-Options constraint)..
        // See http://content-security-policy.com/ , https://developer.mozilla.org/en-US/docs/Web/HTTP/CSP
        SECURITY_HEADERS.put ("Content-Security-Policy", CSP_VALUE );

        // Stops pages from loading when the brower detects reflected cross-site scripting attacks.
        // When a strong Content-Security-Policy disables inline javascipt ('unsafe-inline'), this header is unnecessary.
        // See https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-XSS-ProtectionREQUIRED_HEADERS.put ("X-XSS-Protection", "1; mode=block");

        // A marker used by the server to indicate the MIME types advertised in the Content-Type headers
        // should not be changed. This is also a way to opt-out of MIME sniffing.
        // See https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-Content-Type-Options
        SECURITY_HEADERS.put("X-Content-Type-Options", "nosniff" );

        // Controls how much referrer information (sent via the Referrer header) should be
        // included with requests.
        // See https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Referrer-Policy
        SECURITY_HEADERS.put("Referrer-Policy", "strict-origin");
    }

    /**
     * The <code>doFilter</code> method of the Filter is called by the container
     * each time a request/response pair is passed through the chain due to a
     * client request for a resource at the end of the chain. The FilterChain
     * passed in to this method allows the Filter to pass on the request and
     * response to the next entity in the chain.
     * <p>
     * A typical implementation of this method would follow the following
     * pattern:- <br>
     * 1. Examine the request<br>
     * 2. Optionally wrap the request object with a custom implementation to
     * filter content or headers for input filtering <br>
     * 3. Optionally wrap the response object with a custom implementation to
     * filter content or headers for output filtering <br>
     * 4. a) <strong>Either</strong> invoke the next entity in the chain using
     * the FilterChain object (<code>chain.doFilter()</code>), <br>
     * 4. b) <strong>or</strong> not pass on the request/response pair to the
     * next entity in the filter chain to block the request processing<br>
     * 5. Directly set headers on the response after invocation of the next
     * entity in the filter chain.
     *
     * @param request  The request to process
     * @param response The response associated with the request
     * @param chain    Provides access to the next filter in the chain for this
     *                 filter to pass the request and response to for further
     *                 processing
     * @throws IOException      if an I/O error occurs during this filter's
     *                          processing of the request
     * @throws ServletException if the processing fails for any other reason
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        SECURITY_HEADERS.entrySet().forEach(entry  -> {
            ((HttpServletResponse)response).addHeader(entry.getKey(), entry.getValue());
        });
        chain.doFilter(request, response);
    }
}
