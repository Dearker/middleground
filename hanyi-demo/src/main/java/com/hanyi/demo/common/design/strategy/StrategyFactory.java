package com.hanyi.demo.common.design.strategy;

import com.hanyi.demo.common.design.strategy.impl.GoldStrategy;
import com.hanyi.demo.common.design.strategy.impl.OrdinaryStrategy;
import com.hanyi.demo.common.design.strategy.impl.PlatinumStrategy;
import com.hanyi.demo.common.design.strategy.impl.SilverStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName: middleground com.hanyi.demo.common.design.strategy StrategyFactory
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-01-21 09:45
 * @Version: 1.0
 */
public class StrategyFactory {

    private Map<Integer, Strategy> map;

    private StrategyFactory() {

        List<Strategy> strategyList = new ArrayList<>();

        strategyList.add(new OrdinaryStrategy());
        strategyList.add(new SilverStrategy());
        strategyList.add(new GoldStrategy());
        strategyList.add(new PlatinumStrategy());

        map = strategyList.stream().collect(Collectors.toMap(Strategy::getType,strategy ->strategy));
    }

    /**
     * 单例
     */
    private static class Holder {
        private static StrategyFactory instance = new StrategyFactory();
    }

    public static StrategyFactory getInstance() {
        return Holder.instance;
    }

    public Strategy get(Integer type) {
        return map.get(type);
    }

}
