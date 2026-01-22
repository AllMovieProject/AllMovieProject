package com.sist.web.vo;
/*
 * 	GNO			NUMBER
	GTYPE		NUMBER
	GSUBJECT	VARCHAR2(2000 BYTE)
	GCONTENT	CLOB
	GRTYPE		NUMBER
	GREGDATE	DATE
	ID			VARCHAR2(20 BYTE)
	GHIT		NUMBER
 */
import java.util.*;

import lombok.Data;
@Data
public class GroupVisitVO {
	private int gno, gtype, grtype, ghit;
	private String gsubject, gcontent, id, gtype_name, gdbday;
	private Date gregdate;
}
