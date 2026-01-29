package com.sist.web.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sist.web.mapper.MemberMapper;
import com.sist.web.vo.BookingVO;
import com.sist.web.vo.MemberVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	private final MemberMapper mapper;
	
	@Override
	public int memberIdCheck(String userid) {
		return mapper.memberIdCheck(userid);
	}

	@Override
	public void memberInsert(MemberVO vo) {
		mapper.memberInsert(vo);
	}

	@Override
	public void memberAuthorityInsert(String userid) {
		mapper.memberAuthorityInsert(userid);
	}

	@Override
	public MemberVO memberInfoData(String userid) {
		return mapper.memberInfoData(userid);
	}

	@Override
	public List<BookingVO> bookingListData(String id) {
		return mapper.bookingListData(id);
	}

	@Override
	public String bookingCancel(String booking_id) {
		String res = "error";
		
		LocalDateTime now = LocalDateTime.now();
		String formattedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		String bookingDate = mapper.bookingStartTime(booking_id);
		
		 String[] bookingDateInfo = bookingDate.split(" ");
		 String[] nowDateInfo = formattedNow.split(" ");
		
		if (formattedNow.equals(bookingDateInfo[0])) {
			String bookingTime = bookingDateInfo[1];
			String[] bookingTimeInfo = bookingTime.split(":");
			
			String nowTime = nowDateInfo[1];
			String[] nowTimeInfo = nowTime.split(":");
			
			if (bookingTimeInfo[0].equals(nowTimeInfo[0])) {
				int bookingMin = Integer.parseInt(bookingTimeInfo[1]);
				int nowMin = Integer.parseInt(nowTimeInfo[1]);
				
				if (bookingMin - nowMin < 20) {
					res = "cant";
				}
			}
		}
		
		res = "can";
		
		mapper.bookingCancel(booking_id);
		// 좌석 정보 가져와서 available_flag 0으로 변경
		
		return res;
	}

}
