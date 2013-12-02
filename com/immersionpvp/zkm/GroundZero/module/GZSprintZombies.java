package com.immersionpvp.zkm.GroundZero.module;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import com.immersionpvp.zkm.GroundZero.GZModule;
import com.immersionpvp.zkm.GroundZero.GroundZero;
import com.immersionpvp.zkm.GroundZero.mob.GZMobInfo;

/**
 * This module adds SANIC FAST zombies to the game
 * @author calhal
 */
public class GZSprintZombies extends GZModule implements Listener, Runnable {
	
	private Random rng = new Random();
	
	private static final String MOB_REGISTER_INFO = "sprint_zombies";
	
	private static final ItemStack SANIC_BOOTS = new ItemStack(Material.LEATHER_BOOTS, 1);
	
	private static final String ZOMBIE_CUSTOM_NAME = "Sprint Zombie";
	
	public GZSprintZombies() {
		super("SprintZombies");
		rng = new Random();
	}
	
	@Override
	public void onEnable() {
		GroundZero.getMobRegister().createAccount(MOB_REGISTER_INFO);
		GroundZero.registerListener(this);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(
				GroundZero.getBukkitPlugin(), 
				this,
				0,
				10000);
	}
	
	@Override
	public void onDisable() {
		GroundZero.unregisterListener(this);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onZombieSpawn(CreatureSpawnEvent event) {
		if (!event.getEntityType().equals(EntityType.ZOMBIE))
			return;
		if (rng.nextInt(100) > 12)
			return;
		if (GZFireZombies.isHellZombie(event.getEntity()))
			return;
		if (GZPlagueZombies.isPlagueZombie(event.getEntity()))
			return;
		if (GZGayZombies.isGayZombie(event.getEntity()))
			return;
		event.getEntity().getEquipment().clear();
		event.getEntity().addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 2));
		event.getEntity().getEquipment().setBoots(SANIC_BOOTS);
		event.getEntity().setCustomName(ZOMBIE_CUSTOM_NAME);
		event.getEntity().setCustomNameVisible(true);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onZombieDeath(EntityDeathEvent event) {
		if (!event.getEntityType().equals(EntityType.ZOMBIE))
			return;
		if (!isSprintZombie(event.getEntity()))
			return;
		ItemStack addedItem = null;
		
		if (rng.nextInt(100) < 25)
			addedItem = new ItemStack(Material.GLASS_BOTTLE, rng.nextInt(4)); // i guess they like energy drinks
		
		if (addedItem != null)
			event.getDrops().add(addedItem);
		GroundZero.getMobRegister().removeEntityFromAccount(MOB_REGISTER_INFO, event.getEntity());
	}
	
	@Override
	public void run() {
		for (GZMobInfo info : 
			GroundZero.getMobRegister().getAccounts().
			get(MOB_REGISTER_INFO).getAccountContents()){
			((LivingEntity)info.getMob()).addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 2));
		}
	}
	
	
	public static boolean isSprintZombie(Entity e) {
		return GroundZero.getMobRegister().isEntityInsideAccount(MOB_REGISTER_INFO, e);
	}
	
}
