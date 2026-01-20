package com.sist.web.mapper;
import java.util.*;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import com.sist.web.vo.HelpDeskVO;
/*
 * 	HNO			NUMBER
	HCATE1		NUMBER
	HCATE2		NUMBER
	HMTYPE		NUMBER
	HSUBJECT	VARCHAR2(2000 BYTE)
	HCONTENT	CLOB
	HRTYPE		NUMBER
	HREGDATE	DATE
	ID			VARCHAR2(20 BYTE)
	HHIT		NUMBER
 */
@Mapper
public interface HelpdeskMapper {
	@Select("SELECT h.hno, h.hcate1, h.hcate2, h.hmtype, c.cate_name as hcateName, h.hhit, h.id, h.hsubject, h.hcontent, "
			+ "h.hrtype, TO_CHAR(h.hregdate, 'yyyy-mm-dd HH24:MI:SS') as hdbday "
			+ "FROM helpdesk h "
			+ "JOIN commons_category c "
			+ "ON h.hcate1 = c.cate_no "
			+ "AND c.cate_group = 'HELP1' "			
			+ "ORDER BY h.hno DESC "
			+ "OFFSET #{start} ROWS FETCH NEXT 12 ROWS ONLY")
	public List<HelpDeskVO> helpDeskListData(int start);
	
	@Select("SELECT CEIL(COUNT(*)/12.0) FROM helpdesk")
	public int helpDeskTotalPage();
}
