/* 
 * NPCSk, a robust Skript NPC addon
 * Copyright © 2019 Anand Beh <https://www.arim.space>
 * 
 * NPCSk is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * NPCSk is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with NPCSk. If not, see <https://www.gnu.org/licenses/>
 * and navigate to version 3 of the GNU General Public License.
 */
package space.arim.npcsk.npcs;

import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import net.jitse.npclib.NPCLib;
import net.jitse.npclib.api.NPC;
import net.jitse.npclib.api.skin.MineSkinFetcher;
import net.jitse.npclib.api.state.NPCSlot;

public class NPCExecutor implements AutoCloseable {
	
	private final NPCLib lib;
	private final double defaultAutoHide;
	private final HashMap<String, NPC> npcs = new HashMap<String, NPC>();
	private String latest;
	
	public NPCExecutor(JavaPlugin main) {
		lib = new NPCLib(main);
		defaultAutoHide = lib.getAutoHideDistance();
	}

	private List<String> encodeAll(List<String> input) {
		for (int n = 0; n < input.size(); n++) {
			input.set(n, ChatColor.translateAlternateColorCodes('&', input.get(n)));
		}
		return input;
	}
	
	public String getLatestId() {
		return latest;
	}
	
	public String createNpc(List<String> name, int mineskinId, Location loc) {
		NPC npc = lib.createNPC(encodeAll(name));
		npc.setLocation(loc);
		MineSkinFetcher.fetchSkinFromIdAsync(mineskinId, skin -> {
			npc.setSkin(skin);
		});
		npc.create();
		latest = npc.getId();
		npcs.put(latest, npc);
		return latest;
	}
	
	public Location getLocation(String id) {
		return npcs.containsKey(id) ? npcs.get(id).getLocation() : null;
	}
	
	public boolean isShown(String id, Player target) {
		return npcs.containsKey(id) && npcs.get(id).isShown(target);
	}
	
	public boolean setShown(String id, Player target, boolean show) {
		return show ? show(id, target) : hide(id, target);
	}
	
	private boolean show(String id, Player target) {
		if (npcs.containsKey(id) && !npcs.get(id).isShown(target)) {
			npcs.get(id).show(target);
			return true;
		}
		return false;
	}
	
	private boolean hide(String id, Player target) {
		if (npcs.containsKey(id) && npcs.get(id).isShown(target)) {
			npcs.get(id).hide(target);
			return true;
		}
		return false;
	}
	
	private boolean setForSlot(String id, ItemStack item, NPCSlot slot) {
		if (slot != null && npcs.containsKey(id)) {
			npcs.get(id).setItem(slot, item);
			return true;
		}
		return false;
	}
	
	private ItemStack getForSlot(String id, NPCSlot slot) {
		return slot == null ? null : npcs.containsKey(id) ? npcs.get(id).getItem(slot) : null;
	}
	
	public boolean setNPCSlot(String id, ItemStack item, String slot) {
		return setForSlot(id, item, slotFromString(slot));
	}
	
	public ItemStack getNPCSlot(String id, String slot) {
		return getForSlot(id, slotFromString(slot));
	}
	
	private NPCSlot slotFromString(String slotname) {
		for (NPCSlot slot : NPCSlot.values()) {
			if (slotname.equalsIgnoreCase(slot.name())) {
				return slot;
			}
		}
		return null;
	}
	
	public boolean hasNpc(String id) {
		return npcs.containsKey(id);
	}
	
	public void setAutoHide(double distance) {
		lib.setAutoHideDistance(distance);
	}
	
	public double getAutoHide() {
		return lib.getAutoHideDistance();
	}
	
	public void clearAutoHide() {
		lib.setAutoHideDistance(defaultAutoHide);
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
	
	@Override
	public void close() {
		delAll();
	}
	
}
