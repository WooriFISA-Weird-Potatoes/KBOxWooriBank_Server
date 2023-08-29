package com.woorifisa.kboxwoori.domain.crawling.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TeamLogo {
    LG("https://ssl.gstatic.com/onebox/media/sports/logos/3NqgO_dpTThWu3KBf600tg_48x48.png"),
    KT("https://ssl.gstatic.com/onebox/media/sports/logos/LUZj3ojt_H6lYisolvQ2pg_48x48.png"),
    SSG("https://ssl.gstatic.com/onebox/media/sports/logos/171JeGI-4meYHLIoUPjerQ_48x48.png"),
    NC("https://ssl.gstatic.com/onebox/media/sports/logos/dDCbStDchWQktsZf2swYyA_48x48.png"),
    두산("https://ssl.gstatic.com/onebox/media/sports/logos/AP_sE5nmR8ckhs_zEhDzEg_48x48.png"),
    KIA("https://ssl.gstatic.com/onebox/media/sports/logos/psd7z7tnBo7SD8f_Fxs-yg_48x48.png"),
    롯데("https://ssl.gstatic.com/onebox/media/sports/logos/cGrvIuBYzj4D6KFLPV1MBg_48x48.png"),
    삼성("https://ssl.gstatic.com/onebox/media/sports/logos/c_Jn4jW-NOwRtnGE7uQRAA_48x48.png"),
    한화("https://ssl.gstatic.com/onebox/media/sports/logos/pq5JUk7H0b6KX5Wi8M0xbA_48x48.png"),
    키움("https://ssl.gstatic.com/onebox/media/sports/logos/BXbvDpPIJZ_HpPL4qikxNg_48x48.png"),
    없음("");

    private final String teamLogoUrl;

    public static TeamLogo fromString(String text) {
        if (text == null || text.trim().isEmpty()) {
            return 없음; // 또는 원하는 enum 상수 반환
        }

        for (TeamLogo logo : TeamLogo.values()) {
            if (logo.name().equalsIgnoreCase(text)) {
                return logo;
            }
        }
        throw new IllegalArgumentException("No enum constant " + TeamLogo.class.getName() + "." + text);
    }

}
