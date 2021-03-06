package tempakunoshiro.automaticotakumatching;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Nan on 2016/09/17.
 */
@DatabaseTable(tableName = "scream")
public class MyScream implements Parcelable, Serializable {
    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField(canBeNull = false)
    private long userId;
    @DatabaseField(canBeNull = false)
    private String text;
    @DatabaseField(canBeNull = false)
    private long time;

    private MyScream(){}

    protected MyScream(Parcel src) {
        this.id = src.readLong();
        this.userId = src.readLong();
        this.text = src.readString();
        this.time = src.readLong();
    }

    public MyScream(long userId, String text, long time) {
        if(userId <= 0){
            throw new IllegalArgumentException("idは1以上にしてください");
        }
        this.userId = userId;
        this.text = text;
        this.time = time;
    }

    @Nullable
    public static MyScream getMyScreamById(Context context, long userId) {
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(context);
        MyScream scream = null;
        try {
            Dao screamDao =  dbHelper.getDao(MyScream.class);
            QueryBuilder<MyScream, Integer> queryBuilder = screamDao.queryBuilder();
            queryBuilder.where().eq("id", userId);
            List<MyScream> screams = queryBuilder.query();
            if(screams.size() != 0){
                scream = screams.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scream;
    }

    @Nullable
    public static List<MyScream> getAllMyScream(Context context) {
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(context);
        try {
            Dao screamDao = dbHelper.getDao(MyScream.class);
            return screamDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Nullable
    public static List<MyScream> getAllMyScreamWithinTime(Context context, long time) {
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(context);
        try {
            Dao screamDao = dbHelper.getDao(MyScream.class);
            QueryBuilder<MyScream, Integer> queryBuilder = screamDao.queryBuilder();
            queryBuilder.where().ge("time", time);
            return queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(userId);
        dest.writeString(text);
        dest.writeLong(time);
    }

    public static final Parcelable.Creator<MyScream> CREATOR
            = new Parcelable.Creator<MyScream>() {
        public MyScream createFromParcel(Parcel in) {
            return new MyScream(in);
        }

        public MyScream[] newArray(int size) {
            return new MyScream[size];
        }
    };

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public String getText() {
        return text;
    }

    public long getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "MyScream{" +
                "id=" + id +
                ", userId=" + userId +
                ", text='" + text + '\'' +
                ", time=" + time +
                '}';
    }
}
