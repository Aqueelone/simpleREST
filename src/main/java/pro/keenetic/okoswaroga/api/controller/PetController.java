package pro.keenetic.okoswaroga.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import pro.keenetic.okoswaroga.api.entity.model.NewPet;
import pro.keenetic.okoswaroga.api.entity.model.Pet;
import pro.keenetic.okoswaroga.api.service.PetService;

import java.util.List;

import static spark.Spark.*;

public class PetController {
    private final PetService petService = new PetService();
    private final ObjectMapper om = new ObjectMapper();
    private String welcome = "";

    public PetController(int port, String welcome) {
        this.welcome = welcome;
        // Start embedded server at this port
        port(port);

        // Main Page, welcome
        get("/", (request, response) -> this.welcome);

        // POST - Add an pet
        post("/pet/add", (request, response) -> {
            String name = request.queryParams("name");
            String tag = request.queryParams("tag");
            NewPet pet = petService.add(name, tag);
            response.status(201); // 201 Created
            return om.writeValueAsString(pet);
        });

        // GET - Give me pet with this id
        get("/pet/:id", (request, response) -> {
            Pet pet = petService.findById(request.params(":id"));
            if (pet != null) {
                return om.writeValueAsString(pet);
            } else {
                response.status(404); // 404 Not found
                return om.writeValueAsString("pet not found");
            }
        });

        // Get - Give me all pets
        get("/pet", (request, response) -> {
            List<Pet> result = petService.findAll();
            if (result.isEmpty()) {
                return om.writeValueAsString("pet not found");
            } else {
                return om.writeValueAsString(petService.findAll());
            }
        });

        // PUT - Update pet
        put("/pet/:id", (request, response) -> {
            String id = request.params(":id");
            Pet pet = petService.findById(id);
            if (pet != null) {
                String name = request.queryParams("name");
                String tag = request.queryParams("tag");
                petService.update(id, name, tag);
                return om.writeValueAsString("pet with id " + id + " is updated!");
            } else {
                response.status(404);
                return om.writeValueAsString("pet not found");
            }
        });

        // DELETE - delete pet
        delete("/pet/:id", (request, response) -> {
            String id = request.params(":id");
            Pet pet = petService.findById(id);
            if (pet != null) {
                petService.delete(id);
                return om.writeValueAsString("pet with id " + id + " is deleted!");
            } else {
                response.status(404);
                return om.writeValueAsString("pet not found");
            }
        });
    }
}
