package chat_server.hjs.Infrastructure.po;

import java.util.Date;
import java.io.Serializable;

/**
 * (UserAccount)实体类
 *
 * @author makejava
 * @since 2024-09-11 11:34:31
 */
public class UserAccount implements Serializable {
    private static final long serialVersionUID = 914202055599001185L;
    /**
     * 自增ID
     */
    private Long id;
    /**
     * 用户ID；这里用的是微信ID作为唯一ID，你也可以给用户创建唯一ID，之后绑定微信ID
     */
    private String openid;
    /**
     * 总量额度；分配的总使用次数
     */
    private Integer totalQuota;
    /**
     * 剩余额度；剩余的可使用次数
     */
    private Integer surplusQuota;
    /**
     * 可用模型；gpt-3.5-turbo,gpt-3.5-turbo-16k,gpt-4,gpt-4-32k
     */
    private String modelTypes;
    /**
     * 账户状态；0-可用、1-冻结
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Integer getTotalQuota() {
        return totalQuota;
    }

    public void setTotalQuota(Integer totalQuota) {
        this.totalQuota = totalQuota;
    }

    public Integer getSurplusQuota() {
        return surplusQuota;
    }

    public void setSurplusQuota(Integer surplusQuota) {
        this.surplusQuota = surplusQuota;
    }

    public String getModelTypes() {
        return modelTypes;
    }

    public void setModelTypes(String modelTypes) {
        this.modelTypes = modelTypes;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}

