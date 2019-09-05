package dev.anullihate.envyfactioncore.tasks;

import cn.nukkit.Server;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityHuman;
import cn.nukkit.level.Level;
import cn.nukkit.scheduler.PluginTask;
import cn.nukkit.utils.TextFormat;
import dev.anullihate.envyfactioncore.EnvyFactionCore;

import java.util.Iterator;

public class ClearLagTask extends PluginTask<EnvyFactionCore> {

    public ClearLagTask(EnvyFactionCore core) {
        super(core);
    }

    @Override
    public void onRun(int i) {
        Server server = this.getOwner().getServer();
        String prefix = "&l&0&c(!)&r";
        if (server.getOnlinePlayers().size() > 0) {
            server.getScheduler().scheduleDelayedTask(this.owner, () -> {
                String msg = String.format("%s &o&cLag Will Clear In...", prefix);
                server.broadcastMessage(TextFormat.colorize('&', msg));
            }, 20);
            server.getScheduler().scheduleDelayedTask(this.owner, () -> {
                String msg = String.format("%s &o&c3", prefix);
                server.broadcastMessage(TextFormat.colorize('&', msg));
            }, 60);
            server.getScheduler().scheduleDelayedTask(this.owner, () -> {
                String msg = String.format("%s &o&c2", prefix);
                server.broadcastMessage(TextFormat.colorize('&', msg));
            }, 100);
            server.getScheduler().scheduleDelayedTask(this.owner, () -> {
                String msg = String.format("%s &o&c1", prefix);
                server.broadcastMessage(TextFormat.colorize('&', msg));
            }, 140);
            server.getScheduler().scheduleDelayedTask(this.owner, () -> {
                Iterator levels = server.getLevels().values().iterator();
                String msg = String.format("%s &l&aLag Cleared!", prefix);

                while(levels.hasNext()) {
                    Level level = (Level)levels.next();

                    for (Entity entity : level.getEntities()) {
                        if (!(entity instanceof EntityHuman)) {
                            entity.close();
                        }
                    }

                    level.doChunkGarbageCollection();
                    level.unloadChunks(true);
                }

                System.gc();

                server.broadcastMessage(TextFormat.colorize('&', msg));

            }, 180);
        }
    }
}
