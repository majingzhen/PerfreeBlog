package com.perfree.controller.auth.article.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@Schema(description = "文章RespVO")
@Data
@EqualsAndHashCode(callSuper = true)
public class ArticleRespVO extends ArticleBaseVO{
    @Schema(description = "id")
    private Integer id;

    @Schema(description = "评论数")
    private Integer commentCount;

    @Schema(description = "访问量")
    private Integer viewCount;

    @Schema(description = "点赞数")
    private Integer greatCount;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;

    @Schema(description = "标签")
    private List<ArticleTagRespVO> tagList;

    @Schema(description = "分类")
    private List<ArticleCategoryRespVO> categoryList;

    @Schema(description = "用户信息")
    private ArticleUserRespVO user;

}