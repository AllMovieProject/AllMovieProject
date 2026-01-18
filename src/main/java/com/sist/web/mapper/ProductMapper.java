package com.sist.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.sist.web.vo.ProductCategoryVO;
import com.sist.web.vo.ProductComboVO;
import com.sist.web.vo.ProductItemCategoryVO;
import com.sist.web.vo.ProductItemVO;
import com.sist.web.vo.StoreProductVO;

@Mapper
public interface ProductMapper {
	
	@Select("SELECT category_id, category_name FROM product_category ORDER BY category_id")
	public List<ProductCategoryVO> productCategoryList();
	
	@Select("<script> "
		  + "SELECT pi.item_id, item_name, item_size, item_price, base_item_id, add_price "
		  + "FROM product_item pi "
		  + "JOIN product_item_category pic ON pi.item_id = pic.item_id "
		  + "<where> "
		  	+ "<choose> "
		  	  + "<when test='base_item_id == null'> "
		  		+ "base_item_id IS NULL "
		  	  + "</when> "
		  	  + "<when test='base_item_id == 1'> "
		  		+ "base_item_id IS NOT NULL "
		  	  + "</when> "
		  	+ "</choose> "
		  + "</where> "
		  + "AND category_id = #{category_id} "
		  + "</script>")
	public List<ProductItemVO> productItemList(@Param("category_id") int category_id, @Param("base_item_id") Integer base_item_id);
	
	@SelectKey(keyProperty = "item_id", resultType = int.class, before = false,
	           statement = "SELECT seq_item_id.currval FROM dual")
	@Insert("INSERT INTO product_item(item_id, item_name, item_size, item_price, base_item_id) " +
	        "VALUES(seq_item_id.nextval, #{item_name}, #{item_size}, #{item_price}, #{base_item_id})")
	public void productItemInsert(ProductItemVO vo);
	
	@Insert("INSERT INTO product_item_category(item_category_id, item_id, category_id) "
		  + "VALUES(seq_item_category_id.nextval, #{item_id}, #{category_id})")
	public void productItemCategoryInsert(ProductItemCategoryVO vo);
	
	@SelectKey(keyProperty = "product_id", resultType = int.class, before = false,
	           statement = "SELECT seq_product_id.currval FROM dual")
	@Insert("INSERT INTO store_product(product_id, product_name, product_image, item_id, "
		  + "product_price, discount, description, is_combo) "
		  + "VALUES(seq_product_id.nextval, #{product_name}, #{product_image}, "
		  + "(SELECT NVL(base_item_id, item_id) item_id FROM product_item WHERE item_id = #{item_id}), "
		  + "#{product_price}, #{discount}, #{description}, #{is_combo})")
	public void storeProductInsert(StoreProductVO vo);

	@Insert("INSERT INTO product_combo(combo_id, product_id, item_id, is_upgrade, upgrade_price, item_quantity) "
		  + "VALUES(seq_combo_id.nextval, #{product_id}, #{item_id}, #{is_upgrade}, #{upgrade_price}, #{item_quantity})")
	public void productComboInsert(ProductComboVO vo);
	
}
