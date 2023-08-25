package com.woorifisa.kboxwoori.domain.user.service;

import com.woorifisa.kboxwoori.domain.user.dto.UserDTO;
import com.woorifisa.kboxwoori.domain.user.entity.User;
import com.woorifisa.kboxwoori.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

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

    @Transactional
    public boolean deleteUser(String userId) {
        Optional<User> user = userRepository.findByUserId(userId);

        user.ifPresent(selectUser ->{
            userRepository.deleteByUserId(userId);
        });
        return true;
    }

}
