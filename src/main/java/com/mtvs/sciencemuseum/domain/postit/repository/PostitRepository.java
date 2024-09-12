package com.mtvs.sciencemuseum.domain.postit.repository;

import com.mtvs.sciencemuseum.domain.postit.entity.Postit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostitRepository extends JpaRepository<Postit, Long> {
}