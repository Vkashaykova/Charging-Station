package com.example.hubject.repositories.contracts;

import com.example.hubject.models.Zipcode;

import java.util.Optional;

public interface ZipcodeRepository {

    Optional<Zipcode>getZipcodeById(int zipcodeId);
    Optional<Zipcode>getZipcodeByValue(int zipcode);

    void addZipcode(Zipcode zipcode);

    void updateZipcode(Zipcode zipcode);

    void deleteZipCode(Zipcode zipcode);
}
