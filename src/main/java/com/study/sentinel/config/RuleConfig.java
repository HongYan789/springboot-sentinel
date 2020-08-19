package com.study.sentinel.config;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * sentinel限流配置：
 * 可实现sentinel的几种限流方案：令牌桶、时间窗口、漏斗
 * 不知道为啥，配置config不生效，因此直接就在调用方法处重新实现了一套下述规则，亲测有效
 */
@Configuration
public class RuleConfig {

    @Bean
    public SentinelResourceAspect sentinelResourceAspect(){
        return new SentinelResourceAspect();
    }

    @PostConstruct
    public void initRules() throws BlockException{
        FlowRule rule = new FlowRule();
        rule.setResource("test");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);//基于QPS模式的阈值设置
        rule.setCount(1);//设置每秒最大调用次数为1次

        List<FlowRule> ruleList = new ArrayList<>();
        FlowRuleManager.loadRules(ruleList);
    }
}
