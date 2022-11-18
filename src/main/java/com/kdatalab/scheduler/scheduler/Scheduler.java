package com.kdatalab.scheduler.scheduler;

import com.kdatalab.scheduler.job.service.SchedulerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Slf4j
@Component
@EnableAsync
public class Scheduler {

    private SchedulerService schedulerService;

    public Scheduler(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    // 업체 설정값
    @Value("${key.value}")
    private String key;

    @Value("${key.comp}")
    private String comp;

    @Value("${key.id}")
    private String id;

    /**
     * 해당 메서드 로직이 끝나는 시간 기준, milliseconds 간격으로 실행
     * 이전 작업이 완료될 때까지 대기
     */
    // @Scheduled(fixedDelay = 180000)
    // (cron = "초 분 시 일 월 요일 연도")
    // * -> 매번, 분 : 매분, 시 : 매시
    // ? -> 설정값 없음(일/요일에만 사용), 일자 : 날짜 미지정, 요일 : 요일미지정
    // @Scheduled(cron = "0 0 23 * * ? ") // 매일 밤 11시
    // @Scheduled(cron = "0 0/1 * * * ?")
    @Scheduled(cron = "0 0/30 * * * * ") // 30분마다 호출
    public void start() throws Exception {
        log.info("========= 배치 시작 =========");

        Map<String, Object> paramsMap = new HashMap<String, Object>();

        // 사용자 기본 정보 - company/ip/id/datetime
        paramsMap.put("company", comp);
        paramsMap.put("id", id);
        //IP주소
        String ipAddr = new Scanner(new URL("https://api.ipify.org").openStream(), "UTF-8").next();
        System.out.println("아이피 주소 : " + ipAddr);
        paramsMap.put("ip", ipAddr);

        // 로그인 시간
        // 1. 오전 접속
        int minHours = 8;
        int maxHours = 10;
        int randomHours = (int) (Math.random() * (maxHours - minHours) + minHours);
        paramsMap.put("randomHours", randomHours);
        if (randomHours == 8) {
            paramsMap.put("Mmin", "30");
            paramsMap.put("Mmax", "59");
            schedulerService.insertLog(paramsMap);
        } else if (randomHours == 9) {
            paramsMap.put("Mmin", '0');
            paramsMap.put("Mmax", "30");
            schedulerService.insertLog(paramsMap);
        }

        // 2. 점심 접속(랜덤)
        Random random = new Random();
        Boolean isAddLog = random.nextBoolean();
        paramsMap.put("Hmin", "12");
        paramsMap.put("Hmax", "15");
        log.info("점심추가접속여부 >>> {}", isAddLog);
        if (isAddLog.equals(true)) schedulerService.insertAddLog(paramsMap);

        // 3. 저녁 접속(30%확률)
        int randomNum = random.nextInt(100);
        System.out.println("저녁접속 확률 : " + randomNum);
        if (randomNum <= 30) isAddLog = true;
        paramsMap.put("Hmin", "18");
        paramsMap.put("Hmax", "19");
        log.info("저녁추가접속여부 >>> {}", isAddLog);
        if (isAddLog.equals(true)) schedulerService.insertAddLog(paramsMap);

        log.info("========= 배치 종료 =========");
        JSONObject jsonObject = getLogData();

        HttpConnection(jsonObject);


    }

    public JSONObject getLogData() throws Exception {

        // 로그인 데이터 조회
        List<Map<String, Object>> logDataList = schedulerService.getLogDataList();

        JSONArray jArray = new JSONArray();
        JSONObject obj = new JSONObject();

        //결과 리스트 만큼 loop 를 돌면서 json 에 값을 put 해주고, 그 json 값을 다시 Json Array에 put해준다.
        for (int i = 0; i < logDataList.size(); i++) {
            JSONObject data= new JSONObject();
            data.put("crtfcKey",key);
            data.put("logDt", logDataList.get(i).get("datetime"));
            data.put("useSe","접속");
            data.put("sysUser", "admin");
            data.put("conectIp",logDataList.get(i).get("ip"));
            data.put("dataUsgqty", "0");
            jArray.put(i,data);
        }

        obj.put("logData", jArray);
        System.out.println("JSON 데이터 : " + obj);

        return obj;
    }

    public void HttpConnection(JSONObject jsonObject) throws Exception {

        System.out.println("HttpConnection 호출 !");
        System.out.println("Http 전달받은 jsonObject -> " + jsonObject);

        // JSON 데이터 HTTP POST 전송하기
        String host_url = "https://webhook.site/e96d9fc4-68e2-4c83-9075-4f3ee7c97e53";

        // JSON 데이터를 받을 URL 객체 생성
        URL url = new URL(host_url);

        // HttpURLConnection 객체를 생성해 openConnection 메서드로 url 연결
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // 전송방식
        conn.setRequestMethod("POST");

        // application/json 형식으로 전송, Request body를 JSON으로 던져줌.
        conn.setRequestProperty("Content-Type","application/json");

        // Response data를 JSON으로 받도록 설정
        conn.setRequestProperty("Accept","application/json");

        // POST 방식으로 스트링을 통한 JSON 전송

        // OutputStream으로 POST 데이터를 넘겨주도록 설정
        conn.setDoOutput(true);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        bw.write(jsonObject.toString()); // 버퍼에 있는 값을 출력
        bw.flush(); // 남아있는 데이터를 모두 출력
        bw.close(); // 출력 스트림 닫기


        // 서버에서 보낸 응답 데이터 수신받기
        // InputStream으로 서버로 부터 응답을 받도록 설정
        conn.setDoInput(true);
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String returnMsg = in.readLine();
        System.out.println("응답메세지 : " + returnMsg);

        // HTTP 응답 코드 수신
        int responseCode = conn.getResponseCode();
        if(responseCode == 400) {
            System.out.println("400 : 명령을 실행 오류");
        }else if(responseCode == 500) {
            System.out.println("500 : 서버에러");
        }else {
            System.out.println(responseCode + " : 응답코드");
        }

    }

}

