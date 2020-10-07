package pro.keenetic.okoswaroga.api.controller;

import okhttp3.*;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pro.keenetic.okoswaroga.api.HibernateUtil;
import pro.keenetic.okoswaroga.api.entity.model.PetEntry;
import pro.keenetic.okoswaroga.api.service.PetService;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

class PetControllerTest {

    private final static int PORT = 8080;
    private final static String WELCOME_MSG = "welcome";

    private final static String NEW_PET_NAME1 = "vasya";
    private final static String NEW_PET_TEG1 = "cat";
    private final static String NEW_PET_NAME2 = "sharik";
    private final static String NEW_PET_TEG2 = "dog";
    private final static String UPDATE_MSG1 = "\"pet with id 1 is updated!\"";
    private final static String DELETE_MSG1 = "\"pet with id 1 is deleted!\"";

    private static PetService petService;
    private static PetController petController;

    private static OkHttpClient okHttpClient;

    @BeforeEach
    void setUp() {
        petService = new PetService();
        petController = new PetController(PORT, WELCOME_MSG, petService);
        okHttpClient = new OkHttpClient();

        Assert.assertNotNull("Not up service!", petService);
        Assert.assertNotNull("Not up server!", petController);
        Assert.assertNotNull("Not up HTTP client!", okHttpClient);
    }

    @Test
    void get(){
        Request request = new Request.Builder().url("http://localhost:8080/").get().build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            Assert.assertTrue("Request failed!", response.isSuccessful());
            Assert.assertEquals("Wrong status code!", 200, response.code());
            Assert.assertEquals("Wrong response body!", WELCOME_MSG, response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void postPetAdd(){
        PetEntry petEntryFixture = new PetEntry(1, NEW_PET_NAME1, NEW_PET_TEG1);

        RequestBody requestBody = new FormBody.Builder()
                .add("name", NEW_PET_NAME1)
                .add("tag", NEW_PET_TEG1).build();
        Request request = new Request.Builder().url("http://localhost:8080/pet/add").post(requestBody).build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            Assert.assertTrue("Request failed!", response.isSuccessful());
            Assert.assertEquals("Wrong status code!", 201, response.code());
            Assert.assertEquals("Wrong response body!", petEntryFixture.toString(), response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getPetById() {
        PetEntry petEntryFixture = petService.add(NEW_PET_NAME1, NEW_PET_TEG1);

        Request request = new Request.Builder().url("http://localhost:8080/pet/1").get().build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            Assert.assertTrue("Request failed!", response.isSuccessful());
            Assert.assertEquals("Wrong status code!", 200, response.code());
            Assert.assertEquals("Wrong response body!", petEntryFixture.toString(), response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getAllPet() {
        PetEntry petEntryFixture = petService.add(NEW_PET_NAME1, NEW_PET_TEG1);
        PetEntry petEntryFixture2 = petService.add(NEW_PET_NAME2, NEW_PET_TEG2);
        List<PetEntry> petEntryList = new LinkedList<>();
        petEntryList.add(petEntryFixture);
        petEntryList.add(petEntryFixture2);

        Request request = new Request.Builder().url("http://localhost:8080/pet").get().build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            Assert.assertTrue("Request failed!", response.isSuccessful());
            Assert.assertEquals("Wrong status code!", 200, response.code());
            Assert.assertEquals("Wrong response body!", petEntryList.toString(), response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void put() {
        petService.add(NEW_PET_NAME1, NEW_PET_TEG1);

        RequestBody requestBody = new FormBody.Builder()
                .add("name", NEW_PET_NAME2)
                .add("tag", NEW_PET_TEG2).build();
        Request request = new Request.Builder().url("http://localhost:8080/pet/1").put(requestBody).build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            Assert.assertTrue("Request failed!", response.isSuccessful());
            Assert.assertEquals("Wrong status code!", 200, response.code());
            Assert.assertEquals("Wrong response body!", UPDATE_MSG1, response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void delete() {
        petService.add(NEW_PET_NAME1, NEW_PET_TEG1);

        Request request = new Request.Builder().url("http://localhost:8080/pet/1").delete().build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            Assert.assertTrue("Request failed!", response.isSuccessful());
            Assert.assertEquals("Wrong status code!", 200, response.code());
            Assert.assertEquals("Wrong response body!", DELETE_MSG1, response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
        petService = null;
        petController = null;
        okHttpClient = null;
        HibernateUtil.shutdown();
    }
}