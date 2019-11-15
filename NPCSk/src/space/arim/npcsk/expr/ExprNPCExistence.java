package space.arim.npcsk.expr;

import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import space.arim.npcsk.NPCSk;

public class ExprNPCExistence extends SimpleExpression<Boolean> {
	private Expression<String> id;
	static {
		Skript.registerExpression(ExprNPCVisibility.class, Boolean.class, ExpressionType.SIMPLE, "[arimsk] npc existence of %string%");
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
		return true;
	}
	@Override
	public String toString(@Nullable Event event, boolean debug) {
		return "arimsk npc existence of " + id.toString(event, debug) + ".";
	}
	@Override
	@Nullable
	protected Boolean[] get(Event evt) {
		return new Boolean[] {NPCSk.npcs().hasNpc(id.getSingle(evt))};
	}
}
