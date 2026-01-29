package com.sist.web.restcontroller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sist.web.service.ReviewService;
import com.sist.web.vo.MovieReviewVO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewRestController {
	
	private final ReviewService rService;
	
	@GetMapping("/movie/list")
	public ResponseEntity<List<MovieReviewVO>> reviewMovieList(@RequestParam("movie-id") int movie_id) {
		List<MovieReviewVO> list = null;
		try {
			list = rService.reviewListData(movie_id);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(list, HttpStatus.OK);	
	}
	
	@PostMapping("/movie/insert")
	public ResponseEntity<List<MovieReviewVO>> reviewMovieInsert(@RequestBody MovieReviewVO vo, HttpSession session) {
		List<MovieReviewVO> list = null;
		try {
			vo.setId((String) session.getAttribute("userid"));
			rService.reviewInsert(vo);
			list = rService.reviewListData(vo.getMovie_id());
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

}
