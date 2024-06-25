package com.example.bestweekend_trip.bwt_api.service;
import com.example.bestweekend_trip.bwt_api.entity.Destiny;
import com.example.bestweekend_trip.bwt_api.repository.DestinyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DestinyService {

    @Autowired
    private DestinyRepository destinyRepository;

    public void createDestiny(Destiny destiny) {
        destinyRepository.save(destiny);
    }

    public List<Destiny> getAllDestiny() {
        return destinyRepository.findAll();
    }

    public List<Destiny> getDestinyByName(String name) {
        return destinyRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Destiny> getDestinyByLocation(String location) {
        return destinyRepository.findByLocationContainingIgnoreCase(location);
    }

    public Optional<Destiny> getDestinyById(Long id) {
        return destinyRepository.findById(id);
    }

    public Optional<Destiny> rateDestiny(Long id, int rating) {
        Optional<Destiny> optionalDestiny = destinyRepository.findById(id);
        if (optionalDestiny.isPresent()) {
            Destiny destiny = optionalDestiny.get();
            int totalRatings = destiny.getNumberOfRatings()+ 1;
            double newAverageRating = ((destiny.getAverageRating() * destiny.getNumberOfRatings() + rating) / totalRatings);
            destiny.setAverageRating(newAverageRating);
            destiny.setNumberOfRatings(totalRatings);
            destinyRepository.save(destiny);
            return Optional.of(destiny);
        }
        return Optional.empty();
    }

    public boolean reserveDestiny(Long id) {
        Optional<Destiny> optionalDestiny = destinyRepository.findById(id);
        if (optionalDestiny.isPresent()) {
            Destiny destiny = optionalDestiny.get();
            if (!destiny.isReserved()) {
                destiny.setReserved(true);
                destinyRepository.save(destiny);
                return true;
            }
        }
        return false;
    }

    public Destiny updateDestiny(Long id, Destiny destiny) {
        System.out.println("Finding destiny ID: " + id);
        Optional<Destiny> existingDestiny = destinyRepository.findById(id);
        if (existingDestiny.isPresent()) {
            System.out.println("Reloading destiny...");
            Destiny updatedDestiny = existingDestiny.get();
            updatedDestiny.setName(destiny.getName());
            updatedDestiny.setLocation(destiny.getLocation());
            updatedDestiny.setConvenience(destiny.getConvenience());
            updatedDestiny.setAverageRating(destiny.getAverageRating());
            updatedDestiny.setNumberOfRatings(destiny.getNumberOfRatings());
            updatedDestiny.setReserved(destiny.isReserved());
            return destinyRepository.save(updatedDestiny);
        } else {
            System.out.println("Can't find destiny by id: " + id);
        }
        return destiny;
    }
    public boolean deleteDestiny(Long id) {
        try {
            destinyRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}




