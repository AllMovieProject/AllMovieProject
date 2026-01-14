package com.sist.web.service;

import org.springframework.stereotype.Service;

import com.sist.web.dto.ProductFormDTO;
import com.sist.web.mapper.ProductMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
	
	private final ProductMapper mapper;

	@Override
	public String productInsert(ProductFormDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

}
