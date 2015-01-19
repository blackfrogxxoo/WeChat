package org.wxc.fuckwechat.activity;


import org.wxc.fuckwechat.R;
import org.wxc.fuckwechat.utils.LoginInUtil;
import org.wxc.fuckwechat.utils.RegisterUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends Activity implements OnClickListener{

	EditText user;
	EditText pass;
	Button register;
	Button login;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reg_or_login_layout);
		user = (EditText) findViewById(R.id.user);
		pass = (EditText) findViewById(R.id.pass);
		register = (Button) findViewById(R.id.register);
		login = (Button) findViewById(R.id.login);

		register.setOnClickListener(this);
		login.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		String user = this.user.getText().toString();
		String pass = this.pass.getText().toString();
		if (v.equals(register)) {
			
			register(user, pass);

		} else {
			loginIn(user, pass);
		}
	}

	private void loginIn(String user, String pass) {
		LoginInUtil task = new LoginInUtil(this);
		task.execute(user, pass);
	}

	private void register(String user, String pass) {
		RegisterUtil task = new RegisterUtil(this);
		task.execute(user, pass);
	}

	public void clearEditText() {
		user.setText("");
		pass.setText("");
		pass.clearFocus();
	}

}
