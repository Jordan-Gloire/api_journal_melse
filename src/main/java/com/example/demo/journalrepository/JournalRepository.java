package com.example.demo.journalrepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.journal.Journal;

public interface JournalRepository extends JpaRepository<Journal, Long> {

}
