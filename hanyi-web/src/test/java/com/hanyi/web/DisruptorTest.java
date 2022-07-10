package com.hanyi.web;

import com.hanyi.web.service.DisruptorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.stream.IntStream;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 2022/7/10 11:11 AM
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DisruptorTest {

    @Resource
    private DisruptorService disruptorService;

    @Test
    public void sendMessage() {
        IntStream.range(0, 100).forEach(s -> disruptorService.sendMessageModel(String.valueOf(s)));
    }

    @Test
    public void parallelMessage() {
        IntStream.range(0, 100).forEach(s -> disruptorService.sendParallelMessageModel(String.valueOf(s)));
    }

}
