package com.mym.sk.service.validator;

import com.mym.sk.web.dto.LeagueEntryRequestDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class LeagueEntriesValidator {

    public void validate(LeagueEntryRequestDto dto, Errors errors){
        if (dto.getQueue() == null || dto.getQueue().equals("")){
            // field 에러
            errors.rejectValue("queue", "wrongValue","queue is wrong");
            // global 에러
            errors.reject("wrongQueue", "Values for queue is wrong");
        }

        if (dto.getDivision() == null || dto.getDivision().equals("")){
            // field 에러
            errors.rejectValue("division", "wrongValue","division is wrong");
            // global 에러
            errors.reject("wrongDivision", "Values for division is wrong");
        }

        if (dto.getTier() == null || dto.getTier().equals("")){
            // field 에러
            errors.rejectValue("tier", "wrongValue","tier is wrong");
            // global 에러
            errors.reject("wrongTier", "Values for tier is wrong");
        }

        if (dto.getPage() <= 0){
            // field 에러
            errors.rejectValue("page", "wrongValue","page is wrong");
            // global 에러
            errors.reject("wrongPage", "Values for page is wrong");
        }
    }


}
