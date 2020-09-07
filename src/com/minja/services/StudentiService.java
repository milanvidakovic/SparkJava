package com.minja.services;

import java.util.HashMap;
import java.util.Map;

import com.minja.App;
import com.minja.beans.NaseljenoMesto;
import com.minja.beans.Student;
import com.minja.interceptor.annotations.JwtSecurity;

import spark.Request;
import spark.Response;
import spark.Route;

public class StudentiService {

	private static Map<Long, Student> students = new HashMap<Long, Student>();

	static {
		StudentiService.students.put(1L, new Student(1L, "Pera", "Perić", new NaseljenoMesto(1L, "Novi Sad")));
	}

	public static Route handleGetAll = (Request request, Response response) -> {
		return getAll(request, response);
	};

	public static Route handleUpdate = (Request request, Response response) -> {
		return update(request, response);
	};

	public static Route handleInsert = (Request request, Response response) -> {
		return insert(request, response);
	};

	public static Route handleDelete = (Request request, Response response) -> {
		return delete(request, response);
	};

	@JwtSecurity(path = "/rest/student/getall")
	public static String getAll(Request request, Response response) {
		response.type("application/json");
		return App.gson.toJson(StudentiService.students.values());
	}

	@JwtSecurity(path = "/rest/student/update", role = "ROLE_ADMIN")
	public static String update(Request request, Response response) {
		response.type("application/json");

		Student fromRequest = App.gson.fromJson(request.body(), Student.class);
		Student fromDatabase = students.get(fromRequest.getId());
		if (fromDatabase != null) {
			students.replace(fromDatabase.getId(), fromDatabase, fromRequest);
			return App.gson.toJson(fromRequest);
		} else
			throw new RuntimeException("Unknown student with ID: " + fromRequest.getId());

	}

	@JwtSecurity(path = "/rest/student/insert", role = "ROLE_ADMIN")
	public static String insert(Request request, Response response) {
		response.type("application/json");

		Student fromRequest = App.gson.fromJson(request.body(), Student.class);
		if (students.containsKey(fromRequest.getId()))
			throw new RuntimeException("Student with this ID already exists");
		students.put(fromRequest.getId(), fromRequest);
		return App.gson.toJson(fromRequest);

	}

	@JwtSecurity(path = "/rest/student/delete/:id", role = "ROLE_ADMIN")
	public static String delete(Request request, Response response) {
		response.type("application/json");

		Long id = Long.parseLong(request.params("id"));
		Student fromDatabase = students.get(id);
		if (fromDatabase != null) {
			students.remove(id);
			return "true";
		} else
			return "false";

	}

}