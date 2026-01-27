package com.sist.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.sist.web.vo.CartVO;
import com.sist.web.vo.CartItemVO;

@Mapper
public interface CartMapper {

	// 장바구니 목록 조회
	@Results({
		@Result(property = "cart_id", column = "cart_id"),
		@Result(property = "pvo.product_name", column = "product_name"),
		@Result(property = "pvo.product_image", column = "product_image"),
		@Result(property = "pvo.product_price", column = "product_price"),
		@Result(property = "pvo.discount", column = "discount"),
		@Result(property = "pvo.is_combo", column = "is_combo"),
		@Result(property = "items", column = "cart_id", 
				javaType = List.class, 
				many = @org.apache.ibatis.annotations.Many(select = "getCartItems"))
	})
	@Select("SELECT c.cart_id, c.product_id, c.quantity, c.store_id, "
		  + "sp.product_name, sp.product_image, sp.product_price, sp.discount, sp.is_combo "
		  + "FROM cart c "
		  + "JOIN store_product sp ON c.product_id = sp.product_id "
		  + "WHERE c.userid = #{userid} "
		  + "ORDER BY c.cart_regdate DESC")
	public List<CartVO> getCartList(String userid);

	// 장바구니 아이템 조회
	@Results({
		@Result(property = "ivo.item_name", column = "item_name"),
		@Result(property = "ivo.item_size", column = "item_size"),
		@Result(property = "ivo.item_price", column = "item_price"),
		@Result(property = "ivo.add_price", column = "add_price")
	})
	@Select("SELECT ci.cart_item_id, ci.cart_id, ci.item_id, ci.quantity, "
		  + "pi.item_name, pi.item_size, pi.item_price, pi.add_price "
		  + "FROM cart_item ci "
		  + "JOIN product_item pi ON ci.item_id = pi.item_id "
		  + "WHERE ci.cart_id = #{cart_id}")
	public List<CartItemVO> getCartItems(int cart_id);

	// 장바구니 추가
	@SelectKey(keyProperty = "cart_id", resultType = int.class, before = false,
			   statement = "SELECT seq_cart_id.currval FROM dual")
	@Insert("INSERT INTO cart(cart_id, userid, store_id, product_id, quantity) "
		  + "VALUES(seq_cart_id.nextval, #{userid}, #{store_id}, #{product_id}, #{quantity})")
	public void insertCart(CartVO vo);

	// 장바구니 아이템 추가
	@Insert("INSERT INTO cart_item(cart_item_id, cart_id, item_id, quantity) "
		  + "VALUES(seq_cart_item_id.nextval, #{cart_id}, #{item_id}, #{quantity})")
	public void insertCartItem(CartItemVO vo);

	// 장바구니 수량 수정
	@Update("UPDATE cart SET quantity = #{quantity} WHERE cart_id = #{cart_id}")
	public void updateCartQuantity(@Param("cart_id") int cart_id, @Param("quantity") int quantity);

	// 장바구니 삭제
	@Delete("DELETE FROM cart WHERE cart_id = #{cart_id}")
	public void deleteCart(int cart_id);

	// 장바구니 전체 삭제
	@Delete("DELETE FROM cart WHERE userid = #{userid}")
	public void deleteAllCart(String userid);

	// 선택 상품 삭제
	@Delete("<script>"
		  + "DELETE FROM cart WHERE cart_id IN "
		    + "<foreach collection='list' item='id' open='(' separator=',' close=')'>"
		      + "#{id}"
		    + "</foreach>"
		  + "</script>")
	public void deleteSelectedCart(@Param("list") List<Integer> cartIds);
	
}