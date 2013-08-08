package io.out;

import java.nio.ByteBuffer;

import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

import packet.Packet;

public class BoaEncoder extends OneToOneEncoder {

	@Override
	protected Object encode(ChannelHandlerContext arg0, Channel arg1, Object arg2) throws Exception {
		System.out.println("encode!");
		if (arg2 instanceof ByteBuffer)
			return ChannelBuffers.copiedBuffer((ByteBuffer) arg2);
		else if (arg2 instanceof Packet) {
			Packet packet = (Packet) arg2;
			return ChannelBuffers.copiedBuffer(packet.flip());
		}
		return null;
	}

}
