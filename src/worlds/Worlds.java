package worlds;

import java.util.ArrayList;

import org.jboss.netty.channel.Channel;

import players.Player;

public class Worlds {
	
	static ArrayList<World> worlds;
	
	public Worlds() {
		worlds = new ArrayList<World>();
	}
	
	public static boolean register(World world) {
		return worlds.add(world);
	}
	
	public static World getWorld(Channel channel) {
		for (World world : worlds)
			if (world.getChannel() == channel)
				return world;
		return null;
	}
	
	public static World getWorld(Player player) {
		for (World world : worlds)
			for (Player cyclePlayer : world.getPlayers())
				if (cyclePlayer == player)
					return world;
		return null;
	}
	
	public static World getWorld(String player) {
		for (World world : worlds)
			for (Player cyclePlayer : world.getPlayers())
				if (cyclePlayer.getUsername().equalsIgnoreCase(player))
					return world;
		return null;
	}
	
	public static Player getPlayer(String name) {
		for (World world : worlds)
			for (Player cyclePlayer : world.getPlayers())
				if (cyclePlayer.getUsername().equalsIgnoreCase(name))
					return cyclePlayer;
		return null;
	}
	
	public static ArrayList<World> getWorlds() {
		return worlds;
	}
	
}
