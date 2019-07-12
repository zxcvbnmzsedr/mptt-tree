package com.ztianzeng.tree.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ztianzeng.tree.entity.MpttModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 使用SQL操作MPTT树
 *
 * @author CodeGenerator
 */
@Mapper
public interface MpttModelMapper extends BaseMapper<MpttModel> {
    /**
     * 寻找右边最大的子孩子
     *
     * @param nodeId the app id
     * @param rgt    the rgt
     * @return the resource tree do
     */
    MpttModel findRightMostChild(@Param("tableName") String tableName, @Param("nodeId") String nodeId, @Param("rgt") Long rgt);

    /**
     * 找到比这个左节点更大或者相等的节点
     *
     * @param tableName 表名
     * @param nodeId    the node id
     * @param lft       左节点
     * @return set
     */
    Set<MpttModel> findEntitiesWithLeftGreaterThanOrEqual(@Param("tableName") String tableName, @Param("nodeId") String nodeId, @Param("lft") Long lft);

    /**
     * 找到比这个右节点更大的节点
     *
     * @param tableName 表名
     * @param nodeId    the node id
     * @param rgt       the rgt
     * @return the set
     */
    Set<MpttModel> findEntitiesWhichRgtIsGreaterThan(@Param("tableName") String tableName, @Param("nodeId") String nodeId, @Param("rgt") Long rgt);

    /**
     * 找到比这个左节点更大的节点
     *
     * @param nodeId 森林ID
     * @param lft    the lft
     * @return the set
     */
    Set<MpttModel> findEntitiesWhichLftIsGreaterThan(@Param("tableName") String tableName, @Param("nodeId") String nodeId, @Param("lft") Long lft);

    /**
     * 找到子树
     *
     * @param child the child
     * @return the set
     */
    Set<MpttModel> findSubTree(@Param("tableName") String tableName, @Param("child") MpttModel child);


    /**
     * 获取直接子孩子
     *
     * @param child the child
     * @return list
     */
    List<MpttModel> findChildren(@Param("child") MpttModel child);

    /**
     * 获取夫ID和森林ID下的数据
     *
     * @param nodeId    森林ID
     * @param parentId  父ID
     * @param tableName 表名
     * @return
     */
    MpttModel getByParentId(@Param("tableName") String tableName, @Param("nodeId") String nodeId, @Param("parentId") Long parentId);
}
