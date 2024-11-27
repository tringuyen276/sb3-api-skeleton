package com.digiex.utility.config;

import jakarta.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class initiateData {
  @Autowired private DataSource dataSource;

  @PostConstruct
  public void init() {
    try (Connection connection = dataSource.getConnection()) {
      runSqlScript(connection, "schema.sql");
    } catch (Exception e) {
      log.error("Error while executing script: {}", e.getMessage());
    }
  }

  private void runSqlScript(Connection connection, String scriptLocation) throws Exception {
    var schemaFile = new ClassPathResource("schema.sql");
    try (var inputStream = schemaFile.getInputStream()) {
      var br = new BufferedReader(new InputStreamReader(inputStream));
      StringBuilder script = new StringBuilder();
      String line;
      while ((line = br.readLine()) != null) {
        script.append(line).append("\n");
      }

      String sql = script.toString();
      if (!sql.isEmpty()) {
        try (Statement statement = connection.createStatement()) {
          statement.execute(sql);
          log.info("Script executed successfully: {}", schemaFile.getPath());
        }
      }
    }
  }
}
