package com.sist.web.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.sist.web.vo.MovieVO;

@Mapper
public interface MovieMapper {
	
	@Select("SELECT movie_id, runtime, likeCount, reviewCount, title, director, actor, nation, company, prod_year, plot, "
		  + "kmdb_url, rating, genre, release_date, keywords, poster_url, still_url, movie_type "
		  + "FROM movie "
		  + "WHERE movie_id = #{movieId}")
	public MovieVO movieDetailData(int movieId);

}
