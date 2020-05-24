package com.hanyi.daily.mockito;

import com.hanyi.daily.mapper.mockito.PersonDao;
import com.hanyi.daily.pojo.Person;
import com.hanyi.daily.service.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;

/**
 * <p>
 * https://blog.csdn.net/shensky711/article/details/52771493
 * https://blog.csdn.net/ityqing/article/details/81280998
 * </p>
 *
 * @author wenchangwei@wistronits.com
 * @since 15:31 2020/5/21
 */
@RunWith(MockitoJUnitRunner.class)
public class PersonServiceTest {

    /**
     * mock出来的为代理对象，不会调用真实对象的方法
     */
    @Mock
    private PersonDao mockDao;

    /**
     * InjectMocks出来的为真实的对象，会调用其真实的方法
     */
    @InjectMocks
    private PersonService personService;

    @Before
    public void setup() {
        //模拟PersonDao对象
        when(mockDao.getPerson(1)).thenReturn(new Person(1, "Person1"));
        when(mockDao.update(isA(Person.class))).thenReturn(true);
    }

    @Test
    public void testUpdate() {
        boolean result = personService.update(1, "new name");
        assertTrue("must true", result);
        //验证是否执行过一次getPerson(1)
        verify(mockDao, times(1)).getPerson(eq(1));
        //验证是否执行过一次update
        verify(mockDao, times(1)).update(isA(Person.class));
    }

    @Test
    public void testUpdateNotFind() {
        boolean result = personService.update(2, "new name");
        assertFalse("must true", result);
        //验证是否执行过一次getPerson(1)
        verify(mockDao, times(1)).getPerson(eq(1));
        //验证是否执行过一次update
        verify(mockDao, never()).update(isA(Person.class));
    }

    /**
     * 验证行为
     */
    @Test
    public void testVerify() {
        //mock creation
        List mockedList = mock(List.class);

        //using mock object
        mockedList.add("one");
        mockedList.add("two");
        mockedList.add("two");
        mockedList.clear();

        //verification
        //验证是否调用过一次 mockedList.add("one")方法，若不是（0次或者大于一次），测试将不通过
        verify(mockedList).add("one");
        //验证调用过2次 mockedList.add("two")方法，若不是，测试将不通过
        verify(mockedList, times(2)).add("two");
        //验证是否调用过一次 mockedList.clear()方法，若没有（0次或者大于一次），测试将不通过
        verify(mockedList).clear();
    }

    /**
     * Stubbing
     */
    @Test
    public void testStubbing() {
        //你可以mock具体的类，而不仅仅是接口
        LinkedList mockedList = mock(LinkedList.class);

        //设置桩
        when(mockedList.get(0)).thenReturn("first");
        when(mockedList.get(1)).thenThrow(new RuntimeException());
        doThrow(RuntimeException.class).when(mockedList).clear();
        doNothing().when(mockedList).clear();

        //assertThat(mockedList.get(0),equalTo("first"));
        try {
            //这里会抛runtime exception
            System.out.println(mockedList.get(1));
            mockedList.clear();
            fail();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //打印 "first"
        System.out.println(mockedList.get(0));

        //这里会打印 "null" 因为 get(999) 没有设置
        System.out.println(mockedList.get(999));

        verify(mockedList).get(0);
    }

    @Test
    public void testArgumentMatcher() {
        LinkedList mockedList = mock(LinkedList.class);
        //用内置的参数匹配器来stub
        when(mockedList.get(anyInt())).thenReturn("element");

        //打印 "element"
        System.out.println(mockedList.get(999));

        //你也可以用参数匹配器来验证，此处测试通过
        verify(mockedList).get(anyInt());
        //此处测试将不通过，因为没调用get(33)
        verify(mockedList).get(eq(33));
    }

    @Test
    public void answerTest(){

        List mockList = mock(List.class);

        assertThat(mockList.get(1),nullValue());

        when(mockList.get(anyInt())).thenAnswer((Answer<String>) invocationOnMock -> {
            Integer argumentAt = invocationOnMock.getArgumentAt(0, Integer.class);
            return String.valueOf(argumentAt * 10);
        });

        assertThat(mockList.get(0),equalTo("0"));
        assertThat(mockList.get(999),equalTo("9990"));
    }

    @Test
    public void compareTest(){

        int i = 10;

        assertThat(i,equalTo(10));
        assertThat(i,not(equalTo(20)));
        assertThat(i,is(10));
        assertThat(i,is(not(20)));

        double price = 23.45;

        assertThat(price,either(equalTo(23.45)).or(equalTo(23.54)));
        assertThat(price,both(equalTo(23.45)).and(not(equalTo(23.54))));

        assertThat(price,anyOf(is(23.54),is(23.45),is(25.34)));
        assertThat(price,allOf(is(23.45),not(is(25.23))));
    }

}
