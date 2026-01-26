package com.sist.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.sist.web.vo.StoreStockVO;

@Mapper
public interface StockMapper {
	
	@Results({
		@Result(property = "pvo.product_name", column = "product_name"),
		@Result(property = "pvo.product_image", column = "product_image"),
		@Result(property = "pvo.item_id", column = "item_id"),
		@Result(property = "pvo.product_price", column = "product_price"),
		@Result(property = "pvo.discount", column = "discount"),
		@Result(property = "pvo.is_combo", column = "is_combo")
	})
	@Select("SELECT ss.product_id, product_name, product_image, item_id, product_price, discount, is_combo, "
		  + "stock_quantity, TO_CHAR(stock_regdate, 'YYYY-MM-DD') dbday "
		  + "FROM store_stock ss "
		  + "JOIN store s ON ss.store_id = s.store_id "
		  + "JOIN store_product sp ON ss.product_id = sp.product_id "
		  + "WHERE s.userid = #{userid}"
		  + "ORDER BY ss.stock_regdate DESC")
	public List<StoreStockVO> storeStockListData(String userid);
	
	@SelectKey(keyProperty = "vo.store_id", resultType = int.class, before = true,
			   statement = "SELECT store_id FROM store WHERE userid = #{userid}")
	@Insert("INSERT INTO store_stock(stock_id, store_id, product_id, stock_quantity) "
		  + "VALUES(seq_stock_id.nextval, #{vo.store_id}, #{vo.product_id}, #{vo.stock_quantity})")
	public void stockInsert(@Param("vo") StoreStockVO vo, @Param("userid") String userid);

}
