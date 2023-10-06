package com.mini.beans.factory;

public interface FactoryBean<T> {
	T getObject() throws Exception;

	Class<?> getObjectType();

	default boolean isSingleton() {
		return true;
	}

}
