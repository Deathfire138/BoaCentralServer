package io;

import org.jboss.netty.channel.Channel;

import packet.Packet;

import worlds.World;
import worlds.Worlds;

public class Authenticate {

	//TODO Probably implement some cryptography and passwords to truly authenticate, this is really just registering. Good enough for now, though.
	public static void authenticate(Channel channel, Packet packet) {
		if (Worlds.register(new World(channel, packet.getByte(), packet.getByte() == 1 ? true : false, packet.getByte(), packet.getString())))
			System.out.println("World registration is successful!");
		else
			System.out.println("World registration failed!");
	}
	
}
