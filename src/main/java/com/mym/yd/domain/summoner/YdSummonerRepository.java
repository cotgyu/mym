package com.mym.yd.domain.summoner;

import com.mym.yd.web.dto.SummonerResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.Optional;

public interface YdSummonerRepository extends JpaRepository<YdSummoner, String> {

    @Query("SELECT y FROM YdSummoner y WHERE y.summonerName = :summonerName")
    Optional<YdSummoner> findByName(@Param("summonerName") String summonerName);
}
