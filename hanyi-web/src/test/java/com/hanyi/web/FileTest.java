package com.hanyi.web;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.io.watch.SimpleWatcher;
import cn.hutool.core.io.watch.WatchMonitor;
import cn.hutool.setting.dialect.Props;
import org.junit.Test;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;

/**
 * <p>
 * 文件测试类
 * </p>
 *
 * @author wenchangwei
 * @since 4:56 下午 2021/1/2
 */
public class FileTest {

    /**
     * FileChannel 的 transferTo 方法进行流的复制。
     * 在一些操作系统（比如高版本的 Linux 和 UNIX）上可以实现 DMA（直接内存访问），
     * 也就是数据从磁盘经过总线直接发送到目标文件，无需经过内存和 CPU 进行数据中转
     *
     * @throws IOException 异常
     */
    @Test
    public void fileChannelTest() throws IOException {
        FileChannel in = FileChannel.open(Paths.get("src.txt"), StandardOpenOption.READ);
        FileChannel out = FileChannel.open(Paths.get("dest.txt"), CREATE, WRITE);
        in.transferTo(0, in.size(), out);
    }

    /**
     * Files不能使用测试类获取文件路径
     *
     * @param args 测试
     * @throws IOException 异常
     */
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("apply.txt");
        System.out.println(path.toAbsolutePath());
        System.out.println(path.toUri());

        read(path.toAbsolutePath().toString());
        readTxt(path.toAbsolutePath().toString());

        List<String> stringList = Files.readAllLines(path);

        stringList.forEach(System.out::println);
        List<String> strings = Files.lines(path).collect(Collectors.toList());
        strings.forEach(System.out::println);
    }

    private static void read(String file) {
        // 创建字符流对象，并根据已创建的字节流对象创建字符流对象
        try (FileInputStream fis = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader raf = new BufferedReader(isr)) {

            String s;
            // 读取文件内容，并将其打印
            while ((s = raf.readLine()) != null) {
                System.out.println(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readTxt(String filePath) {
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            try (InputStreamReader isr = new InputStreamReader(new FileInputStream(file));
                 BufferedReader br = new BufferedReader(isr)) {

                String lineTxt;
                while ((lineTxt = br.readLine()) != null) {
                    System.out.println(lineTxt);
                }
            } catch (IOException e) {
                System.out.println("文件读取错误!");
            }
        } else {
            System.out.println("文件不存在!");
        }
    }

    /**
     * 文件监听事件
     */
    @Test
    public void fileListerTest() throws InterruptedException {
        String relativeFileName = "config/test.properties";
        File file = FileUtil.file(relativeFileName);

        Props props = new Props(relativeFileName);
        System.out.println(props);

        //创建需要监听的事件类型
        WatchMonitor watchMonitor = WatchMonitor.create(file, WatchMonitor.ENTRY_MODIFY);
        SimpleWatcher simpleWatcher = new SimpleWatcher() {
            @Override
            public void onModify(WatchEvent<?> event, Path currentPath) {
                // 事件发生源是相对路径
                Path fileRelativePath = (Path) event.context();
                //处理为绝对路径
                Path filePath = currentPath.resolve(fileRelativePath);
                //绝对路径
                String absolutePath = filePath.toFile().getAbsolutePath();
                if (absolutePath.endsWith(relativeFileName)) {
                    props.clear();
                    props.load(ResourceUtil.getResourceObj(absolutePath));
                    System.out.println(props);
                    System.out.println("当前线程id：" + Thread.currentThread().getName());
                }
            }
        };

        watchMonitor.setWatcher(simpleWatcher).start();
        System.out.println("外部线程id：" + Thread.currentThread().getName());
        TimeUnit.HOURS.sleep(1);
    }

    @Test
    public void loadPropsTest() throws IOException {
        String fileName = "config/test.properties";
        Props props = new Props(fileName);
        System.out.println(props);

        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
        System.out.println(resourceAsStream);
        Props properties = new Props();
        properties.load(resourceAsStream);
        System.out.println(properties);
    }

    /**
     * 如果读取的文件不在resource的根路径下，则需要加上前缀，否则无法读取文件
     */
    @Test
    public void toFileTest() {
        String fileName = "config/test.properties";
        File file = FileUtil.file(fileName);
        System.out.println(file);
        Path path = Paths.get(fileName);
        System.out.println(path.toAbsolutePath());

        String absolutePath = FileUtil.getAbsolutePath(fileName);
        System.out.println(absolutePath);

        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
        System.out.println(resourceAsStream);
    }

    /**
     * 使用缓冲区读写文件
     */
    @Test
    public void bufferFileTest() {
        long begin = System.currentTimeMillis();
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream("C:/456.png"));
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("C:/789.png"))) {
            byte[] bytes = new byte[1024];
            int i;
            while ((i = bufferedInputStream.read(bytes)) != -1) {
                bufferedOutputStream.write(bytes, 0, i);
            }
        } catch (IOException ignore) {
        }
        System.out.println("总共耗时ms" + (System.currentTimeMillis() - begin));
    }

}

