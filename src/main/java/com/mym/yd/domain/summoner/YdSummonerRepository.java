package com.mym.yd.domain.summoner;

import com.mym.yd.web.dto.SummonerResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface YdSummonerRepository extends JpaRepository<YdSummoner, String> {

}
