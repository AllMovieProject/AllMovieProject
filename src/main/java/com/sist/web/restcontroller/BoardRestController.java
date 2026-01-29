package com.sist.web.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sist.web.service.BoardService;
import com.sist.web.vo.BoardVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardRestController {
	
	private final BoardService bService;
	
	@GetMapping("/list_vue/")
	public ResponseEntity<Map<String, Object>> boardList(@RequestParam("page") int page) {
		Map<String, Object> map = new HashMap<>();
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
	
	@PostMapping("/insert_vue/")
	public ResponseEntity<Map<String, Object>> boardInsert(@RequestBody BoardVO vo) {
	   Map<String, Object> map = new HashMap<>();
	   try {
		   bService.boardInsert(vo);
		   map.put("msg", "yes");
	   } catch(Exception ex) {
		   return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
	   }
	   return new ResponseEntity<>(map,HttpStatus.OK);
   }
	
	@GetMapping("/detail_vue/")
	public ResponseEntity<BoardVO> boardDetail(@RequestParam("bno") int bno) {
		BoardVO vo = new BoardVO();
		try {			   
			vo = bService.boardDetailData(bno);			   
		} catch(Exception ex) {
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(vo,HttpStatus.OK);
	}
	
}
