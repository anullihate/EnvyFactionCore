package dev.anullihate.envyfactioncore.guis.divisions;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.window.FormWindowSimple;
import dev.anullihate.envyfactioncore.datamanagers.UserManager;
import dev.anullihate.envyfactioncore.dataobjects.users.UserProfile;
import dev.anullihate.envyfactioncore.guis.Gui;
import dev.anullihate.envyfactioncore.guis.server.SetupCompleteGui;

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
            UserProfile userProfile = UserManager.getUser(player.getName());
            switch (id) {
                case 0: // Warrior
                    userProfile.setDivision("warrior");
                    break;
                case 1: // Ranger
                    userProfile.setDivision("ranger");
                    break;
                case 2: // Magic
                    userProfile.setDivision("magic");
                    break;
                case 3: // Rogue
                    userProfile.setDivision("rogue");
                    break;
                case 4: // Cleric
                    userProfile.setDivision("cleric");
                    break;
            }
            userProfile.isLoaded = true;
            userProfile.saveUserProfile();
            player.showFormWindow(new SetupCompleteGui());
        }
    }
}
