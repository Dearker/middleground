package com.hanyi.daily.lambda.exer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//交易类
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

	private Trader trader;
	private int year;
	private int value;

}
