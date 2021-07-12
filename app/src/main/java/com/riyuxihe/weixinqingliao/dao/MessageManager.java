package com.riyuxihe.weixinqingliao.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.riyuxihe.weixinqingliao.model.Msg;
import java.util.ArrayList;
import java.util.List;

public class MessageManager {
    private WechatDemoDbHelper mDbHelper;

    public MessageManager(Context context) {
        this.mDbHelper = new WechatDemoDbHelper(context);
    }

    public void reCreateTable() {
        SQLiteDatabase db = this.mDbHelper.getWritableDatabase();
        try {
            db.execSQL("DROP TABLE IF EXISTS message");
            db.execSQL("CREATE TABLE message (_id INTEGER PRIMARY KEY,client_msg_id TEXT,msg_id TEXT,msg_type INTEGER,content TEXT,from_username TEXT,to_username TEXT,from_nickname TEXT,to_nickname TEXT,from_member_username TEXT,from_member_nickname TEXT,voice_length INTEGER,create_time INTEGER ) ");
        } finally {
            db.close();
        }
    }

    public void insertMessage(Msg msg) {
        SQLiteDatabase db = this.mDbHelper.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            if (msg.CreateTime == 0) {
                msg.CreateTime = System.currentTimeMillis();
            }
            values.put(MessageContract.MessageEntry.COLUMN_CLIENT_MSG_ID, Long.valueOf(msg.ClientMsgId));
            values.put(MessageContract.MessageEntry.COLUMN_MSG_ID, msg.MsgId);
            values.put(MessageContract.MessageEntry.COLUMN_MSG_TYPE, Integer.valueOf(msg.MsgType));
            values.put(MessageContract.MessageEntry.COLUMN_CONTENT, msg.Content);
            values.put(MessageContract.MessageEntry.COLUMN_FROM_USER_NAME, msg.FromUserName);
            values.put(MessageContract.MessageEntry.COLUMN_TO_USER_NAME, msg.ToUserName);
            values.put(MessageContract.MessageEntry.COLUMN_FROM_NICK_NAME, msg.fromNickName);
            values.put(MessageContract.MessageEntry.COLUMN_TO_NICK_NAME, msg.toNickName);
            values.put(MessageContract.MessageEntry.COLUMN_FROM_MEMBER_USER_NAME, msg.fromMemberUserName);
            values.put(MessageContract.MessageEntry.COLUMN_FROM_MEMBER_NICK_NAME, msg.fromMemberNickName);
            values.put(MessageContract.MessageEntry.COLUMN_VOICE_LENGTH, Long.valueOf(msg.VoiceLength));
            values.put(MessageContract.MessageEntry.COLUMN_CREATE_TIME, Long.valueOf(msg.CreateTime));
            db.insert("message", (String) null, values);
        } finally {
            db.close();
        }
    }

    public List<Msg> getMsg(String userName) {
        SQLiteDatabase db = this.mDbHelper.getReadableDatabase();
        List<Msg> msgList = new ArrayList<>();
        try {
            Cursor cursor = db.query("message", (String[]) null, "from_username = ? or to_username = ?", new String[]{userName, userName}, (String) null, (String) null, "create_time ASC");
            while (cursor.moveToNext()) {
                Msg msg = new Msg();
                msg.MsgId = cursor.getString(cursor.getColumnIndexOrThrow(MessageContract.MessageEntry.COLUMN_MSG_ID));
                msg.ClientMsgId = cursor.getLong(cursor.getColumnIndexOrThrow(MessageContract.MessageEntry.COLUMN_CLIENT_MSG_ID));
                msg.MsgType = cursor.getInt(cursor.getColumnIndexOrThrow(MessageContract.MessageEntry.COLUMN_MSG_TYPE));
                msg.Content = cursor.getString(cursor.getColumnIndexOrThrow(MessageContract.MessageEntry.COLUMN_CONTENT));
                msg.FromUserName = cursor.getString(cursor.getColumnIndexOrThrow(MessageContract.MessageEntry.COLUMN_FROM_USER_NAME));
                msg.ToUserName = cursor.getString(cursor.getColumnIndexOrThrow(MessageContract.MessageEntry.COLUMN_TO_USER_NAME));
                msg.fromNickName = cursor.getString(cursor.getColumnIndexOrThrow(MessageContract.MessageEntry.COLUMN_FROM_NICK_NAME));
                msg.toNickName = cursor.getString(cursor.getColumnIndexOrThrow(MessageContract.MessageEntry.COLUMN_TO_NICK_NAME));
                msg.fromMemberUserName = cursor.getString(cursor.getColumnIndexOrThrow(MessageContract.MessageEntry.COLUMN_FROM_MEMBER_USER_NAME));
                msg.fromMemberNickName = cursor.getString(cursor.getColumnIndexOrThrow(MessageContract.MessageEntry.COLUMN_FROM_MEMBER_NICK_NAME));
                msg.VoiceLength = cursor.getLong(cursor.getColumnIndexOrThrow(MessageContract.MessageEntry.COLUMN_VOICE_LENGTH));
                msg.CreateTime = cursor.getLong(cursor.getColumnIndexOrThrow(MessageContract.MessageEntry.COLUMN_CREATE_TIME));
                msgList.add(msg);
            }
            return msgList;
        } finally {
            db.close();
        }
    }
}
