package com.woorifisa.kboxwoori.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Club {
    KIA("KIA", "KIA 타이거즈", "Kia Tigers"),
    KT("KT", "KT 위즈", "KT Wiz"),
    LOTTE("롯데", "롯데 자이언츠", "Lotte Giants"),
    LG("LG", "LG 트윈스", "LG Twins"),
    NC("NC", "NC 다이노스", "NC Dinos"),
    SSG("SSG", "SSG 랜더스", "SSG Landers"),
    DOOSAN("두산", "두산 베어스", "Doosan Bears"),
    KIWOOM("키움", "키움 히어로즈", "Kiwoom Heroes"),
    SAMSUNG("삼성", "삼성 라이온즈", "Samsung Lions"),
    HANWHA("한화", "한화 이글스", "Hanwha Eagles");

    private final String shortName;
    private final String koreanName;
    private final String englishName;
}