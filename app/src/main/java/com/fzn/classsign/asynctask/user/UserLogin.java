package com.fzn.classsign.asynctask.user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.fzn.classsign.R;
import com.fzn.classsign.activitys.fragment.StudentFragmentActivity;
import com.fzn.classsign.activitys.fragment.TeacherFragmentActivity;
import com.fzn.classsign.asynctask.base.CustomAsyncTask;
import com.fzn.classsign.constant.RequestConstant;
import com.fzn.classsign.entity.Token;

import java.util.Map;

public class UserLogin<T> extends CustomAsyncTask<T> {
    Token token = new Token();
    private int type;    //1、教师端，2、学生端
    private int flag;   //1为账户密码登录，2验证码登录
    Context context;

    public UserLogin(Map headers, Map body, Map params, int type, Context context,int flag) {
        super(headers, body, params, RequestConstant.URL.LOGIN);
        this.type = type;
        this.context = context;
        this.flag=flag;
    }

    @Override
    protected void onPostExecute(String s) {
        ResponseResultJson<Map<String, Object>> temp = (ResponseResultJson<Map<String, Object>>) getResponse(s);
        int code = temp.getCode();
        if(code == 200){
            Map<String, Object> map = temp.getData();
            Token.token = map.get("access_token").toString();
            Token.refreshToken = map.get("refresh_token").toString();
            storeRefreshToken();
            if(type==1){
                Intent intent =new Intent(context, TeacherFragmentActivity.class);
                context.startActivity(intent);
            }else if(type ==2){
                Intent intent=new Intent(context, StudentFragmentActivity.class);
                context.startActivity(intent);
            }
        }
        else{
            if(flag == 1){
                Toast.makeText(context,"手机号或密码错误",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context,"验证码错误",Toast.LENGTH_SHORT).show();
            }
        }
        super.onPostExecute(s);
    }

    //存储refreshToken
    private void storeRefreshToken() {
        String spFileName = context.getResources().getString(R.string.shared_preferences_file_name);
        String refreshTokenKey = context.getResources().getString(R.string.refresh_token);

        SharedPreferences spFile = context.getSharedPreferences(spFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spFile.edit();
        editor.putString(refreshTokenKey,Token.refreshToken);
        editor.apply();
    }
}
