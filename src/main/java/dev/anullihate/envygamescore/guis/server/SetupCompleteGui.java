package dev.anullihate.envygamescore.guis.server;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.window.FormWindowModal;
import cn.nukkit.form.window.FormWindowSimple;
import dev.anullihate.envygamescore.guis.Gui;

public class SetupCompleteGui extends FormWindowSimple implements Gui {

    public SetupCompleteGui() {
        super("SetupCompleteGui", "");
    }

    @Override
    public void onPlayerFormResponse(PlayerFormRespondedEvent event) {
        //
    }
}
