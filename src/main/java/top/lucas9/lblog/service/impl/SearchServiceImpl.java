package top.lucas9.lblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import top.lucas9.lblog.entity.Article;
import top.lucas9.lblog.search.model.ArticleDocument;
import top.lucas9.lblog.search.mq.ArticleMqMessage;
import top.lucas9.lblog.search.repository.ArticleRepository;
import top.lucas9.lblog.service.ArticleService;
import top.lucas9.lblog.service.SearchService;
import top.lucas9.lblog.utils.ModelMapperUtil;
import top.lucas9.lblog.vo.ArticleVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lucas
 */
@Slf4j
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    ArticleService articleService;

    @Override
    public IPage search(Integer pageNumber, Integer pageSize, String keyword) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        // 搜索es得到pageData
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(keyword, "title", "authorName", "categoryName");
        org.springframework.data.domain.Page<ArticleDocument> documents = articleRepository.search(multiMatchQueryBuilder, pageable);
        IPage pageData = new Page(pageNumber, pageSize, documents.getTotalElements());
        pageData.setRecords(documents.getContent());
        return pageData;
    }

    @Override
    public void initEsData(List<ArticleVo> records) {

        if(records == null || records.isEmpty()) {
            return;
        }

        List<ArticleDocument> documents = new ArrayList<>();
        for(ArticleVo vo : records) {
            ArticleDocument articleDocument = ModelMapperUtil.articleVoToArticleDocument(vo);
            documents.add(articleDocument);
        }
        articleRepository.saveAll(documents);
        return;
    }

    @Override
    public void createOrUpdateIndex(ArticleMqMessage message) {
        Long articleId = message.getArticleId();
        ArticleVo article = articleService.selectOneArticle(new QueryWrapper<Article>().eq("a.id", articleId));
        ArticleDocument articleDocument = ModelMapperUtil.articleVoToArticleDocument(article);
        articleRepository.save(articleDocument);
        log.info("es 索引更新成功！ ---> {}", articleDocument.toString());
    }

    @Override
    public void removeIndex(ArticleMqMessage message) {
        Long postId = message.getArticleId();
        articleRepository.deleteById(postId);
        log.info("es 索引删除成功！ ---> {}", message.toString());
    }
}
