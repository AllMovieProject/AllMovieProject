package com.sist.web.restcontroller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sist.web.service.MemberService;
import com.sist.web.vo.BookingVO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberRestController {
	private final MemberService mService;
	
	@GetMapping("/member/idCheck_vue/")
	public String member_idcheck(@RequestParam("userid") String userid) {
		int count = mService.memberIdCheck(userid);
		return String.valueOf(count);
	}
	
	// 마이페이지
	@GetMapping("/mypage/bookingListData")
	public ResponseEntity<List<BookingVO>> bookingListData(HttpSession session) {
		List<BookingVO> list = null;
		
        try {
        	String id = (String) session.getAttribute("userid");
            list = mService.bookingListData(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
	}
}
