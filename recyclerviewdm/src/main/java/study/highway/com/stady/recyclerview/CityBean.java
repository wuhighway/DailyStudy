package study.highway.com.stady.recyclerview;

import android.text.TextUtils;

import com.github.promeg.pinyinhelper.Pinyin;

/**
 * @author JH
 * @date 2017/4/17
 */
public class CityBean {
    private String tag;//所属的分类（城市的汉语拼音首字母）
    private String city;
    private String pinyin;

    public CityBean(String city) {
        if (!TextUtils.isEmpty(city)) {
            char[] chars = city.toCharArray();
            StringBuilder ctpy = new StringBuilder();
            for (int i = 0; i < chars.length; i++) {
                ctpy.append(Pinyin.toPinyin(chars[i]));
            }
            this.pinyin = ctpy.toString();
            this.tag = pinyin.substring(0, 1);
        }
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

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

}
