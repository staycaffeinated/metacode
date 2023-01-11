
package ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName};

import ${endpoint.basePackage}.math.SecureRandomSeries;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * Sample ${endpoint.ejbName} objects suitable for test data
 */
public class ${endpoint.ejbName}TestFixtures {

    static final SecureRandomSeries randomSeries = new SecureRandomSeries();

    /*
     * Consider renaming these to something more meaningful to your use cases.
     * The general idea is to have names that indicate what the data point represents.
     * For example: accountWithPositiveBalance, accountWithNegativeBalance, accountWithZeroBalance,
     * activeAccount, closedAccount. With descriptive names, it's easier to discern
     * what condition is being exercised.
     */
    private static final ${endpoint.ejbName} SAMPLE_ONE;
    private static final ${endpoint.ejbName} SAMPLE_TWO;
    private static final ${endpoint.ejbName} SAMPLE_THREE;
    private static final ${endpoint.ejbName} SAMPLE_FOUR;
    private static final ${endpoint.ejbName} SAMPLE_FIVE;
    private static final ${endpoint.ejbName} SAMPLE_SIX;
    private static final ${endpoint.ejbName} SAMPLE_SEVEN;

    private static final ${endpoint.ejbName} ONE_WITH_RESOURCE_ID;
    private static final ${endpoint.ejbName} ONE_WITHOUT_RESOURCE_ID;

    /*
     * Useful for query tests where multiple records have the same text
     */
    private static final PetEntity SAME_TEXT_ONE;
    private static final PetEntity SAME_TEXT_TWO;
    private static final PetEntity SAME_TEXT_THREE;

    static {
        SAMPLE_ONE = aNew${endpoint.ejbName}("Bingo");
        SAMPLE_TWO = aNew${endpoint.ejbName}("Bluey");
        SAMPLE_THREE = aNew${endpoint.ejbName}("Chilli");
        SAMPLE_FOUR = aNew${endpoint.ejbName}("Bandit");
        SAMPLE_FIVE = aNew${endpoint.ejbName}("Muffin");
        SAMPLE_SIX = aNew${endpoint.ejbName}("Jack");
        SAMPLE_SEVEN = aNew${endpoint.ejbName}("Rusty");

        ONE_WITH_RESOURCE_ID = aNew${endpoint.ejbName}("Socks");
        ONE_WITHOUT_RESOURCE_ID = ${endpoint.ejbName}.builder().text("Uncle Stripe").build();

        SAME_TEXT_ONE = aNew${endpoint.ejbName}("Calypso");
        SAME_TEXT_TWO = aNew${endpoint.ejbName}("Calypso");
        SAME_TEXT_THREE = aNew${endpoint.ejbName}("Calypso");
    }

    public static final List<${endpoint.ejbName}> ALL_ITEMS = new ArrayList<>() {{
        add(SAMPLE_ONE);
        add(SAMPLE_TWO);
        add(SAMPLE_THREE);
        add(SAMPLE_FOUR);
        add(SAMPLE_FIVE);
        add(SAMPLE_SIX);
        add(SAMPLE_SEVEN);
    }};

    public static final Flux<${endpoint.ejbName}> FLUX_ITEMS = Flux.fromIterable(ALL_ITEMS);

    public static final List<${endpoint.ejbName}> ALL_WITH_SAME_TEXT = new ArrayList<>() {{
        add(SAME_TEXT_ONE);
        add(SAME_TEXT_TWO);
        add(SAME_TEXT_THREE);
    }};

    public static ${endpoint.ejbName} sampleOne() { return SAMPLE_ONE; }
    public static ${endpoint.ejbName} sampleTwo() { return SAMPLE_TWO; }
    public static ${endpoint.ejbName} sampleThree() { return SAMPLE_THREE; }

    /**
     * For those instances when you want to verify behavior against a
     * ${endpoint.ejbName} that has a resourceId.
     */
    public static ${endpoint.ejbName} oneWithResourceId() {
        return ONE_WITH_RESOURCE_ID;
    }

    /**
     * For those instances when you want to verify behavior against a
     * ${endpoint.ejbName} that has no resourceId assigned.
     */
    public static ${endpoint.ejbName} oneWithoutResourceId() {
        return ONE_WITHOUT_RESOURCE_ID;
    }

    /**
     * Create a sample ${endpoint.ejbName}
     */
    private static ${endpoint.ejbName} aNew${endpoint.ejbName}(String text) {
        return ${endpoint.ejbName}.builder()
                .resourceId(randomSeries.nextResourceId())
                .text(text)
                .build();
    }
}
