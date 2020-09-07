package com.minja;

import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.delete;
import static spark.Spark.staticFiles;

import java.io.File;
import java.io.IOException;

import com.google.gson.Gson;
import com.minja.interceptor.annotations.Scanner;
import com.minja.services.LoginService;
import com.minja.services.NaseljenoMestoService;
import com.minja.services.StudentiService;

import spark.Request;
import spark.Response;

/**
 * Hello world!
 *
 */
@Scanner("com.minja")
public class App {
	public static Gson gson = new Gson();

	public App() {
		port(80);
		try {
			staticFiles.externalLocation(new File("./static").getCanonicalPath());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		exception(Exception.class, (e, req, res) -> {
			System.out.println("%%%%%%%%%%%%%%%%%%%%% ERROR %%%%%%%%%%%%%%%%%%%%%" + e.getMessage());
			res.status(500);
			res.body(gson.toJson(new ErrorMessage(e.getMessage())));
		});

		post("/rest/user/login", LoginService.handleLoginPost);

		get("/rest/mesto/getall", NaseljenoMestoService.handleGetAll);

		get("/rest/student/getall", (Request request, Response response) -> {
			return StudentiService.getAll(request, response);	
		});

		put("/rest/student/update", StudentiService.handleUpdate);

		post("/rest/student/insert", StudentiService.handleInsert);

		delete("/rest/student/delete/:id", StudentiService.handleDelete);

	}

	/**
	 * STARTING POINT 
	 */
	public static void main(String[] args) {
		
		new App();
	}
}
