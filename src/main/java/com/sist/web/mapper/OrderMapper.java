package com.sist.web.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.sist.web.vo.OrderVO;
import com.sist.web.vo.OrderItemVO;
import com.sist.web.vo.OrderItemDetailVO;

@Mapper
public interface OrderMapper {

    @SelectKey(keyProperty = "order_id", resultType = int.class, before = false,
               statement = "SELECT seq_order_id.currval FROM dual")
    @Insert("INSERT INTO orders(order_id, userid, store_id, merchant_uid, total_amount, order_status) "
          + "VALUES(seq_order_id.nextval, #{userid}, #{store_id}, #{merchant_uid}, #{total_amount}, 'pending')")
    public void insertOrder(OrderVO vo);

    @SelectKey(keyProperty = "order_item_id", resultType = int.class, before = false,
               statement = "SELECT seq_order_item_id.currval FROM dual")
    @Insert("INSERT INTO order_item(order_item_id, order_id, product_id, quantity, price) "
          + "VALUES(seq_order_item_id.nextval, #{order_id}, #{product_id}, #{quantity}, #{price})")
    public void insertOrderItem(OrderItemVO vo);

    @Insert("INSERT INTO order_item_detail(order_item_detail_id, order_item_id, item_id, quantity) "
          + "VALUES(seq_order_item_detail_id.nextval, #{order_item_id}, #{item_id}, #{quantity})")
    public void insertOrderItemDetail(OrderItemDetailVO vo);

    @Update("UPDATE orders SET order_status = #{order_status} WHERE merchant_uid = #{merchant_uid}")
    public void updateOrderStatus(@Param("merchant_uid") String merchant_uid, @Param("order_status") String order_status);
    
}