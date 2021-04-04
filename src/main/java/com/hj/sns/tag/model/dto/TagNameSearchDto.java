package com.hj.sns.tag.model.dto;

import lombok.Data;

@Data
public class TagNameSearchDto {
    private Long tagId;
    private String tagName;
    private Long count;

    public TagNameSearchDto(Long tagId, String tagName, Long count) {
        this.tagId = tagId;
        this.tagName = tagName;
        this.count= count;
    }

}
