package com.sist.web.dto;

import java.util.List;

import lombok.Data;

@Data
public class ProductFormDTO {
	
	private int price, basePrice, addPrice, discountPrice;
	private String isCombo, productName, productDesc, image, itemName, size, isBase;
	private List<String> categories, comboItems;

}