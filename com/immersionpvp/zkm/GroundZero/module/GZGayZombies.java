package com.immersionpvp.zkm.GroundZero.module;

import java.util.Random;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffectType;

import com.immersionpvp.zkm.GroundZero.GZModule;
import com.immersionpvp.zkm.GroundZero.GroundZero;

/**
 * Those damn homosexuals - Can't they keep their hands off of anything?
 * @author calhal
 */
public class GZGayZombies extends GZModule implements Listener {

	private Random rng = new Random();
	
	private static final String MOB_REGISTER_INFO = "gay_zombies";
	private static final String ZOMBIE_CUSTOM_NAME = "faget";
	private static final int PRETTY_PINK_COLOR = 0xF5B0B0;
	
	private static ItemStack CUTE_HAT = 
			new ItemStack(Material.LEATHER_HELMET, 1);
	private static ItemStack GORGEOUS_SWEATER = 
			new ItemStack(Material.LEATHER_CHESTPLATE, 1);
	private static ItemStack ADORABLE_JEANS =
			new ItemStack(Material.LEATHER_LEGGINGS, 1);
	private static ItemStack OMG_SHOES =
			new ItemStack(Material.LEATHER_BOOTS, 1);
	
	private static String[] GAY_ATTACK_MESSAGES = {
		"WHITE MALE CISGENDERED SCUM!!!",
		"BITCH SLAPPED!",
		"SAY FAGGOT AGAIN, I DARE YOU, I DOUBLE DARE YOU MOTHERFUCKER",
		"AM I KAWAII~? :3",
		"*blushes*",
		"o-oh anon-kun, I didn't know you felt like that about me~",
		"I need Feminism because...",
		"MISOGYNY!",
		"Can you spot the gay man? Yes, he's the one sucking dicks.",
		"Do you like my MLP collection?",
		"gibe money pl0x",
		"br?",
		"I touched a boy and I liked it~",
	};
	
	public GZGayZombies() {
		super("GayZombies");
		LeatherArmorMeta qt_0 = (LeatherArmorMeta)CUTE_HAT.getItemMeta();
		LeatherArmorMeta qt_1 = (LeatherArmorMeta)GORGEOUS_SWEATER.getItemMeta();
		LeatherArmorMeta qt_2 = (LeatherArmorMeta)ADORABLE_JEANS.getItemMeta();
		LeatherArmorMeta qt_3 = (LeatherArmorMeta)OMG_SHOES.getItemMeta();
		LeatherArmorMeta[] qt_all = { qt_0, qt_1, qt_2, qt_3 };
		for (LeatherArmorMeta qt : qt_all) {
			qt.setColor(Color.fromRGB(PRETTY_PINK_COLOR));
		}
		CUTE_HAT.setItemMeta(qt_0);
		GORGEOUS_SWEATER.setItemMeta(qt_1);
		ADORABLE_JEANS.setItemMeta(qt_2);
		OMG_SHOES.setItemMeta(qt_3);
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
		if (rng.nextInt(100) > 11)
			return;
		if (GZFireZombies.isHellZombie(event.getEntity()))
			return;
		if (GZPlagueZombies.isPlagueZombie(event.getEntity()))
			return;
		if (GZSprintZombies.isSprintZombie(event.getEntity()))
			return;
		GroundZero.getMobRegister().addEntityToAccount(MOB_REGISTER_INFO, event.getEntity());
		event.getEntity().getEquipment().clear();
		event.getEntity().getEquipment().setArmorContents(new ItemStack[] {
			CUTE_HAT,
			GORGEOUS_SWEATER,
			ADORABLE_JEANS,
			OMG_SHOES
		});
		event.getEntity().setCustomName(ZOMBIE_CUSTOM_NAME);
		event.getEntity().setCustomNameVisible(true);
	}
	
	@EventHandler
	public void onZombieAttack(EntityDamageByEntityEvent event) {
		if (!event.getDamager().getType().equals(EntityType.ZOMBIE))
			return;
		if (!event.getEntityType().equals(EntityType.PLAYER))
			return;
		if (!isGayZombie(event.getDamager()))
			return;
		((Player)event.getEntity()).sendMessage("<faget>: " +
				GAY_ATTACK_MESSAGES[rng.nextInt(GAY_ATTACK_MESSAGES.length)]);
		PotionEffectType type = PotionEffectType.CONFUSION;
		((LivingEntity)event.getEntity()).addPotionEffect(type.createEffect(100, 1));
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onZombieDeath(EntityDeathEvent event) {
		if (!event.getEntityType().equals(EntityType.ZOMBIE))
			return;
		if (!isGayZombie(event.getEntity()))
			return;
		// clean up after ourselves, make sure that shit is removed upon death
		GroundZero.getMobRegister().removeEntityFromAccount(MOB_REGISTER_INFO, event.getEntity());
	}
	
	public static boolean isGayZombie(Entity e) {
		return GroundZero.getMobRegister().isEntityInsideAccount(MOB_REGISTER_INFO, e);
	}
}
