package course.labs.todomanager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Date;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import course.labs.todomanager.ToDoItem.Priority;
import course.labs.todomanager.ToDoItem.Status;

public class ToDoManagerActivity extends ListActivity {

	private static final int ADD_TODO_ITEM_REQUEST = 0;
	private static final String FILE_NAME = "TodoManagerActivityData.txt";
	private static final String TAG = "Lab-UserInterface";

	// ID для элментов меню
	private static final int MENU_DELETE = Menu.FIRST;
	private static final int MENU_DUMP = Menu.FIRST + 1;

	ToDoListAdapter mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Создает новый TodoListAdapter для данного ListView
		mAdapter = new ToDoListAdapter(getApplicationContext());

		// Положить разделитель между ToDoItems и FooterView
		getListView().setFooterDividersEnabled(true);

		// TODO - Наполнить футер из файла footer_view.xml
		TextView footerView = null;
		footerView = (TextView) getLayoutInflater().inflate(R.layout.footer_view,null);
		// ЗАМЕЧАНИЕ: Вы можете удалить этот блок когда вы реализуете данное задание.
		if (null == footerView) {
			return;
		}
		// TODO - Добавить footerView в ListView
		this.getListView().addFooterView(footerView);


		
        
        
		// TODO - Прикрепить Listener к FooterView
		footerView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {


				Intent goToAdd = new Intent(getApplicationContext(),AddToDoActivity.class);

				startActivityForResult(goToAdd,ADD_TODO_ITEM_REQUEST);

			}
		});

		// TODO - Прикрепить адаптер к ListView
		setListAdapter(mAdapter);
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		Log.i(TAG,"Entered onActivityResult()");

		// TODO - Проверить код результата и код запроса
		// есди пользователь отправил новый элемент
		// создайте новый ToDoItem из данных Intent-а
		// и затем добавьте его в адаптер

		if (resultCode==RESULT_OK){
			ToDoItem item = new ToDoItem(data);
			mAdapter.add(item);
		}


            
            
            
		

	}

	// Не изменяйте все что ниже

	@Override
	public void onResume() {
		super.onResume();

		// Загрузить сохраненные ToDoItems, при необходимости

		if (mAdapter.getCount() == 0)
			loadItems();
	}

	@Override
	protected void onPause() {
		super.onPause();

		// Сохранить ToDoItems

		saveItems();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		menu.add(Menu.NONE, MENU_DELETE, Menu.NONE, "Delete all");
		menu.add(Menu.NONE, MENU_DUMP, Menu.NONE, "Dump to log");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_DELETE:
			mAdapter.clear();
			return true;
		case MENU_DUMP:
			dump();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void dump() {

		for (int i = 0; i < mAdapter.getCount(); i++) {
			String data = ((ToDoItem) mAdapter.getItem(i)).toLog();
			Log.i(TAG,	"Item " + i + ": " + data.replace(ToDoItem.ITEM_SEP, ","));
		}

	}

	// Загрузка сохраненных ToDoItems
	private void loadItems() {
		BufferedReader reader = null;
		try {
			FileInputStream fis = openFileInput(FILE_NAME);
			reader = new BufferedReader(new InputStreamReader(fis));

			String title = null;
			String priority = null;
			String status = null;
			Date date = null;

			while (null != (title = reader.readLine())) {
				priority = reader.readLine();
				status = reader.readLine();
				date = ToDoItem.FORMAT.parse(reader.readLine());
				mAdapter.add(new ToDoItem(title, Priority.valueOf(priority),
						Status.valueOf(status), date));
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// Сохранение ToDoItems в файл
	private void saveItems() {
		PrintWriter writer = null;
		try {
			FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
			writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					fos)));

			for (int idx = 0; idx < mAdapter.getCount(); idx++) {

				writer.println(mAdapter.getItem(idx));

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != writer) {
				writer.close();
			}
		}
	}
}