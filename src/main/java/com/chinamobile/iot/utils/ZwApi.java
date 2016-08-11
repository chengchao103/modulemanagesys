package com.chinamobile.iot.utils;

import com.chinamobile.iot.model.common.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by buchan on 2016/7/18 0018.
 */
public class ZwApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZwApi.class);


    /**
     * 调用专网接口
     * @param num  8位数字序列（此序列由集团客户自主生成，比如从00000001开始递增
     * @param iccid 所查询的专网号码的机器码
     * @return
     */
    public static CommonResult  getModelInfo(String num,String iccid){
        CommonResult cmrt = new CommonResult();
        cmrt.setRet(0);
        cmrt.setMsg("获取接口成功");
        StringBuffer sb = new StringBuffer();
        try {
            URL url2 = new URL("http://183.230.96.66:8087/v2/gprsrealtimeinfo_inner");

            HttpURLConnection conn = (HttpURLConnection) url2.openConnection();

            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");

            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setUseCaches(false);

            conn.connect();
            DataOutputStream out = new DataOutputStream(conn
                    .getOutputStream());

            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String time =formatter.format(new Date());
//            Integer num=00000001;
            String appid="A1M50WC";
            String transid=appid+time+num;
            String ebid="0001000000190";
            String pwd="ZNMZB";
            String token=appid+pwd+transid;
            String token256=Encrypt(token,"");
//            String iccid="89860010011631234561";


            String parm ="appid="+appid+"&transid="+transid+"&ebid="+ebid+"&token="+token256+"&iccid="+iccid;
            LOGGER.info("ZwApi parm:{}",parm);
            out.writeBytes(parm);
            out.flush();
            out.close();


            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            reader.close();
             conn.disconnect();
            LOGGER.info("调用专网接口成功:" + sb.toString());

        } catch (Exception e) {
            cmrt.setRet(1);
            cmrt.setMsg("http连接异常");
            LOGGER.info("调用专网接口失败:" + e.getMessage());
            return cmrt;
        }
        cmrt.setMsg(sb.toString());
        return cmrt;
    }

    private static String Encrypt(String strSrc, String encName) {
        MessageDigest md = null;
        String strDes = null;

        byte[] bt = strSrc.getBytes();
        try {
            if (encName == null || encName.equals("")) {
                encName = "SHA-256";
            }
            md = MessageDigest.getInstance(encName);
            md.update(bt);
            strDes = bytes2Hex(md.digest()); // to HexString
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes;
    }
    private static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

}
