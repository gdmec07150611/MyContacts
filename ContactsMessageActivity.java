package cn.edu.gdmec.s07150611.mycontacts;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/10/24.
 */
public class ContactsMessageActivity  extends Activity{

    private TextView nameEditText,mobileEditText,qqEditText,companyEditText,addressEditText;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_message);
        setTitle("联系人信息");
        nameEditText= (TextView) findViewById(R.id.name);
        qqEditText= (TextView) findViewById(R.id.qq);
        addressEditText= (TextView) findViewById(R.id.address);
        companyEditText= (TextView) findViewById(R.id.company);
        mobileEditText= (TextView) findViewById(R.id.tel);

        Bundle localBundle=getIntent().getExtras();
        int id=localBundle.getInt("user_ID");
        ContactsTable ct=new ContactsTable(this);
        user =ct.getUserByID(id);
        nameEditText.setText("姓名:"+user.getName());
        mobileEditText.setText("电话:"+user.getTel());
        qqEditText.setText("qq:"+user.getQq());
        companyEditText.setText("单位:"+user.getCompany());
        addressEditText.setText("地址:"+user.getAddress());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE,1,Menu.NONE,"返回");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 1:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
