package com.tool.Data.Flow.Engine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tool.Data.Flow.Engine.model.DataRecord;

@Repository
public interface DataRecordRepository extends JpaRepository<DataRecord, Long> {
}

//JpaRepository: The DataRecordRepository interface extends JpaRepository, which provides JPA-related methods such as save, saveAll, findById, findAll, deleteById, etc.
//Batch Inserts: The saveAll method provided by JpaRepository can be used to perform batch inserts. When you call saveAll with a list of DataRecord entities, Spring Data JPA will handle the batch insert for you.