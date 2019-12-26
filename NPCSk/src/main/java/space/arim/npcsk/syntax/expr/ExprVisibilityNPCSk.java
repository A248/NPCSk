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

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import space.arim.npcsk.NPCSk;

@Name("NPCSk Visibility")
@Description("Whether a NPC is visible for a player. NPCs may be hidden or shown for specific players")
@Examples({"on npc interact:", "\tset npc visibility of npc-event-npc for npc-event-player to true"})
@Since("0.7.0")
public class ExprVisibilityNPCSk extends SimpleExpression<Boolean> {
	
	private Expression<String> id;
	private Expression<Player> target;
	
	static {
		Skript.registerExpression(ExprVisibilityNPCSk.class, Boolean.class, ExpressionType.COMBINED, "[npcsk] npc visibility of %string% for %player%");
	}
	
	@Override
	public Class<? extends Boolean> getReturnType() {
		return Boolean.class;
	}
	
	@Override
	public boolean isSingle() {
		return true;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int arg1, Kleenean arg2, ParseResult arg3) {
		id = (Expression<String>) exprs[0];
		target = (Expression<Player>) exprs[1];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event evt, boolean debug) {
		return "npcsk npc visibility of " + id.toString(evt, debug) + " for " + target.toString(evt, debug);
	}
	
	@Override
	@Nullable
	protected Boolean[] get(Event evt) {
		return new Boolean[] {NPCSk.npcs().isShown(id.getSingle(evt), target.getSingle(evt))};
	}
	
	@Override
	public Class<?>[] acceptChange(ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.RESET) {
			return new Class<?>[] {Boolean.class};
		}
		return null;
	}
	
	@Override
	public void change(Event evt, Object[] delta, ChangeMode mode) {
		NPCSk.npcs().setShown(id.getSingle(evt), target.getSingle(evt), mode == ChangeMode.SET && (Boolean) delta[0]);
	}
	
}
