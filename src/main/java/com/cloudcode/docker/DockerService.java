package com.cloudcode.docker;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Arrays;

@Service
public class DockerService {

    public String startContainer() {
        return runCommand("docker-compose up -d");
    }

    public String stopContainer() {
        return runCommand("docker-compose down");
    }

    public String restartContainer() {
        return runCommand("docker-compose restart");
    }

    public String checkStatus() {
        return runCommand("docker-compose ps");
    }

    private String runCommand(String... command) {
        System.out.println(command[0]);
        StringBuilder output = new StringBuilder();
        try {
            Process process = new ProcessBuilder(command)
//                    .directory(new File("C:/Users/asabi/OneDrive/Documents/cloudcode/src/main/docker"))
                    .start();
            System.out.println(process.toString());
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            process.waitFor();
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            output.append("Error: ").append(e.getMessage());
        }
        return output.toString();
    }

}
