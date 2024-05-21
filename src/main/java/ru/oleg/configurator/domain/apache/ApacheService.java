package ru.oleg.configurator.domain.apache;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.oleg.configurator.domain.apache.dto.ApacheConfig;

@Service
@AllArgsConstructor
public class ApacheService {
    public ApacheConfig getConfigApache() {
        return new ApacheConfig();
    }

    public ApacheConfig updateConfigApache(ApacheConfig configApache) {
        return configApache;
    }
}
