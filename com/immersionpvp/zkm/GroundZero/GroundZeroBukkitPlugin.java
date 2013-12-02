package com.immersionpvp.zkm.GroundZero;

import org.bukkit.plugin.java.JavaPlugin;

public class GroundZeroBukkitPlugin extends JavaPlugin {
	
	@Override
	public void onEnable() {
		GroundZero.initalize(this);
	}
	
	@Override
	public void onDisable() {
		GroundZero.deinit();
	}
}
