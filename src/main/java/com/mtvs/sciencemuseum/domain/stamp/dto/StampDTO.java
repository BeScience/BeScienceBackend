package com.mtvs.sciencemuseum.domain.stamp.dto;

import com.mtvs.sciencemuseum.domain.stamp.entity.Stamp;

import java.time.LocalDateTime;

public class StampDTO {
    private String nickname;
    private String enter;
    private String activity;
    private String startAt;
    private String endAt;

    // 기본 생성자
    public StampDTO() {}

    // 모든 필드에 대한 getter와 setter
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEnter() {
        return enter;
    }

    public void setEnter(String enter) {
        this.enter = enter;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    public String getEndAt() {
        return endAt;
    }

    public void setEndAt(String endAt) {
        this.endAt = endAt;
    }

    // DTO를 엔티티로 변환하는 메서드
    public Stamp toEntity() {
        Stamp stamp = new Stamp();
        stamp.setNickname(this.nickname);
        stamp.setEnter(this.enter);
        stamp.setActivity(this.activity);
        stamp.setStartAt(LocalDateTime.parse(this.startAt));
        stamp.setEndAt(LocalDateTime.parse(this.endAt));
        return stamp;
    }
}

