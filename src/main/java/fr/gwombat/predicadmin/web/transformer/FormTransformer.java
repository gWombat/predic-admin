package fr.gwombat.predicadmin.web.transformer;

interface FormTransformer<T, U> {
    
    T toEntity(U formObject, T existingEntity);

    U toFormObject(T entity);

}
