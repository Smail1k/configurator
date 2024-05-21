package ru.oleg.configurator.domain.nginx;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.oleg.configurator.domain.nginx.dto.NginxConfig;

@Service
@AllArgsConstructor
public class NginxService {
    public NginxConfig getConfigNginx() {
        return new NginxConfig();
    }

    public NginxConfig updateConfigNginx(NginxConfig configNginx) {
        return configNginx;
    }
}
