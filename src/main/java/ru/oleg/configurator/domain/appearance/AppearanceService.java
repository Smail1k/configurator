package ru.oleg.configurator.domain.appearance;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.oleg.configurator.domain.appearance.dto.Appearance;

@Service
@AllArgsConstructor
public class AppearanceService {

    public Appearance getAppearance() {
        Appearance appearance = new Appearance();
        return appearance;
    }

    public Appearance updateAppearance(Appearance appearance) {
        Appearance appearanceUpdated = new Appearance();
        return appearanceUpdated;
    }
}
