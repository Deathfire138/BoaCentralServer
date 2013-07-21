package packet;

import java.nio.ByteBuffer;

public class Packet {

	byte opcode;
	ByteBuffer buffer;
	
	public Packet(byte opcode, int allocate) {
		this.opcode = opcode;
		buffer = ByteBuffer.allocate(allocate + 1);
		buffer.put(opcode);
	}
	
	public ByteBuffer getBuffer() {
		return buffer;
	}
	
}
