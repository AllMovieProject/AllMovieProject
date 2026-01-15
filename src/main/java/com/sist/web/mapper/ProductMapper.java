package com.sist.web.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;

import com.sist.web.vo.ProductComboVO;
import com.sist.web.vo.ProductItemCategoryVO;
import com.sist.web.vo.ProductItemVO;
import com.sist.web.vo.StoreProductVO;

@Mapper
public interface ProductMapper {
	
	@Insert("INSERT INTO product_item(item_id, item_name, item_size, item_price, base_item_id) "
		  + "VALUES(seq_item_id.nextval, #{item_name}, #{item_size}, #{item_price}, #{base_item_id})")
	public void productItemInsert(ProductItemVO vo);
	
	@Insert("INSERT INTO product_item_category(item_category_id, item_id, category_id) "
		  + "VALUES(seq_item_category_id.nextval, seq_item_id.currval, seq_category_id.currval)")
	public void productItemCategoryInsert(ProductItemCategoryVO vo);
	
	@SelectKey(keyProperty = "item_id", resultType = int.class, before = true,
			statement = "SELECT seq_item_id.currval FROM dual")
	@Insert("INSERT INTO store_product(product_id, product_name, product_image, item_id, "
			+ "product_price, discount, description, is_combo) "
		  + "VALUES(seq_product_id.nextval, #{product_name}, #{product_image}, #{item_id}, "
		  + "#{product_price}, #{discount}, #{description}, #{is_combo})")
	public void storeProductInsert(StoreProductVO vo);

	@Insert("INSERT INTO product_combo(combo_id, product_id, item_id, is_upgrade, upgrade_price, item_quantity) "
		  + "VALUES(seq_combo_id.nextval, seq_product_id.currval, seq_item_id.currval, #{is_upgrade}, #{upgrade_price}, #{item_quantity})")
	public void productComboInsert(ProductComboVO vo);
	
}
