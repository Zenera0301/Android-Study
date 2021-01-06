package com.jsc4.aboutactivity.provider;

        import com.jsc4.aboutactivity.database.DatabaseHelper;

public class URIList {
    public static final String CONTENT = "content://";
    public static final String AUTHORITY = "com.jsc4.aboutactivity";//包名

    // content://com.jsc4.aboutactivity/user
    // content://com.jsc4.aboutactivity/book
    public static final String USER_URI = CONTENT + AUTHORITY + "/" + DatabaseHelper.USER_TABLE_NAME;
    public static final String BOOK_URI = CONTENT + AUTHORITY + "/" + DatabaseHelper.BOOK_TABLE_NAME;
}
