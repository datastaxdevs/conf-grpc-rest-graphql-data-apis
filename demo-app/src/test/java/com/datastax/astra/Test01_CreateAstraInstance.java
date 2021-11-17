package com.datastax.astra;

import static com.datastax.astra.DemoDataSet.appToken;
import static com.datastax.astra.DemoDataSet.aws;
import static com.datastax.astra.DemoDataSet.awsEast1;
import static com.datastax.astra.DemoDataSet.clientId;
import static com.datastax.astra.DemoDataSet.clientSecret;
import static com.datastax.astra.DemoDataSet.dbName;
import static com.datastax.astra.DemoDataSet.ksName;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.dstx.astra.sdk.AstraClient;
import com.dstx.astra.sdk.devops.DatabaseStatusType;
import com.dstx.astra.sdk.devops.DatabaseTierType;
import com.dstx.astra.sdk.devops.req.DatabaseCreationRequest;
/**
 * Creating 
 * @author Cedrick LUNVEN (@clunven)
 */
public class Test01_CreateAstraInstance {
   
    public static final String ANSI_RESET           = "\u001B[0m";
    public static final String ANSI_GREEN           = "\u001B[32m";
    
    @Test
    public void createInstance() throws InterruptedException {
        /*
        String databaseId = AstraClient.builder()
                    .appToken(appToken)
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .build().apiDevops()
                    .createDatabase(DatabaseCreationRequest.builder()
                    .name(dbName)
                    .tier(DatabaseTierType.serverless)
                    .cloudProvider(aws).cloudRegion(awsEast1)
                    .keyspace(ksName)
                    .build());
        */
        
        AstraClient astraClient = AstraClient.builder()
                .appToken(appToken)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build();
        System.out.println(ANSI_GREEN + "[OK]" + ANSI_RESET + " - Astra Client Set");
        
        String id = astraClient.apiDevops().createDatabase(DatabaseCreationRequest.builder()
                .name(dbName)
                .tier(DatabaseTierType.serverless)
                .cloudProvider(aws).cloudRegion(awsEast1)
                .keyspace(ksName)
                .build());
        
        System.out.println(ANSI_GREEN + "[OK]" + ANSI_RESET + " - Database [" + dbName + "] id=" + id);
        System.out.print(ANSI_GREEN + "[OK]" + ANSI_RESET + " - Initializing ");
        while(!DatabaseStatusType.ACTIVE.equals(astraClient.apiDevops().findDatabaseById(id).get().getStatus())) {
            System.out.print(ANSI_GREEN + "\u25a0" +ANSI_RESET); 
            Thread.sleep(5000);
        }
        Assert.assertEquals(DatabaseStatusType.ACTIVE, astraClient.apiDevops().findDatabaseById(id).get().getStatus());
        System.out.println(ANSI_GREEN + "\n[OK]" + ANSI_RESET + " - DB is active");
    }
    
    
    
}
