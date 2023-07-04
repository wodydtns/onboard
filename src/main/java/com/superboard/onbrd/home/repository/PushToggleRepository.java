package com.superboard.onbrd.home.repository;

import com.superboard.onbrd.home.entity.PushToggle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PushToggleRepository extends JpaRepository<PushToggle, Long> {

    Optional<PushToggle> findByMemberId(long memberId);
}
