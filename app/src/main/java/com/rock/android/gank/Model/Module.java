package com.rock.android.gank.Model;

import com.litesuits.orm.db.annotation.Column;
import com.rock.android.rocklibrary.Utils.DateFormat;

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

    @Column("_id") public String _id;
    @Column("_ns")public String _ns;
    @Column("createdAt")public Date createdAt;
    @Column("desc")public String desc;
    @Column("publishedAt")public Date publishedAt;
    @Column("type")public String type;
    @Column("url")public String url;
    @Column("used")public boolean used;
    @Column("who")public String who;

    public String fetchPublishedAtAsLocal(){
        return DateFormat.dateToString(publishedAt);
    }

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
