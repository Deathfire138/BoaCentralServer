package io.in;

import io.Authenticate;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

import worlds.World;
import worlds.Worlds;

public class BoaDecoder extends FrameDecoder {

	@Override
	protected Object decode(ChannelHandlerContext arg0, Channel channel, ChannelBuffer buffer) throws Exception {
		World world = Worlds.getWorld(channel);
		if (world == null) {
			Authenticate.authenticate(channel, buffer.toByteBuffer());
		} else {
			PacketHandler.handle(world, buffer.toByteBuffer());
		}
//		int opcode = buffer.readByte();
//		if (opcode == 1) {//log in check
//			try {
//			System.out.println("lol12534");
//			String username = Utilities.readString(buffer);
//			String password = Utilities.readString(buffer);
//			//Player check = Account.loadNecessaryForLogin(username);
//			//if (check == null) {
//				//NEW PLAYER! :D
//				//Player player = new Player(channel);
//				//PacketSender.
//			//}
//			//Player player = new Player(username, password);
//			Player player = Account.newPlayer(username, password);
//			
//			Worlds.getWorld(channel).addPlayer(player);
//			System.out.println("added world");
//			channel.write((ByteBuffer) ByteBuffer.allocate(5 + username.getBytes().length).put((byte) 1).put(username.getBytes()).put((byte) 0).put((byte) 2).put((byte) 2).put((byte) 1).flip());
//			System.out.println("Wrote auth packet");
//			System.out.println("Wrote friend server status");
//			//IMMEDIATELY after writing the buffer (if good login),
//			} catch (Exception e) {
//				System.out.println("lolwtf");
//				e.printStackTrace();
//			}
//		} else if (opcode == 2) { //
//			
//		} else if (opcode == 3) {//request friend server status
//			System.out.println("more snow");
//			PacketSender.sendFriendServerStatus(Worlds.getPlayer(Utilities.longToPlayerName(buffer.readLong())), 2);
//		} else if (opcode == 4) {//add friend
//			Player player = Worlds.getPlayer(Utilities.longToPlayerName(buffer.readLong()));
//			long added = buffer.readLong();
//			byte clanRank = buffer.readByte();
//			if (player.getFriends().size() < 200) {
//				player.addFriend(Utilities.longToPlayerName(added), clanRank);
//			}
//			System.out.println(player.getUsername() + " added "+Utilities.longToPlayerName(added));
//			FriendServer.addFriend(player, added);
//		} else if (opcode == 5) {//remove friend
//			Player player = Worlds.getPlayer(Utilities.longToPlayerName(buffer.readLong()));
//			player.removeFriend(Utilities.longToPlayerName(buffer.readLong()));
//		} else if (opcode == 6) {//send message
//			Player from = Worlds.getPlayer(Utilities.longToPlayerName(buffer.readLong()));
//			Player to = Worlds.getPlayer(Utilities.longToPlayerName(buffer.readLong()));
//			byte[] data = new byte[buffer.readableBytes()];
//			buffer.readBytes(data);
//			String message = Censor.censor(Utilities.textUnpack(data, 0, data.length));
//			byte[] messageData = new byte[data.length];
//			Utilities.textPack(messageData, message);
//			FriendServer.sendMessage(from, to, messageData);
//			//PacketSender.sendPrivateMessage(to, from.getUsername(), from.getRights(), messageData);
//			//PacketSender.sendSentPrivateMessage(from, to.getUsername(), messageData);
//		} else if (opcode == 7) {//update chat settings
//			Player player = Worlds.getPlayer(Utilities.longToPlayerName(buffer.readLong()));
//			player.setPublicChat(buffer.readByte());
//			player.setPrivateChat(buffer.readByte());
//			player.setTrade(buffer.readByte());
//			FriendServer.update(player);
//		} else if (opcode == 8) {//set clan chat prefix
//			String playername = Utilities.longToPlayerName(buffer.readLong());
//			String prefix = Utilities.readString(buffer);
//			boolean isLoaded = false;
//			for (Clan c : Clans.getClans()) {
//				if (c.getOwner().equalsIgnoreCase(playername)) {
//					c.setPrefix(prefix);
//					PacketSender.updateClanSettingsInterface(Worlds.getPlayer(playername), c.getCoinShare(), c.getEnterPermissions(), c.getTalkingPermissions(), c.getKickingPermissions(), c.getLootPermissions(), c.getPrefix());
//					isLoaded = true;
//				}
//			}
//			if (!isLoaded) {
//				if (!ClanSave.load(playername)) {
//					System.out.println("cannot load clan");
//					ClanSave.newClan(playername, prefix);//RETURN PACKET HERE AS WELL
//					PacketSender.sendMessage(Worlds.getPlayer(playername), "Your clan channel has now been enabled!:clan:");
//					PacketSender.sendMessage(Worlds.getPlayer(playername), "Join your channel by clicking 'Join Chat' and typing: "+playername+":clan:");
//				} else {
//					for (Clan c : Clans.getClans()) {
//						if (c.getOwner().equalsIgnoreCase(playername)) {
//							c.setPrefix(prefix);
//							PacketSender.updateClanSettingsInterface(Worlds.getPlayer(playername), c.getCoinShare(), c.getEnterPermissions(), c.getTalkingPermissions(), c.getKickingPermissions(), c.getLootPermissions(), c.getPrefix());
//						}
//					}
//				}
//			}
//		} else if (opcode == 9) {
//			String playername = Utilities.longToPlayerName(buffer.readLong());
//			Clan clan = null;
//			for (Clan c : Clans.getClans()) {
//				if (c.getOwner().equalsIgnoreCase(playername)) {
//					clan = c;
//				}
//			}
//			if (clan != null) {
//				switch(buffer.readByte()) {
//				//@param setting 0 = enter, 1 = talk, 2 = kick, 3 = loot, 4 = CoinShare
//				case 0:
//					clan.setEnterPermissions(buffer.readByte());
//					break;
//				case 1:
//					clan.setTalkingPermissions(buffer.readByte());
//					break;
//				case 2:
//					clan.setKickingPermissions(buffer.readByte());
//					break;
//				case 3:
//					clan.setLootPermissions(buffer.readByte());
//					break;
//				case 4:
//					clan.toggleCoinShare();
//					buffer.readByte();//WASTE
//					break;
//				}
//				PacketSender.updateClanSettingsInterface(Worlds.getPlayer(playername), clan.getCoinShare(), clan.getEnterPermissions(), clan.getTalkingPermissions(), clan.getKickingPermissions(), clan.getLootPermissions(), clan.getPrefix());
//			} else {
//				buffer.readByte();
//				buffer.readByte();//get these out of there so they don't fuck with shit. xD
//				PacketSender.sendMessage(Worlds.getPlayer(playername), "You don't appear to have a clan. To start a clan, click Clan Setup and set a prefix!:clan:");
//			}
//		} else if (opcode == 10) {//logout request
//			Player player = Worlds.getPlayer(Utilities.longToPlayerName(buffer.readLong()));
//		} else if (opcode == 11) {//request to join clan
//			Player player = Worlds.getPlayer(Utilities.longToPlayerName(buffer.readLong()));
//			String clanOwner = Utilities.longToPlayerName(buffer.readLong());
//			Clan clan = null;
//			for (Clan c : Clans.getClans()) {
//				if (c.getOwner().equalsIgnoreCase(clanOwner)) {
//					clan = c;
//				}
//			}
//			if (clan == null) {
//				if (!ClanSave.load(clanOwner)) {
//					System.out.println("cannot load clan");
//					PacketSender.sendMessage(player, "The channel you tried to join does not exist.:clan:");
//					return null;
//				} else {
//					for (Clan c : Clans.getClans()) {
//						if (c.getOwner().equalsIgnoreCase(clanOwner)) {
//							clan = c;
//						}
//					}
//				}
//			}
//			PacketSender.sendMessage(player, "Now talking in clan channel "+Utilities.formatPlayerNameForDisplay(clan.getPrefix())+":clan:");
//			PacketSender.sendMessage(player, "To talk, start each line of chat with the / symbol.:clan:");
//			clan.addMember(Utilities.playerNameToLong(player.getUsername()));
//			Clans.update(clan);
//		} else if (opcode == 12) {//update rank
//			Player player = Worlds.getPlayer(Utilities.longToPlayerName(buffer.readLong()));
//			String friend = Utilities.longToPlayerName(buffer.readLong());
//			player.getFriends().put(friend, buffer.readByte());
//			//PacketSender.sendUpdatedFriend(player, friend, world, );//DO THAT SHIT
//			Clans.update(Clans.getClan(player.getUsername()));
//		} else if (opcode == 13) {//clan message
//			System.out.println("lolwuttt");
//			long from = buffer.readLong();
//			int size = buffer.readByte();
//			String message = Censor.censor(Utilities.readString(buffer));
//			Clans.sendMessage(Clans.getClanFromMember(from), from, message);
//		}
		return null;
	}

}
