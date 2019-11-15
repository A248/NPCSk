package space.arim.npcsk;

import java.io.IOException;

import org.bukkit.plugin.java.JavaPlugin;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import space.arim.npcsk.npcs.NPCExecutor;

public class NPCSk extends JavaPlugin {
	
	private SkriptAddon addon;
	private static NPCExecutor npcs;
	
	private void shutter(String reason) {
		getLogger().severe("**SEVERE**: Unable to load NPCSk's features! Reason: " + reason + ". Shutting down...");
		getServer().getPluginManager().disablePlugin(this);
	}
	
	private void error(String reason) throws IllegalStateException {
		shutter(reason);
		throw new IllegalStateException(reason);
	}
	
	private void error(String reason, Exception cause) throws IllegalStateException {
		shutter(reason);
		throw new IllegalStateException(reason, cause);
	}
	
	private void tryLoad() throws IllegalStateException {
		try {
			Class.forName("ch.njol.skript.Skript");
			Class.forName("net.jitse.npclib.NPCLib");
		} catch (ClassNotFoundException ex) {
			error("Skript and/or NPCLib dependencies not found!", ex);
		}
		if (!Skript.isAcceptRegistrations()) {
			error("Skript is not accepting syntax registrations.");
		}
		addon = Skript.registerAddon(this);
		try {
			addon.loadClasses("space.arim.npcsk", "cond", "eff", "evt", "expr");
		} catch (IOException ex) {
			error("Could not load addon classes!", ex);
		}
		if (npcs == null) {
			error("NPC executor is already loaded!");
		}
		npcs = new NPCExecutor(this);
	}
	
	@Override
	public void onEnable() {
		try {
			tryLoad();
		} catch (IllegalStateException ex) {
			ex.printStackTrace();
			return;
		}
		getLogger().info("Everything ready. Starting...");
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public SkriptAddon getAddonInstance() {
		return addon;
	}
	
	public static NPCExecutor npcs() {
		return npcs;
	}
}
