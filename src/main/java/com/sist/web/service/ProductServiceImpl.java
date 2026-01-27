package com.sist.web.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
	
	@Value("${file.upload-dir}")
	private String uploadDir;

	public StoreProductVO saveFile(StoreProductVO vo, MultipartFile productImageFile) throws IOException {
		File dir = new File(uploadDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		String filename = "";
		boolean checked = false;
		if (productImageFile.isEmpty()) {
			checked = false; // 파일이 없는 상태
		} else {
			String oname = productImageFile.getOriginalFilename();
			File f = new File(uploadDir, oname);
			if (f.exists()) {
				int count = 1;
				String name = oname.substring(0, oname.lastIndexOf("."));
				String ext = oname.substring(oname.lastIndexOf("."));
				while (f.exists()) {
					String newname = name + " (" + count + ")" + ext;
					f = new File(uploadDir + "/" + newname);
					count++;
				}
			}
			filename = f.getName();
			checked = true; // 파일이 존재하는 상태
			
			Path path = Paths.get(uploadDir, f.getName());
			Files.copy(productImageFile.getInputStream(), path);
		}
		
		if (checked) {
			vo.setProduct_image(filename);
		} else {
			vo.setProduct_image("");
		}
		return vo;
	}
	
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
	public String productInsert(ProductFormDTO dto, MultipartFile productImageFile) throws IOException { // 단품
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
		
		saveFile(productVO, productImageFile);
		
		if (dto.isBase()) { // 기본 식품이 경우에만 추가
			productVO.setItem_id(item_id);
			System.out.println(productVO.getItem_id());
			mapper.storeProductInsert(productVO);
		}
		return "yes";
	}

	@Override
	public String productComboInsert(ComboFormDTO dto, MultipartFile productImageFile) throws IOException { // 콤보
		StoreProductVO productVO = dto.getStoreProduct();
		List<ProductComboVO> comboListVO = dto.getProductComboList();
		
		// TODO INSERT productVO => product_id 가져오기
		saveFile(productVO, productImageFile);
		mapper.storeProductInsert(productVO);
		// TODO INSERT comboListVO => product_id 넣어서
		for (ProductComboVO vo : comboListVO) {
			vo.setProduct_id(productVO.getProduct_id());
			mapper.productComboInsert(vo);
		}
		return "yes";
	}
	
	@Override
	public List<StoreProductVO> storeProductListData(int category_id) {
		return mapper.storeProductListData(category_id);
	}

	@Override
	public List<StoreProductVO> storeComboProductList() {
		return mapper.storeComboProductList();
	}
	
	@Override
	public List<ProductComboVO> productComboDetail(int product_id) {
	  return mapper.productComboDetail(product_id);
	}
	
	@Override
	public List<ProductItemVO> getUpgradeOptions(int base_item_id) {
	  return mapper.getUpgradeOptions(base_item_id);
	}

}
