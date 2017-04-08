package com.hippolee.app.module;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.hippolee.app.manager.ApplicationManager;
import com.hippolee.app.manager.TaskDataManager;
import com.hippolee.app.manager.TaskDataManager.ITaskDataListener;
import com.hippolee.app.vo.TaskVO;

public class TaskTableModule implements IApplicationModule, ITaskDataListener {

	private static final String[] TABLE_COLUMN_NAMES = new String[] { TaskVO.PROPERTY_NAME_TASK_ID,
			TaskVO.PROPERTY_NAME_PRODUCT_VERSION, TaskVO.PROPERTY_NAME_PRODUCT_MANAGER,
			TaskVO.PROPERTY_NAME_CLIENT_TYPE, TaskVO.PROPERTY_NAME_DESCRIPTION, TaskVO.PROPERTY_NAME_DEVELOPER };

	@Override
	public void initModule() {
		TaskDataManager.getInstance().addTaskDataListener(this);
	}

	@Override
	public void taskDataLoaded() {
		DefaultTableModel tableModel = new DefaultTableModel();
		tableModel.setDataVector(TaskDataManager.getInstance().getTaskData(), TABLE_COLUMN_NAMES);

		JTable table = new JTable(tableModel);
		JFrame frame = ApplicationManager.getInstance().getMainFrame();
		frame.add(table);
		frame.validate();
	}

}
