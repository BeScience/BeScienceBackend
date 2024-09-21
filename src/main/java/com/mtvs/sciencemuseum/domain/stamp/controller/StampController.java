package com.mtvs.sciencemuseum.domain.stamp.controller;

import com.mtvs.sciencemuseum.domain.stamp.dto.StampDTO;
import com.mtvs.sciencemuseum.domain.stamp.entity.Stamp;
import com.mtvs.sciencemuseum.domain.stamp.repository.StampRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stamp")
public class StampController {

    @Autowired
    private StampRepository stampRepository;

    @PostMapping("/submit")
    public String submitStamp(@RequestBody StampDTO stampDTO) {
        // DTO를 엔티티로 변환 후 저장
        Stamp stamp = stampDTO.toEntity();
        stampRepository.save(stamp);

        return "스탬프 정보가 성공적으로 저장되었습니다.";
    }
}

