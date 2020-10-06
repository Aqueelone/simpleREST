package pro.keenetic.okoswaroga.api.service;

import org.hibernate.Session;
import pro.keenetic.okoswaroga.api.HibernateUtil;
import pro.keenetic.okoswaroga.api.entity.model.NewPet;
import pro.keenetic.okoswaroga.api.entity.model.PetEntry;

import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import static pro.keenetic.okoswaroga.api.entity.model.NewPet.NewPetBuilder.aNewPet;

public class PetService {
    private static Session session;

    public PetService() {
    }

    public PetEntry add(String name, String tag) {
        session = HibernateUtil.getSessionFactory().openSession();
        NewPet newPet = aNewPet().withName(name).withTag(tag).build();
        PetEntry petEntry = new PetEntry(newPet);
        session.getTransaction().begin();
        session.save(petEntry);
        session.getTransaction().commit();
        session.close();
        return petEntry;
    }

    public PetEntry findById(String params) {
        session = HibernateUtil.getSessionFactory().openSession();
        PetEntry petEntry = session.get(PetEntry.class, Integer.valueOf(params));
        session.close();
        return petEntry;
    }

    public List<PetEntry> findAll() {
        session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<PetEntry> cq = cb.createQuery(PetEntry.class);
        Root<PetEntry> entryRoot = cq.from(PetEntry.class);
        CriteriaQuery<PetEntry> all = cq.select(entryRoot);
        TypedQuery<PetEntry> typedQuery = session.createQuery(all);
        List<PetEntry> petEntry = typedQuery.getResultList();
        session.close();
        return petEntry;
    }

    public boolean update(String id, String name, String tag) {
        boolean r = false;
        session = HibernateUtil.getSessionFactory().openSession();
        PetEntry petEntry = session.get(PetEntry.class, Integer.valueOf(id));
        session.getTransaction().begin();
        petEntry.setName(name);
        petEntry.setTag(tag);
        try {
            session.update(petEntry);
            session.getTransaction().commit();
            r = true;
        }
        catch(PersistenceException e){
            session.getTransaction().rollback();
        }
        session.close();
        return r;
    }

    public boolean delete(String id) {
        boolean r = false;
        session = HibernateUtil.getSessionFactory().openSession();
        PetEntry petEntry = session.get(PetEntry.class, Integer.valueOf(id));
        session.getTransaction().begin();
        try {
            session.delete(petEntry);
            session.getTransaction().commit();
            r = true;
        }
        catch(PersistenceException e){
            session.getTransaction().rollback();
        }
        session.close();
        return r;
    }
}
