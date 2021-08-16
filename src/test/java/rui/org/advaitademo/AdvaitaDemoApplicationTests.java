package rui.org.advaitademo;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class AdvaitaDemoApplicationTests {

    @Test
    void contextLoads() {
        AdvaitaDemoApplication.main(Arrays.array());
    }

}
