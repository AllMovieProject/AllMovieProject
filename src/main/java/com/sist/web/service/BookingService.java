package com.sist.web.service;

import java.util.Map;

import com.sist.web.dto.BookingRequestDTO;

public interface BookingService {
    
    public Map<String, Object> bookingListData(BookingRequestDTO dto);

}
