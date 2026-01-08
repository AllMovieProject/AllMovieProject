package com.sist.web.mapper;

import java.util.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.sist.web.vo.BoardVO;

@Mapper
@Repository
public interface BoardMapper {	
	@Select("SELECT bno, bcate, bhit, bsubject. bcontent, TO_CHAR(bregdate, 'yyyy-mm-dd HH24:MI:SS') as dbday "
			+ "FROM board "
			+ "WHERE bno=#{bno}"
			+ "ORDER BY bno DESC"
			+ "OFFSET #{start} ROWS FETCH NEXT 12 ROWS ONLY")
	public List<BoardVO> boradListData(int start);
	
	@Select("SELECT CEIL(COUNT(*)/10.0) FROM board")
	public int boardTotalPage();
}
