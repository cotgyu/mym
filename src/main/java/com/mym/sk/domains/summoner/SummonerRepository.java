package com.mym.sk.domains.summoner;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SummonerRepository extends JpaRepository<Summoner, String> {

    // 컬렉션 때는 optional 사용 x
    Optional<Summoner> findByName(String summonerName);
}
