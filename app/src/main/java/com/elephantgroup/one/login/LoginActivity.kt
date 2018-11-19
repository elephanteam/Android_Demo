package com.elephantgroup.one.login

import android.content.Intent
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

import com.elephantgroup.one.MyApplication
import com.elephantgroup.one.R
import com.elephantgroup.one.base.BaseActivity
import com.elephantgroup.one.entity.UserInfoVo
import com.elephantgroup.one.net.RequestType
import com.elephantgroup.one.ui.home.MainActivity
import com.elephantgroup.one.util.Aes
import com.elephantgroup.one.util.LoadingDialog
import com.elephantgroup.one.util.SharedPrefsUtil
import com.elephantgroup.one.util.Utils

import org.json.JSONObject

import butterknife.BindView
import butterknife.OnClick
import butterknife.OnTextChanged
import okhttp3.ResponseBody

/**
 * 登陆
 * login
 */

class LoginActivity : BaseActivity<LoginContract.Presenter>(), LoginContract.View {

    @BindView(R.id.titleBack)
    lateinit var titleBack: ImageView
    @BindView(R.id.titleRightText)
    lateinit var titleRightText: TextView
    @BindView(R.id.userName)
    lateinit var userName: EditText
    @BindView(R.id.clearUserName)
    lateinit var clearUserName: ImageView
    @BindView(R.id.password)
    lateinit var password: EditText
    @BindView(R.id.isShowPass)
    lateinit var isShowPass: ImageView
    @BindView(R.id.login)
    lateinit var login: TextView

    private var isShowPassWorld: Boolean = false
    private var aeskey: String? = null

    override fun getLayoutId(): Int {
        return R.layout.login_layout
    }

    override fun createPresenter(): LoginPresenter {
        return LoginPresenter(this)
    }

    override fun initData() {
        setTitle(getString(R.string.login))
        titleBack.visibility = View.GONE
        titleRightText.visibility = View.VISIBLE
        titleRightText.text = getString(R.string.register)
    }

    override fun onResult(result: Any, message: String) {
        if (TextUtils.equals(RequestType.LOGIN, message)) {
            val body = result as ResponseBody
            try {
                val `object` = JSONObject(body.string())
                val dataObject = `object`.optJSONObject("data")
                val userInfoVo = UserInfoVo().parse(dataObject)
                var token = dataObject.optString("token")
                if (TextUtils.isEmpty(token)) {
                    LoadingDialog.close()
                    return
                }
                val tokenTmp = Aes.decode(aeskey, token)
                if (!TextUtils.isEmpty(tokenTmp)) {
                    token = tokenTmp
                }
                val info = JSONObject()
                info.put("username", userInfoVo!!.username)
                info.put("mid", userInfoVo.mid)
                info.put("localid", userInfoVo.localId)
                info.put("token", token)
                SharedPrefsUtil.write(this@LoginActivity, SharedPrefsUtil.FILE_USER, SharedPrefsUtil.KEY_LOGIN_USERINFO, info.toString())
                MyApplication.myInfo = UserInfoVo().readMyUserInfo(this@LoginActivity)
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                LoadingDialog.close()
                finish()
            } catch (e: Exception) {
                e.printStackTrace()
                LoadingDialog.close()
            }

        }
    }

    override fun onError(throwable: Throwable, message: String) {
        LoadingDialog.close()
    }

    @OnTextChanged(value = [R.id.userName], callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    fun userNameAfterTextChange() {
        checkInputContent()
    }

    @OnTextChanged(value = [R.id.password], callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    fun passwordAfterTextChange() {
        checkInputContent()
    }

    private fun checkInputContent() {
        login.isEnabled = !(TextUtils.isEmpty(userName.text.toString()) && TextUtils.isEmpty(password.text.toString()))
    }

    @OnClick(R.id.login, R.id.forgotPassword, R.id.titleRightText, R.id.clearUserName, R.id.isShowPass)
    override fun onClick(v: View) {
        when (v.id) {
            R.id.login -> {
                LoadingDialog.show(this, "")
                this.aeskey = Utils.makeRandomKey(16)
                val mUserName = userName.text.toString()
                val mPassword = password.text.toString()
                mPresenter.login(mUserName, mPassword, aeskey, RequestType.LOGIN)
            }
            R.id.titleRightText -> {
            }
            R.id.forgotPassword -> {
            }
            R.id.clearUserName -> userName.setText("")
            R.id.isShowPass -> {
                isShowPassWorld = !isShowPassWorld
                if (isShowPassWorld) { /* Set the EditText content is visible */
                    password.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    isShowPass.setImageResource(R.mipmap.icon_eye_open)
                } else {/* The content of the EditText set as hidden*/
                    password.transformationMethod = PasswordTransformationMethod.getInstance()
                    isShowPass.setImageResource(R.mipmap.icon_eye_close)
                }
            }
            else -> super.onClick(v)
        }
    }

}
