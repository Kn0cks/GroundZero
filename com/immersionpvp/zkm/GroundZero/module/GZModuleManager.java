package com.immersionpvp.zkm.GroundZero.module;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.immersionpvp.zkm.GroundZero.GZModule;

public class GZModuleManager {
	
	private List<GZModule> loadedModules = new ArrayList<GZModule>();
	
	public void addModule(GZModule module) {
		loadedModules.add(module);
	}
	
	public void removeModule(GZModule module) {
		loadedModules.remove(module);
	}
	
	public GZModule[] getModules() {
		return loadedModules.toArray(new GZModule[loadedModules.size()]);
	}
	
	public GZModule getModuleByClass(Class<? extends GZModule> module) {
		for (GZModule m : loadedModules)
			if (m.getClass().equals(module))
				return m;
		return null;
	}
	
	public void disable() {
		for (GZModule m : loadedModules)
			m.disable();
	}
	
	public void enable() {
		for (GZModule m : loadedModules)
			m.enable();
	}
	
	public List<GZModule> getModulesList() {
		return Collections.unmodifiableList(loadedModules);
	}
}
