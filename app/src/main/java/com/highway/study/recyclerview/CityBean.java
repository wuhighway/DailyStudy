package com.highway.study.recyclerview;
/**
 *
 * @author JH
 * @date 2017/3/30
 */
public class CityBean {
    private String tag;//所属的分类（城市的汉语拼音首字母）
    private String city;

    public CityBean(String tag, String city) {
        this.tag = tag;
        this.city = city;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
