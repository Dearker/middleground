package com.hanyi.daily.lambda.stream;

public interface MyFun {
	
	default String getName(){
		return "哈哈哈";
	}

	Integer getValue(Integer num);
}
