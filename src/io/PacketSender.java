package io;

import java.io.File;
import java.nio.ByteBuffer;

import misc.Utilities;

import packet.Packet;
import players.Player;
import worlds.Worlds;

public class PacketSender {

	public static void loginAuthenticated(Player player) {
		Packet packet = new Packet((byte) 0, player.getPassword().getBytes().length + 10);
		packet.putLong(Utilities.playerNameToLong(player.getUsername()));
		packet.putString(player.getPassword());
		packet.putBoolean(false);//TODO Actually send lowMem boolean, lol.
		//TODO also send login information
		//TODO Essentially send returncodes
		System.out.println("auth login");
		Worlds.getWorld(player).getChannel().write(packet);
	}
	
	public static void sendCredentials(Player player) {
		boolean newPlayer = !new File("./data/players/"+player.getUsername().toLowerCase()+".pl").exists();
		ByteBuffer buffer = ByteBuffer.allocate(newPlayer ? 8 : 100);
		buffer.put((byte) 3);//opcode
		buffer.putLong(Utilities.playerNameToLong(player.getUsername()));
		if (newPlayer) {
			Worlds.getWorld(player).getChannel().write((ByteBuffer) buffer.flip());
			return;
		}
		//send rest of credentials (members data, location, inventory, etc)
	}

	public static void sendUpdatedFriend(Player receiver, String friend, int world, byte clanRank) {
		System.out.println("connecttttttt");
		ByteBuffer buffer = ByteBuffer.allocate(19);
		buffer.put((byte) 4);//opcode
		buffer.putLong(Utilities.playerNameToLong(receiver.getUsername()));
		buffer.putLong(Utilities.playerNameToLong(friend));
		buffer.put((byte) world);
		buffer.put(clanRank);
		Worlds.getWorld(receiver).getChannel().write((ByteBuffer) buffer.flip());
	}
	
	public static void sendFriendServerStatus(Player receiver, int status) {
		System.out.println("sendshit");
		ByteBuffer buffer = ByteBuffer.allocate(10);
		buffer.put((byte) 5);//opcode
		buffer.putLong(Utilities.playerNameToLong(receiver.getUsername()));
		buffer.put((byte) status);
		try {
		Worlds.getWorld(receiver).getChannel().write((ByteBuffer) buffer.flip());
		} catch (Exception e) {
			System.out.println("lolqqqq");
		}
	}

	public static void sendPrivateMessage(Player receiver, String from, byte rights, byte[] message) {
		ByteBuffer buffer = ByteBuffer.allocate(18 + message.length);
		buffer.put((byte) 6);//opcode
		buffer.putLong(Utilities.playerNameToLong(receiver.getUsername()));
		buffer.putLong(Utilities.playerNameToLong(from));
		buffer.put(rights);
		buffer.put(message);
		Worlds.getWorld(receiver).getChannel().write((ByteBuffer) buffer.flip());
	}
	
	public static void sendSentPrivateMessage(Player sender, String to, byte[] message) {
		ByteBuffer buffer = ByteBuffer.allocate(17 + message.length);
		buffer.put((byte) 10);//opcode
		buffer.putLong(Utilities.playerNameToLong(sender.getUsername()));
		buffer.putLong(Utilities.playerNameToLong(to));
		buffer.put(message);
		Worlds.getWorld(sender).getChannel().write((ByteBuffer) buffer.flip());
	}
	
	public static void sendMessage(Player receiver, String message) {
		ByteBuffer buffer = ByteBuffer.allocate(10 + message.getBytes().length);
		buffer.put((byte) 7);
		buffer.putLong(Utilities.playerNameToLong(receiver.getUsername()));
		buffer.put(message.getBytes());
		buffer.put((byte) 0);
		Worlds.getWorld(receiver).getChannel().write((ByteBuffer) buffer.flip());
	}
	
	public static void sendConfig(Player receiver, int configId, int value) {
		ByteBuffer buffer = ByteBuffer.allocate(17);
		buffer.put((byte) 8);
		buffer.putLong(Utilities.playerNameToLong(receiver.getUsername()));
		buffer.putInt(configId);
		buffer.putInt(value);
		Worlds.getWorld(receiver).getChannel().write((ByteBuffer) buffer.flip());
	}
	
	public static void updateClanSettingsInterface(Player receiver, boolean coinShare, byte enter, byte talk, byte kick, byte loot, String prefix) {
		ByteBuffer buffer = ByteBuffer.allocate(15 + prefix.getBytes().length);
		buffer.put((byte) 9);
		buffer.putLong(Utilities.playerNameToLong(receiver.getUsername()));
		buffer.put((byte) (coinShare ? 1 : 0));
		buffer.put(enter);
		buffer.put(talk);
		buffer.put(kick);
		buffer.put(loot);
		buffer.put(prefix.getBytes());
		buffer.put((byte) 0);
		Worlds.getWorld(receiver).getChannel().write((ByteBuffer) buffer.flip());
	}

	public static void updateClan(Player receiver, long prefix, long owner, byte kick, long[] users, byte[][] ranksAndWorlds) {
		System.out.println("update clan2");
		ByteBuffer buffer = ByteBuffer.allocate(27 + (10 * users.length));
		buffer.put((byte) 11);
		buffer.putLong(Utilities.playerNameToLong(receiver.getUsername()));
		buffer.putLong(owner);
		buffer.putLong(prefix);
		buffer.put(kick);
		buffer.put((byte) users.length);
		System.out.println("clan12");
		try {
		for (int i = 0; i < users.length; i++) {
			buffer.putLong(users[i]);
			buffer.put(ranksAndWorlds[i][0]);
			buffer.put(ranksAndWorlds[i][1]);
		}
		System.out.println("clan22");
			Worlds.getWorld(receiver).getChannel().write((ByteBuffer) buffer.flip());
			System.out.println("OH MAH GAWD");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void sendClanMessage(Player receiver, long sender, long prefix, String message) {
		System.out.println("bwoooooo");
		ByteBuffer buffer = ByteBuffer.allocate(27 + message.getBytes().length);
		buffer.put((byte) 12);
		buffer.putLong(Utilities.playerNameToLong(receiver.getUsername()));
		buffer.putLong(sender);
		buffer.putLong(prefix);
		//System.out.println(message.getBytes()[message.getBytes().length-1]);
		buffer.put(message.getBytes());
		buffer.put((byte) 0);
		System.out.println("byow");
		Worlds.getWorld(receiver).getChannel().write((ByteBuffer) buffer.flip());
		System.out.println("byowb");
	}
	
}