package com.sist.web.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sist.web.dto.ComboFormDTO;
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
	public String productInsert(ProductFormDTO dto) { // 단품
		ProductItemVO itemVO = dto.getProductItem();
		ProductItemCategoryVO categoryVO = dto.getProductItemCategory();
		StoreProductVO productVO = dto.getStoreProduct();
	
		mapper.productItemInsert(itemVO);
		int item_id = itemVO.getItem_id(); // selectKey에서 대입된 item_id 가져오기
		
		categoryVO.setItem_id(item_id);
		mapper.productItemCategoryInsert(categoryVO);
		if (categoryVO.getCategory_id() == 3) { // 탄산(3)은 음료(2)도 추가
			categoryVO.setCategory_id(2);
			mapper.productItemCategoryInsert(categoryVO);
		}
		System.out.println("isBase:" + dto.isBase());
		if (dto.isBase()) { // 기본 식품이 경우에만 추가
			productVO.setItem_id(item_id);
			System.out.println(productVO.getItem_id());
			mapper.storeProductInsert(productVO);
		}
		return "yes";
	}

	@Override
	public String productComboInsert(ComboFormDTO dto) { // 콤보
		StoreProductVO productVO = dto.getStoreProduct();
		List<ProductComboVO> comboListVO = dto.getProductComboList();
		
		// TODO INSERT productVO => product_id 가져오기
		// TODO INSERT comboListVO => product_id 넣어서
		return "yes";
	}
	
	@Override
	public List<StoreProductVO> storeProductListData(int category_id) {
		return mapper.storeProductListData(category_id);
	}

}
