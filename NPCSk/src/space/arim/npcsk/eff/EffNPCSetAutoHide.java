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
