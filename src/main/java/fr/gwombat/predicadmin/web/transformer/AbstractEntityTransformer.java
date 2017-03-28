package fr.gwombat.predicadmin.web.transformer;

/**
 * 
 * Transformer base class used to switch between an entity, his form
 * representation and his view representation
 * 
 * @author gWombat
 * 
 *
 * @param <T>
 *            The Entity
 * @param <U>
 *            The Form object corresponding to the entity
 * @param <V>
 *            The View object corresponding to the entity
 */
abstract class AbstractEntityTransformer<T, U, V> {

    public abstract T toEntity(U formObject, T existingEntity);

    public abstract U toFormObject(T entity);

    public abstract V toViewObject(T entity);

}
