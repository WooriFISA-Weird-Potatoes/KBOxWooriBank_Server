package com.woorifisa.kboxwoori.domain.user.service;

import com.woorifisa.kboxwoori.domain.user.dto.UpdateUserResponseDTO;
import com.woorifisa.kboxwoori.domain.user.dto.UserDTO;
import com.woorifisa.kboxwoori.domain.user.dto.UserSessionDTO;
import com.woorifisa.kboxwoori.domain.user.entity.User;
import com.woorifisa.kboxwoori.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;


    public Long join(UserDTO userDto) {
        userDto.setPassword(encoder.encode(userDto.getPassword()));
        return userRepository.save(userDto.toEntity()).getId();
    }

    public boolean deleteUser(String userId) {
        Optional<User> user = userRepository.findByUserId(userId);

        user.ifPresent(selectUser ->{
            userRepository.deleteByUserId(userId);
        });
        return true;
    }

    public UpdateUserResponseDTO findUser(String userId) {
        User user = userRepository.findByUserId(userId).get();
        UpdateUserResponseDTO updateUserResponseDTO = new UpdateUserResponseDTO(user);

        if(user != null){
            return updateUserResponseDTO;
        }
        return null;
    }

    @Transactional
    public UpdateUserResponseDTO updateUser(String userId, UpdateUserResponseDTO responseDTO){
        User user = userRepository.findByUserId(userId).get();
        user.updateUser(responseDTO.getUserId(),
                        encoder.encode(responseDTO.getPassword()),
                        responseDTO.getName(),
                        responseDTO.getGender(),
                        responseDTO.getBirth(),
                        responseDTO.getPhone(),
                        responseDTO.getAddr(),
                        responseDTO.getClub());

        return responseDTO;
    }





}
