package com.hanyi.web.common.component;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.io.watch.SimpleWatcher;
import cn.hutool.core.io.watch.WatchMonitor;
import cn.hutool.setting.dialect.Props;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 动态加载资源，该功能在jar包中修改文件，无法触发该事件，有待考察
 * </p>
 *
 * @author wenchangwei
 * @since 11:07 下午 2021/4/22
 */
@Slf4j
@Component
public class DynamicResourceComponent implements CommandLineRunner {

    /**
     * 文件相对路径
     */
    @Value("${relativePath}")
    private String relativePath;

    /**
     * 属性装载对象
     */
    private final Props props = new Props();

    /**
     * 资源解析对象
     */
    private final ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

    /**
     * 监听器对象
     */
    private WatchMonitor watchMonitor;

    /**
     * 初始化监听器，创建需要监听的事件类型
     */
    @PostConstruct
    public void init() {
        this.watchMonitor = WatchMonitor.create(FileUtil.file(relativePath), WatchMonitor.ENTRY_MODIFY);
    }

    /**
     * 销毁监听器
     */
    @PreDestroy
    public void destroy() {
        this.watchMonitor.close();
    }

    /**
     * 获取相对路径和属性集合
     *
     * @return 返回结果
     */
    public Map<String, Object> getFilePathAndProps() {
        Map<String, Object> objectMap = new HashMap<>(Integer.BYTES);
        objectMap.put("relativePath", FileUtil.file(relativePath));
        objectMap.put("resourcePath", ResourceUtil.getResourceObj(relativePath).getUrl());
        Resource resource = resolver.getResource(relativePath);
        try {
            objectMap.put("filePath", DateUtil.date(resource.lastModified()));
            objectMap.put("isFile", resource.isFile());
            objectMap.put("url", resource.getURI());
            objectMap.put("urL", resource.getURL());
        } catch (IOException e) {
            log.error("", e);
        }
        objectMap.put("props", props);
        return objectMap;
    }

    @Override
    public void run(String... args) throws Exception {
        //不能通过构造器加载资源，会无法找到文件路径
        cn.hutool.core.io.resource.Resource resourceObj = ResourceUtil.getResourceObj(relativePath);
        log.info("当前获取的路径：" + resourceObj.getUrl());
        props.load(resourceObj);
        //props.load(resolver.getResource(relativePath).getInputStream());

        SimpleWatcher simpleWatcher = new SimpleWatcher() {
            @SneakyThrows
            @Override
            public void onModify(WatchEvent<?> event, Path currentPath) {
                // 事件发生源是相对路径
                Path fileRelativePath = (Path) event.context();
                //处理为绝对路径

                Path filePath = currentPath.resolve(fileRelativePath);
                //绝对路径
                String absolutePath = filePath.toFile().getAbsolutePath();
                if (absolutePath.endsWith(relativePath)) {
                    props.clear();
                    props.load(resolver.getResource(relativePath).getInputStream());
                    log.info("最新的属性：{}", props.toString());
                    log.info("当前线程id：" + Thread.currentThread().getName());
                }
            }
        };
        //开启监听线程
        watchMonitor.setMaxDepth(3);
        this.watchMonitor.setWatcher(simpleWatcher).start();
    }

}
