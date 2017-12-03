package com.example.demo_11;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TianHuan on 2017/12/1.
 */
public class GetPhoneNumberFromMobile {
    private List<PhoneInfo> list;
    CursorUtils cursorUtils;
    public List<PhoneInfo> getPhoneNumberFromMobile(Context context){
        list = new ArrayList<PhoneInfo>();
        Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            PhoneInfo phoneInfo = new PhoneInfo(name,number);
            list.add(phoneInfo);
        }
        /*cursorUtils = new CursorUtils();
        list = cursorUtils.cursorToListForObject(cursor)*/
        return list;
    }
}
