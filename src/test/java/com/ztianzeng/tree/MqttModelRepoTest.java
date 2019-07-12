package com.ztianzeng.tree;

import com.ztianzeng.tree.entity.MpttModel;
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

        assertThat(root.getLft(), equalTo(1l));
        assertThat(root.getRgt(), equalTo(2l));
        assertThat(root.getLevel(), equalTo(0L));
    }
}