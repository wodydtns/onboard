package com.superboard.onbrd.crawling.job;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.superboard.onbrd.crawling.entity.CrawlingData;
import com.superboard.onbrd.crawling.repository.CrawlingRepository;
import com.superboard.onbrd.crawling.repository.CustomCrawlingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BoardGameJob {


    private final CrawlingRepository crawlingRepository;

    private final CustomCrawlingRepository customCrawlingRepository;


    //@Scheduled(cron = "* * * * 3 *")
    @Transactional
    public void createCrawlingBoardgameLog() {
        // Start the process
        try {
            ProcessBuilder pb = new ProcessBuilder("python", "D:\\getBoardgame.py");

            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            String json = builder.toString();
            Gson gson = new Gson();
            //Map<String, Object> result_list = gson.fromJson(json, Map.class);
            List<LinkedTreeMap> resultList = gson.fromJson(json, List.class);
            List<CrawlingData> crawlingDataList = new ArrayList<>();
            List<CrawlingData> insertDataList = new ArrayList<>();
            List<String> compareExistList = new ArrayList<>();
            for (LinkedTreeMap result : resultList) {
                compareExistList.add((String) result.get("boardGameName"));

                CrawlingData crawlingData = new CrawlingData();
                crawlingData.setBoardgameName((String) result.get("boardGameName"));
                crawlingData.setPageNum((Double) result.get("pageNum") );
                crawlingData.setGameNumber((Double) result.get("gameNumber"));
                crawlingData.setDoneYn("N");
                crawlingDataList.add(crawlingData);
            }
            List<String> updateList = customCrawlingRepository.ifExistUpdateList(compareExistList);
            for (CrawlingData crawlingData : crawlingDataList){
                String crawlingDataName = crawlingData.getBoardgameName();
                if(!updateList.contains(crawlingDataName)) {
                    insertDataList.add(crawlingData);
                }
            }
            if(!insertDataList.isEmpty()){
                crawlingRepository.saveAll(insertDataList);
            }else{
                customCrawlingRepository.updateDoneY(updateList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    * TODO
     *   1. category 필터링
     *   2. boardgame insert 문 생성
     *   3. 이미지 업로드 + insert 문에 이미지 파일 이름 추가 - 완료
     *   4. papago로 번역 생성
    * */
    @Scheduled(cron = "0/10 * * * * *")
    public void insertCrawlingData(){
        // 파일 경로 수정 필요
        ProcessBuilder pb = new ProcessBuilder("python", "D:\\hello2.py");

        pb.redirectErrorStream(true);
        Process process = null;
        try {
            process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            String json = builder.toString();
            System.out.println(json);
            Gson gson = new Gson();
            //Map<String, Object> result_list = gson.fromJson(json, Map.class);
            List<LinkedTreeMap> resultList = gson.fromJson(json, List.class);
            System.out.println(resultList.size());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Scheduled(cron = "0/10 * * * * *")
    public void translationBoardgameDesc(){
        // 파일 경로 수정 필요
        ProcessBuilder pb = new ProcessBuilder("python", "D:\\papago.py");

        pb.redirectErrorStream(true);
        Process process = null;
        try {
            process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            String json = builder.toString();
            System.out.println(json);
            Gson gson = new Gson();
            //Map<String, Object> result_list = gson.fromJson(json, Map.class);
            List<LinkedTreeMap> resultList = gson.fromJson(json, List.class);
            System.out.println(resultList.size());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
