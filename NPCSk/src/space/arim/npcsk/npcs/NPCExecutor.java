package space.arim.npcsk.npcs;

import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import net.jitse.npclib.NPCLib;
import net.jitse.npclib.api.NPC;
import net.jitse.npclib.api.skin.MineSkinFetcher;
import net.jitse.npclib.api.state.NPCSlot;

public class NPCExecutor {
	
	private final NPCLib lib;
	private final HashMap<String, NPC> npcs = new HashMap<String, NPC>();
	
	public NPCExecutor(JavaPlugin main) {
		lib = new NPCLib(main);
	}

	public String createNpc(String name, int mineskinId, Location loc) {
		NPC npc = lib.createNPC(Arrays.asList(new String[] {ChatColor.translateAlternateColorCodes('&', name)}));
		npc.setLocation(loc);
		MineSkinFetcher.fetchSkinFromIdAsync(mineskinId, skin -> {
			npc.setSkin(skin);
		});
		npc.create();
		npcs.put(npc.getId(), npc);
		return npc.getId();
	}
	
	public Location getLocation(String id) {
		if (npcs.containsKey(id)) {
			return npcs.get(id).getLocation();
		}
		return null;
	}
	
	public boolean show(String id, Player...targets) {
		if (npcs.containsKey(id)) {
			for (Player p : targets) {
				if (!npcs.get(id).isShown(p)) {
					npcs.get(id).show(p);
				}
			}
			return true;
		}
		return false;
	}
	public boolean isShown(String id, Player target) {
		if (npcs.containsKey(id)) {
			return npcs.get(id).isShown(target);
		}
		return false;
	}
	public boolean hide(String id, Player...targets) {
		if (npcs.containsKey(id)) {
			for (Player p : targets) {
				if (npcs.get(id).isShown(p)) {
					npcs.get(id).hide(p);
				}
			}
			return true;
		}
		return false;
	}
	
	public boolean setTool(String id, ItemStack item) {
		if (npcs.containsKey(id)) {
			npcs.get(id).setItem(NPCSlot.MAINHAND, item);
			return true;
		}
		return false;
	}
	public ItemStack getTool(String id) {
		if (npcs.containsKey(id)) {
			return npcs.get(id).getItem(NPCSlot.MAINHAND);
		}
		return null;
	}
	
	public boolean setHelmet(String id, ItemStack item) {
		if (npcs.containsKey(id)) {
			npcs.get(id).setItem(NPCSlot.HELMET, item);
			return true;
		}
		return false;
	}
	public boolean setChestplate(String id, ItemStack item) {
		if (npcs.containsKey(id)) {
			npcs.get(id).setItem(NPCSlot.CHESTPLATE, item);
			return true;
		}
		return false;
	}
	public boolean setLeggings(String id, ItemStack item) {
		if (npcs.containsKey(id)) {
			npcs.get(id).setItem(NPCSlot.LEGGINGS, item);
			return true;
		}
		return false;
	}
	public boolean setBoots(String id, ItemStack item) {
		if (npcs.containsKey(id)) {
			npcs.get(id).setItem(NPCSlot.BOOTS, item);
			return true;
		}
		return false;
	}
	
	public ItemStack getHelmet(String id) {
		if (npcs.containsKey(id)) {
			return npcs.get(id).getItem(NPCSlot.HELMET);
		}
		return null;
	}
	public ItemStack getChestplate(String id) {
		if (npcs.containsKey(id)) {
			return npcs.get(id).getItem(NPCSlot.CHESTPLATE);
		}
		return null;
	}
	public ItemStack getLeggings(String id) {
		if (npcs.containsKey(id)) {
			return npcs.get(id).getItem(NPCSlot.LEGGINGS);
		}
		return null;
	}
	public ItemStack getBoots(String id) {
		if (npcs.containsKey(id)) {
			return npcs.get(id).getItem(NPCSlot.BOOTS);
		}
		return null;
	}
	
	public boolean hasNpc(String id) {
		if (npcs.containsKey(id)) {
			return true;
		}
		return false;
	}
	
	public void setAutoHide(Double distance) {
		lib.setAutoHideDistance(distance);
	}
	public Double getAutoHide() {
		return lib.getAutoHideDistance();
	}
	
	public boolean delNpc(String id) {
		if (npcs.containsKey(id)) {
			npcs.get(id).destroy();
			npcs.remove(id);
			return true;
		}
		return false;
	}
	
	public void delAll() {
		for (HashMap.Entry<String, NPC> val : npcs.entrySet()) {
			val.getValue().destroy();
		}
		npcs.clear();
	}
}
