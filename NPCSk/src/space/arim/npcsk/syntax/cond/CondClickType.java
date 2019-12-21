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

import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import net.jitse.npclib.api.events.NPCInteractEvent;

@Name("NPCSk Interaction Clicktype")
@Description("Determines whether the interaction was a leftclick or rightclick.")
@Examples({"on npc interact:","\tif clicktype is leftclick:","\t\t#do stuff"})
@Since("0.6.0")
public class CondClickType extends Condition {

	static {
		Skript.registerCondition(CondClickType.class, "[npcsk] [npc] [interaction] clicktype is (1¦leftclick|2¦rightclick)");
	}
	
	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult parser) {
		if (!ScriptLoader.isCurrentEvent(NPCInteractEvent.class)) {
			Skript.error("The npcsk condition 'npc clicktype' may only be used in npc events");
			return false;
		}
		setNegated(parser.mark == 2);
		return true;
	}

	@Override
	public String toString(@Nullable Event evt, boolean debug) {
		return "npcsk npc interaction clicktype";
	}

	@Override
	public boolean check(Event evt) {
		boolean left = ((NPCInteractEvent) evt).getClickType() == NPCInteractEvent.ClickType.LEFT_CLICK;
		return isNegated() ? !left : left;
	}
}
