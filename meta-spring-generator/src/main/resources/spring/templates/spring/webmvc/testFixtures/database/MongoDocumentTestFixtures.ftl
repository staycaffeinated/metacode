<#include "/common/Copyright.ftl">
package ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName};

import ${endpoint.basePackage}.math.SecureRandomSeries;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Sample ${endpoint.documentName} objects
 */
public class ${endpoint.documentName}TestFixtures {

    private static final SecureRandomSeries RANDOM = new SecureRandomSeries();

    private static final ${endpoint.documentName} SAMPLE_ONE;
    private static final ${endpoint.documentName} SAMPLE_TWO;
    private static final ${endpoint.documentName} ONE_WITH_RESOURCE_ID;

    static {
        SAMPLE_ONE = aNew${endpoint.documentName}("Bluey");
        SAMPLE_TWO = aNew${endpoint.documentName}("Bingo");
        ONE_WITH_RESOURCE_ID = aNew${endpoint.documentName}("Muffin");
    }

    private static final List<${endpoint.documentName}> ALL_ITEMS = new ArrayList<>();
    static {
        ALL_ITEMS.add(SAMPLE_ONE);
        ALL_ITEMS.add(SAMPLE_TWO);
    }

    public static List<${endpoint.documentName}> allItems() { return ALL_ITEMS; }

    public static ${endpoint.documentName} getSampleOne() { return SAMPLE_ONE; }
    public static ${endpoint.documentName} getSampleTwo() { return SAMPLE_TWO; }
    public static ${endpoint.documentName} oneWithResourceId() { return SAMPLE_TWO; }


    public static ${endpoint.documentName} copyOf(${endpoint.documentName} someDocument) {
        // @formatter:off
        return ${endpoint.documentName}.builder()
            .text(someDocument.getText())
            .resourceId(someDocument.getResourceId())
            .build();
        // @formatter:on
    }

    /* ===============================================================================
     * HELPER METHODS
     * =============================================================================== */
     private static ${endpoint.documentName} aNew${endpoint.documentName}(String text) {
         // @formatter:off
         return ${endpoint.documentName}.builder()
                .text(text)
                .resourceId(RANDOM.nextResourceId())
                .build();
         // @formatter:on
    }
}