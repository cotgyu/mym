package com.mym.sk.service.summoner;

import com.mym.sk.domains.summoner.Summoner;
import com.mym.sk.domains.summoner.SummonerRepository;
import com.mym.sk.web.dto.SummonerResponseDto;
import com.mym.sk.web.dto.SummonerSaveDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SummonerDetailService {

    private final SummonerRepository summonerRepository;

    public Optional<SummonerResponseDto> getSummonerDetail(String summonerName){

        Optional<Summoner> optionalSummoner = summonerRepository.findByName(summonerName);

        if(optionalSummoner.isEmpty()){
            return Optional.empty();
        }

        return Optional.ofNullable(new SummonerResponseDto(optionalSummoner.get()));
    }

    @Transactional
    public Summoner saveSummonerDetail(SummonerSaveDto summonerSaveDto){

        return summonerRepository.save(summonerSaveDto.toEntity());
    }
}
