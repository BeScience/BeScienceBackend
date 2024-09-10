package com.mtvs.sciencemuseum.domain.postit.controller;


import com.mtvs.sciencemuseum.domain.postit.dto.PostitDto;
import com.mtvs.sciencemuseum.domain.postit.entity.Postit;
import com.mtvs.sciencemuseum.domain.postit.service.PostitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/postit")
public class PostController {

    @Autowired
    private PostitService postitService;

    @PostMapping
    public PostitDto savePostit(@RequestBody PostitDto postitDto) {
        // DTO를 엔티티로 변환
        Postit postit = postitService.toEntity(postitDto);
        // 엔티티를 데이터베이스에 저장
        Postit savedPostit = postitService.savePostit(postit);
        // 저장된 엔티티를 다시 DTO로 변환하여 반환
        return postitService.toDto(savedPostit);
    }

    @GetMapping
    public List<PostitDto> getAllPostits(){
        return  postitService.getAllPostits();
    }
}
