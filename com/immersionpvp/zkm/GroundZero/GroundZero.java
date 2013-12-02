package com.immersionpvp.zkm.GroundZero;

import java.util.logging.Level;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import com.immersionpvp.zkm.GroundZero.mob.GZMobRegister;
import com.immersionpvp.zkm.GroundZero.module.GZFireZombies;
import com.immersionpvp.zkm.GroundZero.module.GZGayZombies;
import com.immersionpvp.zkm.GroundZero.module.GZModuleManager;
import com.immersionpvp.zkm.GroundZero.module.GZPlagueZombies;
import com.immersionpvp.zkm.GroundZero.module.GZSprintZombies;

public class GroundZero {
	private static GroundZeroBukkitPlugin gzPlugin;
	private static GZModuleManager gzModuleManager;
	private static GZMobRegister gzMobRegister;
	
	
	public static void initalize(GroundZeroBukkitPlugin plugin) {
		gzPlugin = plugin;
		gzModuleManager = new GZModuleManager();
		gzMobRegister = new GZMobRegister();
		gzModuleManager.addModule(new GZFireZombies());
		gzModuleManager.addModule(new GZSprintZombies());
		gzModuleManager.addModule(new GZPlagueZombies());
		gzModuleManager.addModule(new GZGayZombies());
		gzModuleManager.enable();
	}
	
	public static void deinit() {
		gzModuleManager.disable();
	}
	
	public static GZModuleManager getModuleManager() {
		return gzModuleManager;
	}
	
	public static GZMobRegister getMobRegister() {
		return gzMobRegister;
	}
	
	public static GroundZeroBukkitPlugin getBukkitPlugin() {
		return gzPlugin;
	}
	
	public static void registerListener(Listener l) {
		gzPlugin.getServer().getPluginManager().registerEvents(l, gzPlugin);
	}
	
	public static void unregisterListener(Listener l) {
		HandlerList.unregisterAll(l);
	}
	
	public static void catastrophicFailure(String reason) {
		LOG(Level.SEVERE, "Catostrophic failure occurred - " + reason);
		gzPlugin.getPluginLoader().disablePlugin(gzPlugin);
	}
	
	public static void LOG(String words) {
		gzPlugin.getLogger().log(Level.INFO, "[GroundZero]: " + words);
	}
	
	public static void LOG(Level level, String words) {
		gzPlugin.getLogger().log(level, "[GroundZero]: " + words);
	}
}
