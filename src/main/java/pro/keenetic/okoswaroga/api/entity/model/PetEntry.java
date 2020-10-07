package pro.keenetic.okoswaroga.api.entity.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * The type Pet entry.
 */
@Entity
public class PetEntry implements Serializable {
    private static final long serialVersionUID = -8815016473053561713L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    private Integer petId;

    @Column
    private String name;

    @Column
    private String tag;

    /**
     * Instantiates a new Pet entry.
     */
    public PetEntry(){}

    /**
     * Instantiates a new Pet entry.
     *
     * @param petId the pet id
     * @param name  the name
     * @param tag   the tag
     */
    public PetEntry(Integer petId, String name, String tag) {
        this.petId = petId;
        this.name = name;
        this.tag = tag;
    }

    /**
     * Instantiates a new Pet entry.
     *
     * @param newPet the new pet
     */
    public PetEntry(NewPet newPet) {
        this.name = newPet.getName();
        this.tag = newPet.getTag();
    }

    /**
     * Gets pet id.
     *
     * @return the pet id
     */
    public Integer getPetId() {
        return petId;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets tag.
     *
     * @param tag the tag
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PetEntry)) return false;
        PetEntry petEntry = (PetEntry) o;
        return getPetId().equals(petEntry.getPetId()) &&
                name.equals(petEntry.name) &&
                tag.equals(petEntry.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPetId(), name, tag);
    }

    @Override
    public String toString() {
        return "PetEntry{" +
                "petId=" + petId +
                ", name='" + name + '\'' +
                ", tag='" + tag + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getTag() {
        return tag;
    }
}
