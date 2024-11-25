package app.labs.journal.service;

import java.util.List;

import app.labs.journal.model.Journal;

public interface JournalService {
	void createJournal(Journal journal);
	Journal getJournal(int journalId);
	List<Journal> getAllJournal(String userId);
}
