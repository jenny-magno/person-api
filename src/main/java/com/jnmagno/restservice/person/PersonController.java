package com.jnmagno.restservice.person;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.jnmagno.restservice.common.exception.PersonNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/persons")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping("")
    public List <PersonEntity> getAllPersons() {
        return this.personRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity <PersonEntity> getPersonById(@PathVariable(value = "id") Long personId)
    throws PersonNotFoundException {
        PersonEntity person = personRepository.findById(personId)
            .orElseThrow(() -> new PersonNotFoundException(personId));
        return ResponseEntity.ok().body(person);
    }

    @PostMapping("")
    public PersonEntity createPerson(@Valid @RequestBody PersonEntity person) {
        return personRepository.save(person);
    }

    @PutMapping("/{id}")
    public ResponseEntity <PersonEntity> updatePerson(@PathVariable(value = "id") Long personId,
        @Valid @RequestBody PersonEntity personDetails) throws PersonNotFoundException {
        PersonEntity person = personRepository.findById(personId)
            .orElseThrow(() -> new PersonNotFoundException(personId));

        person.setLastName(personDetails.getLastName());
        person.setFirstName(personDetails.getFirstName());
        person.setAge(personDetails.getAge());
        person.setHobby(personDetails.getHobby());
        final PersonEntity updatedPerson = personRepository.save(person);
        return ResponseEntity.ok(updatedPerson);
    }

    @DeleteMapping("/{id}")
    public Map <String, Boolean> deletePerson(@PathVariable(value = "id") Long personId)
    throws PersonNotFoundException {
        PersonEntity person = personRepository.findById(personId)
            .orElseThrow(() -> new PersonNotFoundException(personId));

        personRepository.delete(person);
        Map <String, Boolean> response = new HashMap <> ();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}