
package ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName};

import ${endpoint.basePackage}.math.SecureRandomSeries;

import java.util.ArrayList;
import java.util.List;


/**
 * Sample ${endpoint.entityName} objects suitable for test data
 */
public class ${endpoint.entityName}TestFixtures {

    static final SecureRandomSeries randomSeries = new SecureRandomSeries();

    /*
     * Consider renaming these to something more meaningful to your use cases.
     * The general idea is to have names that indicate what the data point represents.
     * For example: accountWithPositiveBalance, accountWithNegativeBalance, accountWithZeroBalance,
     * activeAccount, closedAccount. With descriptive names, it's easier to discern
     * what condition is being exercised.
     */
    private static final ${endpoint.entityName} SAMPLE_ONE;
    private static final ${endpoint.entityName} SAMPLE_TWO;
    private static final ${endpoint.entityName} SAMPLE_THREE;
    private static final ${endpoint.entityName} SAMPLE_FOUR;
    private static final ${endpoint.entityName} SAMPLE_FIVE;
    private static final ${endpoint.entityName} SAMPLE_SIX;
    private static final ${endpoint.entityName} SAMPLE_SEVEN;

    private static final ${endpoint.entityName} ONE_WITH_RESOURCE_ID;
    private static final ${endpoint.entityName} ONE_WITH_NO_RESOURCE_ID;


    static {
        SAMPLE_ONE = aNew${endpoint.entityName}("Bingo");
        SAMPLE_TWO = aNew${endpoint.entityName}("Bluey");
        SAMPLE_THREE = aNew${endpoint.entityName}("Chilli");
        SAMPLE_FOUR = aNew${endpoint.entityName}("Bandit");
        SAMPLE_FIVE = aNew${endpoint.entityName}("Muffin");
        SAMPLE_SIX = aNew${endpoint.entityName}("Jack");
        SAMPLE_SEVEN = aNew${endpoint.entityName}("Rusty");

        ONE_WITH_RESOURCE_ID = aNew${endpoint.entityName}("Socks");
        ONE_WITH_NO_RESOURCE_ID = ${endpoint.entityName}.builder().text("Uncle Stripe").build();
    }

    private static final List<${endpoint.entityName}> ALL_ITEMS = new ArrayList<>();
    static {
        ALL_ITEMS.add(SAMPLE_ONE);
        ALL_ITEMS.add(SAMPLE_TWO);
        ALL_ITEMS.add(SAMPLE_THREE);
        ALL_ITEMS.add(SAMPLE_FOUR);
        ALL_ITEMS.add(SAMPLE_FIVE);
        ALL_ITEMS.add(SAMPLE_SIX);
        ALL_ITEMS.add(SAMPLE_SEVEN);
    }

    public static final List<${endpoint.entityName}> allItems() { return ALL_ITEMS; }

    public static ${endpoint.entityName} sampleOne() { return SAMPLE_ONE; }
    public static ${endpoint.entityName} sampleTwo() { return SAMPLE_TWO; }
    public static ${endpoint.entityName} sampleThree() { return SAMPLE_THREE; }

    /**
     * For those instances when you want to verify behavior against a
     * ${endpoint.entityName} that has a resourceId.
     */
    public static ${endpoint.entityName} oneWithResourceId() { return ONE_WITH_RESOURCE_ID; }

    /**
     * For those instances when you want to verify behavior against a
     * ${endpoint.entityName} that does not have a resourceId assigned.
     */
    public static ${endpoint.entityName} oneWithoutResourceId() { return ONE_WITH_NO_RESOURCE_ID; }
    
    public static ${endpoint.entityName} copyOf(${endpoint.entityName} copy) {
        return ${endpoint.entityName}.builder()  
            .resourceId(copy.getResourceId())
            .text(copy.getText())
            .build();
    }

    /**
     * Create a sample ${endpoint.entityName}
     */
    private static ${endpoint.entityName} aNew${endpoint.entityName}(String text) {
        return ${endpoint.entityName}.builder()
            .resourceId(randomSeries.nextResourceId())
            .text(text)
            .build();
    }
}
