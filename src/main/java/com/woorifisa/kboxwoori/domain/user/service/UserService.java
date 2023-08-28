package com.woorifisa.kboxwoori.domain.user.service;

import com.woorifisa.kboxwoori.domain.event.exception.WooriLinkRequiredException;
import com.woorifisa.kboxwoori.domain.user.dto.UserInfoResponseDTO;
import com.woorifisa.kboxwoori.domain.user.dto.UserDTO;
import com.woorifisa.kboxwoori.domain.user.dto.UserPointResponseDTO;
import com.woorifisa.kboxwoori.domain.user.entity.User;
import com.woorifisa.kboxwoori.domain.user.exception.AccountNotFoundException;
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
    @Transactional
    public UserInfoResponseDTO findUser(String userId) {
        User user = userRepository.findByUserId(userId).get();
        UserInfoResponseDTO updateUserResponseDTO = new UserInfoResponseDTO(user);

        if(user != null){
            return updateUserResponseDTO;
        }
        return null;
    }

    @Transactional
    public UserInfoResponseDTO updateUserInfo(String userId, UserInfoResponseDTO responseDTO){
        User user = userRepository.findByUserId(userId).get();
        responseDTO.setPassword(encoder.encode(responseDTO.getPassword()));
        user.updateUser(responseDTO);
        return responseDTO;
    }

    @Transactional
    public UserPointResponseDTO getUserPoint(String userId){
        User user = userRepository.findPointByUserId(userId).get();

        UserPointResponseDTO userPointResponseDTO = new UserPointResponseDTO();
        userPointResponseDTO.setPoint(user.getPoint());

        return userPointResponseDTO;

    }

    public Boolean IsWooriLinked(String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(() -> AccountNotFoundException.EXCEPTION);
        if (!user.getWooriLinked()) {
            throw WooriLinkRequiredException.EXCEPTION;
        }
        return true;
    }

}
