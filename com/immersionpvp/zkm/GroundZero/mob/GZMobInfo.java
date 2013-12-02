package com.immersionpvp.zkm.GroundZero.mob;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Entity;

public class GZMobInfo {
	private Map<String, String> externalMobAttributes = 
			new HashMap<String, String>();
	private final Entity gzMob;
	
	public GZMobInfo(Entity mob) {
		this.gzMob = mob;
	}
	
	public Entity getMob() {
		return gzMob;
	}
	
	public String getMobAttribute(String attr) {
		return externalMobAttributes.get(attr);
	}
	
	public void setMobAttribute(String attr, String value) {
		externalMobAttributes.put(attr, value);
	}
}
