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
package space.arim.npcsk.syntax.expr;

import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import net.jitse.npclib.api.events.NPCInteractEvent;

@Name("NPCSk Event-NPC")
@Description("The NPC in a NPC interact event.")
@Since("0.6.0")
public class ExprEventNPCNPCSk extends SimpleExpression<String> {
	
	static {
		Skript.registerExpression(ExprEventNPCNPCSk.class, String.class, ExpressionType.SIMPLE, "[npcsk] npc-event-npc");
	}

	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		if (!ScriptLoader.isCurrentEvent(NPCInteractEvent.class)) {
			Skript.error("The arimsk expression 'npc-event-npc' may only be used in npc events");
			return false;
		}
		return true;
	}

	@Override
	public String toString(@Nullable Event evt, boolean debug) {
		return "npcsk npc-event-npc";
	}

	@Override
	@Nullable
	protected String[] get(Event evt) {
		return new String[] {((NPCInteractEvent) evt).getNPC().getId()};
	}
	

}
