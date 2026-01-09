package com.sist.web.service;

import org.springframework.stereotype.Service;

import com.sist.web.mapper.MemberMapper;
import com.sist.web.vo.MemberVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	private final MemberMapper mapper;
	
	@Override
	public int memberIdCheck(String userid) {
		return mapper.memberIdCheck(userid);
	}

	@Override
	public void memberInsert(MemberVO vo) {
		mapper.memberInsert(vo);
	}

	@Override
	public void memberAuthorityInsert(String userid) {
		mapper.memberAuthorityInsert(userid);
	}

	@Override
	public MemberVO memberInfoData(String userid) {
		return mapper.memberInfoData(userid);
	}

}
