package com.woorifisa.kboxwoori.domain.crawling.dto;

import com.woorifisa.kboxwoori.domain.crawling.entity.TodaySchedule;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TodayScheduleDto {
    private String id;
    private LocalDate date;
    private String gameTime;
    private String team1Name;
    private String team1Logo;
    private String team1Score;
    private String team1SPState;
    private String team1SPLink;
    private String team1SPName;
    private String team2Name;
    private String team2Logo;
    private String team2Score;
    private String team2SPState;
    private String team2SPLink;
    private String team2SPName;

    public TodaySchedule toEntity() {
        return TodaySchedule.builder()
                            .id(this.id)
                            .date(this.date)
                            .gameTime(this.gameTime)
                            .team1Name(this.team1Name)
                            .team1Logo(this.team1Logo)
                            .team1Score(this.team1Score)
                            .team1SPState(this.team1SPState)
                            .team1SPLink(this.team1SPLink)
                            .team1SPName(this.team1SPName)
                            .team2Name(this.team2Name)
                            .team2Logo(this.team2Logo)
                            .team2Score(this.team2Score)
                            .team2SPState(this.team2SPState)
                            .team2SPLink(this.team2SPLink)
                            .team2SPName(this.team2SPName)
                            .build();
    }
}
