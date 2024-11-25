package app.labs.journal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.labs.journal.dao.JournalRepository;
import app.labs.journal.model.Journal;

@Service
public class BasicJournalService implements JournalService{

	@Autowired
	private JournalRepository journalRepository;
	
	@Override
	public void createJournal(Journal journal) {
		journalRepository.createJournal(journal);
	}
	
	@Override
	public List<Journal> getAllJournal(String userId) {
		List<Journal> journals = journalRepository.getAllJournal(userId);
		return journals;
	}
	
	@Override
	public Journal getJournal(int journalId) {
		Journal journal = journalRepository.getJournal(journalId);
		return journal;
	}
}