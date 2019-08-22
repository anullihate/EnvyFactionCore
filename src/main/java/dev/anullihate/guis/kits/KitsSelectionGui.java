package dev.anullihate.guis.kits;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.window.FormWindowSimple;
import dev.anullihate.guis.Gui;

public class KitsSelectionGui extends FormWindowSimple implements Gui {

    public KitsSelectionGui() {
        super("KitsSelectionGui", "");

    }

    @Override
    public void onPlayerFormResponse(PlayerFormRespondedEvent event) {
        //
    }
}
