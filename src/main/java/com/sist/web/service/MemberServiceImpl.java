package com.sist.web.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sist.web.dto.BookingCancelDTO;
import com.sist.web.mapper.MemberMapper;
import com.sist.web.mapper.SeatMapper;
import com.sist.web.vo.BookingVO;
import com.sist.web.vo.MemberVO;
import com.sist.web.vo.ScheduleSeatVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
<<<<<<< HEAD
	
	private final MemberMapper mapper;
	
	@Override
	public int memberIdCheck(String userid) {
		return mapper.memberIdCheck(userid);
	}
=======
    private final MemberMapper bMapper;
    private final SeatMapper sMapper;
>>>>>>> 1/29

    @Override
    public int memberIdCheck(String userid) {
        return bMapper.memberIdCheck(userid);
    }

    @Override
    public void memberInsert(MemberVO vo) {
        bMapper.memberInsert(vo);
    }

    @Override
    public void memberAuthorityInsert(String userid) {
        bMapper.memberAuthorityInsert(userid);
    }

    @Override
    public MemberVO memberInfoData(String userid) {
        return bMapper.memberInfoData(userid);
    }

    @Override
    public List<BookingVO> bookingListData(String id) {
        return bMapper.bookingListData(id);
    }

    @Override
    public String bookingCancel(BookingCancelDTO dto) {
        String res = "error";
        int ScheduleId = dto.getScheduleId();
        String bookingId = dto.getBookingId();

        LocalDateTime now = LocalDateTime.now();
        String formattedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String bookingDate = bMapper.bookingStartTime(bookingId);

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
                    return res;
                }
            }
        }

        String seatIdInfo = bMapper.bookingSeatIdData(bookingId);
        String[] seatIds = seatIdInfo.split(",");
        ScheduleSeatVO vo = new ScheduleSeatVO();
        vo.setSchedule_id(ScheduleId);

        for (int i = 0; i < seatIds.length; i++) {
            vo.setSeat_id(Integer.parseInt(seatIds[i]));
            sMapper.scheduleSeatFlagDown(vo);
        }

        bMapper.bookingCancel(bookingId);
        res = "can";

        return res;
    }

}
