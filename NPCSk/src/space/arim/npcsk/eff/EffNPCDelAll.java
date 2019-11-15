package space.arim.npcsk.eff;

import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import space.arim.npcsk.NPCSk;

public class EffNPCDelAll extends Effect {
	static {
		Skript.registerEffect(EffNPCDelAll.class, "[arimsk] (delete|remove) all npcs");
	}

	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "arimsk delete all npcs";
	}

	@Override
	protected void execute(Event evt) {
		NPCSk.npcs().delAll();
	}
	
}
