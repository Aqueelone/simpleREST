package pro.keenetic.okoswaroga.api;

import pro.keenetic.okoswaroga.api.controller.PetController;
import pro.keenetic.okoswaroga.api.service.PetService;

public class SimpleRESTapp {

    public static void main(String[] args) {
        int port = 8080;
        String welcome = "Welcome to simple REST";

        PetService petService = new PetService();
        PetController petController = new PetController(port, welcome, petService);
    }
}
