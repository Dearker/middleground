package com.hanyi.hikari.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hanyi.hikari.common.thread.QueryCountTask;
import com.hanyi.hikari.dao.BookDao;
import com.hanyi.hikari.pojo.Book;
import com.hanyi.hikari.request.BookQueryPageParam;
import com.hanyi.hikari.request.BookQueryParam;
import com.hanyi.hikari.service.BookService;
import com.hanyi.hikari.vo.BookPageVo;
import com.hanyi.hikari.vo.BookVo;
import com.hanyi.hikari.vo.QueryStats;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 书本逻辑层
 * </p>
 *
 * @author wenchangwei
 * @since 9:56 下午 2020/6/8
 */
@Slf4j
@Service
public class BookServiceImpl extends ServiceImpl<BookDao, Book> implements BookService {

    private static final ThreadPoolExecutor THREADPOOLEXECUTOR = ThreadUtil.newExecutor(Byte.SIZE, Byte.SIZE);

    @Resource
    private BookDao bookDao;

    @Override
    public List<Book> findBookList() {
        Book book = bookDao.selectById(1);
        return CollUtil.emptyIfNull(Collections.singletonList(book));
    }

    /**
     * 统计查询
     *
     * @return 返回查询的结果
     */
    @Override
    public QueryStats queryCount() {
        TimeInterval timer = DateUtil.timer();
        QueryWrapper<Book> wrapper = new QueryWrapper<>();
        //指定需要查询的字段
        wrapper.lambda().select(Book::getId, Book::getBookType, Book::getBookName);
        //构建查询条件
        wrapper.lambda().ge(Book::getBookType, 0);
        wrapper.lambda().le(Book::getBookType, 1);
        Integer integer = bookDao.selectCount(wrapper);

        return new QueryStats(integer, timer.intervalRestart());
    }

    /**
     * 多线程统计
     *
     * @return 返回查询的结果
     */
    @Override
    public QueryStats queryThreadCount() {
        TimeInterval timer = DateUtil.timer();

        List<QueryCountTask> queryCountTaskList = new ArrayList<>();
        LambdaQueryWrapper<Book> lambdaQueryWrapper;
        for (int i = 0; i < Short.BYTES; i++) {
            lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Book::getBookType, i);
            queryCountTaskList.add(new QueryCountTask(lambdaQueryWrapper, bookDao));
        }

        Integer count = 0;
        try {
            List<Future<Integer>> futureList = THREADPOOLEXECUTOR.invokeAll(queryCountTaskList);
            for (Future<Integer> integerFuture : futureList) {
                count += integerFuture.get();
            }
        } catch (Exception e) {
            log.error("queryThreadCount have exception is : {}", e.toString());
        }

        return new QueryStats(count, timer.intervalRestart());
    }

    /**
     * 分页查询书籍集合
     *
     * @param bookQueryPageParam 查询对象
     * @return 返回书籍集合
     */
    @Override
    public BookPageVo findBookListByPage(BookQueryPageParam bookQueryPageParam) {
        //构建查询条件
        LambdaQueryWrapper<Book> bookLambdaQueryWrapper = new LambdaQueryWrapper<>();
        Integer bookType = bookQueryPageParam.getBookType();

        Set<String> fieldSet = Stream.of(BookVo.class.getDeclaredFields()).map(s -> StrUtil.toUnderlineCase(s.getName())).collect(Collectors.toSet());

        bookLambdaQueryWrapper.select(Book.class,s -> fieldSet.contains(s.getColumn()))
                .eq(Objects.nonNull(bookType), Book::getBookType, bookType);

        //构建分页查询对象
        Page<Book> queryPage = new Page<>(bookQueryPageParam.getCurrentPage(), bookQueryPageParam.getPageSize());
        //分页查询
        IPage<Book> bookPage = bookDao.selectPage(queryPage, bookLambdaQueryWrapper);
        return new BookPageVo(bookPage.getTotal(), bookPage.getRecords());
    }

    /**
     * 根据条件查询书籍集合
     *
     * @param bookQueryParam 查询参数
     * @return 返回书籍集合
     */
    @Override
    public List<Book> findBookListByCondition(BookQueryParam bookQueryParam) {
        LambdaQueryWrapper<Book> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //查询条件
        lambdaQueryWrapper.ne(Book::getId, bookQueryParam.getId());
        // AND ( (a.`book_name` = 'jack' AND a.book_type = 1) OR (a.book_title = '13888888888' OR a.book_total = 2) )
        //多条件查询
        lambdaQueryWrapper.and(i -> (i.and(j -> j.eq(Book::getBookName, bookQueryParam.getBookName())
                .eq(Book::getBookType, bookQueryParam.getBookType())))
                .or(j -> j.eq(Book::getBookTitle, bookQueryParam.getBookTitle())
                        .eq(Book::getBookTotal, bookQueryParam.getBookTitle())));

        return baseMapper.selectList(lambdaQueryWrapper);
    }

    /**
     * 分页查询类型数据
     *
     * @param bookQueryPageParam 查询条件
     * @return 返回分页结果
     */
    @Override
    public BookPageVo selectBookPage(BookQueryPageParam bookQueryPageParam) {
        //构建分页查询对象
        Page<Book> queryPage = new Page<>(bookQueryPageParam.getCurrentPage(), bookQueryPageParam.getPageSize());
        IPage<Book> bookPage = baseMapper.selectBookPage(queryPage, bookQueryPageParam.getBookType());

        return new BookPageVo(bookPage.getTotal(), bookPage.getRecords());
    }

}
