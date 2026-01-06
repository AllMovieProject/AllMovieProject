package com.sist.web.vo;

import lombok.Data;

/*
 * 	CATE_NO		NUMBER
	CATE_GROUP	VARCHAR2(50 BYTE)
	CATE_NAME	VARCHAR2(50 BYTE)
 */
@Data
public class Commons_categoryVO {
	private int cate_no;
	private String cate_group, cate_name;
}
