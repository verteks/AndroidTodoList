package course.labs.todomanager;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ToDoListAdapter extends BaseAdapter {

	private final List<ToDoItem> mItems = new ArrayList<ToDoItem>();
	private final Context mContext;

	private static final String TAG = "Lab-UserInterface";

	public ToDoListAdapter(Context context) {

		mContext = context;

	}

	// Добавляем  ToDoItem в адаптер
	// Уведомляем обсерверов, что данные изменились

	public void add(ToDoItem item) {

		mItems.add(item);
		notifyDataSetChanged();

	}

	// Очищаем список адаптеров от всех элементов.

	public void clear() {

		mItems.clear();
		notifyDataSetChanged();

	}

	// Возвращает число элементов ToDoItems

	@Override
	public int getCount() {

		return mItems.size();

	}

	// Возвращает элемент ToDoItem в выбранной позиции

	@Override
	public Object getItem(int pos) {

		return mItems.get(pos);

	}

	// Получает ID для ToDoItem
	// В данном случае это всего лишь позиция

	@Override
	public long getItemId(int pos) {

		return pos;

	}

	// Создайте View для ToDoItem в определенной позиции
	// Не забудьте проверить, содержит ли выделенное convertView уже созданное  View
	// перед созданием нового View
	// Рассмотрите использование паттерна ViewHolder чтобы сделать скроллинг более эффективным.
	// См: http://developer.android.com/training/improving-layouts/smooth-scrolling.html
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// TODO - Получите текущий ToDoItem
		final ToDoItem item = (ToDoItem) this.getItem(position);
		//final ToDoItem toDoItem = null;


		// TODO - Заполните View для данного ToDoItem из todo_item.xml
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View itemLayout = inflater.inflate(R.layout.todo_item, parent, false);
		//RelativeLayout itemLayout = null;


		// Заполните специфичные данные ToDoItem
		// Помните, что данные, которые появляются в этом View
		// соответствуют пользовательскому интерфейсу, описанному
		// в файле макета

		// TODO - Отобразите Title В TextView
		final TextView titleView = (TextView) itemLayout.findViewById(R.id.titleView);
		titleView.setText(item.getTitle());

        final RelativeLayout relativeLayout = (RelativeLayout) itemLayout.findViewById(R.id.RelativeLayout1);
//        relativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent data = new Intent(mContext.getApplicationContext(),AddToDoActivity.class);
//                ToDoItem.packageIntent(data, item.getTitle(), item.getPriority(), item.getStatus(),
//                        item.getDate()+"");
//                mContext.startActivity(data);
//            }
//        });

		// TODO - Установите CheckBox статуса
		final CheckBox statusView = (CheckBox) itemLayout.findViewById(R.id.statusCheckBox);
		if (item.getStatus()== ToDoItem.Status.DONE) {
			statusView.setChecked(true);
            relativeLayout.setBackgroundColor(Color.parseColor("#FF669900"));
		}else{
			statusView.setChecked(false);
            relativeLayout.setBackgroundColor(0);
		}


		// TODO - Обязательно нужно установить OnCheckedChangeListener,
		// который вызывается когда пользователь переключает чекбокс статуса

		statusView.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							item.setStatus(ToDoItem.Status.DONE);
                            relativeLayout.setBackgroundColor(Color.parseColor("#FF669900"));
						}else{
							item.setStatus(ToDoItem.Status.NOTDONE);
                            relativeLayout.setBackgroundColor(0);
						}
                        
					}
				});

		// TODO - Отобразить Приоритет в TextView
		final TextView priorityView = (TextView) itemLayout.findViewById(R.id.priorityView);
        priorityView.setText(item.getPriority().toString());
//        switch (item.getPriority()){
//            case LOW:priorityView.se`
//        }



		// TODO - Отобразить Время и Дату.
		// Подсказка - используйте ToDoItem.FORMAT.format(toDoItem.getDate()) для получения даты и строки
		final TextView dateView = (TextView) itemLayout.findViewById(R.id.dateView);
        dateView.setText(ToDoItem.FORMAT.format(item.getDate()));

		// Возвращает View которое только что создали
		return itemLayout;



	}
}
