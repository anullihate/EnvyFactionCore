package dev.anullihate.envyfactioncore.guis.races;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.window.FormWindowModal;
import dev.anullihate.envyfactioncore.guis.Gui;
import dev.anullihate.envyfactioncore.guis.server.IntroductionGui;

public class RaceIntroGui extends FormWindowModal implements Gui {

    public RaceIntroGui() {
        super("RaceIntroGui", "", "Choose Race", "Back");
    }

    @Override
    public void onPlayerFormResponse(PlayerFormRespondedEvent event) {
        int id = getResponse().getClickedButtonId();
        Player player = event.getPlayer();

        if (id == 0) {
            player.showFormWindow(new RaceSelectionGui());
        } else {
            player.showFormWindow(new IntroductionGui());
        }
    }
}
