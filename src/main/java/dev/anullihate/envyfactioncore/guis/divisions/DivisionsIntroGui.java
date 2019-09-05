package dev.anullihate.envyfactioncore.guis.divisions;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.window.FormWindowModal;
import dev.anullihate.envyfactioncore.guis.Gui;
import dev.anullihate.envyfactioncore.guis.races.RaceSelectionGui;

public class DivisionsIntroGui extends FormWindowModal implements Gui {

    public DivisionsIntroGui() {
        super("DivisionsIntroGui", "", "Choose Division", "Back");

    }

    @Override
    public void onPlayerFormResponse(PlayerFormRespondedEvent event) {
        Player player = event.getPlayer();
        int id = getResponse().getClickedButtonId();
        if (id == 0) {
            player.showFormWindow(new DivisionsSelectionGui());
        } else {
            player.showFormWindow(new RaceSelectionGui());
        }
    }
}
