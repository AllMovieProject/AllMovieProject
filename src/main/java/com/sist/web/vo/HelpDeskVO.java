package com.sist.web.vo;
/*
 * 	HNO			NUMBER
	HCATE1		NUMBER
	HCATE2		NUMBER
	HMTYPE		NUMBER
	HSUBJECT	VARCHAR2(2000 BYTE)
	HCONTENT	CLOB
	HRTYPE		NUMBER
	HREGDATE	DATE
	ID			VARCHAR2(20 BYTE)
	HHIT		NUMBER
 */
import java.util.*;

import lombok.Data;
@Data
public class HelpDeskVO {
	private int hno, hcate1, hcate2, hmtype, hrtype, hhit;
	private String hsubject, hcontent, id, hcateName, hdbday, cateNo, cateName;
	private Date hregdate;
}
