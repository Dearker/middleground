package com.hanyi.demo.component;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: middleground com.hanyi.demo.component MapEhcaChe
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2019-11-12 21:20
 * @Version: 1.0
 */
@Component
public class MapEhcaChe<K, V> {

	private Map<K, V> ehcaCheMap = new ConcurrentHashMap<K, V>();

	public void put(K k, V value) {
		ehcaCheMap.put(k, value);
	}

	public V get(K k) {
		return ehcaCheMap.get(k);
	}

	public void remove(K k) {
		ehcaCheMap.remove(k);
	}
}
