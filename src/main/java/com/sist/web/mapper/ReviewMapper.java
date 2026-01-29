package com.sist.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.sist.web.vo.MovieReviewVO;

@Mapper
public interface ReviewMapper {
	
	@Select("SELECT mrno, movie_id, mrating, id, content, "
	      + "TO_CHAR(mrdate, 'YYYY-MM-DD HH24:MI:SS') as dbday, mr_recom "
	      + "FROM movie_review "
	      + "WHERE movie_id = #{movie_id} "
	      + "ORDER BY mrno DESC")
	public List<MovieReviewVO> reviewListData(int movie_id);
	
	@Insert("INSERT INTO movie_review(mrno, movie_id, mrating, id, content) "
		  + "VALUES(seq_mr_no.nextval, #{movie_id}, #{mrating}, #{id}, #{content})")
	public void reviewInsert(MovieReviewVO vo);

}