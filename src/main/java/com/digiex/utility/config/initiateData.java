package com.digiex.utility.config;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
@Service
public class initiateData {

    @PostConstruct
    public void init(){
        String url = "jdbc:postgresql://localhost:5432/postgres1";
        String user = "postgres";
        String password = "password";
        String filePath = "src/main/resources/schema.sql";

        try(Connection connection=DriverManager.getConnection(url,user,password)){
            runSqlScript(connection,filePath);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void runSqlScript(Connection connection, String scriptLocation) throws IOException {
        // Đọc file SQL
        try (BufferedReader br = new BufferedReader(new FileReader(scriptLocation))) {
            StringBuilder script = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                script.append(line).append("\n");
            }

            // Thực thi câu lệnh SQL
            String sql = script.toString();
            if (!sql.isEmpty()) {
                try (Statement statement = connection.createStatement()) {
                    statement.execute(sql);
                    System.out.println("Script executed successfully: " + scriptLocation);
                }
            }
        } catch (IOException e) {
            System.out.println("Error executing SQL script: " + scriptLocation);
            throw e;
        } catch (Exception e) {
            System.out.println("Error while executing SQL statements.");
            e.printStackTrace();
        }
    }
}
