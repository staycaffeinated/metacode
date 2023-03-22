<#include "/common/Copyright.ftl">
package ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName};

import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.converter.${endpoint.entityName}EntityToPojoConverter;
import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.converter.${endpoint.entityName}PojoToEntityConverter;
import ${endpoint.basePackage}.exception.ResourceNotFoundException;
import ${endpoint.basePackage}.exception.UnprocessableEntityException;
import ${endpoint.basePackage}.math.SecureRandomSeries;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Unit tests
 */
@ExtendWith(MockitoExtension.class)
public class ${endpoint.entityName}DataStoreProviderTests {


    @InjectMocks
    ${endpoint.entityName}DataStoreProvider dataStoreUnderTest;

    @Mock
    ${endpoint.entityName}Repository mockRepository;

    ${endpoint.entityName}EntityToPojoConverter entityToPojoConverter = new ${endpoint.entityName}EntityToPojoConverter();

    ${endpoint.entityName}PojoToEntityConverter pojoToEntityConverter = new ${endpoint.entityName}PojoToEntityConverter();

    SecureRandomSeries secureRandomSeries = new SecureRandomSeries();
    

    @BeforeEach
    public void setUp() {
        mockRepository = Mockito.mock(${endpoint.entityName}Repository.class);
        dataStoreUnderTest = aWellFormedDataStore();
    }    
    
    @Nested
    class TestCreate {
        @Test
        void shouldCreateSuccessfully() {
            Mono<${endpoint.ejbName}> mockReturnValue = Mono.just(${endpoint.entityName}EntityTestFixtures.oneWithResourceId());
            when(mockRepository.save(any(${endpoint.ejbName}.class))).thenReturn(mockReturnValue);

            ${endpoint.pojoName} pojo = ${endpoint.entityName}TestFixtures.oneWithoutResourceId();
            Mono<String> publisher = dataStoreUnderTest.create${endpoint.entityName}(pojo);

            // @formatter:off
            StepVerifier.create(publisher).expectSubscription()
                    .consumeNextWith(v -> assertThat(v).isNotNull().isNotEmpty())
                    .verifyComplete();
            // @formatter:on        
        }

        @Test
        void shouldReturnError() {
            ${endpoint.entityName}DataStoreProvider dodgyProvider = oneWithDodgyPojoConverter();
            ${endpoint.pojoName} pojo = ${endpoint.entityName}TestFixtures.oneWithoutResourceId();
            
            Mono<String> publisher = dodgyProvider.create${endpoint.entityName}(pojo);
            
            // @formatter:off
            StepVerifier.create(publisher).expectSubscription()
                        .expectError(UnprocessableEntityException.class)
                        .verify(Duration.ofMillis(1000));
            // @formatter:on            
        }
    }
    
    @Nested
    class TestUpdate {
        @Test
        void shouldUpdateSuccessfully() {
            Mono<${endpoint.ejbName}> mockReturnValue = Mono.just(${endpoint.entityName}EntityTestFixtures.oneWithResourceId());
            when(mockRepository.findByResourceId(any(String.class))).thenReturn(mockReturnValue);
            when(mockRepository.save(any(${endpoint.ejbName}.class))).thenReturn(mockReturnValue);

            ${endpoint.pojoName} pojo = ${endpoint.entityName}TestFixtures.oneWithoutResourceId();
            pojo.setResourceId(${endpoint.entityName}EntityTestFixtures.oneWithResourceId().getResourceId());

            Mono<${endpoint.pojoName}> publisher = dataStoreUnderTest.update${endpoint.entityName}(pojo);
            StepVerifier.create(publisher).expectSubscription().expectNext(pojo).verifyComplete();
        }

        @Test
        void shouldReturnErrorWhenNotFound() {
            Mono<${endpoint.ejbName}> notFound = Mono.empty();
            when(mockRepository.findByResourceId(any(String.class))).thenReturn(notFound);
            
            ${endpoint.pojoName} pojo = ${endpoint.entityName}TestFixtures.oneWithoutResourceId();
            pojo.setResourceId(${endpoint.entityName}EntityTestFixtures.oneWithResourceId().getResourceId());

            Mono<${endpoint.pojoName}> publisher = dataStoreUnderTest.update${endpoint.entityName}(pojo);
            StepVerifier.create(publisher).expectSubscription().expectError(ResourceNotFoundException.class)
                    .verify(Duration.ofMillis(1000));
        }
    }    
    
