package space.arim.npcsk.eff;

import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import space.arim.npcsk.NPCSk;

public class EffNPCDel extends Effect {
	private Expression<String> id;
	static {
		Skript.registerEffect(EffNPCDel.class, "[arimsk] (delete|remove) npc [with id] %string%");
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int arg1, Kleenean arg2, ParseResult arg3) {
		id = (Expression<String>) exprs[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		return "arimsk delete npc from id " + id.toString(event, debug) + ".";
	}

	@Override
	protected void execute(Event evt) {
		NPCSk.npcs().delNpc(id.getSingle(evt));
	}

}
