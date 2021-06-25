package top.lucas9.lblog.search.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.lucas9.lblog.common.Constant;
import top.lucas9.lblog.service.SearchService;

/**
 * @author lucas
 */
@Slf4j
@Component
@RabbitListener(queues = Constant.MQ_QUEUE)
public class MqMessageHandler {

    @Autowired
    SearchService searchService;

    @RabbitHandler
    public void handler(ArticleMqMessage message) {
        log.info("mq 收到一条消息： {}", message.toString());
        switch (message.getType()) {
            case Constant.MESSAGE_CREATE_OR_UPDATE:
                searchService.createOrUpdateIndex(message);
                break;
            case Constant.MESSAGE_REMOVE:
                searchService.removeIndex(message);
                break;
            default:
                log.error("没找到对应的消息类型，请注意！！ --> {}", message.toString());
                break;
        }
    }

}
