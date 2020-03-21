package com.hanyi.daily.lambda.stream;

@FunctionalInterface
public interface MyPredicate<T> {

	public boolean test(T t);
	
}
