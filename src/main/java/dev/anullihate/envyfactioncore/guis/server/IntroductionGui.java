package dev.anullihate.envyfactioncore.guis.server;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.window.FormWindowModal;
import dev.anullihate.envyfactioncore.guis.Gui;
import dev.anullihate.envyfactioncore.guis.races.RaceIntroGui;

public class IntroductionGui extends FormWindowModal implements Gui {

    public IntroductionGui() {
        super("Introduction", "", "Create", "Leave Server");
    }

    @Override
    public void onPlayerFormResponse(PlayerFormRespondedEvent event) {
        int id = getResponse().getClickedButtonId();
        Player player = event.getPlayer();

        if (id == 0) {
            player.showFormWindow(new RaceIntroGui());
        } else {
            player.kick("Leave");
        }
    }
}
