<#include "/common/Copyright.ftl">

package ${endpoint.packageName};

import ${endpoint.basePackage}.math.SecureRandomSeries;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ${endpoint.entityName}ResourceToBeanConverterTests {

    ${endpoint.entityName}ResourceToBeanConverter converterUnderTest = new ${endpoint.entityName}ResourceToBeanConverter();

    final SecureRandomSeries randomSeries = new SecureRandomSeries();

	@Test
	void whenDataToConvertIsWellFormed_expectSuccessfulConversion() {
		final String expectedPublicId = randomSeries.nextResourceId();
		final String expectedText = "hello world";

		${endpoint.pojoName} pojo = ${endpoint.pojoName}.builder().resourceId(expectedPublicId).text(expectedText).build();
		${endpoint.ejbName} ejb = converterUnderTest.convert(pojo);

		assertThat(ejb).isNotNull();
		assertThat(ejb.getResourceId()).isEqualTo(expectedPublicId);
		assertThat(ejb.getText()).isEqualTo(expectedText);
	}

	@Test
	void whenDataListIsWellFormed_expectSuccessfulConversion() {
		// Given a list of 3 items
		final String itemOne_expectedPublicId = randomSeries.nextResourceId();
		final String itemOne_expectedText = "hello goodbye";

		final String itemTwo_expectedPublicId = randomSeries.nextResourceId();
		final String itemTwo_expectedText = "strawberry fields";

		final String itemThree_expectedPublicId = randomSeries.nextResourceId();
		final String itemThree_expectedText = "sgt pepper";

		${endpoint.pojoName} itemOne = ${endpoint.pojoName}.builder().resourceId(itemOne_expectedPublicId)
				.text(itemOne_expectedText).build();
		${endpoint.pojoName} itemTwo = ${endpoint.pojoName}.builder().resourceId(itemTwo_expectedPublicId)
				.text(itemTwo_expectedText).build();
		${endpoint.pojoName} itemThree = ${endpoint.pojoName}.builder().resourceId(itemThree_expectedPublicId)
				.text(itemThree_expectedText).build();

		ArrayList<${endpoint.pojoName}> list = new ArrayList<>();
		list.add(itemOne);
		list.add(itemTwo);
		list.add(itemThree);

		// When
		List<${endpoint.ejbName}> results = converterUnderTest.convert(list);

		// Then expect the fields of the converted items to match the original items
		assertThat(results.size()).isEqualTo(list.size());
		assertThat(fieldsMatch(itemOne, results.get(0))).isTrue();
		assertThat(fieldsMatch(itemTwo, results.get(1))).isTrue();
		assertThat(fieldsMatch(itemThree, results.get(2))).isTrue();
	}

	@Test
	void whenConvertingNullObject_expectNullPointerException() {
		assertThrows(NullPointerException.class,
            () -> converterUnderTest.convert((${endpoint.pojoName}) null));
	}

	@Test
	void whenConvertingNullList_expectNullPointerException() {
		assertThrows(NullPointerException.class,
			() -> converterUnderTest.convert((List<${endpoint.pojoName}>) null));
	}

    @Test
    void shouldPopulateAllFields() {
        ${endpoint.pojoName} resource = ${endpoint.pojoName}.builder().resourceId(randomSeries.nextResourceId()).text("hello world").build();

        ${endpoint.ejbName} bean = converterUnderTest.convert(resource);
        assertThat(bean.getResourceId()).isEqualTo(resource.getResourceId());
        assertThat(bean.getText()).isEqualTo(resource.getText());
    }

	// ------------------------------------------------------------------
	// Helper methods
	// ------------------------------------------------------------------
	private boolean fieldsMatch(${endpoint.pojoName} expected, ${endpoint.ejbName} actual) {
		if (!Objects.equals(expected.getResourceId(), actual.getResourceId()))
			return false;
		if (!Objects.equals(expected.getText(), actual.getText()))
			return false;
		return true;
	}
}
