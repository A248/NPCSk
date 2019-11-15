package space.arim.npcsk.expr;

import org.bukkit.entity.Player;
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

public class ExprNPCEventPlayer extends SimpleExpression<Player> {

	static {
		Skript.registerExpression(ExprNPCEventPlayer.class, Player.class, ExpressionType.SIMPLE, "[arimsk] npc event-player");
	}

	@Override
	public Class<? extends Player> getReturnType() {
		return Player.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		if (!ScriptLoader.isCurrentEvent(NPCInteractEvent.class)) {
			Skript.error("The arimsk expression 'npc event-player' may only be used in npc events");
			return false;
		}
		return true;
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		return "arimsk npc event-player in a npc interact event";
	}

	@Override
	@Nullable
	protected Player[] get(Event evt) {
		return new Player[] {((NPCInteractEvent) evt).getWhoClicked()};
	}
	
}
