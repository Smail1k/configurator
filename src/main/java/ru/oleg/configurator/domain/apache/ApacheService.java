package ru.oleg.configurator.domain.apache;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.oleg.configurator.domain.apache.dto.ConfigApache;

@Service
@AllArgsConstructor
public class ApacheService {
    public ConfigApache getConfigApache() {
        return new ConfigApache();
    }

    public ConfigApache updateConfigApache(ConfigApache configApache) {
        return configApache;
    }
}
