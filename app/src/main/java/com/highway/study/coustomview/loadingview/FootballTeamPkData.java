package com.highway.study.coustomview.loadingview;

/**
 * Created by Administrator on 2017/8/24.
 */

public class FootballTeamPkData {
    private String hvalue;
    private String gvalue;
    private String title;

    public String getHvalue() {
        return hvalue;
    }

    public void setHvalue(String hvalue) {
        this.hvalue = hvalue;
    }

    public String getGvalue() {
        return gvalue;
    }

    public void setGvalue(String gvalue) {
        this.gvalue = gvalue;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "FootballTeamPkData{" +
                "hvalue='" + hvalue + '\'' +
                ", gvalue='" + gvalue + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