    @Nested
    class TestFindByResourceId {
        @Test
        void shouldFindRecord() {
            Mono<${endpoint.ejbName}> returnValue = Mono.just(${endpoint.entityName}EntityTestFixtures.oneWithResourceId());
            when(mockRepository.findByResourceId(any(String.class))).thenReturn(returnValue);

            String expectedResourceId = ${endpoint.entityName}EntityTestFixtures.oneWithResourceId().getResourceId();
            Mono<${endpoint.pojoName}> publisher = dataStoreUnderTest.findByResourceId(expectedResourceId);

            // @formatter:off
            StepVerifier.create(publisher).expectSubscription()
                    .consumeNextWith(t -> {
                        assertThat(t).isNotNull();
                        assertThat(t.getResourceId()).isEqualTo(expectedResourceId);
                        })
                    .verifyComplete();
            // @formatter:on
        }

        @Test
        void shouldReturnErrorWhenNotFound() {
            Mono<${endpoint.ejbName}> notFound = Mono.empty();
            when(mockRepository.findByResourceId(any(String.class))).thenReturn(notFound);

            Mono<${endpoint.entityName}> publisher = dataStoreUnderTest.findByResourceId("123abc456efg");

            // @formatter:off
            StepVerifier.create(publisher).expectSubscription()
                  .expectError(ResourceNotFoundException.class)
                  .verify(Duration.ofMillis(1000));
            // @formatter:on      
        }

        @Test
        void shouldReturnErrorWhenConversionToPojoFails() {
            ${endpoint.entityName}DataStoreProvider dodgyProvider = oneWithDodgyEjbConverter();

            Mono<${endpoint.ejbName}> returnValue = Mono.just(${endpoint.entityName}EntityTestFixtures.oneWithResourceId());
            when(mockRepository.findByResourceId(any(String.class))).thenReturn(returnValue);

            String expectedResourceId = ${endpoint.entityName}EntityTestFixtures.oneWithResourceId().getResourceId();
            Mono<${endpoint.entityName}> publisher = dodgyProvider.findByResourceId(expectedResourceId);

            StepVerifier.create(publisher).expectSubscription().verifyError();
        }
    }    
    
    @Nested
    class TestFindById {
        @Test
        void shouldFindRecord() {
            Mono<${endpoint.ejbName}> returnValue = Mono.just(${endpoint.entityName}EntityTestFixtures.oneWithResourceId());
            when(mockRepository.findById(any(Long.class))).thenReturn(returnValue);

            Mono<${endpoint.pojoName}> publisher = dataStoreUnderTest.findById(1L);

            // @formatter:off
            StepVerifier.create(publisher).expectSubscription()
                .consumeNextWith(it -> assertThat(it).isNotNull())
                .verifyComplete();
            // @formatter:on    
        }

        @Test
        void shouldReturnErrorWhenRecordNotFound() {
            Mono<${endpoint.ejbName}> returnValue = Mono.empty();
            when(mockRepository.findById(any(Long.class))).thenReturn(returnValue);

            Mono<${endpoint.entityName}> publisher = dataStoreUnderTest.findById(1L);

            // @formatter:off
            StepVerifier.create(publisher).expectSubscription()
                .expectError(ResourceNotFoundException.class)
                .verify(Duration.ofMillis(1000));
            // @formatter:on    
        }
    }    
    
    @Nested
    class TestDeleteByResourceId {
        @Test
        void shouldSucceedWhenRecordIsFound() {
            Mono<Long> returnValue = Mono.just(1L);
            when(mockRepository.deleteByResourceId(any(String.class))).thenReturn(returnValue);

            String aResourceId = ${endpoint.entityName}EntityTestFixtures.oneWithResourceId().getResourceId();
            Mono<Long> publisher = dataStoreUnderTest.deleteByResourceId(aResourceId);

            // Expect: the number of rows deleted is returned
            StepVerifier.create(publisher).expectSubscription().expectNext(1L).verifyComplete();
        }

