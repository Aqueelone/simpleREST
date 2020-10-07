package pro.keenetic.okoswaroga.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import pro.keenetic.okoswaroga.api.entity.model.PetEntry;
import pro.keenetic.okoswaroga.api.service.PetService;

import java.util.List;

import static spark.Spark.*;

/**
 * The type Pet controller.
 */
public class PetController {
    private final ObjectMapper om = new ObjectMapper();
    private String welcome = "";

    /**
     * Instantiates a new Pet controller.
     *
     * @param port       the port
     * @param welcome    the welcome
     * @param petService the pet service
     */
    public PetController(int port, String welcome, PetService petService) {
        this.welcome = welcome;
        // Start embedded server at this port
        port(port);

        // Main Page, welcome
        get("/", (request, response) -> this.welcome);

        // POST - Add an pet
        post("/pet/add", (request, response) -> {
            String name = request.queryParams("name");
            String tag = request.queryParams("tag");
            PetEntry pet = petService.add(name, tag);
            response.status(201); // 201 Created
            return pet;
        });

        // GET - Give me pet with this id
        get("/pet/:id", (request, response) -> {
            PetEntry pet = petService.findById(request.params(":id"));
            if (pet != null) {
                return pet;
            } else {
                response.status(404); // 404 Not found
                return om.writeValueAsString("pet not found");
            }
        });

        // Get - Give me all pets
        get("/pet", (request, response) -> {
            List<PetEntry> result = petService.findAll();
            if (result.isEmpty()) {
                return om.writeValueAsString("pet not found");
            } else {
                return result;
            }
        });

        // PUT - Update pet
        put("/pet/:id", (request, response) -> {
            String id = request.params(":id");
            String name = request.queryParams("name");
            String tag = request.queryParams("tag");

            PetEntry pet = petService.findById(id);
            if (pet != null && petService.update(id, name, tag)) {
                return om.writeValueAsString("pet with id " + id + " is updated!");
            } else {
                response.status(404);
                return om.writeValueAsString("pet not updated");
            }
        });

        // DELETE - delete pet
        delete("/pet/:id", (request, response) -> {
            String id = request.params(":id");
            PetEntry pet = petService.findById(id);
            if (pet != null && petService.delete(id)) {
                return om.writeValueAsString("pet with id " + id + " is deleted!");
            } else {
                response.status(404);
                return om.writeValueAsString("pet not deleted");
            }
        });
    }
}
