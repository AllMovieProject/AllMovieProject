package com.sist.web.vo;

import lombok.Data;

@Data
public class MovieVO {
	private int movie_id, runtime, likeCount, reviewCount;
	private String title, title_eng, director, actor, nation, company, prod_year, plot, kmdb_url,
		rating, genre, release_date, keywords, poster_url, still_url, movie_type;
	// type 2D(자막) 이런 식으로 디비에 추가
}
