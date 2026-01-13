package com.sist.web.mapper;

import java.util.*;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.sist.web.vo.BoardVO;

@Mapper
public interface BoardMapper {	
	@Select("SELECT b.bno, b.bcate, c.cate_name as bcateName, b.bhit, b.id, b.bsubject, TO_CHAR(b.bregdate, 'yyyy-mm-dd HH24:MI:SS') as bdbday "
			+ "FROM board b "
			+ "JOIN commons_category c "
			+ "ON b.bcate = c.cate_no "
			+ "AND c.cate_group = 'BOARD' "			
			+ "ORDER BY b.bno DESC "
			+ "OFFSET #{start} ROWS FETCH NEXT 12 ROWS ONLY")
	public List<BoardVO> boardListData(int start);
	
	@Select("SELECT CEIL(COUNT(*)/12.0) FROM board")
	public int boardTotalPage();
	
	// 데이터 추가 
	@SelectKey(keyProperty = "bno", resultType = int.class,
			before = true,
			statement = "SELECT NVL(MAX(bno) + 1, 1) as bno FROM board")
   
	@Insert("INSERT INTO board (bno, bcate, id, bsubject, bcontent, bregdate, bhit) "
			+"VALUES (#{bno}, #{bcate}, #{id}, #{bsubject}, #{bcontent}, SYSDATE, 0)")			
	public void boardInsert(BoardVO vo);
	   
	// 데이터 상세보기 
    @Update("UPDATE board SET "
	 	   +"bhit = bhit + 1 "
	 	   +"WHERE bno = #{bno}")
    public void boardHitIncrement(int bno);
    @Select("SELECT bno, id, bsubject, bcontent, TO_CHAR(bregdate,'yyyy-mm-dd') as bdbday, bhit "
	 	   +"FROM board "
	 	   +"WHERE bno = #{bno}")
    public BoardVO boardDetailData(int bno);
    
    
	
}
