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
package space.arim.npcsk.syntax.cond;

import org.eclipse.jdt.annotation.Nullable;

import org.bukkit.event.Event;

import space.arim.npcsk.NPCSk;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Name("NPCSk NPC Existence")
@Description("Whether a NPC by an id exists.")
@Examples({"if npc {_hubnpc} exists:","\tsend \"The hub npc already exists!\""})
@Since("0.6.0")
public class CondExists extends Condition {

	private Expression<String> id;
	
	static {
		Skript.registerCondition(CondExists.class, "[npcsk] npc %string% exists");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int arg1, Kleenean arg2, ParseResult arg3) {
		id = (Expression<String>) exprs[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event evt, boolean debug) {
		return "npcsk npc " + id.toString(evt, debug) + " exists";
	}

	@Override
	public boolean check(Event evt) {
		return NPCSk.npcs().hasNpc(id.getSingle(evt));
	}
	
}
