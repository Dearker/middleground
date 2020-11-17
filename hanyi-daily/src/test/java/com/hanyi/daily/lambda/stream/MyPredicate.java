package com.hanyi.daily.lambda.stream;

@FunctionalInterface
public interface MyPredicate<T> {

	boolean test(T t);
	
}
