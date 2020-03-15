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

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import space.arim.universal.registry.RegistryPriority;

import net.jitse.npclib.NPCLib;
import net.jitse.npclib.api.NPC;
import net.jitse.npclib.api.skin.MineSkinFetcher;
import net.jitse.npclib.api.state.NPCSlot;
import net.jitse.npclib.api.state.NPCState;

public class NPCSkExecutor implements NPCExecutor {
	
	private final NPCLib lib;
	private final double defaultAutoHide;
	private final ConcurrentHashMap<String, NPC> npcs = new ConcurrentHashMap<String, NPC>();
	private volatile Set<String> idsView;
	private volatile String latest;
	
	public NPCSkExecutor(JavaPlugin plugin) {
		lib = new NPCLib(plugin);
		defaultAutoHide = lib.getAutoHideDistance();
	}
	
	private List<String> encodeAll(List<String> input) {
		for (int n = 0; n < input.size(); n++) {
			input.set(n, ChatColor.translateAlternateColorCodes('&', input.get(n)));
		}
		return input;
	}
	
	@Override
	public String getLatestId() {
		return latest;
	}
	
	@Override
	public String createNpc(List<String> name, int mineskinId, Location loc) {
		NPC npc = lib.createNPC(encodeAll(name));
		MineSkinFetcher.fetchSkinFromIdAsync(mineskinId, npc::setSkin);
		npc.setLocation(loc);
		npc.create();
		latest = npc.getId();
		npcs.put(latest, npc);
		return latest;
	}
	
	@Override
	public Location getLocation(String id) {
		NPC npc = npcs.get(id);
		return (npc != null) ? npc.getLocation() : null;
	}
	
	@Override
	public boolean isShown(String id, Player target) {
		NPC npc = npcs.get(id);
		return (npc != null) && npc.isShown(target);
	}
	
	@Override
	public boolean setShown(String id, Player target, boolean show) {
		return (show) ? show(id, target) : hide(id, target);
	}
	
	private boolean show(String id, Player target) {
		NPC npc = npcs.get(id);
		if (npc != null && !npc.isShown(target)) {
			npc.show(target);
			return true;
		}
		return false;
	}
	
	private boolean hide(String id, Player target) {
		NPC npc = npcs.get(id);
		if (npc != null && npc.isShown(target)) {
			npc.hide(target);
			return true;
		}
		return false;
	}
	
	private boolean setForSlot(String id, ItemStack item, NPCSlot slot) {
		NPC npc = npcs.get(id);
		if (npc != null && slot != null) {
			npc.setItem(slot, item);
			return true;
		}
		return false;
	}
	
	private ItemStack getForSlot(String id, NPCSlot slot) {
		NPC npc = npcs.get(id);
		return (npc != null && slot != null) ? npc.getItem(slot) : null;
	}
	
	@Override
	public boolean setNPCSlot(String id, ItemStack item, String slot) {
		return setForSlot(id, item, slotFromString(slot));
	}
	
	@Override
	public ItemStack getNPCSlot(String id, String slot) {
		return getForSlot(id, slotFromString(slot));
	}
	
	private NPCSlot slotFromString(String slotname) {
		for (NPCSlot slot : NPCSlot.values()) {
			if (slot.name().equalsIgnoreCase(slotname)) {
				return slot;
			}
		}
		return null;
	}
	
	private boolean setForState(String id, NPCState state, boolean value) {
		NPC npc = npcs.get(id);
		if (npc != null && state != null && (!npc.getState(state) && value || npc.getState(state) && !value)) {
			npc.toggleState(state);
			return true;
		}
		return false;
	}
	
	private boolean getForState(String id, NPCState state) {
		NPC npc = npcs.get(id);
		return (npc != null && state != null) && npc.getState(state);
	}
	
	@Override
	public boolean setNPCState(String id, String state, boolean value) {
		return setForState(id, stateFromString(state), value);
	}
	
	@Override
	public boolean getNPCState(String id, String state) {
		return getForState(id, stateFromString(state));
	}
	
	private NPCState stateFromString(String statename) {
		for (NPCState state : NPCState.values()) {
			if (state.name().equalsIgnoreCase(statename)) {
				return state;
			}
		}
		return null;
	}
	
	@Override
	public Set<String> getAll() {
		return (idsView != null) ? idsView : (idsView = Collections.unmodifiableSet(npcs.keySet()));
	}
	
	@Override
	public boolean hasNpc(String id) {
		return npcs.containsKey(id);
	}
	
	@Override
	public void setAutoHide(double distance) {
		lib.setAutoHideDistance(distance);
	}
	
	@Override
	public double getAutoHide() {
		return lib.getAutoHideDistance();
	}
	
	@Override
	public void clearAutoHide() {
		lib.setAutoHideDistance(defaultAutoHide);
	}
	
	@Override
	public boolean delNpc(String id) {
		NPC npc = npcs.get(id);
		if (npc != null && npcs.remove(npc.getId()) == npc) {
			npc.destroy();
			return true;
		}
		return false;
	}
	
	@Override
	public void delAll() {
		while (!npcs.isEmpty()) {
			Set<String> ids = new HashSet<String>(npcs.keySet());
			ids.forEach(this::delNpc);
		}
	}
	
	@Override
	public void close() {
		delAll();
	}
	
	@Override
	public String getName() {
		return lib.getPlugin().getDescription().getName();
	}
	
	@Override
	public String getVersion() {
		return lib.getPlugin().getDescription().getVersion();
	}
	
	@Override
	public byte getPriority() {
		return RegistryPriority.LOWER;
	}
	
}
