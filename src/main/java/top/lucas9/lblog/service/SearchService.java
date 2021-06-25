package top.lucas9.lblog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import top.lucas9.lblog.search.mq.ArticleMqMessage;
import top.lucas9.lblog.vo.ArticleVo;

import java.util.List;

/**
 * @author lucas
 */
public interface SearchService {

    IPage search(Integer pageNumber, Integer pageSize, String keyword);

    void initEsData(List<ArticleVo> records);

    void createOrUpdateIndex(ArticleMqMessage message);

    void removeIndex(ArticleMqMessage message);
}
