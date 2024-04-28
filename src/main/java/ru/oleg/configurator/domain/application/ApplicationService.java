package ru.oleg.configurator.domain.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.oleg.configurator.domain.application.dto.Application;
import ru.oleg.configurator.domain.application.dto.Player;
import ru.oleg.configurator.domain.application.dto.Way;

@Service
@AllArgsConstructor
public class ApplicationService {

    public Application getApplication() {
        return new Application();
    }

    public Application updateApplication(Application application) {
        return application;
    }

    public Player getPlayer() {
        return new Player();
    }

    public Player updatePlayer(Player player) {
        return player;
    }

    public Way getWay() {
        return new Way();
    }

    public Way updateWay(Way way) {
        return way;
    }
}
