package top.lucas9.lblog.search.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import top.lucas9.lblog.search.model.ArticleDocument;

/**
 * @author lucas
 */
@Repository
public interface ArticleRepository extends ElasticsearchRepository<ArticleDocument, Long> {
}
