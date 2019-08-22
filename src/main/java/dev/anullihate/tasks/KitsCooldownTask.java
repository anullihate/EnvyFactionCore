package dev.anullihate.tasks;

import cn.nukkit.scheduler.PluginTask;
import dev.anullihate.EnvyGamesCore;

public class KitsCooldownTask extends PluginTask<EnvyGamesCore> {

    private int min = 0;
    private int everyMin = 5;

    public KitsCooldownTask(EnvyGamesCore core) {
        super(core);
    }

    @Override
    public void onRun(int i) {
        this.getOwner().kits.cooldownProcessor();

        if (++this.min % everyMin == 0) {
            this.getOwner().kits.save();
        }
    }
}
