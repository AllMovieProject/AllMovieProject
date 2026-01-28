package com.sist.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.sist.web.vo.BookingVO;
import com.sist.web.vo.MemberVO;
import com.sist.web.vo.SeatVO;

@Mapper
@Repository
public interface MemberMapper {
	@Select("SELECT COUNT(*) FROM member WHERE userid = #{userid}")
	public int memberIdCheck(String userid);
	
	@Insert("INSERT INTO member(userid, username, userpwd, email, phone, sex, post, addr1, addr2) "
		  + "VALUES(#{userid}, #{username}, #{userpwd}, #{email}, #{phone}, #{sex}, #{post}, #{addr1}, #{addr2})")
	public void memberInsert(MemberVO vo);
	
	@Insert("INSERT INTO authority VALUES(#{userid}, 'ROLE_USER')")
	public void memberAuthorityInsert(String userid);

	@Select("SELECT * FROM member "
		  + "WHERE userid = #{userid}")
	public MemberVO memberInfoData(String userid);
	
	// 마이페이지
	public List<BookingVO> bookingListData(String id);
	
	public String bookingStartTime(String booking_id);
	
	@Update("UPDATE booking SET cancel_flag = 1 WHERE booking_id = #{booking_id}")
	public void bookingCancel(String booking_id);
}
