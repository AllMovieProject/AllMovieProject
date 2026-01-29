package com.sist.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.sist.web.vo.MovieVO;

@Mapper
public interface MovieMapper {
	
	@Select("SELECT movie_id, title, poster_url, genre, prod_year "
		  + "FROM movie "
		  + "ORDER BY movie_id "
		  + "OFFSET #{start} ROWS FETCH NEXT 12 ROWS ONLY")
	public List<MovieVO> movieListData(int start);
	
	@Select("SELECT CEIL(COUNT(*) / 12) FROM movie")
	public int movieTotalPage();
	
	@Select("SELECT movie_id, runtime, title, title_eng, director, actor, nation, company, prod_year, plot, "
		  + "kmdb_url, rating, genre, release_date, keywords, poster_url, still_url, movie_type "
		  + "FROM movie "
		  + "WHERE movie_id = #{movieId}")
	public MovieVO movieDetailData(int movieId);

}
