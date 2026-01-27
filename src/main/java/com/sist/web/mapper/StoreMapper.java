package com.sist.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.sist.web.vo.StoreStockVO;

@Mapper
public interface StoreMapper {
	
	@Results({
		@Result(property = "pvo.product_name", column = "product_name"),
		@Result(property = "pvo.product_image", column = "product_image"),
		@Result(property = "pvo.product_price", column = "product_price"),
		@Result(property = "pvo.discount", column = "discount"),
		@Result(property = "pvo.is_combo", column = "is_combo"),
		@Result(property = "pvo.description", column = "description"),
		@Result(property = "category_id", column = "category_id")
	})
	@Select("SELECT ss.stock_id, ss.product_id, product_name, product_image, product_price, "
		  + "discount, is_combo, description, stock_quantity, "
		  + "(SELECT MIN(category_id) FROM product_item_category WHERE item_id = sp.item_id) category_id, "
		  + "TO_CHAR(stock_regdate, 'YYYY-MM-DD') dbday "
		  + "FROM store_stock ss "
		  + "JOIN store_product sp ON ss.product_id = sp.product_id "
		  + "WHERE ss.store_id = #{store_id} "
		  + "ORDER BY stock_regdate DESC")
	public List<StoreStockVO> storeListData(int store_id);

}
