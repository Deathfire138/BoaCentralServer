package io.codec;

import java.nio.ByteBuffer;

import misc.Utilities;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

import worlds.World;
import worlds.Worlds;

public class AuthenticationDecoder extends FrameDecoder {

	@Override
	protected Object decode(ChannelHandlerContext arg0, Channel channel, ChannelBuffer buffer) throws Exception {
		String password = Utilities.readString(buffer);
		byte worldId = buffer.readByte();
		boolean members = buffer.readByte() == 1 ? true : false;
		byte location = buffer.readByte();
		String activity = Utilities.readString(buffer);
			if (password.equals("yvutGYGFkugyg%j8757ffu(HIt7f%$&c665c&86go8O*hi7Gt765dD%Yfgop9h")) {
				Worlds.register(new World(channel, buffer, worldId, members, location, activity));
				//channel.getPipeline().replace("decoder", "decoder", new UpdateDecoder());
				channel.getPipeline().replace("decoder", "decoder", new PacketDecoder());
				System.out.println(worldId + ", " + members + ", " + location);
				channel.write((ByteBuffer) ByteBuffer.allocate(2).put((byte) 0).put((byte) 1).flip());
			} else {
				//PacketSender.authenticationReturncode(channel, (byte) 0);//returncode 0 = incorrect password
			}
		return null;
	}

}
