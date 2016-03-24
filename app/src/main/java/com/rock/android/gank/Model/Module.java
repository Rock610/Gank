package com.rock.android.gank.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by rock on 16/3/22.
 */
public class Module implements Serializable{
    /**
     * _id : 56cc6d23421aa95caa707c52
     * _ns : ganhuo
     * createdAt : 2015-08-07T01:21:06.112Z
     * desc : 8.7——（1）
     * publishedAt : 2015-08-07T03:57:47.310Z
     * type : 福利
     * url : http://ww2.sinaimg.cn/large/7a8aed7bgw1eutscfcqtcj20dw0i0q4l.jpg
     * used : true
     * who : 张涵宇
     */
    public String _id;
    public String _ns;
    public Date createdAt;
    public String desc;
    public Date publishedAt;
    public String type;
    public String url;
    public boolean used;
    public String who;

    @Override
    public String toString() {
        return "Module{" +
                "_id='" + _id + '\'' +
                ", _ns='" + _ns + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", desc='" + desc + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", used=" + used +
                ", who='" + who + '\'' +
                '}';
    }
}
