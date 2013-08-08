package io.in;

import io.PacketSender;
import misc.Utilities;
import packet.Packet;
import players.Player;

import worlds.World;

public class PacketHandler {

	public static void handle(World world, Packet packet) {
		System.out.println("handle opcode: "+packet.getOpcode());
		switch(packet.getOpcode()) {
			case 0:
				authenticateLogin(world, packet);
				break;
		}
	}
	
	private static void authenticateLogin(World world, Packet packet) {
		//TODO Actually check auth xD
		Player player = new Player(Utilities.longToPlayerName(packet.getLong()), packet.getString());
		packet.getBoolean(); //TODO Save this as the lowMem boolean
		world.addPlayer(player);
		PacketSender.loginAuthenticated(player);
	}
	
}
