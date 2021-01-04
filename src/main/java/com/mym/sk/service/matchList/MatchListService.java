package com.mym.sk.service.matchList;

import com.mym.sk.domains.matchList.MatchReference;
import com.mym.sk.domains.matchList.MatchReferenceRepository;
import com.mym.sk.domains.summoner.Summoner;
import com.mym.sk.web.dto.MatchListSaveDto;
import com.mym.sk.web.dto.MatchReferenceSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchListService {

    private final MatchReferenceRepository matchReferenceRepository;

    public List<MatchReference> saveMatchReferenceList(Summoner summoner, MatchListSaveDto dto){

        List<MatchReference> collect = dto.getMatches()
                .stream()
                .map(MatchReferenceSaveDto::toEntity)
                .collect(Collectors.toList());

        collect.forEach(matchReference -> matchReference.setSummoner(summoner));

        return matchReferenceRepository.saveAll(collect);
    }

}
