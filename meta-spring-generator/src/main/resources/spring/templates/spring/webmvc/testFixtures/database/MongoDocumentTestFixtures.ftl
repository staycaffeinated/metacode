
package com.example.demo.database.pet;

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

    static {
        SAMPLE_ONE = aNew${endpoint.documentName}("Bluey");
        SAMPLE_TWO = aNew${endpoint.documentName}("Bingo");
    }

    private static final List<${endpoint.documentName}> ALL_ITEMS = new ArrayList<>() {
    {
        add(SAMPLE_ONE);
        add(SAMPLE_TWO);
    }};

    public static List<${endpoint.documentName}> allItems() { return ALL_ITEMS; }

    public static ${endpoint.documentName} getSampleOne() { return SAMPLE_ONE; }
    public static ${endpoint.documentName} getSampleTwo() { return SAMPLE_TWO; }

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