package com.hanyi.demo.controller;

import com.hanyi.common.model.response.CommonCode;
import com.hanyi.common.model.response.QueryResponseResult;
import com.hanyi.demo.component.PersonService;
import com.hanyi.demo.entity.Address;
import com.hanyi.demo.entity.Person;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @ClassName: middleground com.hanyi.demo.controller MongoController
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2019-11-09 12:24
 * @Version: 1.0
 */
@RestController
@RequestMapping("/mongo")
public class MongoController {

    @Autowired
    private PersonService personService;

    @GetMapping("/test1")
    public QueryResponseResult testSave(){
        Person person = new Person(ObjectId.get(), "刘德华", 50,new Address("人民路", "香港市", "666666"));
        return new QueryResponseResult(CommonCode.SUCCESS,this.personService.savePerson(person));
    }

    @GetMapping("/test2")
    public QueryResponseResult testQueryPersonListByName(){
        return new QueryResponseResult(CommonCode.SUCCESS,this.personService.queryPersonListByName("刘德华"));
    }

    @GetMapping("/test3")
    public QueryResponseResult testQueryPagePersonList(){
        return new QueryResponseResult(CommonCode.SUCCESS,this.personService.queryPagePersonList(1, 10));
    }

    @GetMapping("/test4")
    public QueryResponseResult testUpdatae(){
        Person person = new Person();
        person.setId(new ObjectId("5dc640cb056bad2d95313728"));
        person.setAge(23);
        return new QueryResponseResult(CommonCode.SUCCESS,this.personService.update(person));
    }

    @GetMapping("/test5")
    public QueryResponseResult testDelete(){
        return new QueryResponseResult(CommonCode.SUCCESS,this.personService.deleteById("5dc640cb056bad2d95313728"));
    }

}
