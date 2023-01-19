/* 
 * NPCSk, a robust Skript NPC addon
 * Copyright © 2023 Anand Beh <https://www.arim.space>
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

import org.bukkit.plugin.java.JavaPlugin;

import space.arim.npcsk.npcs.NPCExecutor;
import space.arim.npcsk.npcs.NPCSkExecutor;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;

public class NPCSk extends JavaPlugin {

	private static NPCSkExecutor executor;

	private void error(String reason, Exception cause) {
		getLogger().severe("**ERROR**: Unable to load NPCSk's features! Reason: " + reason + ". Shutting down...");
		getServer().getPluginManager().disablePlugin(this);
		if (cause != null) {
			cause.printStackTrace();
		}
	}
	
	private boolean tryLoad() throws IOException, ClassNotFoundException {
		Class.forName("ch.njol.skript.Skript");
		Class.forName("net.jitse.npclib.NPCLib");
		if (!Skript.isAcceptRegistrations()) {
			error("Skript is not accepting syntax registrations", null);
			return false;
		}
		SkriptAddon addon = Skript.registerAddon(this);
		addon.loadClasses("space.arim.npcsk.syntax", "cond", "eff", "evt", "expr");
		executor = new NPCSkExecutor(this);
		return true;
	}
	
	@Override
	public void onEnable() {
		try {
			if (tryLoad()) {
				getLogger().info("Everything ready. Starting...");
			}
		} catch (IOException ex) {
			error("Could not load syntax classes", ex);
		} catch (ClassNotFoundException ex) {
			error("Skript and/or NPCLib dependencies not found", ex);
		}
	}
	
	public static NPCExecutor npcs() {
		return executor;
	}

}
