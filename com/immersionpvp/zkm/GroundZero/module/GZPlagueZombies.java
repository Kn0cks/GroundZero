package com.immersionpvp.zkm.GroundZero.module;

import java.util.Random;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.potion.PotionEffectType;

import com.immersionpvp.zkm.GroundZero.GZModule;
import com.immersionpvp.zkm.GroundZero.GroundZero;

public class GZPlagueZombies extends GZModule implements Listener {

	private Random rng = new Random();
	
	private static String MOB_REGISTER_INFO = "plague_zombies";
	
	private static String ZOMBIE_CUSTOM_NAME = "Plague Zombie";
	
	public GZPlagueZombies() {
		super("PlagueZombies");
	}

	@Override
	public void onEnable() {
		GroundZero.getMobRegister().createAccount(MOB_REGISTER_INFO);
		GroundZero.registerListener(this);
	}
	
	@Override
	public void onDisable() {
		GroundZero.unregisterListener(this);
	}
	
	@EventHandler
	public void onZombieSpawn(CreatureSpawnEvent event) {
		if (!event.getEntityType().equals(EntityType.ZOMBIE))
			return;
		if (rng.nextInt(100) > 2)
			return;
		if (GZFireZombies.isHellZombie(event.getEntity()))
			return;
		if (GZSprintZombies.isSprintZombie(event.getEntity()))
			return;
		if (GZGayZombies.isGayZombie(event.getEntity()))
			return;
		GroundZero.getMobRegister().addEntityToAccount(
				MOB_REGISTER_INFO, event.getEntity());
		event.getEntity().getEquipment().clear();
		event.getEntity().setCustomName(ZOMBIE_CUSTOM_NAME);
		event.getEntity().setCustomNameVisible(true);
	}
	
	/**
	 * This event handler takes care of when the Zombie is damaged.
	 * I separated this and the player damage method because they were
	 * too messy together.
	 * @param event
	 */
	@EventHandler
	public void onZombieDamaged(EntityDamageByEntityEvent event) {
		if (!event.getEntityType().equals(EntityType.ZOMBIE))
			return;
		if (isPlagueZombie(event.getEntity()))
			event.setDamage(event.getDamage() / 2D);
	}
	
	/**
	 * This event handler takes care of when the player is damaged
	 * by a Plague Zombie. Separated from onZombieDamaged for less
	 * messiness.
	 * @param event
	 */
	@EventHandler
	public void onZombieDamagePlayer(EntityDamageByEntityEvent event) {
		if (!event.getEntityType().equals(EntityType.PLAYER))
			return;
		if (!event.getDamager().getType().equals(EntityType.ZOMBIE))
			return;
		if (!isPlagueZombie(event.getDamager()))
			return;
		PotionEffectType type = PotionEffectType.CONFUSION;
		((LivingEntity)event.getEntity()).addPotionEffect(type.createEffect(2500, 1));
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onZombieDeath(EntityDeathEvent event) {
		if (!event.getEntityType().equals(EntityType.ZOMBIE))
			return;
		if (!isPlagueZombie(event.getEntity()))
			return;
		// clean up after ourselves, make sure that shit is removed upon death
		GroundZero.getMobRegister().removeEntityFromAccount(MOB_REGISTER_INFO, event.getEntity());
	}
	
	public static boolean isPlagueZombie(Entity e) {
		return GroundZero.getMobRegister().isEntityInsideAccount(
				MOB_REGISTER_INFO, e);
	}
}
