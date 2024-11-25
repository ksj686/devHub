package app.labs.journal.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import app.labs.journal.model.Journal;

@Mapper
public interface JournalRepository {
	void createJournal(Journal journal);
	Journal getJournal(int journalId);
	List<Journal> getAllJournal(String userId);
}
