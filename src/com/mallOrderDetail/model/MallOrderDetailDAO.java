package com.mallOrderDetail.model;

import java.sql.Connection;
import java.util.List;

public interface MallOrderDetailDAO {
	public void insert(MallOrderDetailVO mallOrderDetailVO);
	public void insert(MallOrderDetailVO mallOrderDetailVO, Connection con);
    public void update(MallOrderDetailVO mallOrderDetailVO);
    public void delete(Integer mallOrderDetailId);
    public MallOrderDetailVO findByPrimaryKey(Integer mallOrderDetailId);
    public List<MallOrderDetailVO> findBymallOrderId(Integer mallOrderId);
    public List<MallOrderDetailVO> getAll();
    // (商品編號, 分類類型)
    // 分類類型: 0 不分類， 1 最新評價 ，2 最舊評價，3 最高評價，4 最低評價
    public List<MallOrderDetailVO> getProductComments(Integer productId, Integer orderType, Integer limitX, Integer limitY);
}
