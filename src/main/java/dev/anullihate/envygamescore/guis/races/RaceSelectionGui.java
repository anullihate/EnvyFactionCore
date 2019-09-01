package dev.anullihate.envygamescore.guis.races;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import dev.anullihate.envygamescore.guis.Gui;
import dev.anullihate.envygamescore.guis.divisions.DivisionsIntroGui;

public class RaceSelectionGui extends FormWindowSimple implements Gui {

    public RaceSelectionGui() {
        super("RaceSelectionGui", "");

        addButton(new ElementButton("Human"));
        addButton(new ElementButton("Elf"));
        addButton(new ElementButton("Orc"));
        addButton(new ElementButton("Dwarf"));
        addButton(new ElementButton("Undead"));
    }

    @Override
    public void onPlayerFormResponse(PlayerFormRespondedEvent event) {
        Player player = event.getPlayer();
        if (wasClosed()) {
            player.showFormWindow(new RaceSelectionGui());
        } else {
            int id = getResponse().getClickedButtonId();
            switch (id) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
            }
            player.showFormWindow(new DivisionsIntroGui());
        }
    }
}
