package space.arim.npcsk.evt;

import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import net.jitse.npclib.api.events.NPCInteractEvent;

public class EvtNPCInteract extends SkriptEvent {
	static {
		Skript.registerEvent("NPC Interact", EvtNPCInteract.class, NPCInteractEvent.class, "[arimsk] npc [interact] event");
	}
	
	@Override
	public boolean init(Literal<?>[] args, int arg1, ParseResult arg2) {
		return true;
	}
	
	@Override
	public String toString(@Nullable Event event, boolean debug) {
		return "arimsk npc interact event.";
	}

	@Override
	public boolean check(Event evt) {
		if (evt instanceof NPCInteractEvent) {
			return true;
		}
		return false;
	}
	
}
