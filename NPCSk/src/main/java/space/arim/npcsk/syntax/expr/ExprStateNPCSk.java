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

@Name("NPCSk State")
@Description("Whether a NPC is has a specific state. Available states are \"crouched\", \"invisible\", and \"on_fire\".")
@Examples({"on npc interact:", "#This example will make the NPC crouch if it is not, and uncrouch if it is.", "\tif npc state \"crouched\" of npc-event-npc = false:", "\t\tset npc state \"crouched\" of npc-event-npc to true", "\telse:", "\t\tset npc state \\\"crouched\\\" of npc-event-npc to false"})
@Since("0.9.0")
public class ExprStateNPCSk extends SimpleExpression<Boolean> {

	private Expression<String> state;
	private Expression<String> id;
	
	static {
		Skript.registerExpression(ExprStateNPCSk.class, Boolean.class, ExpressionType.COMBINED, "[npcsk] npc state %string% of [npc] %string%");
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
		state = (Expression<String>) exprs[0];
		id = (Expression<String>) exprs[1];
		return true;
	}

	@Override
	public String toString(@Nullable Event evt, boolean debug) {
		return "npcsk npc state " + state.toString(evt, debug) + " of " + id.toString(evt, debug);
	}

	@Override
	@Nullable
	protected Boolean[] get(Event evt) {
		return new Boolean[] {NPCSk.npcs().getNPCState(id.getSingle(evt), state.getSingle(evt))};
	}
	
	@Override
	public Class<?>[] acceptChange(ChangeMode mode) {
		return (mode == ChangeMode.SET || mode == ChangeMode.RESET) ? new Class<?>[] {Boolean.class} : null;
	}
	
	@Override
	public void change(Event evt, Object[] delta, ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.RESET) {
			NPCSk.npcs().setNPCState(id.getSingle(evt), state.getSingle(evt), (mode == ChangeMode.SET) && (Boolean) delta[0]);
		}
	}

}
