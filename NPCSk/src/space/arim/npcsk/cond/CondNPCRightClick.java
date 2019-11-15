package space.arim.npcsk.cond;

import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import net.jitse.npclib.api.events.NPCInteractEvent;

public class CondNPCRightClick extends Condition {
	static {
		Skript.registerCondition(CondNPCRightClick.class, "[arimsk] npc clicktype rightclick");
	}

	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		if (!ScriptLoader.isCurrentEvent(NPCInteractEvent.class)) {
			Skript.error("The arimsk condition 'npc clicktype rightclick' may only be used in npc events");
			return false;
		}
		return true;
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		return "arimsk npc clicktype rightclick";
	}

	@Override
	public boolean check(Event evt) {
		return (((NPCInteractEvent) evt).getClickType() == NPCInteractEvent.ClickType.RIGHT_CLICK);
	}
}
