/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.ContentValues
 *  android.content.Context
 *  android.database.sqlite.SQLiteDatabase
 */
package com.riyuxihe.weixinqingliao.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.riyuxihe.weixinqingliao.dao.WechatDemoDbHelper;
import com.riyuxihe.weixinqingliao.model.Msg;
import java.util.ArrayList;
import java.util.List;

public class MessageManager {
    private WechatDemoDbHelper mDbHelper;

    public MessageManager(Context context) {
        this.mDbHelper = new WechatDemoDbHelper(context);
    }

    public List<Msg> getMsg(String string2) {
        SQLiteDatabase sQLiteDatabase = this.mDbHelper.getReadableDatabase();
        ArrayList<Msg> arrayList = new ArrayList<Msg>();
        try {
            string2 = sQLiteDatabase.query("message", null, "from_username = ? or to_username = ?", new String[]{string2, string2}, null, null, "create_time ASC");
            while (string2.moveToNext()) {
                Msg msg = new Msg();
                msg.MsgId = string2.getString(string2.getColumnIndexOrThrow("msg_id"));
                msg.ClientMsgId = string2.getLong(string2.getColumnIndexOrThrow("client_msg_id"));
                msg.MsgType = string2.getInt(string2.getColumnIndexOrThrow("msg_type"));
                msg.Content = string2.getString(string2.getColumnIndexOrThrow("content"));
                msg.FromUserName = string2.getString(string2.getColumnIndexOrThrow("from_username"));
                msg.ToUserName = string2.getString(string2.getColumnIndexOrThrow("to_username"));
                msg.fromNickName = string2.getString(string2.getColumnIndexOrThrow("from_nickname"));
                msg.toNickName = string2.getString(string2.getColumnIndexOrThrow("to_nickname"));
                msg.fromMemberUserName = string2.getString(string2.getColumnIndexOrThrow("from_member_username"));
                msg.fromMemberNickName = string2.getString(string2.getColumnIndexOrThrow("from_member_nickname"));
                msg.VoiceLength = string2.getLong(string2.getColumnIndexOrThrow("voice_length"));
                msg.CreateTime = string2.getLong(string2.getColumnIndexOrThrow("create_time"));
                arrayList.add(msg);
            }
        }
        finally {
            sQLiteDatabase.close();
        }
        return arrayList;
    }

    public void insertMessage(Msg msg) {
        SQLiteDatabase sQLiteDatabase = this.mDbHelper.getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            if (msg.CreateTime == 0L) {
                msg.CreateTime = System.currentTimeMillis();
            }
            contentValues.put("client_msg_id", Long.valueOf(msg.ClientMsgId));
            contentValues.put("msg_id", msg.MsgId);
            contentValues.put("msg_type", Integer.valueOf(msg.MsgType));
            contentValues.put("content", msg.Content);
            contentValues.put("from_username", msg.FromUserName);
            contentValues.put("to_username", msg.ToUserName);
            contentValues.put("from_nickname", msg.fromNickName);
            contentValues.put("to_nickname", msg.toNickName);
            contentValues.put("from_member_username", msg.fromMemberUserName);
            contentValues.put("from_member_nickname", msg.fromMemberNickName);
            contentValues.put("voice_length", Long.valueOf(msg.VoiceLength));
            contentValues.put("create_time", Long.valueOf(msg.CreateTime));
            sQLiteDatabase.insert("message", null, contentValues);
            return;
        }
        finally {
            sQLiteDatabase.close();
        }
    }

    public void reCreateTable() {
        SQLiteDatabase sQLiteDatabase = this.mDbHelper.getWritableDatabase();
        try {
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS message");
            sQLiteDatabase.execSQL("CREATE TABLE message (_id INTEGER PRIMARY KEY,client_msg_id TEXT,msg_id TEXT,msg_type INTEGER,content TEXT,from_username TEXT,to_username TEXT,from_nickname TEXT,to_nickname TEXT,from_member_username TEXT,from_member_nickname TEXT,voice_length INTEGER,create_time INTEGER ) ");
            return;
        }
        finally {
            sQLiteDatabase.close();
        }
    }
}

