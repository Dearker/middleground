package com.hanyi.daily.mockito;

import com.hanyi.daily.mapper.mockito.PersonDao;
import com.hanyi.daily.pojo.Person;
import com.hanyi.daily.service.PersonService;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
public class PersonServiceTest {

    private PersonDao mockDao;
    private PersonService personService;

    @Before
    public void setUp() {
        //模拟PersonDao对象
        mockDao = mock(PersonDao.class);
        when(mockDao.getPerson(1)).thenReturn(new Person(1, "Person1"));
        when(mockDao.update(isA(Person.class))).thenReturn(true);

        personService = new PersonService(mockDao);
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

        //打印 "first"
        System.out.println(mockedList.get(0));

        //这里会抛runtime exception
        System.out.println(mockedList.get(1));

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

}
