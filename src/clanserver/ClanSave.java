package clanserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import misc.Utilities;

public class ClanSave {

	public static void save(Clan clan) throws IOException {
		byte[] data = new byte[6 + clan.getPrefix().getBytes().length];
		ByteBuffer buffer = ByteBuffer.wrap(data);
		buffer.put(clan.getPrefix().getBytes());
		buffer.put((byte) 0);
		buffer.put((byte) (clan.getCoinShare() == true ? 1 : 0));
		buffer.put(clan.getEnterPermissions());
		buffer.put(clan.getKickingPermissions());
		buffer.put(clan.getLootPermissions());
		buffer.put(clan.getTalkingPermissions());
		FileOutputStream fos = new FileOutputStream("./data/clans/"+Utilities.playerNameToLong(clan.getOwner())+".dat");
		fos.write(data);
		fos.close();
	}
	
	public static boolean load(String owner) throws IOException {
		File file = new File("./data/clans/"+Utilities.playerNameToLong(owner)+".dat");
		if (!file.exists()) {
			return false;
		}
		FileInputStream fis = new FileInputStream(file);
		String prefix = Utilities.readString(fis);
		byte[] data = new byte[fis.available()];
		fis.read(data);
		ByteBuffer buffer = ByteBuffer.wrap(data);
		Clan clan = new Clan(prefix, owner);
		clan.setCoinShare(buffer.get() == 1 ? true : false);
		clan.setEnterPermissions(buffer.get());
		clan.setKickingPermissions(buffer.get());
		clan.setLootPermissions(buffer.get());
		clan.setTalkingPermissions(buffer.get());
		fis.close();
		Clans.add(clan);
		return true;
	}
	
	public static void newClan(String owner, String prefix) {
		Clan clan = new Clan(prefix, owner);
		clan.setCoinShare(false);
		clan.setEnterPermissions((byte) 1);
		clan.setKickingPermissions((byte) 5);
		clan.setLootPermissions((byte) 0);
		clan.setTalkingPermissions((byte) 0);
		Clans.add(clan);
	}
	
}
