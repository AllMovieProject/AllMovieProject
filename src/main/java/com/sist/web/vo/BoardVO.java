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
 */
@Data
public class BoardVO {
	private int bno, bcate, bhit;
	private String bsubject, bcontent;
	private Date bregdate;
}
