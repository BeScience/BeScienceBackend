package com.mtvs.sciencemuseum.domain.stamp.repository;

import com.mtvs.sciencemuseum.domain.stamp.entity.Stamp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StampRepository extends JpaRepository<Stamp, Long> {
}

