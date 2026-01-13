package com.sist.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.sist.web.vo.MovieVO;

@Mapper
public interface MainMapper {
	
	@Select("SELECT movie_id, title, genre, poster_url "
		  + "FROM movie "
		  + "WHERE rownum <= 3 "
		  + "ORDER BY movie_id")
	public List<MovieVO> carouListData();
	
	@Select("SELECT movie_id, title, genre, poster_url "
		  + "FROM movie "
		  + "WHERE rownum <= 6 "
		  + "ORDER BY movie_id")
	public List<MovieVO> trendListData();
	
	@Select("SELECT movie_id, title, genre, poster_url "
		  + "FROM movie "
		  + "WHERE rownum <= 6 "
		  + "ORDER BY movie_id")
	public List<MovieVO> popListData();
	
	@Select("SELECT movie_id, title, genre, poster_url "
		  + "FROM movie "
		  + "WHERE rownum <= 6 "
		  + "ORDER BY movie_id")
	public List<MovieVO> recentListData();
	
	@Select("SELECT movie_id, title, poster_url "
		  + "FROM movie "
		  + "WHERE rownum <= 5 "
		  + "ORDER BY movie_id")
	public List<MovieVO> topListData();

}
