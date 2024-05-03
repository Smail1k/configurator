package ru.oleg.configurator.domain.nginx;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.oleg.configurator.domain.nginx.dto.ConfigNginx;

@Service
@AllArgsConstructor
public class NginxService {
    public ConfigNginx getConfigNginx() {
        return new ConfigNginx();
    }

    public ConfigNginx updateConfigNginx(ConfigNginx configNginx) {
        return configNginx;
    }
}
