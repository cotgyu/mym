package com.mym.sk.service.leagueEntry;

import com.mym.sk.domains.leagueEntry.LeagueEntry;
import com.mym.sk.domains.leagueEntry.LeagueEntryRepository;
import com.mym.sk.web.dto.LeagueEntrySaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeagueEntryService {

    private final LeagueEntryRepository leagueEntryRepository;

    @Transactional
    public List<LeagueEntry> saveLeagueEntriesInfo(ArrayList<LeagueEntrySaveDto> saveDtos){

        List<LeagueEntry> collect = saveDtos.stream()
                .map(LeagueEntrySaveDto::toEntity)
                .collect(Collectors.toList());

        return leagueEntryRepository.saveAll(collect);

    }
}
