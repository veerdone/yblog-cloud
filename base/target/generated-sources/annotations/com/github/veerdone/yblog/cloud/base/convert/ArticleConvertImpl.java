package com.github.veerdone.yblog.cloud.base.convert;

import com.github.veerdone.yblog.cloud.base.Dto.post.CreateArticleDto;
import com.github.veerdone.yblog.cloud.base.Dto.post.UpdateArticleDto;
import com.github.veerdone.yblog.cloud.base.Vo.ArticleDetailVo;
import com.github.veerdone.yblog.cloud.base.Vo.ArticleInfoVo;
import com.github.veerdone.yblog.cloud.base.model.ArticleInfo;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-12T17:26:52+0800",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.4 (Oracle Corporation)"
)
public class ArticleConvertImpl implements ArticleConvert {

    @Override
    public ArticleInfoVo toArticleInfoVo(ArticleInfo articleInfo) {
        if ( articleInfo == null ) {
            return null;
        }

        ArticleInfoVo articleInfoVo = new ArticleInfoVo();

        articleInfoVo.setId( articleInfo.getId() );
        articleInfoVo.setTitle( articleInfo.getTitle() );
        articleInfoVo.setDescription( articleInfo.getDescription() );
        articleInfoVo.setViews( articleInfo.getViews() );
        articleInfoVo.setCollection( articleInfo.getCollection() );
        articleInfoVo.setLikes( articleInfo.getLikes() );
        articleInfoVo.setComments( articleInfo.getComments() );
        articleInfoVo.setCoverPicture( articleInfo.getCoverPicture() );
        articleInfoVo.setCreateTime( articleInfo.getCreateTime() );
        List<Long> list = articleInfo.getLabel();
        if ( list != null ) {
            articleInfoVo.setLabel( new ArrayList<Long>( list ) );
        }
        articleInfoVo.setClassify( articleInfo.getClassify() );
        articleInfoVo.setUserId( articleInfo.getUserId() );

        return articleInfoVo;
    }

    @Override
    public ArticleInfo toArticleInfo(CreateArticleDto dto) {
        if ( dto == null ) {
            return null;
        }

        ArticleInfo articleInfo = new ArticleInfo();

        articleInfo.setTitle( dto.getTitle() );
        articleInfo.setDescription( dto.getDescription() );
        articleInfo.setCoverPicture( dto.getCoverPicture() );
        List<Long> list = dto.getLabel();
        if ( list != null ) {
            articleInfo.setLabel( new ArrayList<Long>( list ) );
        }
        articleInfo.setClassify( dto.getClassify() );
        articleInfo.setUserId( dto.getUserId() );

        return articleInfo;
    }

    @Override
    public ArticleInfo toArticleInfo(UpdateArticleDto dto) {
        if ( dto == null ) {
            return null;
        }

        ArticleInfo articleInfo = new ArticleInfo();

        articleInfo.setId( dto.getId() );
        articleInfo.setDescription( dto.getDescription() );
        articleInfo.setCoverPicture( dto.getCoverPicture() );
        List<Long> list = dto.getLabel();
        if ( list != null ) {
            articleInfo.setLabel( new ArrayList<Long>( list ) );
        }
        articleInfo.setClassify( dto.getClassify() );
        articleInfo.setUserId( dto.getUserId() );

        return articleInfo;
    }

    @Override
    public ArticleDetailVo toArticleDetailVo(ArticleInfo articleInfo) {
        if ( articleInfo == null ) {
            return null;
        }

        ArticleDetailVo articleDetailVo = new ArticleDetailVo();

        articleDetailVo.setId( articleInfo.getId() );
        articleDetailVo.setTitle( articleInfo.getTitle() );
        articleDetailVo.setViews( articleInfo.getViews() );
        articleDetailVo.setCollection( articleInfo.getCollection() );
        articleDetailVo.setLikes( articleInfo.getLikes() );
        articleDetailVo.setStatus( articleInfo.getStatus() );
        articleDetailVo.setComments( articleInfo.getComments() );
        articleDetailVo.setUserId( articleInfo.getUserId() );
        articleDetailVo.setCreateTime( articleInfo.getCreateTime() );

        return articleDetailVo;
    }
}
