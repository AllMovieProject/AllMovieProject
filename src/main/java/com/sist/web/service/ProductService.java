package com.sist.web.service;

import java.util.List;

import com.sist.web.dto.ProductFormDTO;
import com.sist.web.vo.ProductCategoryVO;
import com.sist.web.vo.ProductItemVO;

public interface ProductService {
	
	public List<ProductCategoryVO> productCategoryList();
	
	public List<ProductItemVO> productItemList(int category_id, boolean isBase);
	
	public String productInsert(ProductFormDTO dto);

}
