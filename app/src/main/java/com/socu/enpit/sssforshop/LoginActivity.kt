package com.socu.enpit.sssforshop

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.nifcloud.mbaas.core.NCMBException
import com.nifcloud.mbaas.core.NCMBUser
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signupButton.setOnClickListener { login() }
        back_login_textview.setOnClickListener {
            val intent = Intent(applicationContext, SignupActivity::class.java)
            startActivityForResult(intent, REQUEST_SIGNUP)
        }
    }

    fun login() {
        if (!validate()) {
            onLoginFailed()
            return
        }

        signupButton?.isEnabled = false

        val progressDialog = ProgressDialog(this@LoginActivity,
            R.style.Theme_AppCompat_Light_Dialog)
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("Authenticating...")
        progressDialog.show()

        val name = input_name?.text.toString()
        val password = input_password?.text.toString()

        // TODO: Implement your own authentication logic here.
        //ユーザ名とパスワードを指定してログインを実行
        try {
            NCMBUser.loginInBackground(name, password) { user, e ->
                if (e != null) {
                    //エラー時の処理
                    onLoginFailed()
                    progressDialog.dismiss()
                } else {
                    android.os.Handler().postDelayed(
                        {
                            // On complete call either onLoginSuccess or onLoginFailed
                            onLoginSuccess()
                            // onLoginFailed();
                            progressDialog.dismiss()
                        }, 3000)
                }
            }
        } catch (e: NCMBException) {
            e.printStackTrace()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == Activity.RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                //this.finish()
                onLoginSuccess()
            }
        }
    }

    override fun onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true)
    }

    private fun onLoginSuccess() {
        signupButton?.isEnabled = true
        //finish()
        val intent = Intent(this, EditActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun onLoginFailed() {
        Toast.makeText(baseContext, "Login failed", Toast.LENGTH_LONG).show()

        signupButton?.isEnabled = true
    }

    private fun validate(): Boolean {
        var valid = true

        val name = input_name?.text.toString()
        val password = input_password?.text.toString()

        if (name.isEmpty()) {
            input_name?.error = "enter username"
            valid = false
        } else {
            input_name?.error = null
        }

        if (password.isEmpty() || password.length < 4 || password.length > 10) {
            input_password?.error = "between 4 and 10 alphanumeric characters"
            valid = false
        } else {
            input_password?.error = null
        }

        return valid
    }

    companion object {
        private val TAG = "LoginActivity"
        private val REQUEST_SIGNUP = 0
    }
}
