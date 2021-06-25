package top.lucas9.lblog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.lucas9.lblog.entity.UserMessage;

import java.io.Serializable;

/**
 * @author lucas
 */
@ApiModel(value="UserMessageVo对象", description="")
public class UserMessageVo extends UserMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "消息发送者昵称")
    private String usernameOfSend;
    @ApiModelProperty(value = "消息接收者昵称")
    private String usernameOfReceive;
    @ApiModelProperty(value = "消息关联文章标题")
    private String articleTitle;

    public UserMessageVo() {
    }

    public UserMessageVo(String usernameOfSend, String usernameOfReceive, String articleTitle) {
        this.usernameOfSend = usernameOfSend;
        this.usernameOfReceive = usernameOfReceive;
        this.articleTitle = articleTitle;
    }

    public String getUsernameOfSend() {
        return usernameOfSend;
    }

    public void setUsernameOfSend(String usernameOfSend) {
        this.usernameOfSend = usernameOfSend;
    }

    public String getUsernameOfReceive() {
        return usernameOfReceive;
    }

    public void setUsernameOfReceive(String usernameOfReceive) {
        this.usernameOfReceive = usernameOfReceive;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }
}
