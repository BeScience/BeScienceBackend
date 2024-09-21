package com.mtvs.sciencemuseum.domain.stamp.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stamp")
public class Stamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;
    private String enter;
    private String activity;
    private LocalDateTime startAt;
    private LocalDateTime endAt;

    // 기본 생성자
    public Stamp() {}

    // 모든 필드에 대한 getter와 setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public void setStartAt(LocalDateTime startAt) {
        this.startAt = startAt;
    }

    public LocalDateTime getEndAt() {
        return endAt;
    }

    public void setEndAt(LocalDateTime endAt) {
        this.endAt = endAt;
    }
}

