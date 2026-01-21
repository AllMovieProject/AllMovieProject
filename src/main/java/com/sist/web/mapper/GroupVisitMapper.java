package com.sist.web.mapper;
import java.util.*;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
/*
 * 	GNO			NUMBER
	GTYPE		NUMBER
	GSUBJECT	VARCHAR2(2000 BYTE)
	GCONTENT	CLOB
	GRTYPE		NUMBER
	GREGDATE	DATE
	ID			VARCHAR2(20 BYTE)
	GHIT		NUMBER
 */
import com.sist.web.vo.GroupVisitVO;
@Mapper
public interface GroupVisitMapper {
	@Select("SELECT g.gno, g.gtype, c.cate_name AS gtype_name, g.gsubject, g.grtype, g.gregdate "
			+ "FROM GroupVisit g "
			+ "JOIN commons_category c ON g.gtype = c.cate_no AND c.cate_group = 'GROUPVISIT' "
			+ "ORDER BY g.gno DESC "
			+ "OFFSET #{start} ROWS FETCH NEXT 12 ROWS ONLY")
	public List<GroupVisitVO> GroupVisitListData(int start);
	
	@Select("SELECT CEIL(COUNT(*) / 12) "
			+ "FROM GroupVisit g "
			+ "JOIN commons_category c ON g.gtype = c.cate_no AND c.cate_group = 'GROUPVISIT'")
	public int GroupVisitTotalPage();
}
