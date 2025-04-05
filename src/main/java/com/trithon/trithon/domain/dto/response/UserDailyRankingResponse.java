package com.trithon.trithon.domain.dto.response;

import lombok.Getter;

@Getter
public class UserDailyRankingResponse {
    private String userId;
    private String userName;
    private int score;
    private int rank;

    public UserDailyRankingResponse(String userId, String userName, int score, int rank) {
        this.userId = userId;
        this.userName = userName;
        this.score = score;
        this.rank = rank;
    }
}
