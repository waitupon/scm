package com.dwh.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class GenericDao<T> {
    private Class<T> entityClass;
    private T entity;
    protected GenericDao() {
        Type type = getClass().getGenericSuperclass();
        Type trueType = ((ParameterizedType) type).getActualTypeArguments()[0];
        this.entityClass = (Class<T>) trueType;
        try {
            entity = entityClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println(entity);
    }
}
