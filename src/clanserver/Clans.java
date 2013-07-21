package clanserver;

import io.PacketSender;

import java.util.ArrayList;

import misc.Utilities;

import players.Player;

import worlds.World;
import worlds.Worlds;

public class Clans {

	static ArrayList<Clan> clans = new ArrayList<Clan>();

	public static boolean removeClan(Clan clan) {
		return clans.remove(clan);
	}
	
	public static boolean add(Clan clan) {
		return clans.add(clan);
	}
	
	public static ArrayList<Clan> getClans() {
		return clans;
	}
	
	public static void update(Clan clan) {
		System.out.println("update clan");
		Player player = Worlds.getPlayer(clan.getOwner());
		long[] names = new long[clan.getMembers().size()];
		byte[][] ranksAndWorlds = new byte[clan.getMembers().size()][2];
		System.out.println(names.length);
		for (int i = 0; i < names.length; i++) {
			System.out.print("iter");
			names[i] = clan.getMembers().get(i);
			System.out.println("lolwut?");
			ranksAndWorlds[i][0] = (byte) Worlds.getWorld(Utilities.longToPlayerName(names[i])).getId();
			if (player.getUsername().equalsIgnoreCase(Utilities.longToPlayerName(names[i]))) {
				ranksAndWorlds[i][1] = (byte)7;
			} else if (player.getFriends().containsKey(Utilities.longToPlayerName(names[i]))) {
				ranksAndWorlds[i][1] = player.getFriends().get(Utilities.longToPlayerName(names[i]));
			} else {
				ranksAndWorlds[i][1] = (byte)127;
			}
		}
		for (int i = 0; i < names.length; i++) {
			System.out.println("names["+i+"] = "+names[i]);
			Player p = Worlds.getPlayer(Utilities.longToPlayerName(names[i]));
			PacketSender.updateClan(p, Utilities.playerNameToLong(clan.getPrefix()), Utilities.playerNameToLong(clan.getOwner()), clan.getKickingPermissions(), names, ranksAndWorlds);
		}
	}
	
	public static Clan getClan(String owner) {
		for (Clan clan : clans) {
			if (clan.getOwner().equalsIgnoreCase(owner)) {
				return clan;
			}
		}
		return null;
	}
	
	public static Clan getClanFromMember(long member) {
		for (Clan clan : clans) {
			if (clan.getMembers().contains(member)) {
				return clan;
			}
		}
		return null;
	}
	
	public static void sendMessage(Clan clan, long sender, String message) {
		for (long name : clan.getMembers()) {
			Player player = Worlds.getPlayer(Utilities.longToPlayerName(name));
			if (!player.getIgnores().contains(sender)) {
				System.out.println("wootsy");
				PacketSender.sendClanMessage(player, sender, Utilities.playerNameToLong(clan.getPrefix()), message);
			}
		}
	}
	
}