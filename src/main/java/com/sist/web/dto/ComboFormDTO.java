package com.sist.web.dto;

import java.util.List;

import com.sist.web.vo.ProductComboVO;
import com.sist.web.vo.StoreProductVO;

import lombok.Data;

@Data
public class ComboFormDTO {
	
	private List<ProductComboVO> productComboList;
	private StoreProductVO storeProduct;
	
}
