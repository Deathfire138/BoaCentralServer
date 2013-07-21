package clanserver;

import java.util.ArrayList;

public class Clan {

	String prefix, owner;
	boolean coinShare;
	byte enter, talk, kick, loot;
	ArrayList<Long> members;
	
	public Clan(String prefix, String owner) {
		this.prefix = prefix;
		this.owner = owner;
		members = new ArrayList<Long>();
	}
	
	public void setPrefix(String string) {
		prefix = string;
	}
	
	public String getPrefix() {
		return prefix;
	}
	
	public void setEnterPermissions(byte perm) {
		enter = perm;
	}
	
	public void setTalkingPermissions(byte perm) {
		talk = perm;
	}
	
	public void setKickingPermissions(byte perm) {
		kick = perm;
	}
	
	public void setLootPermissions(byte perm) {
		loot = perm;
	}

	public byte getEnterPermissions() {
		return enter;
	}
	
	public byte getTalkingPermissions() {
		return talk;
	}
	
	public byte getKickingPermissions() {
		return kick;
	}
	
	public byte getLootPermissions() {
		return loot;
	}

	public void setCoinShare(boolean bool) {
		coinShare = bool;
	}
	
	public boolean getCoinShare() {
		return coinShare;
	}
	
	public String getOwner() {
		return owner;
	}
	
	public void toggleCoinShare() {
		if(coinShare) {
			coinShare = false;
		} else {
			coinShare = true;
		}
	}
	
	public ArrayList<Long> getMembers() {
		return members;
	}

	public void addMember(long name) {
		members.add(name);
	}
	
}