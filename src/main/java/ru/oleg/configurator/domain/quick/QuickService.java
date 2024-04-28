package ru.oleg.configurator.domain.quick;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class QuickService {
    public Quick getQuick() {
        return new Quick();
    }

    public Quick updateQuick(Quick quick) {
        return new Quick();
    }
}
