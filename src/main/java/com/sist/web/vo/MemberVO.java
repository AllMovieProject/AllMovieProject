package com.sist.web.vo;

import java.util.Date;

import lombok.Data;

@Data
public class MemberVO {
    private Date regdate;
	private int enabled;
	private String userid, username, userpwd, email, phone, sex, post, addr1, addr2, image, dbday;
}
