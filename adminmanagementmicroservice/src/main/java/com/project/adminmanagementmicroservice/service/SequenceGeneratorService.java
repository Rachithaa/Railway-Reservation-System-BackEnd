package com.project.adminmanagementmicroservice.service;

import com.project.adminmanagementmicroservice.model.DBSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

@Service
public class SequenceGeneratorService {

    @Autowired
    private MongoOperations mongoOperations;

    public int getSequenceNumber(String sequencename)
    {
        Query query= new Query(Criteria.where("id").is(sequencename));

        Update update =new Update().inc("seqNo",1);

        DBSequence counter=mongoOperations.findAndModify(query,update
                ,options().returnNew(true).upsert(true), DBSequence.class);
        return !Objects.isNull(counter)? (int) counter.getSeqNo() : 1;

    }
}
