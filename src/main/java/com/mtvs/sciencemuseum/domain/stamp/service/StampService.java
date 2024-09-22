package com.mtvs.sciencemuseum.domain.stamp.service;

import com.mtvs.sciencemuseum.domain.stamp.dto.StampDTO;
import com.mtvs.sciencemuseum.domain.stamp.entity.Stamp;
import com.mtvs.sciencemuseum.domain.stamp.repository.StampRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StampService {

    @Autowired
    private StampRepository stampRepository;

    public String saveStamp(StampDTO stampDTO) {
        Stamp stamp = stampDTO.toEntity();
        stampRepository.save(stamp);
        return "스탬프 정보가 성공적으로 저장되었습니다.";
    }

    public List<StampDTO> getAllStamps() {
        List<Stamp> stamps = stampRepository.findAll();
        return stamps.stream().map(stamp -> {
            StampDTO stampDTO = new StampDTO();
            stampDTO.setNickname(stamp.getNickname());
            stampDTO.setEnter(stamp.getEnter());
            stampDTO.setActivity(stamp.getActivity());
            stampDTO.setStartAt(stamp.getStartAt().toString());
            stampDTO.setEndAt(stamp.getEndAt().toString());
            return stampDTO;
        }).collect(Collectors.toList());
    }
}


