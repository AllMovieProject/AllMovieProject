package com.sist.web.dto;

import com.sist.web.vo.ProductComboVO;
import com.sist.web.vo.ProductItemCategoryVO;
import com.sist.web.vo.ProductItemVO;
import com.sist.web.vo.StoreProductVO;

import lombok.Data;

@Data
public class ProductFormDTO {
	
	private ProductItemVO productItem;
	private ProductItemCategoryVO productItemCategory;
	private ProductComboVO productCombo;
	private StoreProductVO storeProduct;
	private boolean base;

}