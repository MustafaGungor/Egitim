package com.udemy.egitim.dao.inmemory;

import com.udemy.egitim.dao.OwnerRepository;
import com.udemy.egitim.model.Owner;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class OwnerRepositoryInMemoryImpl implements OwnerRepository {

    private Map<Long,Owner> ownerMap = new HashMap<>();

    public OwnerRepositoryInMemoryImpl() {
        Owner owner1 = new Owner();
        owner1.setId(1l);
        owner1.setFirstName("Owner 1");
        owner1.setLastName("LastName 1");
        Owner owner2 = new Owner();
        owner2.setId(2l);
        owner2.setFirstName("Owner 2");
        owner2.setLastName("LastName 2");
        Owner owner3 = new Owner();
        owner3.setId(3l);
        owner3.setFirstName("Owner 3");
        owner3.setLastName("LastName 3");
        Owner owner4 = new Owner();
        owner4.setId(4l);
        owner4.setFirstName("Owner 4");
        owner4.setLastName("LastName 4");

        ownerMap.put(owner1.getId(),owner1);
        ownerMap.put(owner2.getId(),owner2);
        ownerMap.put(owner3.getId(),owner3);
        ownerMap.put(owner4.getId(),owner4);
    }

    @Override
    public List<Owner> findAll() {
        return new ArrayList<>(ownerMap.values());
    }

    @Override
    public Owner findById(Long id) {
        return ownerMap.get(id);
    }

    @Override
    public List<Owner> findByLastName(String lastName) {
        return ownerMap.values().stream().filter(o->o.getLastName().equals(lastName)).collect(Collectors.toList());
    }

    @Override
    public void create(Owner owner) {
        owner.setId(new Date().getTime());
        ownerMap.put(owner.getId(),owner);
    }

    @Override
    public Owner update(Owner owner) {
        ownerMap.replace(owner.getId(),owner);
        return owner;
    }

    @Override
    public void delete(Long id) {
        ownerMap.remove(id);
    }
}
