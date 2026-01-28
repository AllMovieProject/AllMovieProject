package com.sist.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
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
          + "VALUES(seq_order_id.nextval, #{userid}, #{store_id}, #{merchant_uid}, #{total_amount}, 'received')")
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
    
    @Update("UPDATE orders SET order_status = #{order_status} WHERE order_id = #{order_id}")
    public void updateOrderStatusById(@Param("order_id") int order_id, @Param("order_status") String order_status);

    // 주문 상세 조회 (merchant_uid)
    @Select("SELECT order_id, userid, store_id, merchant_uid, total_amount, order_status, "
    	  + "TO_CHAR(order_date, 'YYYY-MM-DD HH24:MI:SS') dbday "
    	  + "FROM orders WHERE merchant_uid = #{merchant_uid}")
    public OrderVO getOrderByMerchantUid(String merchant_uid);

    // 사용자 주문 목록 조회
    @Select("SELECT o.order_id, o.userid, o.store_id, o.merchant_uid, o.total_amount, "
          + "o.order_status, TO_CHAR(o.order_date, 'YYYY-MM-DD HH24:MI:SS') as dbday "
          + "FROM orders o "
          + "WHERE o.userid = #{userid} "
          + "ORDER BY o.order_date DESC")
    public List<OrderVO> getUserOrders(String userid);

    // 주문 상품 조회
    @Results({
        @Result(property = "pvo.product_name", column = "product_name"),
        @Result(property = "pvo.product_image", column = "product_image"),
        @Result(property = "pvo.is_combo", column = "is_combo")
    })
    @Select("SELECT oi.order_item_id, oi.order_id, oi.product_id, oi.quantity, oi.price, "
          + "sp.product_name, sp.product_image, sp.is_combo "
          + "FROM order_item oi "
          + "JOIN store_product sp ON oi.product_id = sp.product_id "
          + "WHERE oi.order_id = #{order_id}")
    public List<OrderItemVO> getOrderItems(int order_id);

    // 주문 상품 상세 조회
    @Results({
        @Result(property = "ivo.item_name", column = "item_name"),
        @Result(property = "ivo.item_size", column = "item_size")
    })
    @Select("SELECT oid.order_item_detail_id, oid.order_item_id, oid.item_id, oid.quantity, "
          + "pi.item_name, pi.item_size "
          + "FROM order_item_detail oid "
          + "JOIN product_item pi ON oid.item_id = pi.item_id "
          + "WHERE oid.order_item_id = #{order_item_id}")
    public List<OrderItemDetailVO> getOrderItemDetails(int order_item_id);
    
}