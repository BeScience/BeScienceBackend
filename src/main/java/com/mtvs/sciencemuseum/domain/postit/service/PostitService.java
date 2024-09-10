package com.mtvs.sciencemuseum.domain.postit.service;

import com.mtvs.sciencemuseum.domain.postit.dto.PostitDto;
import com.mtvs.sciencemuseum.domain.postit.entity.Postit;
import com.mtvs.sciencemuseum.domain.postit.repository.PostitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostitService {

    @Autowired
    private PostitRepository postitRepository;

    // DTO를 엔티티로 변환
    public Postit toEntity(PostitDto dto) {
        Postit postit = new Postit();
        postit.setText(dto.getText());
        postit.setX(dto.getX());
        postit.setY(dto.getY());
        postit.setZ(dto.getZ());
        return postit;
    }

    // 엔티티를 DTO로 변환
    public PostitDto toDto(Postit postit) {
        PostitDto dto = new PostitDto();
        dto.setText(postit.getText());
        dto.setX(postit.getX());
        dto.setY(postit.getY());
        dto.setZ(postit.getZ());
        return dto;
    }

    // 엔티티를 데이터베이스에 저장
    public Postit savePostit(Postit postit) {
        return postitRepository.save(postit);
    }

    public List<PostitDto> getAllPostits() {
        List<Postit> postits = postitRepository.findAll();
        return  postits.stream()
                .map(this::toDto)
                .collect(Collectors.toUnmodifiableList());
    }
}
