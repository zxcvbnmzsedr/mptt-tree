/**
 * Copyright  2017 - 2020 Cnabc. All Rights Reserved.
 */

package com.ztianzeng.tree.repo;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ztianzeng.tree.entity.MpttModel;
import com.ztianzeng.tree.mapper.MpttModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Resource tree repo.
 */
@Repository
public class MqttModelRepo extends ServiceImpl<MpttModelMapper, MpttModel> {

    private MpttModelMapper mapper;

    @Autowired
    public MqttModelRepo(MpttModelMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Get resource tree
     *
     * @param id the id
     * @return the resource tree do
     */
    public MpttModel get(Long id) {
        return getById(id);
    }

    /**
     * 新增根节点
     *
     * @param add 根节点
     */
    @Transactional(rollbackFor = Exception.class)
    public void addRoot(MpttModel add) {
        setRoot(add, add.getNodeId());
    }

    /**
     * 删除子节点
     *
     * @param childId the child id
     */
    @Transactional(rollbackFor = Exception.class)
    public void remove(Long childId) {
        //判断部门下面是否还有子部门，有则不能删除
        MpttModel child = get(childId);
        Set<MpttModel> childSubTree = mapper.findSubTree(child);


        String nodeId = child.getNodeId();
        Long decrement = child.getRgt() - child.getLft() + 1;


        decrementEntitiesLftWith(
                mapper.findEntitiesWhichLftIsGreaterThan("", nodeId, child.getRgt()), decrement);
        decrementEntitiesRgtWith(
                mapper.findEntitiesWhichRgtIsGreaterThan("", nodeId, child.getRgt()), decrement);

        resetEntities(childSubTree);

        removeByIds(childSubTree.stream().map(MpttModel::getId).collect(Collectors.toList()));


    }

    /**
     * @param entities 重制
     */
    private void resetEntities(Set<MpttModel> entities) {
        for (MpttModel entity : entities) {
            entity.setLft(0L);
            entity.setRgt(0L);
            entity.setNodeId(null);
        }
        updateBatchById(entities);
    }

    /**
     * decrementEntitiesLftWith
     *
     * @param entities entities
     * @param value    value
     */
    private void decrementEntitiesLftWith(Set<MpttModel> entities, Long value) {
        addValueToEntitiesLft(entities, -value);
        if (!CollectionUtils.isEmpty(entities)) {
            updateBatchById(entities);
        }
    }


    /**
     * decrementEntitiesRgtWith
     *
     * @param entities entities
     * @param value    value
     */
    private void decrementEntitiesRgtWith(Set<MpttModel> entities, Long value) {
        addValueToEntitiesRgt(entities, -value);
        if (!CollectionUtils.isEmpty(entities)) {
            updateBatchById(entities);
        }
    }

    /**
     * 新增树节点
     *
     * @param parentId           the parent id
     * @param manageDepartmentBO the manage department bo
     */
    @Transactional(rollbackFor = Exception.class)
    public void add(Long parentId, MpttModel manageDepartmentBO) {
        // 当在顶级节点下面挂资源的时候，需要查询这个顶级节点的数据的Id
        if (parentId == null || parentId == 0) {

            MpttModel one = mapper.getByParentId("", manageDepartmentBO.getNodeId(), parentId);
            parentId = one.getId();
        }
        addChild(parentId, manageDepartmentBO);
    }

    /**
     * 添加子节点
     *
     * @param parentId 父节点ID
     * @param child    子节点信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void addChild(Long parentId, MpttModel child) {
        MpttModel parent = getById(parentId);
        // 子节点的左右数据
        long childLft;
        long childRgt;
        String nodeId = parent.getNodeId();
        // 寻找右边最大的子节点
        MpttModel rightMostChild = mapper.findRightMostChild("", nodeId, parent.getRgt() - 1);

        if (rightMostChild == null) {
            childLft = parent.getLft() + 1;
            childRgt = childLft + 1;

            incrementEntitiesLft(
                    mapper.findEntitiesWithLeftGreaterThanOrEqual("", nodeId, childLft), 2L);
            incrementEntitiesRgt(
                    mapper.findEntitiesWhichRgtIsGreaterThan("", nodeId, parent.getLft()), 2L);
        } else {
            childLft = rightMostChild.getRgt() + 1;
            childRgt = childLft + 1;

            incrementEntitiesLft(
                    mapper.findEntitiesWhichLftIsGreaterThan("", nodeId, rightMostChild.getRgt()), 2L);
            incrementEntitiesRgt(
                    mapper.findEntitiesWhichRgtIsGreaterThan("", nodeId, rightMostChild.getRgt()), 2L);
        }

        child.setLft(childLft);
        child.setRgt(childRgt);
        child.setNodeId(parent.getNodeId());
        child.setParentId(parentId);

        save(child);

    }

    /**
     * incrementEntitiesLft
     *
     * @param entities entities
     * @param value    value
     */
    private void incrementEntitiesLft(Set<MpttModel> entities, long value) {
        addValueToEntitiesLft(entities, value);
        if (!CollectionUtils.isEmpty(entities)) {
            updateBatchById(entities);
        }

    }

    /**
     * incrementEntitiesRgt
     *
     * @param entities entities
     * @param value    value
     */
    private void incrementEntitiesRgt(Set<MpttModel> entities, long value) {
        addValueToEntitiesRgt(entities, value);
        if (!CollectionUtils.isEmpty(entities)) {
            updateBatchById(entities);
        }

    }

    /**
     * addValueToEntitiesRgt
     *
     * @param entities entities
     * @param value    value
     */
    private void addValueToEntitiesRgt(Set<MpttModel> entities, long value) {
        for (MpttModel entity : entities) {
            entity.setRgt(entity.getRgt() + value);
        }
    }

    /**
     * addValueToEntitiesLft
     *
     * @param entities entities
     * @param value    value
     */
    private void addValueToEntitiesLft(Set<MpttModel> entities, long value) {
        for (MpttModel entity : entities) {
            entity.setLft(entity.getLft() + value);
        }
    }

    /**
     * 设置根节点
     *
     * @param node   the node
     * @param nodeId the node id
     */
    private void setRoot(MpttModel node, String nodeId) {
        node.setLft(1L);
        node.setRgt(2L);
        node.setNodeId(nodeId);
        node.setParentId(0L);
        node.setLevel(0L);
        node.insert();
    }


    /**
     * 编辑节点信息
     *
     * @param manageDepartmentDO the manage department do
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(MpttModel manageDepartmentDO) {
        updateById(manageDepartmentDO);
    }


}