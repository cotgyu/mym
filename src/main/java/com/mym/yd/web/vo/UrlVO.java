package com.mym.yd.web.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@PropertySource("classpath:yd-url-info.properties")
@Component
public class UrlVO {
    @Value("${yd-api-key}")
    private String apiKey;
    @Value("${yd-entries-url}")
    private String entriesUrl;
    @Value("${yd-summoner-url}")
    private String summonerUrl;
}
