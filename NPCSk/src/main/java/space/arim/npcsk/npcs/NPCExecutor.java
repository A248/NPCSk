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

/**
 * An API layer for managing NPCs, without having to access NPCLib directly. <br>
 * <br>
 * Implementations should be thread safe.
 * 
 * @author A248
 *
 */
public interface NPCExecutor extends Registrable, AutoClosable {

	/**
	 * The ID of the last NPC created using {@link #createNpc(List, int, Location)}
	 * 
	 * @return the id of the last created NPC
	 */
	String getLatestId();
	
	/**
	 * Creates an NPC with name, skin ID, and location. <br>
	 * <br>
	 * The skin is fetched asynchronously and may therefore load
	 * after the NPC is spawned, causing a slight delay in client rendering. <br>
	 * <br>
	 * Implementations must parse colours and styling according to '{@literal &}' formatting codes.
	 * 
	 * @param name a list of text which will become the NPC's display name
	 * @param mineskinId the mineskin skin id
	 * @param location where to spawn the NPC
	 * @return the ID of the NPC created
	 */
	String createNpc(List<String> name, int mineskinId, Location location);
	
	/**
	 * Gets the location of an NPC by ID
	 * 
	 * @param id the NPC ID
	 * @return the location, or <code>null</code> if no such NPC exists
	 */
	Location getLocation(String id);
	
	/**
	 * Gets whether an NPC is visible for a specific player. <br>
	 * <br>
	 * If visible, the player will see the NPC when the NPC
	 * is within the distance specified by {@link #getAutoHide()}. <br>
	 * If invisible, the player will never see the NPC. <br>
	 * <br>
	 * Use {@link #setShown(String, Player, boolean)} to change the visibility.
	 * 
	 * @param id the NPC ID
	 * @param target the player for whom the NPC is shown or hidden
	 * @return true if shown, false otherwise
	 */
	boolean isShown(String id, Player target);
	
	/**
	 * Makes a NPC visible or invisible for a specific player. <br>
	 * <br>
	 * If visible, the player will see the NPC when the NPC
	 * is within the distance specified by {@link #getAutoHide()}. <br>
	 * If invisible, the player will never see the NPC. <br>
	 * <br>
	 * If the NPC by the ID specified does not exist, <code>false</code> is returned. <br>
	 * If the NPC is already visible or already invisible, <code>false</code> is returned. <br>
	 * Otherwise, <code>true</code> is returned
	 * 
	 * @param id the NPC ID
	 * @param target the player for whom to show or hide the NPC
	 * @return true if the NPC exists and the visibiliy to the player changed as a result of the call, false otherwise
	 */
	boolean setShown(String id, Player target, boolean show);
	
	/**
	 * Sets the item in the NPC's slot to the given item. <br>
	 * <br>
	 * Available slots are: <br>
	 * * "HELMET" <br>
	 * * "CHESTPLATE" <br>
	 * * "LEGGINGS" <br>
	 * * "BOOTS" <br>
	 * * "MAINHAND" <br>
	 * * "OFFHAND" (1.9+ only) <br>
	 * (as of February 2020) <br>
	 * <br>
	 * The slot is provided as a <code>String</code> so that NPCSk will
	 * automatically support additions to the NPCLib's <code>NPCSlot</code> enum.
	 * (The string is resolved to the enum at runtime) <br>
	 * <br>
	 * If either the item or the slot is <code>null</code>, or the slot is invalid,
	 * nothing happens; <code>false</code> is returned.
	 * 
	 * @param id the NPC ID
	 * @param item the item
	 * @param slot the slot
	 * @return true if the NPC exists, the item is nonnull, and the slot is valid; false otherwise
	 */
	boolean setNPCSlot(String id, ItemStack item, String slot);
	
	/**
	 * Gets the current item in the NPC's slot. <br>
	 * <br>
	 * See {@link #setNPCSlot(String, ItemStack, String)} for valid NPC slots. <br>
	 * <br>
	 * If NPC by the specified ID does not exist, or the slot is
	 * <code>null</code> or invalid, <code>null</code> is returned.
	 * 
	 * @param id the NPC ID
	 * @param slot the slot
	 * @return the item in the NPC's slot
	 */
	ItemStack getNPCSlot(String id, String slot);
	
	/**
	 * Toggles the specified NPC state of a NPC on or off. <br>
	 * <br>
	 * Available states are: <br>
	 * * "ON_FIRE" <br>
	 * * "CROUCHED" <br>
	 * * "INVISIBLE" <br>
	 * (as of February 2020) <br>
	 * <br>
	 * The state is provided as a <code>String</code> so that NPCSk will
	 * automatically support additions to the NPCLib's <code>NPCState</code> enum.
	 * (The string is resolved to the enum at runtime) <br>
	 * 
	 * @param id the NPC ID
	 * @param state the state
	 * @param value whether to toggle on or off
	 * @return true if the NPC exists, the state is valid, and the state changed as a result of the call; false otherwise
	 */
	boolean setNPCState(String id, String state, boolean value);
	
	/**
	 * Gets whether the current state of the NPC is on or off. <br>
	 * <br>
	 * See {@link #setNPCState(String, String, boolean)} for valid NPC states. <br>
	 * <br>
	 * If NPC by the specified ID does not exist, or the state is
	 * <code>null</code> or invalid, <code>false</code> is returned.
	 * 
	 * @param id the NPC ID
	 * @param state the state
	 * @return true if the NPC state is on, false otherwise
	 */
	boolean getNPCState(String id, String state);
	
	/**
	 * Gets all NPC ids. <br>
	 * <br>
	 * The returned set is an unmodifiable view.
	 * The set automatically reflects new NPCs added
	 * or existing NPCs deleted. Also, writes are not supported.
	 * 
	 * @return a backed set of NPC ids
	 */
	Set<String> getAll();
	
	/**
	 * Gets whether the NPC by the specified ID exists
	 * 
	 * @param id the NPC ID
	 * @return true if the NPC exists, false otherwise
	 */
	boolean hasNpc(String id);
	
	/**
	 * Sets the NPC auto hide distance to the given value. <br>
	 * <br>
	 * If {@link #isShown(String, Player)} is false, or the player
	 * is outside the auto hide distance, the NPC will be hidden. <br>
	 * Otherwise, if the NPC is visible for the player and the player
	 * is within the distance, the player will see the NPC.
	 * 
	 * @param distance the distance
	 */
	void setAutoHide(double distance);
	
	/**
	 * Gets the auto hide distance described in {@link #setAutoHide(double)}
	 * 
	 * @return the distance
	 */
	double getAutoHide();
	
	/**
	 * Sets the auto hide distance back to the default value. <br>
	 * <br>
	 * See {@link #setAutoHide(double)}
	 * 
	 */
	void clearAutoHide();
	
	/**
	 * Deletes the NPC by the given ID. <br>
	 * The NPC is removed permanently.
	 * 
	 * @param id the NPC ID
	 * @return true if the NPC existed and was deleted, false otherwise
	 */
	boolean delNpc(String id);
	
	/**
	 * Deletes all NPCs.
	 * 
	 */
	void deleteAll();
	
}
