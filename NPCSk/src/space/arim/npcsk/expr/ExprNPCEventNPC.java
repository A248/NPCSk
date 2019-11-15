package space.arim.npcsk.expr;

import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import net.jitse.npclib.api.events.NPCInteractEvent;

public class ExprNPCEventNPC extends SimpleExpression<String> {
	
	static {
		Skript.registerExpression(ExprNPCEventNPC.class, String.class, ExpressionType.SIMPLE, "[arimsk] event-npc");
	}

	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		if (!ScriptLoader.isCurrentEvent(NPCInteractEvent.class)) {
			Skript.error("The arimsk expression 'event-npc' may only be used in npc events");
			return false;
		}
		return true;
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		return "arimsk event-npc in a npc interact event";
	}

	@Override
	@Nullable
	protected String[] get(Event evt) {
		return new String[] {((NPCInteractEvent) evt).getNPC().getId()};
	}
	

}
