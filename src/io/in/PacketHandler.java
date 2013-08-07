package io.in;

import java.nio.ByteBuffer;

import worlds.World;

public class PacketHandler {

	public static void handle(World world, ByteBuffer buffer) {
		int opcode = buffer.get() & 0xFF;
		switch(opcode) {
			case 1:
			
		}
	}
	
}
