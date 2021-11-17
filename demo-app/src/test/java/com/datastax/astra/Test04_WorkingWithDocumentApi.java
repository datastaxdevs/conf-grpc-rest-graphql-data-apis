package com.datastax.astra;

import static com.datastax.astra.DemoDataSet.appToken;
import static com.datastax.astra.DemoDataSet.awsEast1;
import static com.datastax.astra.DemoDataSet.clientId;
import static com.datastax.astra.DemoDataSet.clientSecret;
import static com.datastax.astra.DemoDataSet.ksName;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.dstx.astra.sdk.AstraClient;

import io.stargate.sdk.doc.ApiDocument;

public class Test04_WorkingWithDocumentApi {
    
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
    public void createNamespace() {
        astraClient.apiDevops().createNamespace(databaseId, "namespace1");
        System.out.println(ANSI_GREEN + "[OK]" + ANSI_RESET + " - Namespace created");
    }
    
    @Test
    public void listNamespaces() {
        astraClient.apiDocument().namespaceNames()
                   .forEach(System.out::println);
        System.out.println(ANSI_GREEN + "[OK]" + ANSI_RESET + " - Namespace listed");
    }
    
    @Test
    public void createCollection() {
        astraClient.apiDocument().namespace("namespace1").collection("videos").create();
        System.out.println(ANSI_GREEN + "[OK]" + ANSI_RESET + " - Collection created");
    }
    
    @Test
    public void createDocument() {
        Video video = new Video();
        video.setVideoid(UUID.fromString("e466f561-4ea4-4eb7-8dcc-126e0fbfd573"));
        video.setEmail("clunven@sample.com");
        video.setUpload("2020-02-26 15:09:22 +00:00");
        video.setUrl("http://google.fr");
        video.getFrames().add(1);
        video.getFrames().add(2);
        video.getFrames().add(3);
        video.getFrames().add(4);
        video.getTags().add("cassandra");
        video.getTags().add("accelerate");
        video.getTags().add("2020");
        Map<String, Integer> map1 = new HashMap<>();
        map1.put("width", 1);
        map1.put("height", 1);
        video.getFormats().put("mp4", map1);
        video.getFormats().put("ogg", map1);
        
        String docId = astraClient.apiDocument()
                   .namespace("namespace1").collection("videos")
                   .createNewDocument(video);
        
        System.out.println(ANSI_GREEN + "[OK]" + ANSI_RESET + " - Video Inserted id=" + docId);
    }
    
    @Test
    public void retrieveDocuments() {
        astraClient.apiDocument()
                   .namespace("namespace1")
                   .collection("videos")
                   .findAll(Video.class)
                   .getResults()
                   .stream()
                   .map(ApiDocument::getDocument)
                   .map(Video::toString)
                   .forEach(System.out::println);
        System.out.println(ANSI_GREEN + "[OK]" + ANSI_RESET + " - List document");
    }
    
    

}
