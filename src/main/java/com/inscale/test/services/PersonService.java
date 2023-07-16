package com.inscale.test.services;

import com.inscale.test.aws.S3Controller;
import com.inscale.test.aws.SqsController;
import com.inscale.test.dto.AddressDTO;
import com.inscale.test.dto.PersonDTO;
import com.inscale.test.entity.Address;
import com.inscale.test.entity.Person;
import com.inscale.test.exception.PersonException;
import com.inscale.test.repository.AddressRepository;
import com.inscale.test.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static java.util.Objects.nonNull;

@Slf4j
@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final AddressRepository addressRepository;
    private final S3Controller s3Controller;
    private final SqsController sqsController;

    @Autowired
    public PersonService(PersonRepository personRepository, AddressRepository addressRepository, S3Controller s3Controller, SqsController sqsController) {
        this.personRepository = personRepository;
        this.addressRepository = addressRepository;
        this.s3Controller = s3Controller;
        this.sqsController = sqsController;
    }

    /**
     * @param personDTO
     * Method that saves the person, and send it to the SQS
     * @return Person
     * @throws Exception
     */
    @Transactional
    public Person createPerson(PersonDTO personDTO) throws Exception {
        try {
            log.info("Inserting Person");
            if(nonNull(personDTO)){
                Person personEntity = new Person();
                BeanUtils.copyProperties(personDTO, personEntity);
                personEntity.setAddress(saveOrUpdateAddresses(personDTO.getAddressDTOList()));
                String messageId = sqsController.sendMessage(personDTO);
                log.info("Message id: {}", messageId);
                return personRepository.save(personEntity);
            } else {
                throw new PersonException("No person to create.");
            }
        } catch (Exception ex) {
            log.error("Error while inserting Person");
            throw new Exception(ex);
        }
    }

    /**
     * Method that returns a List of persons
     * @return List<Person>
     */
    @Transactional
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    /**
     * @param id
     * Method that returns a person by Id
     * @return Person
     */
    @Transactional
    public Person getPersonById(Long id) {
        log.info("Recovering Person By Id {}", id);
        Optional<Person> optionalPersonEntity = personRepository.findById(id);
        return optionalPersonEntity.orElse(null);
    }

    /**
     * @param personDTO
     * Method that updates the person using patch, we first recover the correct entity and then overwrite it with the DTO
     * @return Person
     * @throws Exception
     */
    @Transactional
    public Person updatePerson(PersonDTO personDTO) throws Exception {
        try {
            log.info("Updating Person with ID {}", personDTO.getId());
            Optional<Person> optionalPersonEntity = personRepository.findById(personDTO.getId());
            if (optionalPersonEntity.isPresent()) {
                Person personEntity = optionalPersonEntity.get();
                BeanUtils.copyProperties(personDTO, personEntity);
                personEntity.setAddress(saveOrUpdateAddresses(personDTO.getAddressDTOList()));
                return personRepository.save(personEntity);
            } else {
                throw new PersonException("No person to update.");
            }
        } catch (Exception ex) {
            log.error("Error while updating Person");
            throw new Exception(ex);
        }
    }

    /**
     * @param id
     * Recover the person and save into the bucket, then delete
     */
    @Transactional
    public void deletePerson(Long id) {
        s3Controller.uploadToS3(getPersonById(id));
        personRepository.deleteById(id);
    }

    /**
     * @param addressDTOS
     * Method that receives the AddressDTO, iterates over it and save.
     * @return Set<Address>
     */
    private Set<Address> saveOrUpdateAddresses(Set<AddressDTO> addressDTOS) {
        Set<Address> addresses = new HashSet<>();
        for (AddressDTO dto : addressDTOS) {
            Address address = new Address();
            BeanUtils.copyProperties(dto, address);
            addresses.add(addressRepository.save(address));
        }
        return addresses;
    }

}
