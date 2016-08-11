package com.chinamobile.iot.model.business;

/**
 * Created by buchan on 2016/5/26 0026.
 */
public class LbsInfo {
    private String mcc;
    private String mnc;
    private String lac;
    private String cell;
    public LbsInfo(){
    }
    public LbsInfo(String mcc, String mnc, String lac, String cell) {
        this.mcc = mcc;
        this.mnc = mnc;
        this.lac = lac;
        this.cell = cell;
    }
    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getMnc() {
        return mnc;
    }

    public void setMnc(String mnc) {
        this.mnc = mnc;
    }

    public String getLac() {
        return lac;
    }

    public void setLac(String lac) {
        this.lac = lac;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    @Override
    public String toString() {
        return "LbsInfo{" +
                "mcc='" + mcc + '\'' +
                ", mnc='" + mnc + '\'' +
                ", lac='" + lac + '\'' +
                ", cell='" + cell + '\'' +
                '}';
    }
}
