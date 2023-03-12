package com.superboard.onbrd.global.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class BoardGameJob {

    @Scheduled(cron = "0/5 * * * * *")
    public void crawlingBoardgame() {
        // Create process builder for the python command
        ProcessBuilder pb = new ProcessBuilder("python", "D:\\hello.py");

        // Start the process
        Process process = null;
        try {
            process = pb.start();
            // Read the output from the process
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder result = new StringBuilder();
            List<String> test = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                result.append(line);
                result.append("\n");
            }
            test.add(result.toString());
            System.out.println(result);
            System.out.println(test);
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
