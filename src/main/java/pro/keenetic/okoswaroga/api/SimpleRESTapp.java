package pro.keenetic.okoswaroga.api;

import pro.keenetic.okoswaroga.api.controller.PetController;
import pro.keenetic.okoswaroga.api.entity.JSON;
import pro.keenetic.okoswaroga.api.service.PetService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Optional;

public class SimpleRESTapp {

    public static void main(String[] args) {
        int port = 8080;
        String welcome = "Welcome to simple REST";

        PetController petController = new PetController(port, welcome);

    }
}
