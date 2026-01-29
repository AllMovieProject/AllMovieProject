package com.sist.web.restcontroller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sist.web.service.MainService;
import com.sist.web.vo.MovieVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/main")
public class MainRestController {
	
	private final MainService mService;
	
	@GetMapping("/list_vue")
	public ResponseEntity<Map<String, List<MovieVO>>> mainList() {
		Map<String, List<MovieVO>> map = null;
		try {
//			map = mService.homeListData();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

}
