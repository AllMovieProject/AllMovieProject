package com.sist.web.vo;

import java.util.Date;

import lombok.Data;

@Data
public class MemberVO {
	private int enabled;
	private String userid, username, userpwd, sex, birthday, email, post, addr1, addr2, phone, phone1, phone2, content,
			dbday;
	private Date regdate;
}
