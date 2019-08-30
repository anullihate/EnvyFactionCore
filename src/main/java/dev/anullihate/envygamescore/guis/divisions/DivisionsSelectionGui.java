package dev.anullihate.envygamescore.guis.divisions;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.window.FormWindowSimple;
import dev.anullihate.envygamescore.EnvyGamesCore;
import dev.anullihate.envygamescore.datamanagers.UserManager;
import dev.anullihate.envygamescore.datatables.User;
import dev.anullihate.envygamescore.guis.Gui;
import dev.anullihate.envygamescore.guis.server.IntroductionGui;

import java.io.File;
import java.sql.SQLException;

public class DivisionsSelectionGui extends FormWindowSimple implements Gui {

    public DivisionsSelectionGui() {
        super("DivisionsSelectionGui", "Please choose a Division to Join");
        String type = ElementButtonImageData.IMAGE_DATA_TYPE_URL;
        String knightimg = "https://icon-library.net/images/warrior-icon/warrior-icon-25.jpg";

        addButton(new ElementButton("Knight Division", new ElementButtonImageData(type, knightimg)));
        addButton(new ElementButton("Archer Division", new ElementButtonImageData(type, knightimg)));
        addButton(new ElementButton("Magic Division", new ElementButtonImageData(type, knightimg)));
        addButton(new ElementButton("Rogue Division", new ElementButtonImageData(type, knightimg)));
        addButton(new ElementButton("Cleric Division", new ElementButtonImageData(type, knightimg)));
    }

    @Override
    public void onPlayerFormResponse(PlayerFormRespondedEvent event) {
        Player player = event.getPlayer();
        if (wasClosed()) {
            player.showFormWindow(new DivisionsSelectionGui());
        } else {
            int id = getResponse().getClickedButtonId();
            User user = UserManager.users.get(player.getName());
            try {
                switch (id) {
                    case 0: // Knight
                        user.setDivision("Knight");
                        EnvyGamesCore.accountDao.update(user);
                        break;
                    case 1: // Archer
                        user.setDivision("Archer");
                        EnvyGamesCore.accountDao.update(user);
                        break;
                    case 2: // Magic
                        user.setDivision("Magic");
                        EnvyGamesCore.accountDao.update(user);
                        break;
                    case 3: // Rogue
                        user.setDivision("Rogue");
                        EnvyGamesCore.accountDao.update(user);
                        break;
                    case 4: // Cleric
                        user.setDivision("Cleric");
                        EnvyGamesCore.accountDao.update(user);
                        break;
                }
                player.showFormWindow(new IntroductionGui());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
