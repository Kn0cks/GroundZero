package com.immersionpvp.zkm.GroundZero.module;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;

import com.immersionpvp.zkm.GroundZero.GZModule;
import com.immersionpvp.zkm.GroundZero.GroundZero;
import com.immersionpvp.zkm.GroundZero.mob.GZMobInfo;

/**
 * This module creates fire zombies which will set you on fire at a touch.
 * @author calhal
 */
public class GZFireZombies extends GZModule implements Listener {

	private static final String MOB_REGISTER_INFO = "fire_zombies";
	private Random rng = new Random();
	
	private static final ItemStack UNLUCKY_HEAD = 
			new ItemStack(Material.IRON_HELMET, 1);
	private static final ItemStack UNLUCKY_CHEST =
			new ItemStack(Material.IRON_CHESTPLATE, 1);
	private static final ItemStack UNLUCKY_LEGS =
			new ItemStack(Material.IRON_LEGGINGS, 2);
	private static final ItemStack UNLUCKY_BOOTS = 
			new ItemStack(Material.IRON_BOOTS);
	
	private static final String ZOMBIE_CUSTOM_NAME = "Hell Zombie";
	
	public GZFireZombies() {
		super("FireZombies");
		rng = new Random(System.nanoTime());
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
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onZombieSpawn(CreatureSpawnEvent event) {
		if (!event.getEntityType().equals(EntityType.ZOMBIE))
			return;
		if (rng.nextInt(100) > 5)
			return;
		if (GZSprintZombies.isSprintZombie(event.getEntity()))
			return;
		if (GZPlagueZombies.isPlagueZombie(event.getEntity()))
			return;
		if (GZGayZombies.isGayZombie(event.getEntity()))
			return;
		GroundZero.getMobRegister().addEntityToAccount(MOB_REGISTER_INFO, 
				event.getEntity());
		event.getEntity().getEquipment().clear();
		int[] chances = { rng.nextInt(100), 
				rng.nextInt(100), 
				rng.nextInt(100), 
				rng.nextInt(100) };
		if (chances[0] < 15)
			event.getEntity().getEquipment().setHelmet(UNLUCKY_HEAD);
		if (chances[1] < 15)
			event.getEntity().getEquipment().setChestplate(UNLUCKY_CHEST);
		if (chances[2] < 15)
			event.getEntity().getEquipment().setLeggings(UNLUCKY_LEGS);
		if (chances[3] < 15)
			event.getEntity().getEquipment().setBoots(UNLUCKY_BOOTS);
		event.getEntity().setCustomName(ZOMBIE_CUSTOM_NAME);
		event.getEntity().setCustomNameVisible(true);
		event.getEntity().setFireTicks(Integer.MAX_VALUE);
	}
	
	/**
	 * Cancel fire damage for our special "Fire Zombies"
	 * @param event
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void onZombieTakeDamage(EntityDamageEvent event) {
		if (!event.getEntityType().equals(EntityType.ZOMBIE))
			return;
		if (!event.getCause().equals(DamageCause.FIRE) && 
				!event.getCause().equals(DamageCause.FIRE_TICK))
			return;
		if (!GroundZero.getMobRegister().
				isEntityInsideAccount(MOB_REGISTER_INFO, event.getEntity()))
			return;
		event.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onZombieAttacked(EntityDamageByEntityEvent event) {
		if (!event.getEntity().getType().equals(EntityType.ZOMBIE))
			return;
		if (!event.getDamager().getType().equals(EntityType.PLAYER))
			return;
		if (!isHellZombie(event.getEntity()))
			return;
		if (event.getEntity().getWorld().hasStorm())
			event.setDamage(event.getDamage() * 1.4);
		if (rng.nextInt(100) > 5)
			return;
		((LivingEntity)event.getEntity()).launchProjectile(Fireball.class);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onZombieDeath(EntityDeathEvent event) {
		if (!event.getEntityType().equals(EntityType.ZOMBIE))
			return;
		if (!isHellZombie(event.getEntity()))
			return;
		ItemStack addedItem = null;
		if (rng.nextInt(100) < 45) {
			addedItem = new ItemStack(Material.TORCH, 5);
		}
		if (rng.nextInt(100) < 30) {
			addedItem = new ItemStack(Material.COAL, 3);
		}
		if (rng.nextInt(100) < 20) {
			addedItem = new ItemStack(Material.BLAZE_ROD, 1);
		}
		if (rng.nextInt(100) < 10) {
			addedItem = new ItemStack(Material.LAVA_BUCKET, 1);
		}
		if (rng.nextInt(1000) == 0) {
			addedItem = new ItemStack(Material.SKULL_ITEM, 1, (short)1);
		}
		if (addedItem != null)
			event.getDrops().add(addedItem);
		event.getEntity().getWorld().createExplosion(event.getEntity().getLocation(), 0F);
		GroundZero.getMobRegister().removeEntityFromAccount(MOB_REGISTER_INFO, event.getEntity());
	}
	
	@EventHandler
	public void onWeatherChange(WeatherChangeEvent event) {
		if (!event.toWeatherState())
			for (GZMobInfo info : 
				GroundZero.getMobRegister().getAccounts().get(MOB_REGISTER_INFO).
					getAccountContents())
				info.getMob().setFireTicks(Integer.MAX_VALUE);
	}
	
	public static boolean isHellZombie(Entity e) {
		return GroundZero.getMobRegister().isEntityInsideAccount(MOB_REGISTER_INFO, e);
	}

}
