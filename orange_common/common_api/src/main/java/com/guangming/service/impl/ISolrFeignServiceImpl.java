package com.guangming.service.impl;

import com.guangming.pojo.Question;
import com.guangming.service.ISolrFeignService;
import com.guangming.utils.Result;
import com.guangming.utils.ResultEnum;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ISolrFeignServiceImpl implements FallbackFactory<ISolrFeignService> {
    @Override
    public ISolrFeignService create(Throwable throwable) {
        throwable.printStackTrace();
        return new ISolrFeignService() {
            @Override
            public Result save(List<Question> questions) {
                return serviceExcept();
            }

            @Override
            public Result query(Integer type, Question question) {
                return serviceExcept();
            }

            @Override
            public Result update(List<Question> questions) {
                return serviceExcept();
            }

            @Override
            public Result delete(List<Integer> id) {
                return serviceExcept();
            }

            @Override
            public Result indexQuery(String tm) {
                return serviceExcept();
            }

            @Override
            public Result indexUpdate(String type) {
                return serviceExcept();
            }

            @Override
            public Result indexAccount() {
                return serviceExcept();
            }
        };
    }

    private Result serviceExcept() {
        return Result.build(ResultEnum.SERVER_CONNECT_TIME_OUT);
    }
}
