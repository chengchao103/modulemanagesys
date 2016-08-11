package com.chinamobile.iot.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.chinamobile.iot.model.onenet.request.BaseRequest;
import com.chinamobile.iot.model.onenet.request.BaseRequest.HTTPMETHOD;
import com.chinamobile.iot.model.onenet.request.UploadSoftwareReq;
import com.chinamobile.iot.model.response.CommonMulitiResp;
import com.chinamobile.iot.model.response.CommonSingleResp;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.net.ssl.HttpsURLConnection;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by dds on 2016/3/10.
 */
@Component
public class HttpRequestor {
    private String charset = "utf-8";
    private Integer connectTimeout = null;
    private Integer readTimeout = null;
    private String proxyHost = null;
    private Integer proxyPort = null;
    private Map<String, Object> parameter = new HashMap<String, Object>();

    /**
     * 返回单条数据
     *
     * @param request
     * @param classType
     * @param <T>
     * @return
     */
    public <T> CommonSingleResp<T> executeSingle(BaseRequest request, TypeReference<CommonSingleResp<T>> classType) {

        CommonSingleResp<T> result = null;
        try {
            String rsp = execute(request);
            result = JSON.parseObject(rsp, classType);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 返回多条数据
     *
     * @param request
     * @param <T>
     * @return
     */
    public <T> CommonMulitiResp<T> executeMuliti(BaseRequest request, TypeReference<CommonMulitiResp<T>> classType) {

        CommonMulitiResp rsp = null;
        try {
            String rlt = execute(request);
            rsp = JSON.parseObject(rlt, classType);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsp;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public String execute(BaseRequest request) {
        long start = System.currentTimeMillis();
        //清除参数
        removeAllParameter();
        //url中需要替换的参数
        if (request.getParam() != null) {
            parameter.putAll(request.getParam());
        }
        //获取http方法
        HTTPMETHOD method = request.getMethod();
        //获取url
        String url = request.getURL();
        //格式化url
        url = URIUtils.fmtURI(url, parameter);
        String response = null;
        HttpURLConnection conn = null;
        OutputStream out = null;
        InputStream in = null;
        try {
            //鉴权参数无论什么方法都需要放到url上
            if (BaseRequest.class.isAssignableFrom(request.getClass())) {
                Map<String, String> auth = new HashMap<>();
                auth.put("token", BaseRequest.getToken());
                auth.put("product_id", BaseRequest.getProduct_id());
                url = URIUtils.buildQuery(url, auth.entrySet());
            }
            //如果是get和delete方法,需要把参数拼到url上
            if (HTTPMETHOD.GET == method || HTTPMETHOD.DELETE == method) {
                url = URIUtils.buildQuery(url, beanToMap(request).entrySet());
            }
            conn = getConnection(new URL(url), method.name());
//            conn.setConnectTimeout(connectTimeout);
            conn.setUseCaches(false);
//            conn.setReadTimeout(readTimeout);
            //如果是put和post方法,需要把参数写到body里
            if (HTTPMETHOD.POST == method || HTTPMETHOD.PUT == method) {
                out = conn.getOutputStream();
                byte[] content = JSON.toJSONBytes(request);
                out.write(content);
            }

            in = conn.getInputStream();
            response = getStreamAsString(in, charset);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return response;
    }

    private void removeAllParameter() {
        parameter.clear();
        parameter.put("API_ADDRESS", MainConfig.getStringProperty("onenet.api_address"));
    }


    private static HttpURLConnection getConnection(URL url, String method) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        if (conn instanceof HttpsURLConnection) {
            HttpsURLConnection connHttps = (HttpsURLConnection) conn;

            conn = connHttps;
        }

        conn.setRequestMethod(method);
        conn.setDoInput(true);// 允许输入
        conn.setDoOutput(true);// 允许输出
        conn.setUseCaches(false); // 不允许使用缓存
        conn.setRequestProperty("Host", url.getHost());
//        conn.setRequestProperty("Accept", "text/json;charset=utf-8");
        conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
        return conn;
    }

    private String buildUrl(String url, Map parameterMap) {
        StringBuilder parameterSb = new StringBuilder();
        parameterSb.append(url);
        parameterSb.append("?");
        if (parameterMap != null) {
            Iterator iterator = parameterMap.keySet().iterator();
            String key = null;
            String value = null;
            while (iterator.hasNext()) {
                key = (String) iterator.next();
                if (parameterMap.get(key) != null) {
                    value = (String) parameterMap.get(key);
                } else {
                    value = "";
                }

                parameterSb.append(key).append("=").append(value);
                if (iterator.hasNext()) {
                    parameterSb.append("&");
                }
            }

        }
        return parameterSb.toString();

    }

    public byte[] getStreamAsBytes(InputStream stream, String charset) throws IOException {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        try {
            final byte[] buff = new byte[1024];
            int read = 0;
            while ((read = stream.read(buff)) > 0) {
                arrayOutputStream.write(buff, 0, read);
            }

            return arrayOutputStream.toByteArray();
        } finally {
            if (stream != null) {
                stream.close();
            }
            arrayOutputStream.close();
        }
    }

    public static String getStreamAsString(InputStream stream, String charset) throws IOException {
        try {
            Reader reader = new InputStreamReader(stream, charset);
            StringBuilder response = new StringBuilder();

            final char[] buff = new char[1024];
            int read = 0;
            while ((read = reader.read(buff)) > 0) {
                response.append(buff, 0, read);
            }

            return response.toString();
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }


    /**
     * 通过Introspector 的方式复制属性值
     */
    private static <T> Map<String, String> beanToMap(T o) {
        Map<String, String> result = new HashMap<>();

        try {
            BeanInfo info = Introspector.getBeanInfo(o.getClass());
            for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
                Method reader = pd.getReadMethod();
                //内容为null的过滤掉
                if (reader == null || reader.invoke(o) == null) {
                    continue;
                }
                //默认继承Object类的属性，过滤掉
                if (pd.getName().equalsIgnoreCase("class") || pd.getName().equalsIgnoreCase("url") || pd.getName().equalsIgnoreCase("method") || pd.getName().equalsIgnoreCase("param")) {
                    continue;
                }
                result.put(pd.getName(), String.valueOf(reader.invoke(o)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;

    }

    public String post(UploadSoftwareReq req, MultipartFile file) throws IOException {

        //连接参数设定
        String BOUNDARY = java.util.UUID.randomUUID().toString();
        String PREFIX = "--", LINEND = "\r\n";
        String MULTIPART_FROM_DATA = "multipart/form-data";
        String CHARSET = "UTF-8";
        URL uri = new URL("http://172.19.3.3:9091/software");//据实填写\

        HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
        conn.setReadTimeout(5 * 1000); // 缓存的最长时间
        conn.setDoInput(true);// 允许输入
        conn.setDoOutput(true);// 允许输出
        conn.setUseCaches(false); // 不允许使用缓存
        conn.setRequestMethod(req.getMethod().name());
        conn.setRequestProperty("connection", "keep-alive");
        conn.setRequestProperty("Charsert", "UTF-8");
        conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);

        /*** -------组拼参数数据-----------***/
        Map<String, String> params = beanToMap(req);
        params.put("token", BaseRequest.getToken());
        params.put("product_id", BaseRequest.getProduct_id());
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(PREFIX);
            sb.append(BOUNDARY);
            sb.append(LINEND);
            sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINEND);
            sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
            sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
            sb.append(LINEND);
            sb.append(entry.getValue());
            sb.append(LINEND);
        }
        DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
        outStream.write(sb.toString().getBytes());

        /*** -------组拼文件数据-----------***/
//        File file = new File("E:\\restsdk.zip");
        StringBuilder sb1 = new StringBuilder();
        sb1.append(PREFIX);
        sb1.append(BOUNDARY);
        sb1.append(LINEND);
        // name是post中传参的键 filename是文件的名称
        sb1.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getOriginalFilename() + "\"" + LINEND);
        sb1.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINEND);
        sb1.append(LINEND);
        outStream.write(sb1.toString().getBytes());

//        InputStream is = new FileInputStream(file);
        InputStream is = file.getInputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = is.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        is.close();
        outStream.write(LINEND.getBytes());
        //结束标志
        byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
        outStream.write(end_data);
        outStream.flush();
        /***------ 发送请求---------**********/
        InputStream in;
        StringBuilder sb2 = new StringBuilder();
        int res = conn.getResponseCode();

        in = conn.getInputStream();
        int ch;
        while ((ch = in.read()) != -1) {
            sb2.append((char) ch);
        }

        outStream.close();
        conn.disconnect();
        return sb2.toString();
    }

}