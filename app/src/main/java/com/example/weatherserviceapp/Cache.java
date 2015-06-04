package com.example.weatherserviceapp;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Адрей on 04.06.2015.
 */
public class Cache {

    private HashMap<String, Date> _timeMap = new HashMap<>();
    private HashMap<String, Object> _dataMap = new HashMap<>();

    private  int SAVE_TIME = 10;

    public void WriteToCache(String Key, Object value) {

        Date curDate = Calendar.getInstance().getTime();
        _timeMap.put(Key, curDate);
        _dataMap.put(Key, value);
    }

    private void RemoveFromCache(String Key)
    {
        _timeMap.remove(Key);
        _dataMap.remove(Key);
    }

    public Object ReadFromCache(String key)
    {
        Date lastUpdateTime = _timeMap.get(key);
        if(lastUpdateTime == null)
            return null;

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MINUTE, -SAVE_TIME);
        Date d = c.getTime();

        if(d.after(lastUpdateTime)) {
            RemoveFromCache(key);
            return null;
        }else
            return _dataMap.get(key);
    }

}
