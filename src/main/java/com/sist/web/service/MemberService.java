package com.sist.web.service;

import java.util.List;

import com.sist.web.dto.BookingCancelDTO;
import com.sist.web.vo.BookingVO;
import com.sist.web.vo.MemberVO;

public interface MemberService {
	public int memberIdCheck(String userid);
	public void memberInsert(MemberVO vo);
	public void memberAuthorityInsert(String userid);
	public MemberVO memberInfoData(String userid);
	
	// 마이페이지
	public List<BookingVO> bookingListData(String id);
	public String bookingCancel(BookingCancelDTO dto);
}
