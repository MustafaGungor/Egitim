package com.udemy.egitim.service;

import com.udemy.egitim.exception.OwnerNotFoundException;
import com.udemy.egitim.model.Owner;

import java.util.List;

public interface PetClinicService {
    List<Owner> findOwners();
    List<Owner> findOwners(String lastName);
    Owner findOwner(Long id) throws OwnerNotFoundException;
    void createOwner(Owner owner);
    void update(Owner owner);
    void deleteOwner(Long id);
}
