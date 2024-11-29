package com.example.demo.journalcontroller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.journal.Journal;
import com.example.demo.journalrepository.JournalRepository;

@RestController
@RequestMapping(path = "api/journal")
public class JournalController {
    final JournalRepository journalRepository;

    public JournalController(JournalRepository journalRepository) {
        this.journalRepository = journalRepository;
    }

    @GetMapping
    public ResponseEntity<List<Journal>> getAll() {
        return new ResponseEntity<>(journalRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Journal> createJournal(@RequestBody Journal journal) {
        Journal journalCreated = journalRepository.save(journal);
        return new ResponseEntity<>(journalCreated, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Journal> getJournalById(@PathVariable Long id) {
        Optional<Journal> journal = journalRepository.findById(id);
        if (journal.isPresent()) {
            return new ResponseEntity<>(journal.get(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Journal> updateJournal(@PathVariable Long id, @RequestBody Journal journalDetails) {
        Optional<Journal> journal = journalRepository.findById(id);
        if (journal.isPresent()) {
            Journal existingJournal = journal.get();
            existingJournal.setTitre(journalDetails.getTitre());
            existingJournal.setDescription(journalDetails.getDescription());
            Journal updatedJournal = journalRepository.save(existingJournal);
            return new ResponseEntity<>(updatedJournal, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Journal> deleteJournal(@PathVariable Long id) {
        Optional<Journal> journal = journalRepository.findById(id);
        if (journal.isPresent()) {
            journalRepository.delete(journal.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
