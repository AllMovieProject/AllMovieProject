package com.sist.web.vo;
import java.util.*;
import lombok.Data;

/*
 * 	BNO			NUMBER
	BCATE		NUMBER
	BSUBJECT	VARCHAR2(2000 BYTE)
	BCONTENT	CLOB
	BREGDATE	DATE
	BHIT		NUMBER
	ID			VARCHAR2(20 BYTE)
 */
@Data
public class BoardVO {
	private int bno, bcate, bhit;
	private String id, bsubject, bcontent;
	private Date bregdate;	
}
