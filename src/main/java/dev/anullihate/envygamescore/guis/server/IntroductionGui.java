package dev.anullihate.envygamescore.guis.server;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.utils.TextFormat;
import dev.anullihate.envygamescore.guis.Gui;

public class IntroductionGui extends FormWindowSimple implements Gui {

    public IntroductionGui() {
        super("Introduction", "");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("&o&cThank you for for joining EnvyGames Factions/MMO,").append("\n");
        stringBuilder.append("Hoping your having an awesome experience staying with us");
        stringBuilder.append("\n");
        stringBuilder.append("\n");
        stringBuilder.append("\n");
        stringBuilder.append("&r&e- EnvyGames Team");

        setContent(TextFormat.colorize(stringBuilder.toString()));

        addButton(new ElementButton("Close"));
    }

    @Override
    public void onPlayerFormResponse(PlayerFormRespondedEvent event) {
        //
    }
}
