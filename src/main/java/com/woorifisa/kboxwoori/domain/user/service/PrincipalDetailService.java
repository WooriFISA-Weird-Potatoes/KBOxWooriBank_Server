package com.woorifisa.kboxwoori.domain.user.service;

import com.woorifisa.kboxwoori.domain.user.dto.UserSessionDto;
import com.woorifisa.kboxwoori.domain.user.entity.User;
import com.woorifisa.kboxwoori.domain.user.repository.UserRepository;
import com.woorifisa.kboxwoori.global.config.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    private final HttpSession session;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUserId(username).orElseThrow(() ->
                new UsernameNotFoundException("해당 회원이 존재하지 않습니다."));

        session.setAttribute("user", new UserSessionDto(user));

        return new PrincipalDetails(user);
    }
}
