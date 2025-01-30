package com.cloudcode.docker;

import com.cloudcode.auth.Security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Service
public class DockerService {

    private static final Path DOCKER_COMPOSE_FILE = Path.of("src/main/java/com/cloudcode/docker/docker-compose.yml");
    private final Map<String, String> userContainer = new HashMap<>();

    @Autowired
    private CurrentUser currentUser;

    private String getCurrentUser() {
        return currentUser.getCurrentUser().getId();
    }

    public String startContainer() {
        String username = getCurrentUser();
        String containerName = "container_" + username;
        if (userContainer.containsKey(username)) {
            return "Container already exists for user: " + username;
        }

        try {
            // Write a user-specific docker-compose override
            Path userComposeFile = createUserComposeFile(username, containerName);

            // Run docker-compose up
            String result = runCommand("docker-compose", "-f", userComposeFile.toString(), "up", "-d");
            if (!result.contains("Error")) {
                userContainer.put(username, containerName);
            }
            return result;
        } catch (Exception e) {
            return "Error starting container: " + e.getMessage();
        }
    }

    public String stopContainer() {
        String username = getCurrentUser();
        String containerName = userContainer.get(username);
        if (containerName == null) {
            return "No container found for user: " + username;
        }

        try {
            // Stop and remove the container using docker-compose down
            Path userComposeFile = createUserComposeFile(username, containerName);
            String result = runCommand("docker-compose", "-f", userComposeFile.toString(), "down");
            if (!result.contains("Error")) {
                userContainer.remove(username);
                Files.deleteIfExists(userComposeFile); // Cleanup override file
            }
            return result;
        } catch (Exception e) {
            return "Error stopping container: " + e.getMessage();
        }
    }

    public String restartContainer() {
        String username = getCurrentUser();
        String containerName = userContainer.get(username);
        if (containerName == null) {
            return "No container found for user: " + username;
        }

        try {
            Path userComposeFile = createUserComposeFile(username, containerName);
            return runCommand("docker-compose", "-f", userComposeFile.toString(), "restart");
        } catch (Exception e) {
            return "Error restarting container: " + e.getMessage();
        }
    }

    public String checkStatus() {
        String username = getCurrentUser();
        String containerName = userContainer.get(username);
        if (containerName == null) {
            return "No container found for user: " + username;
        }

        try {
            Path userComposeFile = createUserComposeFile(username, containerName);
            return runCommand("docker-compose", "-f", userComposeFile.toString(), "ps");
        } catch (Exception e) {
            return "Error checking status: " + e.getMessage();
        }
    }

    private Path createUserComposeFile(String username, String containerName) throws Exception {
        // Check if the base docker-compose.yml file exists
        if (!Files.exists(DOCKER_COMPOSE_FILE)) {
            throw new Exception("Base docker-compose.yml file not found at: " + DOCKER_COMPOSE_FILE);
        }

        // Create a user-specific override file
        Path userComposePath = Path.of("docker-compose-" + username + ".yml");
        String baseComposeContent = Files.readString(DOCKER_COMPOSE_FILE);
        String userComposeContent = baseComposeContent.replace("${CONTAINER_NAME}", containerName);

        try (FileWriter writer = new FileWriter(userComposePath.toFile())) {
            writer.write(userComposeContent);
        }

        return userComposePath;
    }

    private String runCommand(String... command) {
        System.out.println("Executing command: " + String.join(" ", command));
        StringBuilder output = new StringBuilder();

        try {
            Process process = new ProcessBuilder(command)
                    .redirectErrorStream(true) // Combine stderr and stdout
                    .start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                output.append("Error: Command exited with code ").append(exitCode);
            }
        } catch (Exception e) {
            output.append("Error: ").append(e.getMessage());
        }

        return output.toString().trim();
    }
}
