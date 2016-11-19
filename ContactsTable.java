package cn.edu.gdmec.s07150611.mycontacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.Vector;

/**
 * Created by Administrator on 2016/10/22.
 */
public class ContactsTable {
    private final static String TABLENAME="contactsTable";
    private MyDB db;
    public ContactsTable(Context context){
        db=new MyDB(context);
        if(!db.isTableExits(TABLENAME)){
            String createTableSql="CREATE TABLE IF NOT EXISTS"+TABLENAME+"(id_DB integer "+
                    "primary key AUTOINCREMENT,"+User.NAME+"VARCHAR,"+User.TEL+"VARCHAR,"+User.QQ+"VARCHAR,"
                    +User.COMPANY+"VARCGAR,"+User.ADDRESS+"VARCHAR)";
            db.creatTable(createTableSql);
        }
    }
    public boolean addData(User user){
        ContentValues values=new ContentValues();
        values.put(User.NAME,user.getName());
        values.put(User.TEL,user.getTel());
        values.put(User.COMPANY,user.getCompany());
            values.put(User.QQ,user.getQq());
        values.put(User.ADDRESS,user.getAddress());
        return db.save(TABLENAME,values);
    }
    public User getUserByID(int id){
        Cursor  cursor=null;
        User temp=new User();
        try{
            cursor=db.find("select * from "+TABLENAME + "where"+"id_DB=?",new String[]{id+""});
            cursor.moveToNext();
            temp.setId_DB(cursor.getInt(cursor.getColumnIndex("id_DB")));
            temp.setName(cursor.getString(cursor.getColumnIndex(User.NAME)));
            temp.setAddress(cursor.getString(cursor.getColumnIndex(User.ADDRESS)));
            temp.setCompany(cursor.getString(cursor.getColumnIndex(User.COMPANY)));
            temp.setQq(cursor.getString(cursor.getColumnIndex(User.QQ)));
            temp.setTel(cursor.getString(cursor.getColumnIndex(User.TEL)));
            Log.d("aa",temp.getName());
            return temp;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor!=null){
                cursor.close();
            }
            db.closeConnection();
        }
        return null;
    }
    public boolean updateUser(User user){
        ContentValues values=new ContentValues();
        values.put(User.NAME,user.getName());
        values.put(User.TEL,user.getTel());
        values.put(User.ADDRESS,user.getAddress());
        values.put(User.COMPANY,user.getCompany());
        values.put(User.QQ,user.getQq());
        return db.update(TABLENAME,values,"id_DB=?",new String[]{user.getId_DB()+""});

    }
    public User[] getAllUser(){
        Vector<User> v=new Vector<User>();
        Cursor cursor=null;
        try{
            cursor=db.find("select * from "+TABLENAME,null);
            while(cursor.moveToNext()){
                    User temp =new User();
                temp.setId_DB(cursor.getInt(cursor.getColumnIndex("id_DB")));
                temp.setName(cursor.getString(cursor.getColumnIndex(User.NAME)));
                temp.setAddress(cursor.getString(cursor.getColumnIndex(User.ADDRESS)));
                temp.setCompany(cursor.getString(cursor.getColumnIndex(User.COMPANY)));
                temp.setQq(cursor.getString(cursor.getColumnIndex(User.QQ)));
                temp.setTel(cursor.getString(cursor.getColumnIndex(User.TEL)));
                v.add(temp);
            }
        }catch (Exception e){
                e.printStackTrace();
        }finally {
            if(cursor!=null){
                cursor.close();
            }
            db.closeConnection();
        }
        if (v.size()>0){
            return v.toArray(new User[]{});
        }else{
            User[] users=new User[1];
            User user=new User();
            user.setName("无结果");
            users[0]=user;
            return users;
        }
    }
    public User[] findUserByKey(String key){
        Vector<User> v=new Vector<User>();
        Cursor cursor=null;
        try{
            cursor=db.find("select * from "+TABLENAME+" where "+User.NAME+"like '%+"+key+"%'"+
            "or" +User.TEL+"like '%"+key+"'"+"or"+User.QQ+"like '%"+key+"%'",null);
            while(cursor.moveToNext()){
                User temp=new User();
                temp.setId_DB(cursor.getInt(cursor.getColumnIndex("id_DB")));
                temp.setName(cursor.getString(cursor.getColumnIndex(User.NAME)));
                temp.setAddress(cursor.getString(cursor.getColumnIndex(User.ADDRESS)));
                temp.setCompany(cursor.getString(cursor.getColumnIndex(User.COMPANY)));
                temp.setQq(cursor.getString(cursor.getColumnIndex(User.QQ)));
                temp.setTel(cursor.getString(cursor.getColumnIndex(User.TEL)));
                v.add(temp);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(cursor!=null){
                cursor.close();
            }
            db.closeConnection();
        }
        if (v.size()>0){
            return v.toArray(new User[]{});
        }else{
            User[] users=new User[1];
            User user=new User();
            user.setName("无结果");
            users[0]=user;
            return users;
            }
        }
    public boolean deleteByUser(User user){
        return db.delete(TABLENAME,"id_DB=?",new String[]{user.getId_DB()+""});
    }

}
