package com.egitim.web;

import com.udemy.egitim.model.Owner;
import junit.framework.AssertionFailedError;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PetClinicestControllerTests {
    private RestTemplate restTemplate;

    @Before
    public void setUp(){
        restTemplate = new RestTemplate();
    }

    @Test
    public void testGetOwnerById(){
        ResponseEntity<Owner> responseEntity = restTemplate.getForEntity("http://localhost:8585/rest/owner/1", Owner.class);
        MatcherAssert.assertThat(responseEntity.getStatusCodeValue(), Matchers.equalTo(200));
        MatcherAssert.assertThat(responseEntity.getBody().getFirstName(),Matchers.equalTo("Owner 1"));
        MatcherAssert.assertThat(responseEntity.getBody().getLastName(),Matchers.equalTo("LastName 1"));
    }
    @Test
    public void testGetOwnersByLastName(){
       ResponseEntity<List> responseEntity = restTemplate.getForEntity("http://localhost:8585/rest/owner?ln=LastName 2", List.class);

       MatcherAssert.assertThat(responseEntity.getStatusCodeValue(),Matchers.equalTo(200));

       List<Map<String,String>> body = responseEntity.getBody();
       List<String> firstNameList = body.stream().map(e -> e.get("firstName")).collect(Collectors.toList());

       MatcherAssert.assertThat(firstNameList,Matchers.containsInAnyOrder("Owner 2"));
    }
    @Test
    public void testGetOwners(){
        ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8585/rest/owners",List.class);

        MatcherAssert.assertThat(response.getStatusCodeValue(),Matchers.equalTo(200));

        List<Map<String,String>> body = response.getBody();
        List<String> firstNameList = body.stream().map(e -> e.get("firstName")).collect(Collectors.toList());
        MatcherAssert.assertThat(firstNameList,Matchers.containsInAnyOrder("Owner 1","Owner 2","Owner 3","Owner 4"));
    }

    @Test
    public void testCreateOwner(){
        Owner owner = new Owner();
        owner.setFirstName("Mustafa");
        owner.setLastName("Güngör");
        URI location = restTemplate.postForLocation("http://localhost:8585/rest/owner",owner);

        Owner owner2 = restTemplate.getForObject(location,Owner.class);

        MatcherAssert.assertThat(owner2.getFirstName(),Matchers.equalTo(owner.getFirstName()));
        MatcherAssert.assertThat(owner2.getLastName(),Matchers.equalTo(owner.getLastName()));
    }

    @Test
    public void testUpdateOwner(){
        Owner owner = restTemplate.getForObject("http://localhost:8585/rest/owner/4",Owner.class);
        MatcherAssert.assertThat(owner.getFirstName(),Matchers.equalTo("Mustafa"));

        owner.setFirstName("Mustafa G");
        restTemplate.put("http://localhost:8585/rest/owner/4",owner);

        owner = restTemplate.getForObject("http://localhost:8585/rest/owner/4",Owner.class);
        MatcherAssert.assertThat(owner.getFirstName(),Matchers.equalTo("Mustafa G"));
    }

    @Test
    public void testDeleteOwner(){
        restTemplate.delete("http://localhost:8585/rest/owner/1");
        try {
            restTemplate.getForEntity("http://localhost:8585/rest/owner/1",Owner.class);
            Assert.fail("Failure");
        }catch (RestClientException e){

        }
    }
}
