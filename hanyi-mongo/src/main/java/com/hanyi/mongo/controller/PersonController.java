package com.hanyi.mongo.controller;

import com.hanyi.framework.model.response.ResponseResult;
import com.hanyi.mongo.pojo.many.Person;
import com.hanyi.mongo.pojo.many.incloud.Address;
import com.hanyi.mongo.service.impl.PersonService;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName: middleground com.hanyi.demo.controller MongoController
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2019-11-09 12:24
 * @Version: 1.0
 */
@RestController
@RequestMapping("/mongo")
public class PersonController {

    @Resource
    private PersonService personService;

    @GetMapping("/test1")
    public ResponseResult testSave(){
        Person person = new Person(ObjectId.get(), "刘德华", 50,new Address("人民路", "香港市", "666666"));
        return ResponseResult.success(this.personService.savePerson(person));
    }

    @GetMapping("/test2")
    public ResponseResult testQueryPersonListByName(){
        return ResponseResult.success(this.personService.queryPersonListByName("刘德华"));
    }

    @GetMapping("/test3")
    public ResponseResult testQueryPagePersonList(){
        return ResponseResult.success(this.personService.queryPagePersonList(1, 10));
    }

    @GetMapping("/test4")
    public ResponseResult testUpdatae(){
        Person person = new Person();
        person.setId(new ObjectId("5dc640cb056bad2d95313728"));
        person.setAge(23);
        return ResponseResult.success(this.personService.update(person));
    }

    @GetMapping("/test5")
    public ResponseResult testDelete(){
        return ResponseResult.success(this.personService.deleteById("5dc640cb056bad2d95313728"));
    }

}
