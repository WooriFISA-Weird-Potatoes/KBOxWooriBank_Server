package com.woorifisa.kboxwoori.global.config.security;

import com.woorifisa.kboxwoori.domain.user.entity.User;
import com.woorifisa.kboxwoori.domain.user.exception.AccountNotFoundException;
import com.woorifisa.kboxwoori.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(username).orElseThrow(() -> AccountNotFoundException.EXCEPTION);
        return new CustomUserDetails(user);
    }
}
