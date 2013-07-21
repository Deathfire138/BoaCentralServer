package worlds;

import io.PacketSender;

import java.util.ArrayList;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;

import friendserver.FriendServer;

import players.Player;

public class World {

	byte worldId, location;
	boolean members;
	ArrayList<Player> players;
	Channel channel;
	ChannelBuffer buffer;
	String activity;
	
	public World(Channel channel, ChannelBuffer buffer, byte worldId, boolean members, byte location, String activity) {
		this.channel = channel;
		this.buffer = buffer;
		this.worldId = worldId;
		this.location = location;
		this.members = members;
		this.activity = activity;
		players = new ArrayList<Player>();
	}
	
	public Channel getChannel() {
		return channel;
	}
	
	public boolean addPlayer(Player player) {
		boolean bool = players.add(player);
		if (true) {
			FriendServer.loggedIn(player, worldId);
		}
		return bool;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	public int getId() {
		return worldId;
	}
	
}