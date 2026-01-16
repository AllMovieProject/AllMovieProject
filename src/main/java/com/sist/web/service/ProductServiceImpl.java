package com.sist.web.service;

import org.springframework.stereotype.Service;

import com.sist.web.dto.ProductFormDTO;
import com.sist.web.mapper.ProductMapper;
import com.sist.web.vo.ProductComboVO;
import com.sist.web.vo.ProductItemCategoryVO;
import com.sist.web.vo.ProductItemVO;
import com.sist.web.vo.StoreProductVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
	
	private final ProductMapper mapper;

	@Override
	public String productInsert(ProductFormDTO dto) {
		if (dto.getIsCombo().equals("Y")) {
			// TODO for List
		} else {
			// TODO once
		}
		
//		@Insert("INSERT INTO product_item(item_id, item_name, item_size, item_price, base_item_id) "
//			  + "VALUES(seq_item_id.nextval, #{item_name}, #{item_size}, #{item_price}, #{base_item_id})")
		ProductItemVO piVO = new ProductItemVO();
		piVO.setItem_name(dto.getItemName());
		
		mapper.productItemInsert(piVO);
		
//		@Insert("INSERT INTO product_item_category(item_category_id, item_id, category_id) "
//			  + "VALUES(seq_item_category_id.nextval, #{item_id}, #{category_id})")
		ProductItemCategoryVO picVO = new ProductItemCategoryVO();
		picVO.setItem_id(piVO.getItem_id());
		picVO.setCategory_id(1);
		mapper.productItemCategoryInsert(picVO);
		
//		StoreProductVO spVO = new StoreProductVO(product_id, dto.getProductName(), dto.getImage(), item_id, 
//				dto.getPrice(), dto.getDiscountPrice(), dto.getProductDesc(), dto.getIsCombo());
		StoreProductVO spVO = new StoreProductVO();
		mapper.storeProductInsert(spVO);

//		@Insert("INSERT INTO product_combo(combo_id, product_id, item_id, is_upgrade, upgrade_price, item_quantity) "
//			  + "VALUES(seq_combo_id.nextval, #{product_id}, #{item_id}, #{is_upgrade}, #{upgrade_price}, #{item_quantity})")
//		ProductComboVO pcVO = new ProductComboVO(combo_id, product_id, item_id, is_upgrade, upgrade_price, dto.quantity)
		ProductComboVO pcVO = new ProductComboVO();
		pcVO.setProduct_id(spVO.getProduct_id());
		
//		mapper.productComboInsert(pcVO);
		return null;
	}

}
