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

import org.eclipse.jdt.annotation.Nullable;

import org.bukkit.event.Event;

import space.arim.npcsk.NPCSk;

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

@Name("NPCSk Auto Hide Distance")
@Description("Npcs are visible to a nearby player if the player is within this distance.")
@Examples({"set npc auto hide distance to 60", "reset npc auto hide distance"})
@Since("0.6.0")
public class ExprAutoHideNPCSk extends SimpleExpression<Number> {

	static {
		Skript.registerExpression(ExprAutoHideNPCSk.class, Number.class, ExpressionType.SIMPLE, "[npcsk] [npc] auto hide (distance|radius)");
	}
	
	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}
	
	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		return true;
	}

	@Override
	public String toString(@Nullable Event evt, boolean debug) {
		return "npcsk npc auto hide distance";
	}

	@Override
	@Nullable
	protected Number[] get(Event evt) {
		return new Number[] {NPCSk.npcs().getAutoHide()};
	}
	
	@Override
	public Class<?>[] acceptChange(ChangeMode mode) {
		return (mode == ChangeMode.SET || mode == ChangeMode.RESET) ? new Class<?>[] {Number.class} : null;
	}
	
	@Override
	public void change(Event evt, Object[] delta, ChangeMode mode) {
		if (mode == ChangeMode.SET) {
			NPCSk.npcs().setAutoHide((Double) delta[0]);
		} else if (mode == ChangeMode.RESET) { 
			NPCSk.npcs().clearAutoHide();
		}
	}

}
