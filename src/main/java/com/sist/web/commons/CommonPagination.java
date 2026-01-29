package com.sist.web.commons;

import com.sist.web.dto.ListDTO;

public class CommonPagination {
	
	private static final int ROW_SIZE = 12;
	private static final int BLOCK = 10;

	public static int getOffSet(int page) {
		return (page - 1) * ROW_SIZE;
	}
	
	public static void setPagination(ListDTO<?> dto) {
		int startPage = (dto.getCurpage() - 1) / BLOCK * BLOCK + 1;
		int endPage = (dto.getCurpage() - 1) / BLOCK * BLOCK + BLOCK;
		if (endPage > dto.getTotalpage())
			endPage = dto.getTotalpage();
		dto.setStartPage(startPage);
		dto.setEndPage(endPage);
	}
	
}
