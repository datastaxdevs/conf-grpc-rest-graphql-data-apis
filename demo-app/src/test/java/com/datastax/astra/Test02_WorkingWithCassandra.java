package com.datastax.astra;

import static com.datastax.astra.DemoDataSet.appToken;
import static com.datastax.astra.DemoDataSet.awsEast1;
import static com.datastax.astra.DemoDataSet.clientId;
import static com.datastax.astra.DemoDataSet.clientSecret;
import static com.datastax.astra.DemoDataSet.ksName;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.dstx.astra.sdk.AstraClient;

public class Test02_WorkingWithCassandra {
    
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
    public void createEntitiies() {
          
        astraClient.cqlSession().execute(SchemaBuilder
                .createType("video_format").ifNotExists()
                .withField("width", DataTypes.INT)
                .withField("height", DataTypes.INT)
                .build());
        System.out.println(ANSI_GREEN + "[OK]" + ANSI_RESET + " - UDT Created");
        
        astraClient.cqlSession().execute(SchemaBuilder
                .createTable("videos")
                .ifNotExists()
                .withPartitionKey("videoid", DataTypes.UUID)
                .withColumn("title", DataTypes.TEXT)
                .withColumn("upload", DataTypes.TIMESTAMP)
                .withColumn("email", DataTypes.TEXT)
                .withColumn("url", DataTypes.TEXT)
                .withColumn("tags", DataTypes.setOf(DataTypes.TEXT))
                .withColumn("frames", DataTypes.listOf(DataTypes.INT))
                .withColumn("formats", DataTypes.mapOf(DataTypes.TEXT, 
                        SchemaBuilder.udt("video_format", true))).build());
        System.out.println(ANSI_GREEN + "[OK]" + ANSI_RESET + " - Table Created");
             
    }
    
    @Test
    public void useTheDataModel() {
        astraClient.cqlSession().execute(""
                + "INSERT INTO videos(videoid, email, title, upload, url, tags, frames, formats)\n"
                + "VALUES(uuid(), 'clu@sample.com', 'sample video', \n"
                + "     toTimeStamp(now()), 'http://google.fr',\n"
                + "     { 'cassandra','accelerate','2020'},\n"
                + "     [ 1, 2, 3, 4], \n"
                + "     { 'mp4':{width:1,height:1},'ogg':{width:1,height:1}});");
        
        astraClient.cqlSession().execute(""
                + "INSERT INTO videos(videoid, email, title, upload, url)\n"
                + "VALUES(uuid(), 'clu@sample.com', 'video2', toTimeStamp(now()), 'http://google.fr');");
        
        System.out.println(ANSI_GREEN + "[OK]" + ANSI_RESET + " - Data Inserted");
       
        astraClient.cqlSession().execute(""
                + "INSERT INTO videos JSON '{\n"
                + "   \"videoid\":\"e466f561-4ea4-4eb7-8dcc-126e0fbfd573\",\n"
                + "     \"email\":\"clunven@sample.com\",\n"
                + "     \"title\":\"A Second videos\",\n"
                + "     \"upload\":\"2020-02-26 15:09:22 +00:00\",\n"
                + "     \"url\": \"http://google.fr\",\n"
                + "     \"frames\": [1,2,3,4],\n"
                + "     \"tags\":   [ \"cassandra\",\"accelerate\", \"2020\"],\n"
                + "     \"formats\": { \n"
                + "        \"mp4\": {\"width\":1,\"height\":1},\n"
                + "        \"ogg\": {\"width\":1,\"height\":1}\n"
                + "     }\n"
                + "}';");
        
        UUID videoid4 = UUID.randomUUID();
        astraClient.cqlSession().execute(SimpleStatement.builder("INSERT INTO videos JSON ? ")
                .addPositionalValue("{"
                    + "\"videoid\":\""+ videoid4.toString() + "\"," 
                    + "\"email\":\"clu@sample.com\"," 
                    + "\"title\":\"sample video\"," 
                    + "\"upload\":\"2020-02-26 15:09:22 +00:00\"," 
                    + "\"url\":\"http://google.fr\","
                    + "\"frames\": [1,2,3,4],"
                    + "\"tags\": [\"cassandra\",\"accelerate\", \"2020\"],"
                    + "\"formats\": {" 
                    + "   \"mp4\":{\"width\":1,\"height\":1},"
                    + "   \"ogg\":{\"width\":1,\"height\":1}"
                    + "}}")
                .build());
        
        System.out.println(ANSI_GREEN + "[OK]" + ANSI_RESET + " - More Data Inserted as JSON");
        
        astraClient.cqlSession().execute("select * from videos;")
                   .all().stream()
                   .forEach(row -> { System.out.println(ANSI_GREEN + "+ [ROW]" + ANSI_RESET + " - id=" + row.getUuid("videoid"));});
        
        System.out.println(ANSI_GREEN + "[OK]" + ANSI_RESET + " - Data Retrieved");
        
    }

}
