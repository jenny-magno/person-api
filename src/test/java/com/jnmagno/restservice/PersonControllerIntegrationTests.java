package com.jnmagno.restservice;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.util.Arrays;

import com.jnmagno.restservice.person.PersonController;
import com.jnmagno.restservice.person.PersonEntity;
import com.jnmagno.restservice.person.PersonRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = RestServiceApplication.class)
@AutoConfigureMockMvc
public class PersonControllerIntegrationTests {

    @Autowired
    private PersonRepository personRepository;
    
    @Autowired
    PersonController personController;

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser("USER")
    @Test
    public void whenGetPersons_thenCorrectResponse() throws Exception {
      PersonEntity karen = new PersonEntity();
      karen.setFirstName("Karen");
      karen.setLastName("Johnson");
      karen.setAge(19);
      karen.setHobby(Arrays.asList("swimming", "skiing"));
      karen = this.personRepository.save(karen);

      PersonEntity mike = new PersonEntity();
      mike.setFirstName("Mike");
      mike.setLastName("Summer");
      mike.setAge(20);
      mike.setHobby(Arrays.asList("skiing", "cooking"));
      mike = this.personRepository.save(mike);

      mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/persons")
              .contentType(MediaType.APPLICATION_JSON))
              .andExpect(MockMvcResultMatchers.status().isOk())
              .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @WithMockUser("USER")
    @Test
    public void whenGetSinglePerson_thenCorrectResponse() throws Exception {
      PersonEntity karen = new PersonEntity();
      karen.setFirstName("Karen");
      karen.setLastName("Johnson");
      karen.setAge(19);
      karen.setHobby(Arrays.asList("swimming", "skiing"));
      karen = this.personRepository.save(karen);

      mockMvc.perform(MockMvcRequestBuilders.get(
        String.format("/api/v1/persons/%d", karen.getId()))
              .contentType(MediaType.APPLICATION_JSON))
              .andExpect(MockMvcResultMatchers.status().isOk())
              .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", is("Karen")));
    }
    
    @WithMockUser(username = "USER")
    @Test
    public void whenPostRequestToPersons_thenCorrectResponse() throws Exception {
      PersonEntity person = new PersonEntity();
      person.setFirstName("Jane");
      person.setLastName("Doe");
      person.setAge(29);
      person.setHobby(Arrays.asList("reading", "cooking"));
      mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/persons")
        .content(person.toFormattedString())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", 
          is("Jane")))
        .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", 
          is("Doe")))
        .andExpect(MockMvcResultMatchers.jsonPath("$.age", 
          is(29)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.hobby").isArray())
        .andExpect(MockMvcResultMatchers.jsonPath("$.hobby", hasSize(2)));
    }

    @WithMockUser("USER")
    @Test
    public void whenPutRequestToPersons_thenCorrectResponse() throws Exception {
      PersonEntity brittany = new PersonEntity();
      brittany.setFirstName("Brittany");
      brittany.setLastName("Johnson");
      brittany.setAge(22);
      brittany.setHobby(Arrays.asList("reading", "sleeping"));
      brittany = this.personRepository.save(brittany);

      brittany.setAge(24);
      brittany.setHobby(Arrays.asList("reading", "dancing", "swimming"));

      mockMvc.perform(MockMvcRequestBuilders.put(
        String.format("/api/v1/persons/%d", brittany.getId()))
          .content(brittany.toFormattedString())
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(MockMvcResultMatchers.status().isOk())
          .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", 
            is("Brittany")))
          .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", 
            is("Johnson")))
          .andExpect(MockMvcResultMatchers.jsonPath("$.age", 
            is(24)))
          .andExpect(MockMvcResultMatchers.jsonPath("$.hobby").isArray())
          .andExpect(MockMvcResultMatchers.jsonPath("$.hobby", hasSize(3)));
    }


    @WithMockUser("USER")
    @Test
    public void whenDeleteRequestToPersons_thenCorrectResponse() throws Exception {
      PersonEntity caleb = new PersonEntity();
      caleb.setFirstName("Caleb");
      caleb.setLastName("Johnson");
      caleb.setAge(20);
      caleb.setHobby(Arrays.asList("skiing", "singing"));
      caleb = this.personRepository.save(caleb);

      mockMvc.perform(MockMvcRequestBuilders.delete(
        String.format("/api/v1/persons/%d", caleb.getId()))
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(MockMvcResultMatchers.status().isOk())
          .andExpect(MockMvcResultMatchers.jsonPath("$.deleted", 
            is(true)));

      mockMvc.perform(MockMvcRequestBuilders.delete(
        String.format("/api/v1/persons/%d", caleb.getId()))
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}