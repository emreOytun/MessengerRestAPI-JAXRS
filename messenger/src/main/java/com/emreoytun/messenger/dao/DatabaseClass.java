package com.emreoytun.messenger.dao;

import java.util.HashMap;
import java.util.Map;

import com.emreoytun.messenger.entity.Message;
import com.emreoytun.messenger.entity.Profile;

/* This class is to mock dao layer not to deal with jdbc/jpa(hibernate) etc. for learning purposes. */
public class DatabaseClass {
	
	/* Not thread-safe, but it's only for learning purposes. This should not be in that way in real production. */
	private static Map<Long, Message> messages = new HashMap<>();
	private static Map<String, Profile> profiles = new HashMap<>();
	
	public static Map<Long, Message> getMessages() {
		return messages;
	}
	
	public static Map<String, Profile> getProfiles() {
		return profiles;
	}
	
	
}
