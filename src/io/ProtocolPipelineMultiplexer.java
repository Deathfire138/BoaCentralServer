package io;

import io.in.BoaDecoder;
import io.out.BoaEncoder;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

public final class ProtocolPipelineMultiplexer implements ChannelPipelineFactory {

	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = Channels.pipeline();
		pipeline.addLast("encoder", new BoaEncoder());
		pipeline.addLast("decoder", new BoaDecoder());
		pipeline.addLast("logic", new ConnectionHandler());
		return pipeline;
	}
}