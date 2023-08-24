package com.woorifisa.kboxwoori.domain.user.service;

import com.woorifisa.kboxwoori.domain.user.dto.UserDTO;
import com.woorifisa.kboxwoori.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    public Long join(UserDTO userDto) {
        userDto.setPassword(encoder.encode(userDto.getPassword()));
        return userRepository.save(userDto.toEntity()).getId();
    }


}
