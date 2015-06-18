package com.funshion.dobo.api.rest.schema;

import com.funshion.dobo.api.rest.AbstractPagerForm;

public class SchemaPagerForm extends AbstractPagerForm {
	
	private String topicPath;
	private String version;
	
	public SchemaPagerForm() {
		setSqlHeaderForSelect("select a.*, b.name as topic_name, b.path as topic_path ");
	}
	
	
	public String getTopicPath() {
		return topicPath;
	}
	public void setTopicPath(String topicPath) {
		this.topicPath = topicPath;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	

}
