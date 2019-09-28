package com.example.administrator.txcloud;

import android.content.Context;
import android.content.SharedPreferences;



public class UserInfo {

    private String account;
    private String password;
    private int room = 4321;

    private String replayUrl;

    private static UserInfo instance;

    public static UserInfo getInstance() {
        if (null == instance) {
            synchronized (UserInfo.class) {
                if (null == instance) {
                    instance = new UserInfo();
                }
            }
        }
        return instance;
    }

    private UserInfo() {
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public String getReplayUrl() {
        return replayUrl;
    }

    public void setReplayUrl(String replayUrl) {
        this.replayUrl = replayUrl;
    }

    public void writeToCache(Context context) {
        SharedPreferences shareInfo = context.getSharedPreferences(Constans.USERINFO, 0);
        SharedPreferences.Editor editor = shareInfo.edit();
        editor.putString(Constans.ACCOUNT, account);
        editor.putString(Constans.PWD, password);
        editor.putInt(Constans.ROOM, room);
        editor.commit();
    }

    public void clearCache(Context context) {
        SharedPreferences shareInfo = context.getSharedPreferences(Constans.USERINFO, 0);
        SharedPreferences.Editor editor = shareInfo.edit();
        editor.clear();
        editor.commit();
    }

    public void getCache(Context context) {
        SharedPreferences shareInfo = context.getSharedPreferences(Constans.USERINFO, 0);
        account = shareInfo.getString(Constans.ACCOUNT, null);
        password = shareInfo.getString(Constans.PWD, null);
        room = shareInfo.getInt(Constans.ROOM, 1234);
    }
}
