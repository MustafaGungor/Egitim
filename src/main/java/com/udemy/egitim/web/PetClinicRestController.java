package com.udemy.egitim.web;

import com.udemy.egitim.exception.InteralServerException;
import com.udemy.egitim.exception.OwnerNotFoundException;
import com.udemy.egitim.model.Owner;
import com.udemy.egitim.service.PetClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class PetClinicRestController {
    @Autowired
    private PetClinicService petClinicService;

    @RequestMapping(method = RequestMethod.GET,value = "/owners")
    public ResponseEntity<List<Owner>> getOwners(){
        List<Owner> owners= petClinicService.findOwners();
        return ResponseEntity.ok(owners);
    }

    //PathVariable ------>  /owner?ln=lastName
    @RequestMapping(method = RequestMethod.GET,value = "/owner")
    public ResponseEntity<List<Owner>> getOwners(@RequestParam("ln") String lastName){
        return ResponseEntity.ok(petClinicService.findOwners(lastName));
    }

    //PathVariable ------>  /owner/3
    @RequestMapping(method = RequestMethod.GET,value = "/owner/{id}",produces = "application/json")
    public ResponseEntity<?> getOwners(@PathVariable("id") Long id){
        try {
            Owner owner= petClinicService.findOwner(id);

            ControllerLinkBuilder linkBuilder = ControllerLinkBuilder.linkTo(PetClinicRestController.class);
            Link self = linkBuilder.slash("/owner/"+id).withSelfRel();
            Link create = linkBuilder.slash("/owner").withRel("create");
            Link update = linkBuilder.slash("/owner/"+id).withRel("update");
            Link delete = linkBuilder.slash("/owner/"+id).withRel("delete");
            Resource<Owner> resource= new Resource<Owner>(owner,self,create,update,delete);
            return ResponseEntity.ok(resource);
        }catch (OwnerNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/owner")
    public ResponseEntity<URI> createOwner(@RequestBody Owner owner){
        try {
            petClinicService.createOwner(owner);
            Long id = owner.getId();
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
            return ResponseEntity.created(location).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/owner/{id}")
    public ResponseEntity<?> updateOwner(@PathVariable("id") Long id, @RequestBody Owner ownerReq){
        try{
            Owner owner = petClinicService.findOwner(id);
            owner.setFirstName(ownerReq.getFirstName());
            owner.setLastName(ownerReq.getLastName());
            petClinicService.update(owner);
            return ResponseEntity.ok().build();
        }catch (OwnerNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(method = RequestMethod.DELETE,value = "/owner/{id}")
    public ResponseEntity<?> deleteOwner(@PathVariable("id") Long id){
        try {
            petClinicService.deleteOwner(id);
            return ResponseEntity.ok().build();
        }catch (OwnerNotFoundException e){
            throw new OwnerNotFoundException("NOT FOUND");
        }catch (Exception e){
            throw new InteralServerException(e);
        }
    }

}
