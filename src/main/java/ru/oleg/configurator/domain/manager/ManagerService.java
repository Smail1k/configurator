package ru.oleg.configurator.domain.manager;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.oleg.configurator.domain.manager.dto.kwin.Kwin;
import ru.oleg.configurator.domain.manager.dto.window.behavior.*;
import ru.oleg.configurator.domain.manager.dto.window.switching.MainAlternative;

@Service
@AllArgsConstructor
public class ManagerService {
    public Focus getFocus() {
        return new Focus();
    }

    public Focus updateFocus(Focus focus) {
        return new Focus();
    }

    public HeaderAction getHeaderAction() {
        return new HeaderAction();
    }

    public HeaderAction updateHeaderAction(HeaderAction headerAction) {
        return new HeaderAction();
    }

    public WindowAction getWindowAction() {
        return new WindowAction();
    }

    public WindowAction updateWindowAction(WindowAction windowAction) {
        return new WindowAction();
    }

    public Moving getMoving() {
        return new Moving();
    }

    public Moving updateMoving(Moving moving) {
        return new Moving();
    }

    public Additionally getAdditionally() {
        return new Additionally();
    }

    public Additionally updateAdditionally(Additionally additionally) {
        return new Additionally();
    }

    public MainAlternative getMainAlternative() {
        return new MainAlternative();
    }

    public MainAlternative updateMainAlternative(MainAlternative mainAlternative) {
        return new MainAlternative();
    }

    public Kwin getKwin() {
        return new Kwin();
    }

    public Kwin updateKwin(Kwin kwin) {
        return new Kwin();
    }
}
