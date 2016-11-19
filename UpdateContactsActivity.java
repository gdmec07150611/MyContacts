package cn.edu.gdmec.s07150611.mycontacts;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/10/24.
 */
public class UpdateContactsActivity extends Activity{
    private EditText nameEditText,mobileEditText,qqEditText,companyEditText,addressEditText;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);

        nameEditText= (EditText) findViewById(R.id.name);
        qqEditText= (EditText) findViewById(R.id.qq);
        addressEditText= (EditText) findViewById(R.id.address);
        companyEditText= (EditText) findViewById(R.id.company);
        mobileEditText= (EditText) findViewById(R.id.tel);

        Bundle localBundle=getIntent().getExtras();
        int id=localBundle.getInt("user_ID");
        ContactsTable ct=new ContactsTable(this);
        user =ct.getUserByID(id);
        nameEditText.setText(user.getName());
        mobileEditText.setText(user.getTel());
        qqEditText.setText(user.getQq());
        companyEditText.setText(user.getCompany());
        addressEditText.setText(user.getAddress());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,1,0,"保存");
        menu.add(0,2,0,"返回");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 1:
                if(!nameEditText.getText().toString().trim().equals("")){
                    user.setName(nameEditText.getText().toString());
                    user.setTel(mobileEditText.getText().toString());
                    user.setQq(qqEditText.getText().toString());
                    user.setCompany(companyEditText.getText().toString());
                    user.setAddress(addressEditText.getText().toString());
                    ContactsTable ct=new ContactsTable(this);
                    if(ct.updateUser(user)){
                        Toast.makeText(this,"修改成功",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this,"修改失败",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this,"数据不能为空！",Toast.LENGTH_SHORT).show();
                }break;
            case 2:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
