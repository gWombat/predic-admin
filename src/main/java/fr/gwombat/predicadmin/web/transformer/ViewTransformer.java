package fr.gwombat.predicadmin.web.transformer;

interface ViewTransformer<T, V> {

    V toViewObject(T entity);

}
