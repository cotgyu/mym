package com.mym.sk.service.summoner;

import com.mym.sk.domains.summoner.Summoner;
import com.mym.sk.domains.summoner.SummonerRepository;
import com.mym.sk.web.dto.SummonerRequestDto;
import com.mym.sk.web.dto.SummonerSaveDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SummonerDetailService {

    private final SummonerRepository summonerRepository;

    private final ModelMapper modelMapper;

    public SummonerRequestDto getSummonerInfoFromDB(String summonerName){

        SummonerRequestDto summonerRequestDto = new SummonerRequestDto();

        Summoner summoner = summonerRepository.findByName(summonerName).orElseGet(Summoner::new);

        modelMapper.map(summoner, summonerRequestDto);

        return summonerRequestDto;
    }

    public Summoner saveSummonerDetail(SummonerSaveDto summonerSaveDto){

        return summonerRepository.save(summonerSaveDto.toEntity());
    }
}
