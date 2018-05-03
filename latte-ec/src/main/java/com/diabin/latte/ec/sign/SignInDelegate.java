package com.diabin.latte.ec.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.diabin.latte.delegates.LatteDelegate;
import com.diabin.latte.ec.R;
import com.diabin.latte.ec.R2;
import com.diabin.latte.net.RestClient;
import com.diabin.latte.net.callback.ISuccess;
import com.diabin.latte.util.log.LatteLogger;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fei on 2017/8/1.
 */

public class SignInDelegate extends LatteDelegate {

    @BindView(R2.id.edit_sign_in_email)
    TextInputEditText mEmail=null;
    @BindView(R2.id.edit_sign_in_password)
    TextInputEditText mPassword=null;

    private ISignListener mISignListener =null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof ISignListener){
            mISignListener= (ISignListener) activity;
        }
    }

    @OnClick(R2.id.btn_sign_in)
    void onClickSignIn(){

        if(checkForm()){

            RestClient.builder()
                    .url("http://www.xiufm.com/RestServer/api/user_profile.php")
                    .params("email",mEmail.toString())
                    .params("password",mPassword.toString())
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            LatteLogger.json("USER_PROFILE",response);
                            //数据持久化
                            SignHandler.onSignIn(response,mISignListener);

                        }
                    })
                    .build()
                    .post();

            Toast.makeText(getContext(),"通过",Toast.LENGTH_LONG).show();
        }

    }

    @OnClick(R2.id.icon_sign_in_wechat)
    void onClickWeChat(){

    }

    @OnClick(R2.id.tv_link_sign_up)
    void onClickLink(){
        start(new SignUpDelegate());
    }


    private boolean checkForm(){


        final String email=mEmail.getText().toString();
        final String password=mPassword.getText().toString();


        boolean isPass=true;



        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mEmail.setError("邮箱输入有误！");
            isPass=false;
        }else {
            mEmail.setError(null);
        }


        if(password.isEmpty() || password.length()<6){
            mPassword.setError("请填写至少6位的密码！");
            isPass=false;
        }else {
            mPassword.setError(null);
        }


        return isPass;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
