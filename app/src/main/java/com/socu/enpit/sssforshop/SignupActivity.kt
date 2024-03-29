package com.socu.enpit.sssforshop

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.nifcloud.mbaas.core.NCMBUser
import kotlinx.android.synthetic.main.activity_login.*

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        signupButton.setOnClickListener { signup() }
        back_login_textview.setOnClickListener {
            // Finish the registration screen and return to the Login activity
            setResult(Activity.RESULT_CANCELED, intent)
            finish()
        }
    }

    private fun signup() {
        if (!validate()) {
            onSignupFailed()
            return
        }

        signupButton!!.isEnabled = false

        val progressDialog = ProgressDialog(this@SignupActivity,
            R.style.Theme_AppCompat_Light_Dialog)
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("アカウントを作成しています...")
        progressDialog.show()

        val name = input_name.text.toString()
        val password = input_password.text.toString()

        // TODO: Implement your own signup logic here.
        //NCMBUserのインスタンスを作成
        val user = NCMBUser()
        //ユーザ名を設定
        user.userName = name
        //パスワードを設定
        user.setPassword(password)
        //設定したユーザ名とパスワードで会員登録を行う
        user.signUpInBackground { e ->
            if (e != null) {
                //会員登録時にエラーが発生した場合の処理
                onSignupFailed()
                progressDialog.dismiss()
            } else {
                android.os.Handler().postDelayed(
                    {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess()
                        // onSignupFailed();
                        progressDialog.dismiss()
                    }, 3000)
            }
        }
    }


    private fun onSignupSuccess() {
        signupButton!!.isEnabled = true
        val name = input_name.text.toString()
        intent.putExtra("input_name", name)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun onSignupFailed() {
        Toast.makeText(baseContext, "そのアカウント名は既に存在しています。", Toast.LENGTH_LONG).show()

        signupButton!!.isEnabled = true
    }

    private fun validate(): Boolean {
        var valid = true

        val name = input_name.text.toString()
        val password = input_password.text.toString()

        if (name.isEmpty() || name.length < 3) {
            input_name!!.error = "3文字以上の半角英数字を入力してください。"
            valid = false
        } else if (name == CloudDataManager.getAccountUserNameDefault()) {
            input_name!!.error = "その名前は使用できません。"
            valid = false
        } else {
            input_name!!.error = null
        }

        if (password.isEmpty() || password.length < 4 || password.length > 10) {
            input_password!!.error = "4から10文字の半角英数字を入力してください。"
            valid = false
        } else {
            input_password!!.error = null
        }

        return valid
    }

    companion object {
        private val TAG = "SignupActivity"
    }
}
