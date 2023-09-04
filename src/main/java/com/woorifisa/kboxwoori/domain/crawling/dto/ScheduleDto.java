package com.woorifisa.kboxwoori.domain.crawling.dto;

import com.woorifisa.kboxwoori.domain.crawling.entity.Schedule;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ScheduleDto {
    private String id;
    private String date;
    private String time;
    private String team1;
    private String team1Logo;
    private String team2;
    private String team2Logo;
    private String score;
    private String teamInfo;
    private String stadium;
    private String remarks;

    public Schedule toEntity() {
        return Schedule.builder()
                .id(id)
                .date(date)
                .time(time)
                .team1(team1)
                .team1Logo(team1Logo)
                .team2(team2)
                .team2Logo(team2Logo)
                .score(score)
                .teamInfo(teamInfo)
                .stadium(stadium)
                .remarks(remarks)
                .build();
    }
}
