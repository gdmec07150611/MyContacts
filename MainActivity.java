package cn.edu.gdmec.s07150611.mycontacts;

import android.app.Activity;
import android.app.Dialog;
import android.app.job.JobInfo;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private ListView listView;
    private BaseAdapter listViewAdapter;
    private User[]  users;
    private int selectItem=0;

    public BaseAdapter getListViewAdapter(){
        return listViewAdapter;
    }
    public void setUsers(User[] users){
        this.users=users;
    }
    public void setSelectItem(int selectItem){
        this.selectItem=selectItem;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("通讯录");
        listView= (ListView) findViewById(R.id.listView);
        loadContacts();

    }
    public void loadContacts(){
        ContactsTable ct=new ContactsTable(this);
        users=ct.getAllUser();
        listViewAdapter=new BaseAdapter() {
            @Override
            public int getCount() {
                return users.length;
            }

            @Override
            public Object getItem(int position) {
                return users[position];
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView==null){
                    TextView textView=new TextView(MainActivity.this);
                    textView.setTextSize(22);
                    convertView=textView;
                }
                String tel=users[position].getTel()==null?"":users[position].getTel();
                ((TextView)convertView).setText(users[position].getName()+"---"+tel);
                if (position==selectItem){
                    convertView.setBackgroundColor(Color.YELLOW);
                }else{
                    convertView.setBackgroundColor(0);

                }
                return convertView;
            }
        };
        listView.setAdapter(listViewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem=position;
                listViewAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE,1,Menu.NONE,"添加");
        menu.add(Menu.NONE,2,Menu.NONE,"编辑");
        menu.add(Menu.NONE,3,Menu.NONE,"查看信息");
        menu.add(Menu.NONE,4,Menu.NONE,"删除");
        menu.add(Menu.NONE,5,Menu.NONE,"查询");
        menu.add(Menu.NONE,6,Menu.NONE,"导入到手机通讯录");
        menu.add(Menu.NONE,7,Menu.NONE,"退出");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent=new Intent();
        String sHint;
        switch (item.getItemId()){
            case 1:
                intent.setClass(MainActivity.this,AddContactsActivity.class);
                startActivity(intent);
               break;
            case 2:
                if (users[selectItem].getId_DB()>0) {
                    intent.setClass(MainActivity.this, UpdateContactsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("user_ID", users[selectItem].getId_DB());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else{
                    sHint="无结果记录,无法操作！";
                    Toast.makeText(this,sHint,Toast.LENGTH_SHORT).show();
                }
                break;
            case 3:
                if (users[selectItem].getId_DB()>0) {
                intent.setClass(MainActivity.this,ContactsMessageActivity.class);
                intent.putExtra("user_ID",users[selectItem].getId_DB());
                startActivity(intent);
                }else{
                    sHint="无结果记录,无法操作！";
                    Toast.makeText(this,sHint,Toast.LENGTH_SHORT).show();
                }break;
            case 4:if (users[selectItem].getId_DB()>0) {
                delete();
            }else{
                sHint="无结果记录,无法操作！";
                Toast.makeText(this,sHint,Toast.LENGTH_SHORT).show();
             }

                break;
            case 5:
                new FindDialog(this).show();
                break;
            case 6:if (users[selectItem].getId_DB()>0) {
                importPhone(users[selectItem].getName(),users[selectItem].getTel());
                sHint="已经成功导入"+users[selectItem].getName()+"导入到手机电话簿！";
                Toast.makeText(this,sHint,Toast.LENGTH_SHORT).show();
            }else{
            sHint="无结果记录,无法操作！";
            Toast.makeText(this,sHint,Toast.LENGTH_SHORT).show();
            }
                break;
            case 7:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ContactsTable ct=new ContactsTable(this);
        users=ct.getAllUser();
        listViewAdapter.notifyDataSetChanged();
    }

    public void delete(){
        AlertDialog.Builder alert=new AlertDialog.Builder(this);
        alert.setTitle("危险操作提示");
        alert.setMessage("是否删除联系人？");
        alert.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                ContactsTable ct=new ContactsTable(MainActivity.this);
                if (ct.deleteByUser(users[selectItem])){
                    users=ct.getAllUser();
                    listViewAdapter.notifyDataSetChanged();
                    selectItem=0;
                    Toast.makeText(MainActivity.this,"删除成功！",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"删除失败！",Toast.LENGTH_SHORT).show();
                }
            }
        });
        alert.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.show();
    }
    public void importPhone(String name,String phone){
        Uri phoneUri= ContactsContract.Data.CONTENT_URI;
        ContentValues values=new ContentValues();
        Uri rawContactUri=this.getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI,values);
        long rawContactId= ContentUris.parseId(rawContactUri);
        values.clear();
        values.put(ContactsContract.Data.RAW_CONTACT_ID,rawContactId);
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME,name);
        this.getContentResolver().insert(phoneUri,values);
        values.clear();
        values.put(ContactsContract.Data.RAW_CONTACT_ID,rawContactId);
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER,phone);
        values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
        this.getContentResolver().insert(phoneUri,values);
    }
}
