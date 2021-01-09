package com.mym.sk.service.matchDetail;

import com.mym.sk.domains.match.*;
import com.mym.sk.domains.matchList.MatchReference;
import com.mym.sk.domains.summoner.Summoner;
import com.mym.sk.web.dto.SummonerMatchListResponseDto;
import com.mym.sk.web.dto.SummonerResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchDetailService
{
    private final MatchDetailRepository matchDetailRepository;

    private final TeamStatsDetailRepository teamStatsDetailRepository;

    private final ParticipantIdentityDetailRepository participantIdentityDetailRepository;

    private final ParticipantDetailRepository participantDetailRepository;

    private final PlayerDetailRepository playerDetailRepository;

    private final ParticipantStatsDetailRepository participantStatsDetailRepository;

    // TODO 연관관계 정리 필요 (지금 수동임)
    @Transactional
    public List<MatchDetail> saveMatchDetail(List<MatchDetail> matchDetails){

        List<MatchDetail> matchDetailList = matchDetailRepository.saveAll(matchDetails);

        for (MatchDetail matchDetail : matchDetails) {

            List<ParticipantIdentityDetail> participantIdentities = matchDetail.getParticipantIdentities();

            for (ParticipantIdentityDetail participantIdentity : participantIdentities) {
                participantIdentity.addMatchDetail(matchDetail);
                participantIdentityDetailRepository.save(participantIdentity);
            }


            for (ParticipantIdentityDetail participantIdentity : participantIdentities) {
                PlayerDetail player = participantIdentity.getPlayer();
                player.addParticipantIdentityDetail(participantIdentity);
                playerDetailRepository.save(player);
            }


            List<ParticipantDetail> participants = matchDetail.getParticipants();
            for (ParticipantDetail participant : participants) {
                participant.addMatchDetail(matchDetail);
                participantDetailRepository.save(participant);
            }

            for (ParticipantDetail participant : participants) {
                participantStatsDetailRepository.save(participant.getStats());
            }


            List<TeamStatsDetail> teams = matchDetail.getTeams();
            for (TeamStatsDetail team : teams) {
                team.addMatchDetail(matchDetail);
                teamStatsDetailRepository.save(team);

            }

        }

        return matchDetailList;
    }

    @Transactional
    public List<SummonerMatchListResponseDto> getMatchDetailList(List<MatchReference> matchReferences, SummonerResponseDto summoner){

        List<SummonerMatchListResponseDto> matchListResponseDtoList = new ArrayList<>();


        for (MatchReference matchReference : matchReferences) {

            MatchDetail matchDetail = matchDetailRepository.findByGameId(matchReference.getGameId()).orElseThrow(() -> new IllegalArgumentException(""));


            List<ParticipantIdentityDetail> participantIdentities = matchDetail.getParticipantIdentities();

            int participantId = 0;

            for (ParticipantIdentityDetail participantIdentity : participantIdentities) {
                if(participantIdentity.getPlayer().getAccountId().equals(summoner.getAccountId())){

                    participantId = participantIdentity.getParticipantId();

                    break;
                }
            }

            int kill = -1;
            int deaths = -1;
            int assists = -1;

            List<ParticipantDetail> participants = matchDetail.getParticipants();

            for (ParticipantDetail participant : participants) {

                if(participant.getParticipantId() == participantId){
                    kill = participant.getStats().getKills();
                    deaths = participant.getStats().getDeaths();
                    assists = participant.getStats().getAssists();

                    break;
                }

            }

            int teamId = 100;
            if(participantId > 5){
                teamId = 200;
            }

            String win = "";

            List<TeamStatsDetail> teams = matchDetail.getTeams();
            for (TeamStatsDetail team : teams) {

                if(team.getTeamId() == teamId){
                    win = team.getWin();

                    break;
                }

            }

            matchListResponseDtoList.add(new SummonerMatchListResponseDto(
                    matchReference.getGameId(), matchReference.getChampion(), matchReference.getRole(), win, kill, deaths, assists));

        }


        return matchListResponseDtoList;
    }
}
