package com.ironbank.bankingsystem.config;

import com.ironbank.bankingsystem.repository.ThirdPartyRepository;
import com.ironbank.bankingsystem.security.ThirdPartyAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class ThirdPartyFilterConfig {

    @Bean
    public ThirdPartyAuthFilter thirdPartyAuthFilter(@Lazy ThirdPartyRepository thirdPartyRepository) {
        return new ThirdPartyAuthFilter(thirdPartyRepository);
    }
}