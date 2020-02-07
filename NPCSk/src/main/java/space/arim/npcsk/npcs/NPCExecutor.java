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

import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import space.arim.universal.registry.Registrable;
import space.arim.universal.util.AutoClosable;

public interface NPCExecutor extends Registrable, AutoClosable {

	String getLatestId();
	
	String createNpc(List<String> name, int mineskinId, Location location);
	
	Location getLocation(String id);
	
	boolean isShown(String id, Player target);
	
	boolean setShown(String id, Player target, boolean show);
	
	boolean setNPCSlot(String id, ItemStack item, String slot);
	
	ItemStack getNPCSlot(String id, String slot);
	
	boolean setNPCState(String id, String state, boolean value);
	
	boolean getNPCState(String id, String state);
	
	Set<String> getAll();
	
	boolean hasNpc(String id);
	
	void setAutoHide(double distance);
	
	double getAutoHide();
	
	void clearAutoHide();
	
	boolean delNpc(String id);
	
	void delAll();
	
}
