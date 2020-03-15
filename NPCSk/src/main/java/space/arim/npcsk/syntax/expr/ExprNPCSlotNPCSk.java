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

import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

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

@Name("NPCSk NPC Slot")
@Description("The item a NPC is holding in a slot. Available slots: mainhand, offhand, helmet, chestplate, leggings, boots.")
@Examples({"set item in npc slot \"chestplate\" to diamond sword", "set {_tool} to item in npc slot \"mainhand\"", "broadcast \"The NPC's held tool is %{_tool}%!\""})
@Since("0.7.0")
public class ExprNPCSlotNPCSk extends SimpleExpression<ItemStack> {

	private Expression<String> id;
	private Expression<String> slot;
	
	static {
		Skript.registerExpression(ExprNPCSlotNPCSk.class, ItemStack.class, ExpressionType.COMBINED, "[npcsk] [item in] npc slot %string% (of|for) [npc] %string%");
	}
	
	@Override
	public Class<? extends ItemStack> getReturnType() {
		return ItemStack.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int arg1, Kleenean arg2, ParseResult arg3) {
		id = (Expression<String>) exprs[0];
		slot = (Expression<String>) exprs[1];
		return true;
	}

	@Override
	public String toString(@Nullable Event evt, boolean debug) {
		return "npcsk item in npc slot " + slot.toString(evt, debug) + " for npc " + id.toString(evt, debug);
	}

	@Override
	@Nullable
	protected ItemStack[] get(Event evt) {
		ItemStack item = NPCSk.npcs().getNPCSlot(id.getSingle(evt), slot.getSingle(evt));
		return item != null ? new ItemStack[] {item} : null;
	}
	
	@Override
	public Class<?>[] acceptChange(ChangeMode mode) {
		return (mode == ChangeMode.SET || mode == ChangeMode.RESET) ? new Class<?>[] {ItemStack.class} : null;
	}
	
	@Override
	public void change(Event evt, Object[] delta, ChangeMode mode) {
		ItemStack item = (mode == ChangeMode.SET) ? (ItemStack) delta[0] : (mode == ChangeMode.RESET) ? new ItemStack(Material.AIR) : null;
		NPCSk.npcs().setNPCSlot(id.getSingle(evt), item, slot.getSingle(evt));
	}

}
