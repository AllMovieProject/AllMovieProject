package com.sist.web.vo;

import lombok.Data;

@Data
public class MovieVO {
	private int movie_id, runtime;
	private String title, director, actor, nation, company, prod_year, plot,
		rating, genre, release_date, keywords, poster_url, still_url;
}
