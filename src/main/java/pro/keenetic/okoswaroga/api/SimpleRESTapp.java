package pro.keenetic.okoswaroga.api;

import pro.keenetic.okoswaroga.api.controller.PetController;
import pro.keenetic.okoswaroga.api.service.PetService;

/**
 * The type Simple rest app.
 */
public class SimpleRESTapp {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        int port = 8080;
        String welcome = "Welcome to simple REST";

        PetService petService = new PetService();
        PetController petController = new PetController(port, welcome, petService);
    }
}
