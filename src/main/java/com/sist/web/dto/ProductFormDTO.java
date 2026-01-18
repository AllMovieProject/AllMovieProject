package com.sist.web.dto;

import com.sist.web.vo.ProductCategoryVO;
import com.sist.web.vo.ProductComboVO;
import com.sist.web.vo.ProductItemCategoryVO;
import com.sist.web.vo.ProductItemVO;
import com.sist.web.vo.StoreProductVO;

import lombok.Data;

@Data
public class ProductFormDTO {
	
	private ProductCategoryVO categoryVO;
	private ProductItemVO itemVO;
	private ProductItemCategoryVO itemCategoryVO;
	private ProductComboVO comboVO;
	private StoreProductVO productVO;

}