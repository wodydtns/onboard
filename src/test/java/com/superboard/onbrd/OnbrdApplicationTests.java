package com.superboard.onbrd;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@SpringBootTest
class OnbrdApplicationTests {

    @Test
    void contextLoads() throws Exception {
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
            while ((line = reader.readLine()) != null) {
                result.append(line);
                result.append("\n");
            }
            process.waitFor();
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(process != null){
                process.destroy();
            }
        }
    }

}
