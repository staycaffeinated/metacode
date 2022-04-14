<#include "/common/Copyright.ftl">

package ${endpoint.packageName};

import ${endpoint.basePackage}.math.SecureRandomSeries;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ${endpoint.entityName}BeanToResourceConverterTests {

    ${endpoint.entityName}BeanToResourceConverter converterUnderTest = new ${endpoint.entityName}BeanToResourceConverter();

    final SecureRandomSeries randomSeries = new SecureRandomSeries();

	@Test
	void whenDataToConvertIsWellFormed_expectSuccessfulConversion() {
		final String expectedPublicId = randomSeries.nextResourceId();
		final String expectedText = "hello world";

		${endpoint.ejbName} ejb = ${endpoint.ejbName}.builder().resourceId(expectedPublicId).id(0L).text(expectedText).build();
		${endpoint.pojoName} pojo = converterUnderTest.convert(ejb);

		assertThat(pojo).isNotNull();
		assertThat(pojo.getResourceId()).isEqualTo(expectedPublicId);
		assertThat(pojo.getText()).isEqualTo(expectedText);
	}

	@Test
	void whenDataListIsWellFormed_expectSuccessfulConversion() {
		// Given a list of 3 items
		final String itemOne_expectedPublicId = randomSeries.nextResourceId();
		final Long itemOne_expectedDatabaseId = 2424L;
		final String itemOne_expectedText = "hello goodbye";

		final String itemTwo_expectedPublicId = randomSeries.nextResourceId();
		final Long itemTwo_expectedDatabaseId = 42348L;
		final String itemTwo_expectedText = "strawberry fields";

		final String itemThree_expectedPublicId = randomSeries.nextResourceId();
		final Long itemThree_expectedDatabaseId = 9341L;
		final String itemThree_expectedText = "sgt pepper";

		${endpoint.ejbName} itemOne = ${endpoint.ejbName}.builder().resourceId(itemOne_expectedPublicId).text(itemOne_expectedText)
				.id(itemOne_expectedDatabaseId).build();
		${endpoint.ejbName} itemTwo = ${endpoint.ejbName}.builder().resourceId(itemTwo_expectedPublicId).text(itemTwo_expectedText)
				.id(itemTwo_expectedDatabaseId).build();
		${endpoint.ejbName} itemThree = ${endpoint.ejbName}.builder().resourceId(itemThree_expectedPublicId).text(itemThree_expectedText)
				.id(itemThree_expectedDatabaseId).build();

		ArrayList<${endpoint.ejbName}> list = new ArrayList<>();
		list.add(itemOne);
		list.add(itemTwo);
		list.add(itemThree);

		// When
		List<${endpoint.pojoName}> results = converterUnderTest.convert(list);

		// Then expect the fields of the converted items to match the original items
		assertThat(results.size()).isEqualTo(list.size());
		assertThat(fieldsMatch(itemOne, results.get(0))).isTrue();
		assertThat(fieldsMatch(itemTwo, results.get(1))).isTrue();
		assertThat(fieldsMatch(itemThree, results.get(2))).isTrue();
	}

	@Test
	void whenBeanIsNull_expectNullPointerException() {
		assertThrows(NullPointerException.class, () -> converterUnderTest.convert((${endpoint.ejbName}) null));
	}

	@Test
	void whenListIsNull_expectNullPointerException() {
		assertThrows(NullPointerException.class, () -> converterUnderTest.convert((List<${endpoint.ejbName}>) null));
	}

    /**
     * Verify that properties of the EJB that must not shared outside
     * the security boundary of the service are not copied into
     * the RESTful resource.  For example, the database ID
     * assigned to an entity bean must not be exposed to
     * external applications, thus the database ID is never
     * copied into a RESTful resource.
     */
    @Test
    void shouldCopyOnlyExposedProperties() {
        ${endpoint.ejbName} bean = new ${endpoint.ejbName}();
        bean.setResourceId(randomSeries.nextResourceId());
        bean.setText("hello, world");
        bean.setId(100L);

        ${endpoint.pojoName} pojo = converterUnderTest.convert(bean);
        assertThat(pojo.getResourceId()).isEqualTo(bean.getResourceId());
        assertThat(pojo.getText()).isEqualTo(bean.getText());
    }

	// ------------------------------------------------------------------
	// Helper methods
	// ------------------------------------------------------------------
	private boolean fieldsMatch(${endpoint.ejbName} expected, ${endpoint.pojoName} actual) {
		if (!Objects.equals(expected.getResourceId(), actual.getResourceId()))
			return false;
		if (!Objects.equals(expected.getText(), actual.getText()))
			return false;
		return true;
	}
}
