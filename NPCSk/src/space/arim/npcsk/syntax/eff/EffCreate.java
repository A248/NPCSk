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
package space.arim.npcsk.syntax.eff;

import java.util.Arrays;

import org.eclipse.jdt.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.event.Event;

import space.arim.npcsk.NPCSk;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Name("NPCSk Create NPC")
@Description("Creates a new NPC. Skins are according to Mineskin. By default, the NPC is not shown to any player. You must show it to them with \"set visibility of last created npc to true\"")
@Examples({"Command /createnpc <text>:", "\tTrigger:", "\t\tcreate npc with name arg 1, skin 100680423, and location player's location",})
@Since("0.6.0")
public class EffCreate extends Effect {

	private Expression<String> name;
	private Expression<Location> location;
	private Expression<Number> skinid;
	
	static {
		Skript.registerEffect(EffCreate.class, "[npcsk] create npc with name %strings%, skin %number%, and location %location%");
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
	public String toString(@Nullable Event evt, boolean debug) {
		return "npcsk create npc with name " + name.toString(evt, debug) + ", skin " + skinid.toString(evt, debug) + ", and location " + location.toString(evt, debug);
	}

	@Override
	protected void execute(Event evt) {
		NPCSk.npcs().createNpc(Arrays.asList(name.getArray(evt)), skinid.getSingle(evt).intValue(), location.getSingle(evt));
	}

}
