package com.funshion.dobo.base.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.funshion.dobo.api.rest.topic.TopicPagerForm;
import com.funshion.dobo.base.dao.entity.Topic;

public interface TopicMapper {

	public List<Topic> findTopicListAutoPaging(@Param(value="pagerForm") TopicPagerForm pagerForm);
	
	public List<Topic> findSimpleTopicListAutoPaging(@Param(value="pagerForm") TopicPagerForm pagerForm);

	public void addTopic(@Param(value="topic") Topic topic);

	public Topic getTopicByName(@Param(value="name") String name);

	public List<Topic> getAllTopics();

	public Topic getTopicById(@Param(value="id") long topicId);

	public List<Topic> getNoSchemaTopics();

	@Delete("delete from topic where id = #{topicId}")
	public void delTopic(@Param(value="topicId") long topicId);

	@Update("update topic set status = #{status} where name = #{topicName} ")
	public void updateTopicStatus(@Param(value="topicName") String topicName, @Param(value="status") int status);

	public void delSchema(long topicId);

	@Update("update topic set latest_version = #{latestVersion} where id = #{id}")
	public void updateLatestVersion(@Param(value="id")long id, @Param(value="latestVersion")int latestVersion);

	public void update(@Param(value="topic") Topic topic);

}
