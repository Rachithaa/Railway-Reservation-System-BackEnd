package com.project.bookingmanagementmicroservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "db_sequence")
public class DBSequence {

    @Id
    private String id;

    private long seqNo;
}
