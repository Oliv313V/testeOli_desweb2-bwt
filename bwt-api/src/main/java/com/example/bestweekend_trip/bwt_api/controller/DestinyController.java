package com.example.bestweekend_trip.bwt_api.controller;

import com.example.bestweekend_trip.bwt_api.entity.Destiny;
import com.example.bestweekend_trip.bwt_api.service.DestinyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/destiny")
public class DestinyController {

    @Autowired
    private DestinyService destinyService;


    @PostMapping
    public ResponseEntity<Destiny> createDestiny(@RequestBody Destiny destiny) {
        destinyService.createDestiny(destiny);
        return new ResponseEntity<>(destiny, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<Destiny>> getAllDestiny() {
        List<Destiny> destinies = destinyService.getAllDestiny();
        return new ResponseEntity<>(destinies, HttpStatus.OK);
    }


    @GetMapping("/search")
    public ResponseEntity<List<Destiny>> searchDestiny(@RequestParam (required = false) String name,
                                                       @RequestParam (required = false) String location) {
        List<Destiny> results;
        if (name != null && !name.isEmpty()) {
            results = destinyService.getDestinyByName(name);
        } else if (location != null && !location.isEmpty()) {
            results = destinyService.getDestinyByLocation(location);
        } else {
            results = destinyService.getAllDestiny();
        }
        return new ResponseEntity<>(results,HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Destiny> getDestinyById(@PathVariable Long id) {
        Optional<Destiny> destiny = destinyService.getDestinyById(id);
        if(destiny.isPresent())
            return new ResponseEntity<>(destiny.get(), HttpStatus.OK);
         else
            return new ResponseEntity<>(new Destiny(), HttpStatus.NOT_FOUND);
    }


    @PostMapping("/{id}/reserve")
    public ResponseEntity<String> reserveDestiny(@PathVariable Long id) {
        boolean success = destinyService.reserveDestiny(id);
        if(success) {
            return new ResponseEntity<>("Successfully reserved package", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Failed to reserve package", HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/{id}/rate")
    public ResponseEntity<Destiny> rateDestiny(@PathVariable Long id, @RequestParam int rating) {
        if (rating < 1 || rating > 10){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Destiny> destiny = destinyService.rateDestiny(id, rating);
        return destiny.map(value -> new ResponseEntity<>(value,HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PutMapping("/{id}/reload")
    public ResponseEntity<Destiny> updateDestiny(@PathVariable Long id, @RequestBody Destiny destiny) {
        System.out.println("Reloading destiny by ID: " + id);
        Destiny updatedDestiny = destinyService.updateDestiny(id, destiny);
        System.out.println("Reloaded destiny: " + updatedDestiny);
        return new ResponseEntity<>(updatedDestiny, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDestiny(@PathVariable Long id) {
        boolean success = destinyService.deleteDestiny(id);
        if(success) {
            return new ResponseEntity<>("Successfully deleted destiny", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Failed to delete destiny", HttpStatus.NOT_FOUND);
        }
    }
}
