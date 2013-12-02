package com.immersionpvp.zkm.GroundZero;

public class GZModule {
	
	private final String gzModuleName;
	
	public GZModule(String name) {
		this.gzModuleName = name;
		GroundZero.LOG("Module instantiated: " + name);
	}
	
	public String getModuleName() {
		return gzModuleName;
	}
	
	public final void enable() {
		GroundZero.LOG("Module being enabled: " + gzModuleName);
		onEnable();
		GroundZero.LOG("Module successfully enabled: " + gzModuleName);
	}
	
	public final void disable() {
		GroundZero.LOG("Module being disabled: " + gzModuleName);
		onDisable();
		GroundZero.LOG("Module successfully disabled: " + gzModuleName);
	}
	
	public void onEnable() {}
	public void onDisable() {}
}
