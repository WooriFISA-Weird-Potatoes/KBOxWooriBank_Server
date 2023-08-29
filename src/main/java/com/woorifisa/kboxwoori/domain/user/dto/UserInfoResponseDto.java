package com.woorifisa.kboxwoori.domain.user.dto;

import com.woorifisa.kboxwoori.domain.user.entity.Club;
import com.woorifisa.kboxwoori.domain.user.entity.Gender;
import com.woorifisa.kboxwoori.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
@Getter
@AllArgsConstructor
@Data
public class UserInfoResponseDto {
    @NotNull(message = "변경할 아이디를 입력해주세요.")
    private String userId;

    @NotNull(message = "변경할 비밀번호를 입력해주세요.")
    private String password;

    @NotNull(message = "변경할 이름을 입력해주세요.")
    private String name;

    @NotNull(message = "변경할 성별을 선택해주세요.")
    private Gender gender;

    @NotNull(message = "변경할 생년월일을 입력해주세요.")
    private LocalDate birth;

    @NotNull(message = "변경할 휴대폰 번호를 입력해주세요.")
    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message = "10 ~ 11 자리의 숫자만 입력 가능합니다.")
    private String phone;

    @NotNull(message = "변경할 주소를 입력해주세요.")
    private String addr;

    @NotNull(message = "변경할 구단을 선택해주세요.")
    private Club club;

    public UserInfoResponseDto(User user) {
        this.userId = user.getUserId();
        this.password = user.getPassword();
        this.name = user.getName();
        this.gender = user.getGender();
        this.birth = user.getBirth();
        this.phone = user.getPhone();
        this.addr = user.getAddr();
        this.club = user.getClub();
    }

}
