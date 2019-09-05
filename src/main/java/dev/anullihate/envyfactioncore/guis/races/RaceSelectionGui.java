package dev.anullihate.envyfactioncore.guis.races;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import dev.anullihate.envyfactioncore.datamanagers.UserManager;
import dev.anullihate.envyfactioncore.dataobjects.users.UserProfile;
import dev.anullihate.envyfactioncore.guis.Gui;
import dev.anullihate.envyfactioncore.guis.divisions.DivisionsIntroGui;

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
            UserProfile userProfile = UserManager.getUser(player.getName());
            switch (id) {
                case 0: // Human
                    userProfile.setRace("human");
                    break;
                case 1: // Elf
                    userProfile.setRace("elf");
                    break;
                case 2: // Orc
                    userProfile.setRace("orc");
                    break;
                case 3: // Dwarf
                    userProfile.setRace("dwarf");
                    break;
                case 4: // Undead
                    userProfile.setRace("undead");
                    break;
            }
            player.showFormWindow(new DivisionsIntroGui());
        }
    }
}
