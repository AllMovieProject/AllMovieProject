package com.sist.web.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.sist.web.dto.ComboFormDTO;
import com.sist.web.dto.ProductFormDTO;
import com.sist.web.vo.ProductCategoryVO;
import com.sist.web.vo.ProductItemVO;
import com.sist.web.vo.StoreProductVO;
import com.sist.web.vo.StoreStockVO;

public interface ProductService {
	
	public List<ProductCategoryVO> productCategoryList();
	
	public List<ProductItemVO> productItemList(int category_id, boolean isBase);
	
	public String productInsert(ProductFormDTO dto, MultipartFile productImageFile) throws IOException;
	
	public String productComboInsert(ComboFormDTO dto, MultipartFile productImageFile) throws IOException;
	
	public List<StoreProductVO> storeProductListData(int category_id);
	
	public List<StoreStockVO> storeStockListData(String userid);

}
