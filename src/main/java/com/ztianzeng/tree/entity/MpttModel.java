package com.ztianzeng.tree.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * MPTT树模型
 *
 * @author zhaotianzeng
 * @version V1.0
 * @date 2019-07-12 14:37
 */
@TableName("mptt_model")
public class MpttModel extends Model<MpttModel> {
    private Long id;
    /**
     * 层级
     */
    private Long level;

    /**
     * 左节点
     */
    private Long lft;

    /**
     * 右节点
     */
    private Long rgt;


    /**
     * 父ID
     */
    private Long parentId;

    /**
     * 用于区分数据是属于哪一片森林
     * 可以作为业务类型而使用
     */
    private String nodeId;

    public MpttModel(String nodeId) {
        this.nodeId = nodeId;
    }

    public MpttModel() {
    }

    public Long getId() {
        return id;
    }

    public MpttModel setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getLevel() {
        return level;
    }

    public MpttModel setLevel(Long level) {
        this.level = level;
        return this;
    }

    public Long getLft() {
        return lft;
    }

    public MpttModel setLft(Long lft) {
        this.lft = lft;
        return this;
    }

    public Long getRgt() {
        return rgt;
    }

    public MpttModel setRgt(Long rgt) {
        this.rgt = rgt;
        return this;
    }

    public Long getParentId() {
        return parentId;
    }

    public MpttModel setParentId(Long parentId) {
        this.parentId = parentId;
        return this;
    }

    public String getNodeId() {
        return nodeId;
    }

    public MpttModel setNodeId(String nodeId) {
        this.nodeId = nodeId;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}