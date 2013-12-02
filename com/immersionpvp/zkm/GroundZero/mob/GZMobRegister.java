package com.immersionpvp.zkm.GroundZero.mob;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Entity;

/** 
 * A class to manage the mob (entity) accounts.
 * @author calhal
 */
public class GZMobRegister {
	
	private Map<String, GZMobAccount> registeredAccounts = new
			HashMap<String, GZMobAccount>();
	
	public Map<String, GZMobAccount> getAccounts() {
		return Collections.unmodifiableMap(registeredAccounts);
	}
	
	public void createAccount(String acc) {
		if (registeredAccounts.get(acc.toLowerCase()) == null) 
			registeredAccounts.put(acc.toLowerCase(), new GZMobAccount());
	}
	
	public GZMobInfo addEntityToAccount(String acc, Entity e) {
		GZMobAccount account = registeredAccounts.get(acc.toLowerCase());
		if (account == null) {
			account = new GZMobAccount();
			registeredAccounts.put(acc.toLowerCase(), account);
		}
		return account.addEntity(e);
	}
	
	public void removeEntityFromAccount(String acc, Entity e) {
		GZMobAccount account = registeredAccounts.get(acc.toLowerCase());
		if (account == null) {
			account = new GZMobAccount();
			registeredAccounts.put(acc.toLowerCase(), account);
		}
		account.removeEntity(e);
	}
	
	public boolean isEntityInsideAccount(String acc, Entity e) {
		GZMobAccount account = registeredAccounts.get(acc.toLowerCase());
		if (account == null) {
			account = new GZMobAccount();
			registeredAccounts.put(acc.toLowerCase(), account);
			return false;
		}
		for (GZMobInfo info : account.getAccountContents()) {
			if (info.getMob().equals(e))
				return true;
		}
		return false;
	}
}
