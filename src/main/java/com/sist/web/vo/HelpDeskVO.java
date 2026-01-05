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
 */
import java.util.*;

import lombok.Data;
@Data
public class HelpDeskVO {
	private int hno, hcate1, hcate2, hmtype, hrtype;
	private String hsubject, hcontent;
	private Date hregdate;
}
