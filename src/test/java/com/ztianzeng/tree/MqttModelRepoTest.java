package com.ztianzeng.tree;

import com.ztianzeng.tree.entity.MpttModel;
import com.ztianzeng.tree.exception.MqttException;
import com.ztianzeng.tree.repo.MqttModelRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author zhaotianzeng
 * @version V1.0
 * @date 2019-07-12 15:38
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MqttModelRepoTest {


    @Autowired
    public MqttModelRepo repo;

    /**
     * 创建根节点
     */
    @Test
    public void should_set_root_when_there_is_none() {
        String rootS = getRandomRoot();
        MpttModel root = new MpttModel(rootS);
        repo.addRoot(root);

        assertThat(root.getLft(), equalTo(1L));
        assertThat(root.getRgt(), equalTo(2L));
        assertThat(root.getLevel(), equalTo(0L));
    }

    /**
     * 不允许创建多个相同的根节点
     */
    @Test
    public void fails_to_set_as_root_when_root_already_exists() {

        String rootS = getRandomRoot();
        MpttModel root = new MpttModel(rootS);
        repo.addRoot(root);

        try {
            MpttModel root2 = new MpttModel(rootS);
            repo.addRoot(root2);
        } catch (MqttException mq) {

        }

    }

    /**
     * 创建根节点之后添加子节点
     */
    @Test
    public void add_first_child_to_root() {
        String rootS = getRandomRoot();
        MpttModel root = new MpttModel(rootS);
        MpttModel child = new MpttModel(rootS);

        repo.addRoot(root);
        repo.addChild(root.getId(), child);

        Long id = root.getId();
        root = repo.get(id);
        assertThat(root.getLft(), equalTo(1L));
        assertThat(root.getRgt(), equalTo(4L));

        assertThat(child.getLft(), equalTo(2L));
        assertThat(child.getRgt(), equalTo(3L));
        assertThat(child.getLevel(), equalTo(1L));
    }

    /**
     * 添加多个子节点
     */
    @Test
    public void should_add_child_to_child_to_root() {
        String rootS = getRandomRoot();
        MpttModel root = new MpttModel(rootS);
        MpttModel level1Child = new MpttModel(rootS);
        MpttModel level2Child = new MpttModel(rootS);

        repo.addRoot(root);
        repo.addChild(root.getId(), level1Child);
        repo.addChild(level1Child.getId(), level2Child);

        root = repo.get(root.getId());
        assertThat(root.getLft(), equalTo(1L));
        assertThat(root.getRgt(), equalTo(6L));
        assertThat(root.getLevel(), equalTo(0L));

        level1Child = repo.get(level1Child.getId());
        assertThat(level1Child.getLft(), equalTo(2L));
        assertThat(level1Child.getRgt(), equalTo(5L));
        assertThat(level1Child.getLevel(), equalTo(1L));

        level2Child = repo.get(level2Child.getId());
        assertThat(level2Child.getLft(), equalTo(3L));
        assertThat(level2Child.getRgt(), equalTo(4L));
        assertThat(level2Child.getLevel(), equalTo(2L));

    }


    /**
     * 删除节点
     */
    @Test
    public void should_remove_the_only_child() {
        String rootS = getRandomRoot();
        MpttModel root = new MpttModel(rootS);
        MpttModel child = new MpttModel(rootS);

        repo.addRoot(root);
        repo.addChild(root.getId(), child);
        repo.remove(child.getId());

        root = repo.get(root.getId());
        assertThat(root.getLft(), equalTo(1L));
        assertThat(root.getRgt(), equalTo(2L));
        assertThat(root.getLevel(), equalTo(0L));

    }

    private String getRandomRoot() {
        return UUID.randomUUID().toString();
    }
}