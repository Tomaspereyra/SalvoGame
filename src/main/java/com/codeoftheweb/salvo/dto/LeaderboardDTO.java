package com.codeoftheweb.salvo.dto;

import com.codeoftheweb.salvo.model.Player;

import java.util.List;

public class LeaderboardDTO {
    private Double totalScore;
    private Integer totalWins;
    private Integer totalLosses;
    private Integer totalTies;

    public Double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getTotalWins() {
        return totalWins;
    }

    public void setTotalWins(Integer totalWins) {
        this.totalWins = totalWins;
    }

    public Integer getTotalLosses() {
        return totalLosses;
    }

    public void setTotalLosses(Integer totalLosses) {
        this.totalLosses = totalLosses;
    }

    public Integer getTotalTies() {
        return totalTies;
    }

    public void setTotalTies(Integer totalTies) {
        this.totalTies = totalTies;
    }
}
