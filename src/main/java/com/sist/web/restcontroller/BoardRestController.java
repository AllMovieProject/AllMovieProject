package com.sist.web.restcontroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import java.util.*;
import com.sist.web.vo.*;
import com.sist.web.service.*;
@RestController
@RequiredArgsConstructor
public class BoardRestController {
	private final BoardService bService;
	
	@GetMapping("/board/list_vue/")
	public ResponseEntity<Map> board_list_vue(@RequestParam("page") int page) {
		Map map = new HashMap<>();
		try {			
			List<BoardVO> list = bService.boradListData((page-1)*10);
			int totalpage = bService.boardTotalPage();
			map.put("list", list);
			map.put("curpage", page);
			map.put("totalpage", totalpage);
			
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
}
