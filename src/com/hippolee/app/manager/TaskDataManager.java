package com.hippolee.app.manager;

import java.io.File;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import org.apache.commons.lang3.StringUtils;

import com.hippolee.app.util.POIUtil;

/**
 * 任务数据管理
 *
 * @author hippolee
 * @create 2017年2月24日
 * @version 1.0
 */
public class TaskDataManager {

	private static final String DEFAULT_XLSX_FOLDER_KEY = "DEFAULT_XLSX_FOLDER_KEY";
	private static final String DEFAULT_PPTX_FOLDER_KEY = "DEFAULT_PPTX_FOLDER_KEY";

	private static final TaskDataManager instance = new TaskDataManager();

	private TaskDataManager() {

	}

	public static TaskDataManager getInstance() {
		return instance;
	}

	/** 任务数据 */
	private Object[][] taskData = null;
	/** 监听器 */
	private Vector<ITaskDataListener> listeners = new Vector<>();

	/**
	 * 读取Excel文件
	 * 
	 * @param file
	 */
	public void readTaskData(File file) {
		this.taskData = POIUtil.readExcelByArray(file);
		this.taskDataLoaded();
	}

	/**
	 * 写入Powerpoint文件
	 * 
	 * @param folder
	 */
	public void writeTaskData(File folder) {
		POIUtil.writePowerpointByArray(folder);
	}

	/**
	 * 获得任务数据
	 * 
	 * @return
	 */
	public Object[][] getTaskData() {
		return taskData;
	}

	public Boolean importExeclFileDefault() {
		String folderPath = PropertiesManager.getInstance().getProperty(DEFAULT_XLSX_FOLDER_KEY, "");
		File folder = null;
		if (!StringUtils.isEmpty(folderPath)) {
			folder = new File(folderPath);
			if (!folder.exists() || !folder.isDirectory()) {
				folder = null;
				PropertiesManager.getInstance().removeProperty(DEFAULT_XLSX_FOLDER_KEY);
			}
		}

		File excelFile = doImportExcelFile(folder);
		if (excelFile != null) {
			folder = excelFile.getParentFile();
			PropertiesManager.getInstance().setProperty(DEFAULT_XLSX_FOLDER_KEY, folder.getAbsolutePath());
			return true;
		}
		return false;
	}

	public Boolean exportPowerpointFileDefault() {
		String folderPath = PropertiesManager.getInstance().getProperty(DEFAULT_PPTX_FOLDER_KEY, "");
		File folder = null;
		if (!StringUtils.isEmpty(folderPath)) {
			folder = new File(folderPath);
			if (!folder.exists() || !folder.isDirectory()) {
				folder = null;
				PropertiesManager.getInstance().removeProperty(DEFAULT_PPTX_FOLDER_KEY);
			}
		}

		File pptFolder = doExportPowerpointFile(folder);
		if (pptFolder != null) {
			folder = pptFolder.getParentFile();
			PropertiesManager.getInstance().setProperty(DEFAULT_PPTX_FOLDER_KEY, folder.getAbsolutePath());
			return true;
		}
		return false;
	}

	private File doImportExcelFile(File defaultFolder) {
		JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView());
		// 单选
		fileChooser.setMultiSelectionEnabled(false);
		// title
		fileChooser.setDialogTitle("选择任务Excel");
		// 默认目录
		if (defaultFolder != null) {
			fileChooser.setCurrentDirectory(defaultFolder);
		}
		// 只选择文件
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		// 不显示所有文件选项
		fileChooser.setAcceptAllFileFilterUsed(false);
		// 文件过滤
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel2007(*.xlsx)", "xlsx");
		fileChooser.addChoosableFileFilter(filter);
		// 显示对话框
		int returnVal = fileChooser.showOpenDialog(ApplicationManager.getInstance().getMainFrame());
		if (returnVal != JFileChooser.APPROVE_OPTION) {
			return null;
		}
		File selectedFile = fileChooser.getSelectedFile();
		// 读取任务数据
		TaskDataManager.getInstance().readTaskData(selectedFile);
		return selectedFile;
	}

	private File doExportPowerpointFile(File defaultFolder) {
		JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView());
		// title
		fileChooser.setDialogTitle("保存任务PPT");
		// 默认目录
		if (defaultFolder != null) {
			fileChooser.setCurrentDirectory(defaultFolder);
		}
		// 只选择文件夹
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		// 显示对话框
		int returnVal = fileChooser.showSaveDialog(ApplicationManager.getInstance().getMainFrame());
		if (returnVal != JFileChooser.APPROVE_OPTION) {
			return null;
		}

		File selectedFolder = fileChooser.getSelectedFile();
		// 导出任务数据
		TaskDataManager.getInstance().writeTaskData(selectedFolder);
		return selectedFolder;
	}

	/**
	 * 添加监听器
	 * 
	 * @param listener
	 */
	public void addTaskDataListener(ITaskDataListener listener) {
		this.listeners.add(listener);
	}

	/**
	 * 移除监听器
	 * 
	 * @param listener
	 */
	public void removeTaskDataListener(ITaskDataListener listener) {
		this.listeners.remove(listener);
	}

	private void taskDataLoaded() {
		Iterator<ITaskDataListener> it = this.listeners.iterator();
		while (it.hasNext()) {
			ITaskDataListener listener = (ITaskDataListener) it.next();
			listener.taskDataLoaded();
		}
	}

	public interface ITaskDataListener {

		public void taskDataLoaded();

	}

}
