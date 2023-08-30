package com.woorifisa.kboxwoori.domain.admin.dto;

import java.time.LocalDate;

public class UserResponseDto {
    private Integer totalUsers;
    private LocalDate createdAt;

    public UserResponseDto(Integer totalUsers, LocalDate createdAt) {
        this.totalUsers = totalUsers;
        this.createdAt = createdAt;
    }
    public Integer getTotalUsers() {
        return totalUsers;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }
}
