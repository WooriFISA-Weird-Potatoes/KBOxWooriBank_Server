package com.woorifisa.kboxwoori.domain.user.repository;

import com.woorifisa.kboxwoori.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userID);
    Optional<User> deleteByUserId(String UserID);

}