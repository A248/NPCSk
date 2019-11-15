package space.arim.npcsk.expr;

import org.bukkit.Location;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import space.arim.npcsk.NPCSk;

public class ExprNPCLocation extends SimpleExpression<Location> {
	private Expression<String> id;
	static {
		Skript.registerExpression(ExprNPCLocation.class, Location.class, ExpressionType.SIMPLE, "[arimsk] npc location of [npc] [with id] %string%");
	}

	@Override
	public Class<? extends Location> getReturnType() {
		return Location.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int arg1, Kleenean arg2, ParseResult arg3) {
		id = (Expression<String>) exprs[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		return "arimsk npc location of npc with id " + id.toString(event, debug) + ".";
	}

	@Override
	@Nullable
	protected Location[] get(Event evt) {
		if (NPCSk.npcs().hasNpc(id.getSingle(evt))) {
			return new Location[] {NPCSk.npcs().getLocation(id.getSingle(evt))};
		}
		return null;
	}
	
}
