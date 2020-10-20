package com.example.sqllearning.controller.transactional;

import com.example.sqllearning.dao.RegionLv1Mapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/transactional")
@Api ("/transactional controller")
public class TransactionalController {

    @Autowired
    private RegionLv1Mapper regionLv1Mapper;

    @GetMapping ("/do-execute/{i}")
    @ApiOperation ("do execute")
    @Transactional
    public void doExecute(@PathVariable ("i") Integer i) {
        // regionLv1Mapper.executeStatement();
        int count = 1 / i;
    }
}
