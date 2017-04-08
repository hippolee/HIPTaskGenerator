package com.hippolee.app.vo;

import java.io.Serializable;

public class TaskVO implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	/** 属性名-任务编码 */
	public static final String PROPERTY_NAME_TASK_ID = "任务编码";
	/** 属性名-版本 */
	public static final String PROPERTY_NAME_PRODUCT_VERSION = "版本";
	/** 属性名-产品经理 */
	public static final String PROPERTY_NAME_PRODUCT_MANAGER = "产品经理";
	/** 属性名-客户端 */
	public static final String PROPERTY_NAME_CLIENT_TYPE = "客户端";
	/** 属性名-特性 */
	public static final String PROPERTY_NAME_DESCRIPTION = "特性";
	/** 属性名-开发 */
	public static final String PROPERTY_NAME_DEVELOPER = "开发";

	/** 任务编码 */
	private String task_id;
	/** 版本 */
	private String product_version;
	/** 产品经理 */
	private String product_manager;
	/** 客户端 */
	private String client_type;
	/** 特性 */
	private String description;
	/** 开发 */
	private String developer;

	public String getTask_id() {
		return task_id;
	}

	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}

	public String getProduct_version() {
		return product_version;
	}

	public void setProduct_version(String product_version) {
		this.product_version = product_version;
	}

	public String getProduct_manager() {
		return product_manager;
	}

	public void setProduct_manager(String product_manager) {
		this.product_manager = product_manager;
	}

	public String getClient_type() {
		return client_type;
	}

	public void setClient_type(String client_type) {
		this.client_type = client_type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDeveloper() {
		return developer;
	}

	public void setDeveloper(String developer) {
		this.developer = developer;
	}

}
