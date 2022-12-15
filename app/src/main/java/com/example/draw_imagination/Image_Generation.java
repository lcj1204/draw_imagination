package com.example.draw_imagination;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Image_Generation extends AsyncTask<String, Void, String> {

    // you may separate this or combined to caller class.
    public interface AsyncResponse {
        void processFinish(String output);
    }

    public AsyncResponse delegate = null;

    public Image_Generation(AsyncResponse delegate){
        this.delegate = delegate;
    }

    @Override
    protected String doInBackground(String... strings) {
        String clientId = "cZ2oEfAYpXIPWbMKKWdF";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "vIvBx6_alq";//애플리케이션 클라이언트 시크릿값";

        String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
        String text;
        try {
            text = URLEncoder.encode(strings[0], "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("인코딩 실패", e);
        }

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);

        String responseBody = post(apiURL, requestHeaders, text);

        //JSON parsing
        JSONObject jObject = null;
        try {
            jObject = new JSONObject(responseBody);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject post1Object = null;
        try {
            post1Object = jObject.getJSONObject("message");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject result = null;
        try {
            result = post1Object.getJSONObject("result");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String translatedText = "";
        try {
            translatedText = result.getString("translatedText");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(translatedText);

        URL url = null;
        try {
            url = new URL("https://api.openai.com/v1/images/generations");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection httpConn = null;
        try {
            httpConn = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            httpConn.setRequestMethod("POST");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }

        httpConn.setRequestProperty("Content-Type", "application/json");
//        httpConn.setRequestProperty("Authorization", "Bearer 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");
        httpConn.setRequestProperty("Authorization", "Bearer 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");

        httpConn.setDoOutput(true);
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new OutputStreamWriter(httpConn.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        writer.println("{");
        writer.println("\"prompt\": \"" + translatedText + "\",");
        writer.println("\"n\": 1,");
        writer.println("\"size\": \"1024x1024\"");
        writer.println("}");
        //writer.write("{\n    \"prompt\": \""+ translatedText +"\",\n    \"n\": 1,\n    \"size\": \"1024x1024\"\n  }");
        writer.flush();
        writer.close();

        try {
            httpConn.getOutputStream().close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream responseStream = null;
        try {
            responseStream = httpConn.getResponseCode() / 100 == 2
                    ? httpConn.getInputStream()
                    : httpConn.getErrorStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        String response = s.hasNext() ? s.next() : "";
        //System.out.println(response);

        JSONObject re = null;
        try {
            re = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray data = null;
        try {
            data = re.getJSONArray("data");
            System.out.println(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String result_url = "";
        try {
            JSONObject ss = data.getJSONObject(0);
            result_url = ss.getString("url");
            //result_url = data.getJSONObject(0).getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //System.out.println(result_url);

        return result_url;
    }


    private String post(String apiUrl, Map<String, String> requestHeaders, String text){
        HttpURLConnection con = connect(apiUrl);
        String postParams = "source=ko&target=en&text=" + text; //원본언어: 한국어 (ko) -> 목적언어: 영어 (en)
        try {
            con.setRequestMethod("POST");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            con.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postParams.getBytes());
                wr.flush();
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 응답
                return readBody(con.getInputStream());
            } else {  // 에러 응답
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }

    @Override
    protected void onPreExecute() {
        // 스레드가 시작하기 전에 수행할 작업(메인 스레드)
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Void... Params) {
        // 스레드가 수행되는 사이에 수행할 중간 작업(메인 스레드)
        // doInBackground()에서
        //publishProgress() 메소드를 호출하여 중간 작업 수행가능
    }

    @Override
    protected void onPostExecute(String s) {
        // 스레드 작업이 모두 끝난 후에 수행할 작업(메인 스레드)
        delegate.processFinish(s);
        //myMethod(s);
        super.onPostExecute(s);
    }

    private String myMethod(String myValue) {
        //handle value
        return myValue;
    }
    @Override
    protected void onCancelled(){
        // cancel() 메소드가 호출되었을때 즉,
        // 강제로 취소하라는 명령이 호출되었을 때
        // 스레드가 취소되기 전에 수행할 작업(메인 스레드)
        super.onCancelled();
    }
}
