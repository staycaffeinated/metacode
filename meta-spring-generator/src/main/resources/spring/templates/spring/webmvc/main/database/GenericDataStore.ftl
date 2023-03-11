
<#include "/common/Copyright.ftl">

package ${project.basePackage}.database;

import ${project.basePackage}.math.SecureRandomSeries;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * This class provides default implementation of CRUD operations for a
 * DataStore. A DataStore is, basically, a wrapper around a Repository. The
 * DataStore implements any business rules that need to happen when
 * reading/writing from a Repository. The DataStore mainly exposes Domain
 * objects, and encapsulates the EntityBeans and Repository.
 * <p>
 * {@code D} is the Domain object type</p>
 * <p>{@code B} is the EntityBean type</p>
 * <p>{@code ID} is the primary key data type (e.g., Long or String)</p>
 *
 * For example {@code GenericDataStore<Pet,PetEntity,Long>}.
 */
@SuppressWarnings("java:S119") // 'ID' mimics Spring convention
public abstract class GenericDataStore<D,B,ID> {

    private final CustomRepository<B,ID> repository;
    private final Converter<B, D> ejbToPojoConverter;
    private final Converter<D, B> pojoToEjbConverter;

    private final SecureRandomSeries secureRandomSeries;

    protected GenericDataStore(CustomRepository<B,ID> repository, Converter<B, D> ejbToPojoConverter,
        Converter<D, B> pojoToEjbConverter, SecureRandomSeries secureRandomSeries) {
        this.repository = repository;
        this.ejbToPojoConverter = ejbToPojoConverter;
        this.pojoToEjbConverter = pojoToEjbConverter;
        this.secureRandomSeries = secureRandomSeries;
    }

    /**
     * Returns a handle to the Repository that's enabling the DataStore to
     * read/write to the database.
     */
    protected CustomRepository<B,ID> repository() { return repository; }

    /**
     * Returns the Converter used to convert an entity bean into a domain object
     */
    protected Converter<B, D> converterToPojo() { return ejbToPojoConverter; }

    /**
     * Returns the Converter used to convert domain objects into (unmanaged) entity
     * beans.
     */
    protected Converter<D, B> converterToEjb() { return pojoToEjbConverter; }

    /**
     * Returns a handle to the SecureRandom generator that yields resourceIds.
     */
    protected String nextResourceId() { return secureRandomSeries.nextResourceId(); }

    /**
     * Retrieve an EJB having the given {@code resourceId}
     *
     * @param resourceId the public identifier of the entity
     * @return an Optional that contains either the desired EJB or is empty
     */
    public Optional<D> findByResourceId(@NonNull String resourceId) {
        Optional<B> optional = repository.findByResourceId(resourceId);
        if (optional.isPresent()) {
        D pojo = ejbToPojoConverter.convert(optional.get());
        if (Objects.nonNull(pojo))
            return Optional.of(pojo);
        }
        return Optional.empty();
    }

    public List<D> findAll() {
        return repository.findAll().stream().map(ejbToPojoConverter::convert).toList();
    }

    public void deleteByResourceId(@NonNull String resourceId) {
        Optional<B> optional = repository.findByResourceId(resourceId);
        if (optional.isPresent()) {
            B ejb = optional.get();
            repository.delete(ejb);
        }
    }

    /**
     * Fetch the EJB corresponding to {@code item}. A basic implementation will look
     * similar to: <code>
     *     Optional findItem(Some item) {
     *         return repository().findByResourceId(item.getResourceId());
     *     }
     * </code>
     *
     * @param item the Domain object being sought in the database
     * @return an Optional containing the corresponding EJB or, if not found, empty.
     */
    protected abstract Optional<B> findItem(D item);

    /**
     * Implements any custom steps that need to be taken. A common step to take
     * within this method is to copy the fields to update from
     * {@code copyFieldsFrom} into the EJB; for example:
     * <code>
     *     personEntity.setFirstName(personDomainObj.getFirstName());
     *     personEntity.setLastName(personDomainObj.getLastName());
     * </code>
     *
     * @param copyFieldsFrom
     *            the Domain object contains the new field values that need to be
     *            persisted
     * @param copyFieldsTo
     *            this is essentially the database record to which the updates are
     *            applied, followed by the updated record being written to the
     *            database.
     */
    protected abstract void applyBeforeUpdateSteps(D copyFieldsFrom, B copyFieldsTo);

    /**
     * Implements any custom steps that need to be taken when creating a new record
     * in the database.
     *
     * A common step to take within this method is to copy the fields to update from
     * {@code copyFieldsFrom} into the EJB; for example:
     * <code>
     *     personEntity.setFirstName(personDomainObj.getFirstName());
     *     personEntity.setLastName(personDomainObj.getLastName());
     * </code>
     *
     * @param copyFieldsFrom
     *            the Domain object contains the field values that need to be
     *            persisted
     * @param copyFieldsTo
     *            this is essentially the database record which is inserted into the
     *            database.
     */
    protected abstract void applyBeforeInsertSteps(D copyFieldsFrom, B copyFieldsTo);

    public Optional<D> update(D item) {
        Optional<B> optional = findItem(item);
        if (optional.isPresent()) {
            B ejb = optional.get();
            applyBeforeUpdateSteps(item, ejb);
            B managed = repository().save(ejb);
            D updated = converterToPojo().convert(managed);
            if (updated != null)
                return Optional.of(updated);
            }
        return Optional.empty();
    }

    public D save(@NonNull D item) {
        B ejb = converterToEjb().convert(item);
        if (ejb != null) {
            applyBeforeInsertSteps(item, ejb);
            B managedEntity = repository().save(ejb);
            return converterToPojo().convert(managedEntity);
        }
        return null;
    }
}