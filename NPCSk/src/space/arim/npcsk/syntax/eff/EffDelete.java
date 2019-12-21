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
package space.arim.npcsk.syntax.eff;

import org.eclipse.jdt.annotation.Nullable;

import org.bukkit.event.Event;

import space.arim.npcsk.NPCSk;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Name("NPCSk Delete NPC")
@Description("Removes a NPC.")
@Since("0.6.0")
public class EffDelete extends Effect {
	
	private Expression<String> id;
	
	static {
		Skript.registerEffect(EffDelete.class, "[npcsk] (delete|remove|clear) npc [(with|from) id] %string%");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int arg1, Kleenean arg2, ParseResult arg3) {
		id = (Expression<String>) exprs[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event evt, boolean debug) {
		return "npcsk delete npc from id " + id.toString(evt, debug);
	}

	@Override
	protected void execute(Event evt) {
		NPCSk.npcs().delNpc(id.getSingle(evt));
	}

}
