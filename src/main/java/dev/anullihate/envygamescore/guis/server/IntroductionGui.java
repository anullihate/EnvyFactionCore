package dev.anullihate.envygamescore.guis.server;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowModal;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.utils.TextFormat;
import dev.anullihate.envygamescore.guis.Gui;
import dev.anullihate.envygamescore.guis.races.RaceIntroGui;

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
