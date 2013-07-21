package io;

import io.codec.AuthenticationDecoder;
import io.codec.PacketEncoder;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

public final class ProtocolPipelineMultiplexer implements ChannelPipelineFactory {

	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = Channels.pipeline();
		pipeline.addLast("encoder", new PacketEncoder());
		pipeline.addLast("decoder", new AuthenticationDecoder());
		pipeline.addLast("logic", new ConnectionHandler());
		return pipeline;
	}
}