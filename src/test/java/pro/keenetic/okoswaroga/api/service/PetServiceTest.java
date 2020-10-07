package pro.keenetic.okoswaroga.api.service;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pro.keenetic.okoswaroga.api.HibernateUtil;
import pro.keenetic.okoswaroga.api.entity.model.NewPet;
import pro.keenetic.okoswaroga.api.entity.model.PetEntry;

import java.util.List;

class PetServiceTest {

    private final static String NEW_PET_NAME1 = "vasya";
    private final static String NEW_PET_TEG1 = "cat";
    private final static String NEW_PET_NAME2 = "sharik";
    private final static String NEW_PET_TEG2 = "dog";

    private static PetService petService;

    @BeforeEach
    void setUp() {
        petService = new PetService();
    }

    @AfterEach
    void tearDown() {
        petService = null;
        HibernateUtil.shutdown();
    }

    @Test
    void add() {
        NewPet newPet = NewPet.NewPetBuilder.aNewPet().withName(NEW_PET_NAME1).withTag(NEW_PET_TEG1).build();
        PetEntry petEntryFixture = new PetEntry(newPet);
        PetEntry petEntry = petService.add(NEW_PET_NAME1, NEW_PET_TEG1);

        Assert.assertNotNull("PetEntry not created!", petEntry);
        Assert.assertEquals("Names not equal!", petEntry.getName(), petEntryFixture.getName());
        Assert.assertEquals("Tags not equal!", petEntry.getTag(), petEntryFixture.getTag());
    }

    @Test
    void findByIdSuccess() {
        NewPet newPet = NewPet.NewPetBuilder.aNewPet().withName(NEW_PET_NAME1).withTag(NEW_PET_TEG1).build();
        PetEntry petEntryFixture = new PetEntry(newPet);
        petService.add(NEW_PET_NAME1, NEW_PET_TEG1);
        PetEntry petEntry = petService.findById("1");

        Assert.assertNotNull("PetEntry not created!", petEntry);
        Assert.assertEquals("Names not equal!", petEntry.getName(), petEntryFixture.getName());
        Assert.assertEquals("Tags not equal!", petEntry.getTag(), petEntryFixture.getTag());
    }

    @Test
    void findByIdFail() {
        NewPet newPet = NewPet.NewPetBuilder.aNewPet().withName(NEW_PET_NAME1).withTag(NEW_PET_TEG1).build();
        PetEntry petEntryFixture = new PetEntry(newPet);
        petService.add(NEW_PET_NAME1, NEW_PET_TEG1);
        PetEntry petEntry = petService.findById("2");

        Assert.assertNull("PetEntry should be not found!", petEntry);
    }

    @Test
    void findAll() {
        NewPet newPet = NewPet.NewPetBuilder.aNewPet().withName(NEW_PET_NAME1).withTag(NEW_PET_TEG1).build();
        PetEntry petEntryFixture = new PetEntry(newPet);
        petService.add(NEW_PET_NAME1, NEW_PET_TEG1);
        NewPet newPet2 = NewPet.NewPetBuilder.aNewPet().withName(NEW_PET_NAME2).withTag(NEW_PET_TEG2).build();
        PetEntry petEntryFixture2 = new PetEntry(newPet2);
        petService.add(NEW_PET_NAME2, NEW_PET_TEG2);

        List<PetEntry> petEntryList = petService.findAll();

        Assert.assertNotNull("PetEntryList not returned!", petEntryList);
        Assert.assertEquals("Size not correct!", petEntryList.size(), 2);
        Assert.assertEquals("Names1 not equal!", petEntryList.get(0).getName(), petEntryFixture.getName());
        Assert.assertEquals("Tags1 not equal!", petEntryList.get(0).getTag(), petEntryFixture.getTag());
        Assert.assertEquals("Names2 not equal!", petEntryList.get(1).getName(), petEntryFixture2.getName());
        Assert.assertEquals("Tags2 not equal!", petEntryList.get(1).getTag(), petEntryFixture2.getTag());
    }

    @Test
    void update() {
        NewPet newPet = NewPet.NewPetBuilder.aNewPet().withName(NEW_PET_NAME2).withTag(NEW_PET_TEG2).build();
        PetEntry petEntryFixture = new PetEntry(newPet);
        petService.add(NEW_PET_NAME1, NEW_PET_TEG1);

        petService.update("1", NEW_PET_NAME2, NEW_PET_TEG2);
        PetEntry petEntry = petService.findById("1");

        Assert.assertNotNull("PetEntry not returned!", petEntry);
        Assert.assertEquals("Updated names not equal!", petEntry.getName(), petEntryFixture.getName());
        Assert.assertEquals("Updated tags not equal!", petEntry.getTag(), petEntryFixture.getTag());
    }

    @Test
    void delete() {
        petService.add(NEW_PET_NAME1, NEW_PET_TEG1);

        petService.delete("1");
        PetEntry petEntry = petService.findById("1");

        Assert.assertNull("PetEntry should be null!", petEntry);
    }
}