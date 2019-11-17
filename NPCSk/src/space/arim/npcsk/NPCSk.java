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

import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptEventInfo;
import net.jitse.npclib.api.events.NPCInteractEvent;
import space.arim.npcsk.cond.CondNPCRightClick;
import space.arim.npcsk.eff.EffNPCDel;
import space.arim.npcsk.evt.EvtNPCInteract;
import space.arim.npcsk.npcs.NPCExecutor;

public class NPCSk extends JavaPlugin {
	
	private SkriptAddon addon;
	private static NPCExecutor npcs;
	
	private static final String SYNTAX_PREFIX = "[npcsk] ";
	
	private void shutter(String reason) {
		getLogger().severe("**SEVERE**: Unable to load NPCSk's features! Reason: " + reason + ". Shutting down...");
		getServer().getPluginManager().disablePlugin(this);
	}
	
	private void error(String reason) throws IllegalStateException {
		shutter(reason);
		throw new IllegalStateException(reason);
	}
	
	private void error(String reason, Exception cause) throws IllegalStateException {
		shutter(reason);
		throw new IllegalStateException(reason, cause);
	}
	
	private void tryLoad() throws IllegalStateException {
		try {
			Class.forName("ch.njol.skript.Skript");
			Class.forName("net.jitse.npclib.NPCLib");
		} catch (ClassNotFoundException ex) {
			error("Skript and/or NPCLib dependencies not found!", ex);
		}
		if (!Skript.isAcceptRegistrations()) {
			error("Skript is not accepting syntax registrations.");
		}
		addon = Skript.registerAddon(this);
		loadClasses();
		if (npcs == null) {
			error("NPC executor is already loaded!");
		}
		npcs = new NPCExecutor(this);
	}
	
	private static String[] modifyPatterns(String[] patterns) {
		for (int n = 0; n < patterns.length; n++) {
			patterns[n] = SYNTAX_PREFIX + patterns[n];
		}
		return patterns;
	}
	
	private static <E extends SkriptEvent> SkriptEventInfo<E> event(final String name, final Class<E> c, final Class<? extends Event> event, String... patterns) {
		patterns = modifyPatterns(patterns);
		return Skript.registerEvent(name, c, event, patterns);
	}
	
	private static <E extends Condition> void condition(final Class<E> condition, String...patterns) {
		patterns = modifyPatterns(patterns);
		Skript.registerCondition(condition, patterns);
	}
	
	private static <E extends Effect> void effect(final Class<E> effect, String...patterns) {
		patterns = modifyPatterns(patterns);
		Skript.registerEffect(effect, patterns);
	}
	
	private static <E extends Expression<T>, T> void expression(final Class<E> c, final Class<T> returnType, final ExpressionType type, String... patterns) {
		patterns = modifyPatterns(patterns);
		Skript.registerExpression(c, returnType, type, patterns);
	}
	
	private void loadClasses() {
		event("NPC Interact", EvtNPCInteract.class, NPCInteractEvent.class, "npc interact [event]");
		condition(CondNPCRightClick.class, "clicktype (1¦is|2¦is(n't| not)) leftclick");
		effect(EffNPCDel.class, "[arimsk] (delete|remove) npc [with id] %string%");
	}
	
	@Override
	public void onEnable() {
		try {
			tryLoad();
		} catch (IllegalStateException ex) {
			ex.printStackTrace();
			return;
		}
		getLogger().info("Everything ready. Starting...");
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public SkriptAddon getAddonInstance() {
		return addon;
	}
	
	public static NPCExecutor npcs() {
		return npcs;
	}
}
