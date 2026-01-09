package com.sist.web.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.sist.web.vo.MemberVO;

@Mapper
@Repository
public interface MemberMapper {
	@Select("SELECT COUNT(*) FROM member "
		  + "WHERE userid = #{userid}")
	public int memberIdCheck(String userid);
	
	@Insert("INSERT INTO member(userid, username, userpwd, "
		  + "sex, birthday, email, post, addr1, addr2, phone, content) "
		  + "VALUES(#{userid}, #{username}, #{userpwd}, #{sex}, "
		  + "#{birthday}, #{email}, #{post}, #{addr1}, #{addr2}, "
		  + "#{phone}, #{content})")
	public void memberInsert(MemberVO vo);
	
	@Insert("INSERT INTO authority VALUES(#{userid}, 'ROLE_USER')")
	public void memberAuthorityInsert(String userid);

	@Select("SELECT * FROM member "
		  + "WHERE userid = #{userid}")
	public MemberVO memberInfoData(String userid);
}
