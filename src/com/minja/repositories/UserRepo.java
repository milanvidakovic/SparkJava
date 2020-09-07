package com.minja.repositories;

import java.util.HashMap;
import java.util.Map;

import com.minja.beans.Role;
import com.minja.beans.User;
import com.minja.interceptor.annotations.UserGetter;
import com.minja.interceptor.annotations.UserProvider;

@UserProvider
public class UserRepo {
	 public static Map<String, User> users = new HashMap<String, User>();

	    static {
	        users.put("pera", new User("pera", "pera", Role.ROLE_ADMIN));
	        users.put("mika", new User("mika", "mika", Role.ROLE_USER));
	    }
	    
	    @UserGetter
	    public User getUser(String username) {
	    	return users.get(username);
	    }
}
