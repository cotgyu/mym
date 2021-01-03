package com.mym.yd.web.vo;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;


@Getter
@PropertySource("classpath:yd-param-info.properties")
@Component
public class ParameterVO {

    @Value("${yd-queue}")
    private List<String> queue;
    @Value("${yd-tier}")
    private List<String> tier;
    @Value("${yd-division}")
    private List<String> division;

}
