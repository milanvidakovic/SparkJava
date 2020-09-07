package com.minja.services;

import java.util.HashMap;
import java.util.Map;


import com.minja.App;
import com.minja.beans.NaseljenoMesto;

import spark.Request;
import spark.Response;
import spark.Route;

public class NaseljenoMestoService {
    private static Map<Long, NaseljenoMesto> mesta = new HashMap<Long, NaseljenoMesto>();

    static {
        NaseljenoMestoService.mesta.put(1L, new NaseljenoMesto(1L, "Novi Sad"));
    }
    
    public static Route handleGetAll = (Request request, Response response) -> {
        response.type("application/json");
        return App.gson.toJson(NaseljenoMestoService.mesta.values());
    };
}