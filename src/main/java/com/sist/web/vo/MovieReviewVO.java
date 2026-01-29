package com.sist.web.vo;

import java.util.Date;

import lombok.Data;

@Data
public class MovieReviewVO {
	
    private int mrno, movie_id, mrating, mr_recom;
    private String id, content, dbday;
    private Date mrdate;
    
}