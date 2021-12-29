package com.mallOrder.model;

import redis.clients.jedis.Jedis;

public class QrcodeService {

	public String getQrCode(Integer mallOrderId) {
		Jedis jedis = null;
		String base64str = null;
		try {
			jedis = new Jedis("localhost", 6379);
			base64str = jedis.get("mallOrder:"+ mallOrderId);
			
		}finally {
			if(jedis != null)
			   jedis.close();
		} 
		return base64str;
	}
}
