package packet;

import java.nio.ByteBuffer;

public class Packet {

	private Size size;
	private int bitPos;
	private ByteBuffer buffer;
	private byte opcode;
	
	private final static int BIT_MASKS[] = {
		0, 0x1, 0x3, 0x7,
		0xf, 0x1f, 0x3f, 0x7f,
		0xff, 0x1ff, 0x3ff, 0x7ff,
		0xfff, 0x1fff, 0x3fff, 0x7fff,
		0xffff, 0x1ffff, 0x3ffff, 0x7ffff,
		0xfffff, 0x1fffff, 0x3fffff, 0x7fffff,
		0xffffff, 0x1ffffff, 0x3ffffff, 0x7ffffff,
		0xfffffff, 0x1fffffff, 0x3fffffff, 0x7fffffff,
		-1
	};
	
	public static final int[] BIT_MASK_OUT = new int[32];
	
	static {
		for(int i = 0; i < BIT_MASK_OUT.length; i++) {
			BIT_MASK_OUT[i] = (1 << i) - 1;
		}
	}
	
	public enum Size {
		VARIABLE,
		VARIABLE_BYTE,
		VARIABLE_SHORT;
	}
	
	public Packet(ByteBuffer buf, boolean hasOpcode) {
		buffer = buf;
		if (hasOpcode)
			opcode = buffer.get();
	}
	
	public Packet(byte opcode, int expectedAllocation) {
		buffer = ByteBuffer.allocate(expectedAllocation + 1);
		putByte(opcode);
		
	}
	
	public Packet(byte opcode, int expectedAllocation, Size s) {
		buffer = ByteBuffer.allocate(expectedAllocation + 1);
		putByte(opcode);
		size = s;
	}
	
	public Packet(int expectedAllocation) {
		buffer = ByteBuffer.allocate(expectedAllocation);
	}
	
	public void putBoolean(boolean b) {
		buffer.put((byte) (b ? 1 : 0));
	}
	
	public boolean getBoolean() {
		return buffer.get() == 1 ? true : false;
	}
	
	public void putByte(byte b) {
		buffer.put(b);
	}
	
	public byte getByte() {
		return buffer.get();
	}
	
	public void putByteA(byte b) {
		buffer.put((byte) (b + 128));
	}
	
	public byte getByteA() {
		return (byte) ((buffer.get() & 0xff) - 128);
	}
	
	//TODO If this does not work, try (128 - b)
	public void putByteS(byte b) {
		buffer.put((byte) (b - 128));
	}
	
	public byte getByteS() {
		return (byte) ((buffer.get() & 0xff) + 128);
	}
	
	public void putByteC(byte b) {
		buffer.put((byte) -b);
	}
	
	public byte getByteC() {
		return (byte) -(buffer.get() & 0xff);
	}
	
	public void putShort(short s) {
		buffer.putShort(s);
	}
	
	public short getShort() {
		return buffer.getShort();
	}
	
	public void putShortA(short s) {
		buffer.put((byte) (s >> 8));
		buffer.put((byte) (s + 128));
	}
	
	public short getShortA() {
		return (short) (((buffer.get() & 0xff) << 8) + (buffer.get() - 128 & 0xff));
	}
	
	public void putLEShort(short s) {
		buffer.put((byte) s);
		buffer.put((byte) (s >> 8));
	}
	
	//TODO There's a sort of if-statement safety feature that I'm not going to add for the sake of simplicity. If problems arise, use that shit, kay? Find it in Paul's.
	public short getLEShort() {
		return (short) (((buffer.get() & 0xff)) + ((buffer.get() & 0xff) << 8));
	}
	
	public void putLEShortA(short s) {
		buffer.put((byte) (s + 128));
		buffer.put((byte) (s >> 8));
	}
	
	//TODO Just as with getLEShort, that safety feature.
	public short getLEShortA() {
		return (short) (((buffer.get() - 128 & 0xff)) + ((buffer.get() * 0xff) << 8));
	}
	
	public void putTriByte(int t) {
		buffer.put((byte) (t >> 8));
		buffer.put((byte) (t >> 16));
		buffer.put((byte) t);
	}
	
