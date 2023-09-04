package com.woorifisa.kboxwoori.domain.crawling.entity;

import com.woorifisa.kboxwoori.domain.crawling.dto.ScheduleDto;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@Getter
@ToString
@NoArgsConstructor
@RedisHash("crawling:schedule")
public class Schedule {
    @Id
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

    @Builder
    public Schedule(String id, String date, String time, String team1, String team1Logo, String team2, String team2Logo, String score, String teamInfo, String stadium, String remarks) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.team1 = team1;
        this.team1Logo = team1Logo;
        this.team2 = team2;
        this.team2Logo = team2Logo;
        this.score = score;
        this.teamInfo = teamInfo;
        this.stadium = stadium;
        this.remarks = remarks;
    }

    public ScheduleDto toResponseDTO() {
        return ScheduleDto.builder()
                          .id(this.id)
                          .date(this.date)
                          .time(this.time)
                          .team1(this.team1)
                          .team1Logo(this.team1Logo)
                          .team2(this.team2)
                          .team2Logo(team2Logo)
                          .score(this.score)
                          .teamInfo(this.teamInfo)
                          .stadium(this.stadium)
                          .remarks(this.remarks)
                          .build();
    }
}
