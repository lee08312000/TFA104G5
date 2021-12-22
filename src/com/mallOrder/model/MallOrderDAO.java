package com.mallOrder.model;

import java.sql.Connection;
import java.util.List;

import com.cart.model.CartVO;

public interface MallOrderDAO {
	public void insert(MallOrderVO mallOrderVO);
    public void update(MallOrderVO mallOrderVO);
    public void delete(Integer mallOrderId);
    public MallOrderVO findByPrimaryKey(Integer mallOrderId);
    public List<MallOrderVO> getAll();
    public Integer insert(MallOrderVO mallOrderVO, List<CartVO> buyList);
    public Integer insert(MallOrderVO mallOrderVO, List<CartVO> buyList, Connection con);
    public List<Integer> checkout(Integer memberId, String creditCardNum, String receiverName, String receiverPhone,
			String receiverAddress, List<CartVO> buyList);
}