	public int getTriByte() {
		return (((buffer.get() & 0xff) << 8) + ((buffer.get() * 0xff) << 16) + (buffer.get() & 0xff));
	}
	
	public void putInt(int i) {
		buffer.putInt(i);
	}
	
	public int getInt() {
		return buffer.getInt();
	}
	
	public void putInt1(int i) {
		buffer.put((byte) (i >> 8));
		buffer.put((byte) i);
		buffer.put((byte) (i >> 24));
		buffer.put((byte) (i >> 16));
	}
	
	public int getInt1() {
		return ((buffer.get() & 0xff) << 8) | (buffer.get() & 0xff) | ((buffer.get() & 0xff) << 24) | ((buffer.get() & 0xff) << 16);
	}
	
	public void putInt2(int i) {
		buffer.put((byte) (i >> 16));
		buffer.put((byte) (i >> 24));
		buffer.put((byte) i);
		buffer.put((byte) (i >> 8));
	}
	
	public int getInt2() {
		return ((buffer.get() & 0xff) << 16) | ((buffer.get() & 0xff) << 24) | (buffer.get() & 0xff) | ((buffer.get() & 0xff) << 8);
	}
	
	//Pretty fuckin' sure this is how a normal int is stored but whatever! xD (Might be reversed, though!)
	public void putLEInt(int i) {
		buffer.put((byte) i);
		buffer.put((byte) (i >> 8));
		buffer.put((byte) (i >> 16));
		buffer.put((byte) (i >> 24));
	}
	
	public int getLEInt() {
		return (buffer.get() * 0xff) | ((buffer.get() & 0xff) << 8) | ((buffer.get() & 0xff) << 16) | ((buffer.get() & 0xff) << 24);
	}
	
	public void putLong(long l) {
		buffer.putLong(l);
	}
	
	public long getLong() {
		return buffer.getLong();
	}
	
	public void putDouble(double d) {
		buffer.putDouble(d);
	}
	
	public double getDouble() {
		return buffer.getDouble();
	}
	
	public void putFloat(float f) {
		buffer.putFloat(f);
	}
	
	public float getFloat() {
		return buffer.getFloat();
	}
	
	public void putString(String s) {
		buffer.put(s.getBytes());
		buffer.put((byte) 0);
	}
	
	public String getString() {
		StringBuilder stringBuilder = new StringBuilder();
		int currentCharacter;
		while ((currentCharacter = buffer.get()) != 0)
			stringBuilder.append((char) currentCharacter);
		return stringBuilder.toString();
	}

	public void put(ByteBuffer b) {
		buffer.put(b);
	}
	
	public void putByteArray(byte[] data) {
		buffer.put(data);
	}
	
	public void startBitAccess() {
		bitPos = buffer.position() * 8;
	}
	
	public void finishBitAccess() {
		buffer.position((bitPos + 7) / 8);
	}
	
	public void putBits(int numBits, int value) {
		int bytePos = bitPos >> 3;
		int bitOffset = 8 - (bitPos & 7);
		bitPos += numBits;
		int pos = (bitPos + 7) / 8;
		buffer.position(pos);
		for (; numBits > bitOffset; bitOffset = 8) {
			int tmp = buffer.get(bytePos);
			tmp &= ~BIT_MASKS[bitOffset];
			tmp |= (value >> (numBits-bitOffset)) & BIT_MASKS[bitOffset];
			buffer.put(bytePos++,(byte) tmp);
			numBits -= bitOffset;
		}
		if (numBits == bitOffset) {
			int tmp = buffer.get(bytePos);
			tmp &= ~BIT_MASKS[bitOffset];
			tmp |= value & BIT_MASKS[bitOffset];
			buffer.put(bytePos,(byte) tmp);
		} else {
			int tmp = buffer.get(bytePos);
			tmp &= ~(BIT_MASKS[numBits] << (bitOffset - numBits));
			tmp |= (value & BIT_MASKS[numBits]) << (bitOffset - numBits);
			buffer.put(bytePos,(byte) tmp);
		}
	}
	
	public ByteBuffer getBuffer() {
		return buffer;
	}

	public byte getOpcode() {
		return opcode;
	}
	
	public ByteBuffer flip() {
		return (ByteBuffer) buffer.flip();
	}
	
}