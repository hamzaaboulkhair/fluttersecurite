package com.example.securite.services;

import com.example.securite.entities.APS;
import com.example.securite.repositories.APSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class APSService {
    @Autowired
    private APSRepository apsRepository;

    public List<APS> getAllAPS(){
        return apsRepository.findAll();
    }
    public Optional<APS> getAPSById(long id){
        return apsRepository.findById(id);
    }

    public APS createAPS(APS aps){
        return apsRepository.save(aps);
    }

    public void deleteAPSById(long id){
        apsRepository.deleteById(id);
    }
}
