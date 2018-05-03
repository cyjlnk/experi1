package com.diabin.latte.ec.database;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

/**
 * Created by fei on 2017/8/1.
 */

public class ReleaseOpenHelper extends DaoMaster.OpenHelper {


    public ReleaseOpenHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onCreate(Database db) {
        super.onCreate(db);
    }
}
