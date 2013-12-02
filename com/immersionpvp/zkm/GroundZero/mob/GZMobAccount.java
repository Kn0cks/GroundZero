package com.immersionpvp.zkm.GroundZero.mob;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bukkit.entity.Entity;

/**
 * A class to manage a list of GZGZMobInfoInformation
 * @author calhal
 *
 */
public class GZMobAccount {
	private List<GZMobInfo> entitiesWithAccountFlags = new CopyOnWriteArrayList<GZMobInfo>();
	
	public List<GZMobInfo> getAccountContentsList() {
		return Collections.unmodifiableList(entitiesWithAccountFlags);
	}
	
	public GZMobInfo[] getAccountContents() {
		return entitiesWithAccountFlags.
				toArray(new GZMobInfo[entitiesWithAccountFlags.size()]);
	}
	
	public GZMobInfo addEntity(Entity e) {
		GZMobInfo info = new GZMobInfo(e);
		addMob(info);
		return info;
	}
	
	public void removeEntity(Entity e) {
		for (GZMobInfo i: entitiesWithAccountFlags) {
			if (i.getMob().equals(e))
				entitiesWithAccountFlags.remove(i);
		}
	}
	
	public void addMob(GZMobInfo e) {
		entitiesWithAccountFlags.add(e);
	}
	
	public void removeMob(GZMobInfo e) {
		entitiesWithAccountFlags.remove(e);
	}
}
