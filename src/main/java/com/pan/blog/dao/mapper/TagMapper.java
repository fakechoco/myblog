package com.pan.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pan.blog.dao.pojo.Tag;

import java.util.List;

public interface TagMapper extends BaseMapper<Tag> {
    /**
     * 根据id查询标签列表
     * @param articleId
     * @return
     */
    List<Tag> findTagsByArticleId(Long articleId);

    List<Long> findHotsTagIds(int limit);
    List<Tag> findTagsByTagIds( List<Long> tagIds);


}
