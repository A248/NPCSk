/* 
 * NPCSk
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
package space.arim.npcsk;

import java.io.IOException;
import java.util.HashMap;

import org.bukkit.plugin.java.JavaPlugin;

import space.arim.npcsk.npcs.NPCExecutor;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;

public class NPCSk extends JavaPlugin {
	
	private SkriptAddon addon;
	private static NPCExecutor npcs;
	
	private static final HashMap<String, Object> LATEST = new HashMap<String, Object>();
	
	private void shutter(String reason) {
		getLogger().severe("**SEVERE**: Unable to load NPCSk's features! Reason: " + reason + ". Shutting down...");
		getServer().getPluginManager().disablePlugin(this);
	}
	
	private void error(String reason) {
		shutter(reason);
		throw new IllegalStateException(reason);
	}
	
	private void error(String reason, Exception cause) {
		shutter(reason);
		throw new IllegalStateException(reason, cause);
	}
	
	private void tryLoad() throws IOException {
		try {
			Class.forName("ch.njol.skript.Skript");
			Class.forName("net.jitse.npclib.NPCLib");
		} catch (ClassNotFoundException ex) {
			error("Skript and/or NPCLib dependencies not found!", ex);
		}
		if (!Skript.isAcceptRegistrations()) {
			error("Skript is not accepting syntax registrations");
		}
		addon = Skript.registerAddon(this);
		addon.loadClasses("space.arim.npcsk.syntax", "cond", "eff", "evt", "expr");
		npcs = new NPCExecutor(this);
	}
	
	@Override
	public void onEnable() {
		try {
			tryLoad();
		} catch (IllegalStateException | IOException ex) {
			ex.printStackTrace();
			return;
		}
		getLogger().info("Everything ready. Starting...");
	}
	
	@Override
	public void onDisable() {
		npcs.close();
	}
	
	public static Object getLatest(String type) {
		return LATEST.get(type);
	}
	
	public static void setLatest(String type, Object latest) {
		LATEST.put(type, latest);
	}
	
	public static NPCExecutor npcs() {
		return npcs;
	}
}
