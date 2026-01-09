package com.sist.web.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.sist.web.service.MemberService;
import com.sist.web.vo.MemberVO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final MemberService mService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        MemberVO vo = mService.memberInfoData(authentication.getName());
        
        HttpSession session = request.getSession();
        session.setAttribute("userid", vo.getUserid());
        session.setAttribute("username", vo.getUsername());
        session.setAttribute("sex", vo.getSex());
        session.setAttribute("address", vo.getAddr1());
        session.setAttribute("email", vo.getEmail());
        
        response.sendRedirect("/");
    }

}
