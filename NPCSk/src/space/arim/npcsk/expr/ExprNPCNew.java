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
package space.arim.npcsk.expr;

import org.bukkit.Location;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import space.arim.npcsk.NPCSk;

public class ExprNPCNew extends SimpleExpression<String> {
	static {
		Skript.registerExpression(ExprNPCNew.class, String.class, ExpressionType.COMBINED, "[arimsk] (create|make|new) npc [(of|with)] (name|title) %string% (skin|skinid) %number% (loc|location) %location%");
	}
	private Expression<String> name;
	private Expression<Location> location;
	private Expression<Number> skinid;
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int arg1, Kleenean arg2, ParseResult arg3) {
		name = (Expression<String>) exprs[0];
		skinid = (Expression<Number>) exprs[1];
		location = (Expression<Location>) exprs[2];
		return true;
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		return "arimsk new npc with name " + name.toString(event, debug) + ", " + skinid.toString(event, debug) + ", and location " + location.toString(event, debug) + ".";
	}

	@Override
	@Nullable
	protected String[] get(Event evt) {
		return new String[] {NPCSk.npcs().createNpc(name.getSingle(evt), skinid.getSingle(evt).intValue(), location.getSingle(evt))};
	}

}
