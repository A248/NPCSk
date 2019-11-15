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

public class ExprNPCAutoHide extends SimpleExpression<Number> {
	static {
		Skript.registerExpression(ExprNPCAutoHide.class, Number.class, ExpressionType.SIMPLE, "[arimsk] get npc [auto] hide [(distance|radius)]");
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
	public String toString(@Nullable Event event, boolean debug) {
		return "arimsk get npc auto hide distance";
	}

	@Override
	@Nullable
	protected Number[] get(Event evt) {
		return new Number[] {NPCSk.npcs().getAutoHide()};
	}

}