        @Test
        void shouldReturnsZeroWhenRecordIsNotFound() {
            Mono<Long> returnValue = Mono.just(0L);
            when(mockRepository.deleteByResourceId(any(String.class))).thenReturn(returnValue);

            String aResourceId = ${endpoint.entityName}EntityTestFixtures.oneWithResourceId().getResourceId();
            Mono<Long> publisher = dataStoreUnderTest.deleteByResourceId(aResourceId);

            // Expect: the number of rows deleted is returned
            StepVerifier.create(publisher).expectSubscription().expectNext(0L).verifyComplete();
        }
    }    
    
    @Nested
    class TestFindAllByText {
        @Test
        void shouldFindRecords() {
            Flux<${endpoint.ejbName}> returnValue = ${endpoint.entityName}EntityTestFixtures.allItemsAsFlux();
            when(mockRepository.findAllByText(any(String.class))).thenReturn(returnValue);

            Flux<${endpoint.pojoName}> publisher = dataStoreUnderTest.findAllByText("Hello, world");

            int expectedCount = ${endpoint.entityName}EntityTestFixtures.allItems().size();
            StepVerifier.create(publisher).expectSubscription().expectNextCount(expectedCount).verifyComplete();
        }
    }    
    
    @Nested
    class TestFindAll {
        @Test
        void shouldFindRecords() {
            Flux<${endpoint.ejbName}> returnValue = ${endpoint.entityName}EntityTestFixtures.allItemsAsFlux();
            when(mockRepository.findAll()).thenReturn(returnValue);

            Flux<${endpoint.pojoName}> publisher = dataStoreUnderTest.findAll();

            int expectedCount = ${endpoint.entityName}EntityTestFixtures.allItems().size();
            StepVerifier.create(publisher).expectSubscription().expectNextCount(expectedCount).verifyComplete();
        }
    }    
    
    
    // -----------------------------------------------------------------------------------------------
    // Helper Methods
    // -----------------------------------------------------------------------------------------------
    
    private ${endpoint.entityName}DataStoreProvider aWellFormedDataStore() {
        // @formatter:off
        return ${endpoint.entityName}DataStoreProvider.builder()
                .ejbToPojoConverter(entityToPojoConverter)
                .pojoToEjbConverter(pojoToEntityConverter)
                .repository(mockRepository)
                .secureRandom(secureRandomSeries)
                .build();
        // @formatter:on       
    }

    /**
     * Creates a DataStore with a converter who's convert method always returns null
     */
    private ${endpoint.entityName}DataStoreProvider oneWithDodgyPojoConverter() {
        ${endpoint.entityName}PojoToEntityConverter dodgyConverter = Mockito.mock(${endpoint.entityName}PojoToEntityConverter.class);
        when(dodgyConverter.convert(any(${endpoint.pojoName}.class))).thenReturn(null);

        // @formatter:off
        return ${endpoint.entityName}DataStoreProvider.builder()
                .ejbToPojoConverter(entityToPojoConverter)
                .pojoToEjbConverter(dodgyConverter)
                .repository(mockRepository)
                .secureRandom(secureRandomSeries)
                .build();
        // @formatter:on        
    }


    private ${endpoint.entityName}DataStoreProvider oneWithDodgyEjbConverter() {
        ${endpoint.entityName}EntityToPojoConverter dodgyConverter = Mockito.mock(${endpoint.entityName}EntityToPojoConverter.class);
        when(dodgyConverter.convert(any(${endpoint.ejbName}.class))).thenReturn(null);

        // @formatter:off
        return ${endpoint.entityName}DataStoreProvider.builder()
                .ejbToPojoConverter(dodgyConverter)
                .pojoToEjbConverter(pojoToEntityConverter)
                .repository(mockRepository)
                .secureRandom(secureRandomSeries)
                .build();
        // @formatter:on        
    }
}    
    
    
    
    
    

