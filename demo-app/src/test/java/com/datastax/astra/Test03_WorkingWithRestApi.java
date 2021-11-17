package com.datastax.astra;

import static com.datastax.astra.DemoDataSet.appToken;
import static com.datastax.astra.DemoDataSet.awsEast1;
import static com.datastax.astra.DemoDataSet.clientId;
import static com.datastax.astra.DemoDataSet.clientSecret;
import static com.datastax.astra.DemoDataSet.ksName;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.dstx.astra.sdk.AstraClient;

import io.stargate.sdk.rest.domain.CreateTable;
import io.stargate.sdk.rest.domain.Ordering;
import io.stargate.sdk.rest.domain.SearchTableQuery;

public class Test03_WorkingWithRestApi {
    
    public static final String ANSI_RESET           = "\u001B[0m";
    public static final String ANSI_GREEN           = "\u001B[32m";
    
    private static AstraClient astraClient;
    
    // <CHANGE_ME>
    private static String databaseId    = "cfa040d4-cfd4-489f-9dd2-352cb01801f1"; 
     
    @BeforeAll
    public static void init() {
        astraClient = AstraClient.builder()
                .appToken(appToken).clientId(clientId).clientSecret(clientSecret)
                .databaseId(databaseId).cloudProviderRegion(awsEast1)
                .keyspace(ksName)
                .build();
        System.out.println(ANSI_GREEN + "[OK]" + ANSI_RESET + " - Astra Client Set");
    }
    
    
    @Test
    public void listKeyspaces() {
        astraClient.apiRest().keyspaceNames()
                   .forEach(System.out::println);
        System.out.println(ANSI_GREEN + "[OK]" + ANSI_RESET + " - Keyspaces listed");
    }
    
    @Test
    public void createKeyspace() {
        
        astraClient.apiDevops().createKeyspace(databaseId, "keyspace2");
        
        
        System.out.println(ANSI_GREEN + "[OK]" + ANSI_RESET + " - Keyspace created");
    }
    
    
    @Test
    public void createTable() {
        astraClient.apiRest().keyspace("keyspace2").table("users").create(
                CreateTable.builder()
                .addPartitionKey("firstname", "text")
                .addClusteringKey("lastname",  "text", Ordering.ASC)
                .addColumn("email", "text")
                .addColumn("color", "text")
                .ifNotExist(true)
                .build());
        System.out.println(ANSI_GREEN + "[OK]" + ANSI_RESET + " - Table created");
    }
    
    @Test
    public void insertRow() {
        Map<String, Object> record = new HashMap<>();
        record.put("firstname", "Mookie");
        record.put("lastname", "Betts");
        record.put("email", "mookie.betts@gmail.com");
        record.put("color", "blue");
        astraClient.apiRest().keyspace("keyspace2").table("users").upsert(record);
        System.out.println(ANSI_GREEN + "[OK]" + ANSI_RESET + " - Row Inserted");
    }
    
    @Test
    public void readData() {
        astraClient.apiRest().keyspace("keyspace2")
                   .table("users").search(SearchTableQuery.builder().build())
                   .getResults().stream().forEach(System.out::println);
        System.out.println(ANSI_GREEN + "[OK]" + ANSI_RESET + " - List records");
    }
    
    

}
