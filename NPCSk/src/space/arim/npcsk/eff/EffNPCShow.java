package space.arim.npcsk.eff;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import space.arim.npcsk.NPCSk;

public class EffNPCShow extends Effect {
	private Expression<String> id;
	private Expression<Player> targets;
	static {
		Skript.registerEffect(EffNPCShow.class, "[arimsk] (show|reveal) npc %string% to %players%");
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int arg1, Kleenean arg2, ParseResult arg3) {
		id = (Expression<String>) exprs[0];
		targets = (Expression<Player>) exprs[1];
		return true;
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		return "arimsk show npc " + id.toString(event, debug) + " to " + targets.toString(event, debug);
	}

	@Override
	protected void execute(Event evt) {
		NPCSk.npcs().show(id.getSingle(evt), targets.getArray(evt));
	}
	
}
