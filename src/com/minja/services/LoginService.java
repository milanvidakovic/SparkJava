package com.minja.services;

import java.security.Key;
import java.util.Date;

import com.minja.App;
import com.minja.beans.User;
import com.minja.interceptor.annotations.JwtKey;
import com.minja.repositories.UserRepo;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import spark.Request;
import spark.Response;
import spark.Route;

public class LoginService {

	/**
	 * https://github.com/jwtk/jjwt
	 */
	@JwtKey
	public static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);


	static UserRepo userRepo = new UserRepo();


    public static Route handleLoginPost = (Request request, Response response) -> {
        response.type("application/json");
        String body = request.body();
        User u = (User)App.gson.fromJson(body, User.class);

        User fromDatabase = userRepo.getUser(u.username);
        if (fromDatabase == null || !fromDatabase.equals(u)) {
            throw new RuntimeException("Wrong username or password!");
        }

        String jwt = Jwts.builder().setSubject(u.username).setExpiration(new Date(new Date().getTime() + 1000L*60L*60L*24L*30L)).setIssuedAt(new Date()).signWith(key).compact();
        fromDatabase.jwt = jwt;

        return App.gson.toJson(fromDatabase);
    };
}