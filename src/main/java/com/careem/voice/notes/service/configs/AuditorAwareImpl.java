package com.careem.voice.notes.service.configs;

import org.springframework.data.domain.AuditorAware;

public class AuditorAwareImpl implements AuditorAware<String> {
    private final String SYSTEM_USER = "SYSTEM";
    @Override
    public String getCurrentAuditor() {
        //Here you can get the user from Authentication token from SSO and return it
        //For the purpose of this task, always return system user
        return SYSTEM_USER;
    }
}
