package io.codec;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

public class UpdateDecoder extends FrameDecoder {

	@Override
	protected Object decode(ChannelHandlerContext arg0, Channel arg1, ChannelBuffer arg2) throws Exception {
		System.out.println("lol");
		arg1.getPipeline().replace("decoder", "decoder", new PacketDecoder());
		return null;
	}

}
