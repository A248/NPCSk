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
package space.arim.npcsk.evt;

import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import net.jitse.npclib.api.events.NPCInteractEvent;

public class EvtNPCInteract extends SkriptEvent {
	
	@Override
	public boolean init(Literal<?>[] args, int arg1, ParseResult arg2) {
		return true;
	}
	
	@Override
	public String toString(@Nullable Event event, boolean debug) {
		return "arimsk npc interact event.";
	}

	@Override
	public boolean check(Event evt) {
		if (evt instanceof NPCInteractEvent) {
			return true;
		}
		return false;
	}
	
}
