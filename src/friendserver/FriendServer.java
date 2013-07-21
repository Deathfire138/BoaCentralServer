package friendserver;

import misc.Utilities;
import io.PacketSender;
import players.Player;
import worlds.World;
import worlds.Worlds;

public class FriendServer {

	public static void loggedIn(Player player, int worldId) {
		if (player.getPrivateChat() == 2) {
			return;
		}
		for(World world : Worlds.getWorlds()) {
			for(Player cyclePlayer : world.getPlayers()) {
				for(String friend : cyclePlayer.getFriends().keySet()) {
					if (friend != null) {
						if (friend.equalsIgnoreCase(player.getUsername())) {
							if (player.getPrivateChat() == 0) {
								PacketSender.sendUpdatedFriend(cyclePlayer, friend, worldId, cyclePlayer.getFriends().get(friend));//WRONG
							} else if (player.getPrivateChat() == 1 & player.getFriends().containsKey(cyclePlayer.getUsername())) {
								PacketSender.sendUpdatedFriend(cyclePlayer, friend, worldId, cyclePlayer.getFriends().get(friend));
							}
						}
					}
				}
			}
		}
	}
	
	public static void addFriend(Player adder, long added) {
		int worldId = -1;
		for(World world : Worlds.getWorlds()) {
			for(Player cyclePlayer : world.getPlayers()) {
				if (Utilities.playerNameToLong(cyclePlayer.getUsername()) == added) {
					worldId = world.getId();
					break;
				}
			}
		}
		Player addedPlayer = Worlds.getPlayer(Utilities.longToPlayerName(added));
		if (addedPlayer != null && (addedPlayer.getPrivateChat() != 2 || (addedPlayer.getPrivateChat() == 1 && !addedPlayer.getFriends().containsKey(adder.getUsername())))) {
			if(worldId != -1) {
				PacketSender.sendUpdatedFriend(adder, Utilities.longToPlayerName(added), worldId, adder.getFriends().get(Utilities.longToPlayerName(added)));
			}
		}
	}

	public static void update(Player updated) {
		System.out.println("update friends");
		int worldId = -1;
		for(World world : Worlds.getWorlds()) {
			if (world.getPlayers().contains(updated)) {
				worldId = world.getId();
				break;
			}
		}
		System.out.println("Second : "+updated.getPrivateChat());
		for (World world : Worlds.getWorlds()) {
			for (Player cyclePlayer : world.getPlayers()) {
				if (cyclePlayer.getFriends().containsKey(updated.getUsername())) {
					if (updated.getPrivateChat() == 0) {
						PacketSender.sendUpdatedFriend(cyclePlayer, updated.getUsername(), worldId, cyclePlayer.getFriends().get(updated.getUsername()));
					} else if (updated.getPrivateChat() == 1) {//do rights check, admins can see them, but moderators can't see them
						if (updated.getFriends().containsKey(cyclePlayer.getUsername())) {
							PacketSender.sendUpdatedFriend(cyclePlayer, updated.getUsername(), worldId, cyclePlayer.getFriends().get(updated.getUsername()));	
						} else {
							PacketSender.sendUpdatedFriend(cyclePlayer, updated.getUsername(), 0, cyclePlayer.getFriends().get(updated.getUsername()));
						}
					} else if (updated.getPrivateChat() == 2) {//rights check here as well
						PacketSender.sendUpdatedFriend(cyclePlayer, updated.getUsername(), 0, cyclePlayer.getFriends().get(updated.getUsername()));
					}
				}
			}
		}
	}
	
	public static void sendMessage(Player from, Player to, byte[] message) {
		if (to.getPrivateChat() == 2 || (to.getPrivateChat() == 1 && !to.getFriends().containsKey(from)) || to == null) {
			PacketSender.sendMessage(from, "That player is currently offline.");
		} else {
			PacketSender.sendPrivateMessage(to, from.getUsername(), from.getRights(), message);
			PacketSender.sendSentPrivateMessage(from, to.getUsername(), message);
			if (from.getPrivateChat() == 2) {
				from.setPrivateChat((byte)1);
				//set their private chat to 1 (friends) and send packet to update it
			}
		}
	}
	
}