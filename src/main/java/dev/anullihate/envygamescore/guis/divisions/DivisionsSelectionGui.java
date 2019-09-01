package dev.anullihate.envygamescore.guis.divisions;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.window.FormWindowSimple;
import dev.anullihate.envygamescore.EnvyGamesCore;
import dev.anullihate.envygamescore.datamanagers.UserManager;
import dev.anullihate.envygamescore.datatables.UserTable;
import dev.anullihate.envygamescore.guis.Gui;
import dev.anullihate.envygamescore.guis.server.IntroductionGui;

import java.sql.SQLException;

public class DivisionsSelectionGui extends FormWindowSimple implements Gui {

    public DivisionsSelectionGui() {
        super("DivisionsSelectionGui", "Please choose a Division to Join");
        String type = ElementButtonImageData.IMAGE_DATA_TYPE_URL;
        String knightimg = "https://icon-library.net/images/warrior-icon/warrior-icon-25.jpg";

        addButton(new ElementButton("Warrior Division"));
        addButton(new ElementButton("Ranger Division"));
        addButton(new ElementButton("Magic Division"));
        addButton(new ElementButton("Rogue Division"));
        addButton(new ElementButton("Cleric Division"));
    }

    @Override
    public void onPlayerFormResponse(PlayerFormRespondedEvent event) {
        Player player = event.getPlayer();
        if (wasClosed()) {
            player.showFormWindow(new DivisionsSelectionGui());
        } else {
            int id = getResponse().getClickedButtonId();
            switch (id) {
                case 0: // Warrior
                    System.out.println("asds");
                    break;
                case 1: // Ranger
                    break;
                case 2: // Magic
                    break;
                case 3: // Rogue
                    break;
                case 4: // Cleric
                    break;
            }
        }
    }
}
