package com.woorifisa.kboxwoori.domain.user.dto;

import com.woorifisa.kboxwoori.domain.user.entity.Club;
import com.woorifisa.kboxwoori.domain.user.entity.Gender;
import com.woorifisa.kboxwoori.domain.user.entity.User;
import lombok.*;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    @NotNull(message = "아이디를 입력해주세요.")
    private String userId;

    @NotNull(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotNull(message = "이름을 입력해주세요.")
    private String name;

    @NotNull(message = "성별을 선택해주세요.")
    private Gender gender;

    @NotNull(message = "생년월일을 입력해주세요.")
    private LocalDate birth;

    @NotNull(message = "휴대폰 번호를 입력해주세요.")
    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message = "10 ~ 11 자리의 숫자만 입력 가능합니다.")
    private String phone;

    @NotNull(message = "주소를 입력해주세요.")
    private String addr;

    @NotNull(message = "구단을 선택해주세요.")
    private Club club;

    @AssertTrue(message = "서비스이용약관에 동의해야 합니다.")
    private Boolean svcAgmt;

    @AssertTrue(message = "개인정보처리방침에 동의해야 합니다.")
    private Boolean infoAgmt;


    //DTO -> Entity
    public User toEntity() {
        User user;
        user = User.builder()
                .admin(false)
                .userId(userId)
                .password(password)
                .name(name)
                .gender(gender)
                .birth(birth)
                .phone(phone)
                .addr(addr)
                .club(club)
                .point(0)
                .svcAgmt(svcAgmt)
                .infoAgmt(infoAgmt)
                .wooriLinked(false)
                .build();
        return user;
    }
}
