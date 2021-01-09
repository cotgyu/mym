package com.mym.sk.domains.match;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MatchDetailRepository extends JpaRepository<MatchDetail, Long> {

    Optional<MatchDetail> findByGameId(long gameId);
}
