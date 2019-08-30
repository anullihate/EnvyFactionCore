package dev.anullihate.envygamescore.guis.divisions;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import dev.anullihate.envygamescore.guis.Gui;

public class DivisionsIntroGui extends FormWindowSimple implements Gui {

    public DivisionsIntroGui() {
        super("DivisionsIntroGui", "");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Hello Adventurer,").append("\n");
        stringBuilder.append("").append("\n");
        stringBuilder.append("We are happy to see you here in our lands, ");
        stringBuilder.append("but unfortunately a war is being brewed by the Gods, ");
        stringBuilder.append("it is a bad timing for you to visit, but also a good time for you to be a Hero ");
        stringBuilder.append("").append("\n");
        stringBuilder.append("").append("\n");
        stringBuilder.append("Adventurers must join a Division to learn abilities").append("\n");
        setContent(stringBuilder.toString());
        addButton(new ElementButton("Choose Division"));
    }

    @Override
    public void onPlayerFormResponse(PlayerFormRespondedEvent event) {
        Player player = event.getPlayer();
        if (wasClosed()) {
            player.showFormWindow(new DivisionsIntroGui());
        } else {
            int id = getResponse().getClickedButtonId();
            if (id == 0) {
                player.showFormWindow(new DivisionsSelectionGui());
            }
        }
    }
}
