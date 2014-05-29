package cn.com.datateller;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import cn.com.datateller.utils.DateUtils;
import cn.com.datateller.utils.DialogHelper;
import cn.com.datateller.utils.SharedPreferencesUtils;

public class UpdateBabyBirthdayActivity extends Activity {

	private static final String TAG = "IndexActivity";
	private EditText birthdayEditText;
	private Button confirmButton;
	private int mYear;
	private int mMonth;
	private int mDay;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_baby_birthday);
		birthdayEditText = (EditText) findViewById(R.id.birthday_edittext);
		confirmButton = (Button) findViewById(R.id.confirm_button);

		// 关闭默认的输入法
		birthdayEditText.setInputType(InputType.TYPE_NULL);
		// 初始化日期
		mYear = DateUtils.getCurrentYear();
		mMonth = DateUtils.getCurrentMonth();
		mDay = DateUtils.getCurrentDayOfMonth();

		confirmButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (birthdayEditText.getText() == null) {
					DialogHelper.showDialog(UpdateBabyBirthdayActivity.this,
							"请您填写宝宝的出生日期");
					return;
				}
				String dateString = birthdayEditText.getText().toString();
				String year = dateString.substring(0, 4);
				String month = dateString.substring(5, 7);
				String day = dateString.substring(8, 10);
				String formatDateString = year + "-" + month + "-" + day;
				SharedPreferencesUtils.saveDateInfor(
						UpdateBabyBirthdayActivity.this, formatDateString);
				Intent intent = new Intent();
				intent.putExtra("formatDateString", formatDateString);
				intent.setClass(UpdateBabyBirthdayActivity.this,
						MainActivity.class);
				startActivity(intent);
			}
		});

		birthdayEditText.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				showDialog(0);
			}
		});

		birthdayEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus == true) {
					hideIM(v);
					showDialog(0);
				}
			}
		});
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			String mm;
			String dd;
			if (monthOfYear <= 9) {
				mMonth = monthOfYear + 1;
				mm = "0" + mMonth;
			} else {
				mMonth = monthOfYear + 1;
				mm = String.valueOf(mMonth);
			}
			if (dayOfMonth <= 9) {
				mDay = dayOfMonth;
				dd = "0" + mDay;
			} else {
				mDay = dayOfMonth;
				dd = String.valueOf(mDay);
			}
			mDay = dayOfMonth;
			birthdayEditText.setText(String.valueOf(mYear) + "年" + mm + "月"
					+ dd + "日");
		}
	};

	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case 0:
			// 将默认的日期传入日期控件中
			Log.d(TAG, String.valueOf(id));
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		case 1:
			Log.d(TAG, String.valueOf(id));
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		}
		return null;
	}

	// 隐藏手机键盘
	private void hideIM(View edt) {
		try {
			InputMethodManager im = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
			IBinder windowToken = edt.getWindowToken();
			if (windowToken != null) {
				im.hideSoftInputFromWindow(windowToken, 0);
			}
		} catch (Exception e) {

		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.update_baby_birthday, menu);
		return true;
	}

}
