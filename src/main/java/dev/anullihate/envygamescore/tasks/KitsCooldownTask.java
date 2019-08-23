package dev.anullihate.envygamescore.tasks;

import cn.nukkit.scheduler.PluginTask;
import dev.anullihate.envygamescore.EnvyGamesCore;
import dev.anullihate.envygamescore.objects.kits.Kit;

public class KitsCooldownTask extends PluginTask<EnvyGamesCore> {

    private int min = 0;
    private int everyMin = 5;

    public KitsCooldownTask(EnvyGamesCore core) {
        super(core);
    }

    @Override
    public void onRun(int i) {
        for (Kit kit : EnvyGamesCore.kits.getKits().values()) {
            kit.processCooldown();
        }

        if (++this.min % everyMin == 0) {
            for (Kit kit : EnvyGamesCore.kits.getKits().values()) {
                kit.save();
            }
        }
    }
}
