package com.ztianzeng.tree;

import com.ztianzeng.tree.entity.MpttModel;
import com.ztianzeng.tree.exception.MqttException;
import com.ztianzeng.tree.repo.MqttModelRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

    @Test
    public void should_set_root_when_there_is_none() {

        MpttModel root = new MpttModel("root");
        repo.addRoot(root);

        assertThat(root.getLft(), equalTo(1L));
        assertThat(root.getRgt(), equalTo(2L));
        assertThat(root.getLevel(), equalTo(0L));
    }

    @Test
    public void fails_to_set_as_root_when_root_already_exists() {

        MpttModel root = new MpttModel("root");
        repo.addRoot(root);

        try {
            MpttModel root2 = new MpttModel("root");
            repo.addRoot(root2);
        } catch (MqttException mq) {

        }

    }

    @Test
    public void add_first_child_to_root() {
        MpttModel root = new MpttModel("root");
        MpttModel child = new MpttModel("child");

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
}