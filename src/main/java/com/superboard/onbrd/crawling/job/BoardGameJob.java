package com.superboard.onbrd.crawling.job;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.superboard.onbrd.boardgame.entity.Boardgame;
import com.superboard.onbrd.boardgame.repository.BoardgameRepository;
import com.superboard.onbrd.crawling.entity.CrawlingData;
import com.superboard.onbrd.crawling.entity.CrawlingTranslationDto;
import com.superboard.onbrd.crawling.repository.CrawlingRepository;
import com.superboard.onbrd.crawling.repository.CustomCrawlingRepository;
import com.superboard.onbrd.tag.entity.BoardgameTag;
import com.superboard.onbrd.tag.entity.Tag;
import com.superboard.onbrd.tag.repository.BoardgameTagRepository;
import com.superboard.onbrd.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BoardGameJob {


    private final CrawlingRepository crawlingRepository;

    private final CustomCrawlingRepository customCrawlingRepository;

    private final BoardgameRepository boardgameRepository;

    private final TagRepository tagRepository;

    private final BoardgameTagRepository boardgameTagRepository;


    //@Scheduled(cron = "* * * * 3 *")
    @Transactional
    public void createCrawlingBoardgameLog() {
        // Start the process
        try {
            ProcessBuilder pb = new ProcessBuilder("python", "E:\\crawling\\getBoardgame.py");

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
     * FIXME
     *  1. category size가 20

    * */

    @Scheduled(cron = "0/10 * * * * *")
    @Transactional
    public void insertCrawlingData(){
        // 파일 경로 수정 필요
        ProcessBuilder pb = new ProcessBuilder("python", "E:\\crawling\\getBoardgame2.py");

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
            Gson gson = new Gson();
            //Map<String, Object> result_list = gson.fromJson(json, Map.class);
            List<LinkedTreeMap> resultList = gson.fromJson(json, List.class);
            List<Boardgame> boardgameList = new ArrayList<>();
            HashSet<Long> tagHashSet = new HashSet();
            List<Long> categoriesTagList =  new ArrayList<>();
            for (LinkedTreeMap result : resultList) {
                CrawlingData crawlingData = new CrawlingData();
                String boardgameName = (String) result.get("title_text");
                String description = (String) result.get("description");
                String imageUrl = (String) result.get("image_url");
                Boardgame boardgame = new Boardgame(boardgameName,description,imageUrl);
                String categories = (String) result.get("categories");
                String age = (String) result.get("age");
                String playing_time = (String) result.get("playing_time");
                String best_player = (String) result.get("best_player");
                tagHashSet.add(Long.parseLong(categories));
                tagHashSet.add(Long.parseLong(age));
                tagHashSet.add(Long.parseLong(playing_time));
                tagHashSet.add(Long.parseLong(best_player));
                categoriesTagList.add(Long.parseLong(categories));
                categoriesTagList.add(Long.parseLong(age));
                categoriesTagList.add(Long.parseLong(playing_time));
                categoriesTagList.add(Long.parseLong(best_player));
                boardgameList.add(boardgame);
            }
            List<Boardgame> savedBoardgames = boardgameRepository.saveAll(boardgameList);

            // tag 저장 로직
            
            for (Boardgame boardgame: savedBoardgames) {
                for(Long category : categoriesTagList){
                    Tag tag = tagRepository.findById(category).orElseThrow();
                    BoardgameTag boardgameTag = BoardgameTag.builder().boardgame(boardgame).tag(tag)
                            .build();
                    boardgameTagRepository.saveAndFlush(boardgameTag);
                }

            }
            customCrawlingRepository.selectOauthIdForPushMessageByFavorite(tagHashSet);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    * FIXME
    *  1. python 에서 크롤링 한 데이터에서 첫번째 번역한 translation 데이터가 없는 문제가 있음
    * */
    //@Scheduled(cron = "0/10 * * * * *")
    public void translationBoardgameDesc(){

        List<CrawlingTranslationDto> BoardgameDescriptionList = customCrawlingRepository.selectAllBoardgameDescription();
        Gson gson = new Gson();
        String descriptionJson = gson.toJson(BoardgameDescriptionList);
        byte[] encodedBytes = Base64.getEncoder().encode(descriptionJson.getBytes(StandardCharsets.UTF_8));
        String base64Encoded = new String(encodedBytes, StandardCharsets.UTF_8);

        // 파일 경로 수정 필요
        ProcessBuilder pb = new ProcessBuilder("python", "D:\\papago1.py", base64Encoded);

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
            String returnJson = builder.toString();
            System.out.println(returnJson);

            //Map<String, Object> result_list = gson.fromJson(json, Map.class);
            List<LinkedTreeMap> resultList = gson.fromJson(returnJson, List.class);
            List<CrawlingTranslationDto> crawlingTranslationDtoList = new ArrayList<>();
            for (LinkedTreeMap result : resultList) {

                CrawlingTranslationDto crawlingTranslationDto = new CrawlingTranslationDto();
                Double id = (Double) result.get("id");
                crawlingTranslationDto.setId(id.longValue());
                crawlingTranslationDto.setDescription((String) result.get("description"));

                crawlingTranslationDtoList.add(crawlingTranslationDto);
            }
            customCrawlingRepository.updateAllCrawlingTranslationData(crawlingTranslationDtoList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
