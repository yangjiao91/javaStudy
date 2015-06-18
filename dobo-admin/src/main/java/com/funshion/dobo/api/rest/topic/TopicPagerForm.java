package com.funshion.dobo.api.rest.topic;

import com.funshion.dobo.api.rest.AbstractPagerForm;

public class TopicPagerForm extends AbstractPagerForm {
	
	private String topicName;
	private String owner;
	
	public String getTopicName() {
		return topicName;
	}
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
	

}
