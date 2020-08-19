package com.study.sentinel.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sentinel")
@Slf4j
public class SentinelController {


    //method 1:手动实现限流熔断机制,配合RuleConfig规则（config方式配置不生效，调整为增加initFlowRules()方法生效）
    @SentinelResource(value = "test")
    @RequestMapping("/test")
    public String test(){
        initFlowRules();
        Entry entry = null;
        try{
            entry = SphU.entry("test");
        }catch (BlockException e){
            log.error(">>>>>>>>>test请求量过大，请稍后重试>>>>>>>>>");
        }finally {
            if(entry != null){
                entry.exit();
            }
        }
        log.info(">>>>>>>>>test request sucess>>>>>>>>>");
        return "test request sucess";
    }

    private  void initFlowRules(){
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("test");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // Set limit QPS to 20.
        rule.setCount(1);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }

    //method 2：注解@SentinelResource实现限流熔断机制
    @SentinelResource(value = "testQps",blockHandler = "testQpsHander")
    @RequestMapping("/testQps")
    public String testQps(){
        log.info(">>>>>>>>>testQps request sucess>>>>>>>>>");
        return "testQps request sucess";
    }

    public String testQpsHander(BlockException e){
        log.error(">>>>>>>>>请求量过大，请稍后重试>>>>>>>>>");
        return "error";
    }

    //method 3：无需侵入代码实现限流熔断机制；在sentinel-dashboard服务端配置即可
    @RequestMapping("/testQpsNoInrush")
    public String testQpsNoInrush(){
        log.info(">>>>>>>>>testQpsNoInrush request sucess>>>>>>>>>");
        return "testQpsNoInrush request sucess";
    }
}
