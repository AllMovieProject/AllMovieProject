package com.sist.web.restcontroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
			List<BoardVO> list = bService.boardListData((page-1)*12);
			int totalpage = bService.boardTotalPage();
			
			final int BLOCK = 10;
			int startPage = ((page-1)/BLOCK*BLOCK)+1;
			int endPage = ((page-1)/BLOCK*BLOCK)+BLOCK;
			
			if(endPage > totalpage)
				endPage = totalpage;
			
			map.put("list", list);
			map.put("curpage", page);
			map.put("totalpage", totalpage);
			map.put("startPage", startPage);
			map.put("endPage", endPage);
			
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@PostMapping("/board/insert_vue/")
	   public ResponseEntity<Map> board_insert_vue(@RequestBody BoardVO vo) {
		   System.out.println(vo);
		   Map map = new HashMap();
		   try {
			   bService.boardInsert(vo);
			   map.put("msg", "yes");
		   }catch(Exception ex) {
			   return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		   }
		   return new ResponseEntity<>(map,HttpStatus.OK);
	   }
	
	@GetMapping("/board/detail_vue/")
	   public ResponseEntity<BoardVO> board_detail_vue(@RequestParam("bno") int bno) {
		   BoardVO vo = new BoardVO();
		   try {			   
			   vo = bService.boardDetailData(bno);			   
		   }catch(Exception ex) {
			   return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		   }
		   return new ResponseEntity<>(vo,HttpStatus.OK);
	   }
}
