package com.sist.web.vo;
/*
 * 	GNO			NUMBER
	GTYPE		NUMBER
	GSUBJECT	VARCHAR2(2000 BYTE)
	GCONTENT	CLOB
	GRTYPE		NUMBER
	GREGDATE	DATE
 */
import java.util.*;

import lombok.Data;
@Data
public class GroupVisitVO {
	private int gno, gtype, grtype;
	private String gsubject, gcontent;
	private Date gregdate;
}
