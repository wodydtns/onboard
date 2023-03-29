package com.superboard.onbrd.crawling.job;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.superboard.onbrd.boardgame.entity.Boardgame;
import com.superboard.onbrd.boardgame.repository.BoardgameRepository;
import com.superboard.onbrd.crawling.entity.CrawlingBoardgameTagDto;
import com.superboard.onbrd.crawling.entity.CrawlingData;
import com.superboard.onbrd.crawling.entity.CrawlingTranslationDto;
import com.superboard.onbrd.crawling.repository.CrawlingRepository;
import com.superboard.onbrd.crawling.repository.CustomCrawlingRepository;
import com.superboard.onbrd.tag.entity.BoardgameTag;
import com.superboard.onbrd.tag.repository.BoardgameTagRepository;
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
import java.util.List;

@Component
@RequiredArgsConstructor
public class BoardGameJob {


    private final CrawlingRepository crawlingRepository;

    private final CustomCrawlingRepository customCrawlingRepository;

    private final BoardgameRepository boardgameRepository;

    private final BoardgameTagRepository boardgameTagRepository;


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
     **  1. category 필터링
     *   2. 보드게임 좋아요 추가한 사용자에게 push message 보내기
     *   3. boardgame tag에 저장 필요

    * */

    //"title_text": "Brass: Birmingham", "image_url": "Brass: Birmingham.png", "best_player": "— Best: 3–4",
    // "playing_time": "1시간", "age": "14세이하", "description": "Brass: Birmingham is an economic strategy game sequel to
    // Martin Wallace' 2007 masterpiece, Brass. Brass: Birmingham tells the story of competing entrepreneurs in Birmingham during the
    // industrial revolution, between the years of 1770-1870.\nAs in its predecessor, you must develop, build, and establish your
    // industries and network, in an effort to exploit low or high market demands.\nEach round, players take turns according to the turn order track,
    // receiving two actions to perform any of the following actions (found in the original game):\n1) Build - Pay required resources and place an industry tile.\n2) N
    // etwork - Add a rail / canal link, expanding your network.\n3) Develop - Increase the VP value of an industry.\n4) Sell - Sell your cotton, manufactured goods and pottery.\n5) Loan -
    // Take a £30 loan and reduce your income.\nalso features a new sixth action:"
    // , "categories": "Economic"}
    @Scheduled(cron = "0/10 * * * * *")
    @Transactional
    public void insertCrawlingData(){
        // 파일 경로 수정 필요
        ProcessBuilder pb = new ProcessBuilder("python", "D:\\getBoardgame2.py");

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
            List<String> categoriesTagList =  new ArrayList<>();
            //System.out.println(resultList);
            for (LinkedTreeMap result : resultList) {
                CrawlingData crawlingData = new CrawlingData();
                String boardgameName = (String) result.get("title_text");
                String description = (String) result.get("description");
                String imageUrl = (String) result.get("image_url");
                Boardgame boardgame = new Boardgame(boardgameName,description,imageUrl);

                boardgameList.add(boardgame);

                categoriesTagList.add((String) result.get("categories"));
            }

            customCrawlingRepository.selectOauthIdForPushMessageByFavorite(categoriesTagList);
            List<Boardgame> savedBoardgames = boardgameRepository.saveAll(boardgameList);

            List<CrawlingBoardgameTagDto> boardgameTagList = new ArrayList<>();
            for (Boardgame boardgame: savedBoardgames) {
                CrawlingBoardgameTagDto crawlingBoardgameTagDto = new CrawlingBoardgameTagDto();
                crawlingBoardgameTagDto.setBoardgameId(boardgame.getId());

                boardgameTagList.add(crawlingBoardgameTagDto);
            }
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
