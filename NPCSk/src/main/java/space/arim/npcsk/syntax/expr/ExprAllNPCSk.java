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
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Name("NPCSk All NPCs")
@Description("Gets a list of all NPCs.")
@Examples({"on command \"/showmeall\":", "\tloop all npcs:", "\t\tset visibility of loop-value for player to true", "\tsend \"You can now see all NPCs!\""})
@Since("0.8.0")
public class ExprAllNPCSk extends SimpleExpression<String> {

	static {
		Skript.registerExpression(ExprAllNPCSk.class, String.class, ExpressionType.SIMPLE, "[npcsk] [all] (npcs|npc ids)");
	}
	
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	public boolean isSingle() {
		return false;
	}

	@Override
	public boolean init(Expression<?>[] exprs, int arg1, Kleenean arg2, ParseResult arg3) {
		return true;
	}

	@Override
	public String toString(@Nullable Event evt, boolean debug) {
		return "npcsk all npc ids";
	}

	@Override
	@Nullable
	protected String[] get(Event evt) {
		return NPCSk.npcs().getAll().toArray(new String[] {});
	}
	
}
