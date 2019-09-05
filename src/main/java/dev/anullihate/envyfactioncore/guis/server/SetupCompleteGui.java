package dev.anullihate.envyfactioncore.guis.server;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.window.FormWindowSimple;
import dev.anullihate.envyfactioncore.guis.Gui;

public class SetupCompleteGui extends FormWindowSimple implements Gui {

    public SetupCompleteGui() {
        super("SetupCompleteGui", "");
    }

    @Override
    public void onPlayerFormResponse(PlayerFormRespondedEvent event) {
        //
    }
}
