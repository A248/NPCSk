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
package space.arim.npcsk.eff;

import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import space.arim.npcsk.NPCSk;

public class EffNPCSetAutoHide extends Effect {
	private Expression<Number> radius;
	static {
		Skript.registerEffect(EffNPCSetAutoHide.class, "[arimsk] set npc [auto] hide [(radius|distance)] to %number%");
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int arg1, Kleenean arg2, ParseResult arg3) {
		radius = (Expression<Number>) exprs[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event event, boolean debug) {
		return "arimsk set npc auto hide distance to " + radius.toString(event, debug);
	}
	@Override
	protected void execute(Event evt) {
		NPCSk.npcs().setAutoHide(radius.getSingle(evt).doubleValue());
	}
	
}
