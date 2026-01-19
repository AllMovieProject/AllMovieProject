package com.sist.web.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sist.web.dto.ProductFormDTO;
import com.sist.web.mapper.ProductMapper;
import com.sist.web.vo.ProductCategoryVO;
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
	public List<ProductCategoryVO> productCategoryList() {
		return mapper.productCategoryList();
	}

	@Override
	public List<ProductItemVO> productItemList(int category_id, boolean isBase) {
		List<ProductItemVO> list = null;
		list = mapper.productItemList(category_id, isBase);
		return list;
	}

	@Override
	public String productInsert(ProductFormDTO dto) {
		if (dto.getStoreProduct().getIs_combo().equals("N")) {
			// TODO 단품
			/*
			 * isBase:true
			 * productItem:
				 * add_price:0
				 * base_item_id:"0"
				 * item_id:null
				 * item_name:"단순팝콘"
				 * item_price:5500
				 * item_size:"M"
			 * productItemCategory:
			 	* category_id:1
			 * storeProduct:
				 * is_combo:"N"
				 * product_desc:"단순팝콘(M)"
				 * product_image:"/img/popcorn.png"
				 * product_name:"단순팝콘(M)"
				 * product_price:5500
			 */
			ProductItemVO itemVO = dto.getProductItem();
			ProductItemCategoryVO categoryVO = dto.getProductItemCategory();
			StoreProductVO productVO = dto.getStoreProduct();
		
			mapper.productItemInsert(itemVO);
			int item_id = itemVO.getItem_id();
			categoryVO.setItem_id(item_id);
			mapper.productItemCategoryInsert(categoryVO);
			productVO.setItem_id(item_id);
			mapper.storeProductInsert(productVO);
		} else {
			// TODO 콤보
			//mapper.productComboInsert(pcVO);
		}
		return "yes";
	}

}
