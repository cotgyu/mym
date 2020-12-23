package com.mym.yd.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MiniSeriesDTO {
    private int losses;
    private String progress;
    private int target;
    private int wins;
}
