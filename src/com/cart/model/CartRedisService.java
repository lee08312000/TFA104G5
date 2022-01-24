package com.cart.model;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import redis.clients.jedis.Jedis;

public class CartRedisService {
	
	public void setBuyList(Integer memberId, List<CartVO> buyList) {
		Gson gson = new Gson();
		String buyListJson = gson.toJson(buyList);
		Jedis jedis = null;
		try {
			jedis = new Jedis("localhost",6379,5000);
			jedis.set("member:" + memberId, buyListJson);
			jedis.expire("member:" + memberId, 60*60*24*7);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	
	public List<CartVO> getBuyList(Integer memberId) {
		Gson gson = new Gson();
		Jedis jedis = null;
		String buyListJson = "";
		try {
			jedis = new Jedis("localhost",6379,5000);
			buyListJson = jedis.get("member:" + memberId);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		Type collectionType = new TypeToken<List<CartVO>>() {
		}.getType();
		
		List<CartVO> buyList = gson.fromJson(buyListJson, collectionType);
		return buyList;
	}
	
	public void clearBuyList(Integer memberId) {
		Jedis jedis = null;
		try {
			jedis = new Jedis("localhost",6379,5000);
			Long delete = jedis.del("member:" + memberId);
			System.out.println(delete);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

//	public static void main(String[] args) {
//		CartRedisService cartRedisSvc = new CartRedisService();
//		cartRedisSvc.setBuyList(1, null);
//		
//		
//		System.out.println(cartRedisSvc.getBuyList(1));
//		if (cartRedisSvc.getBuyList(1) == null) {
//			System.out.println("y");
//		}
//	}

}
