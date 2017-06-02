package fr.gwombat.predicadmin.web.transformer;

@FunctionalInterface
interface ViewTransformer<T, V> {

    V toViewObject(T entity);

}
