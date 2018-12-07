package io.pivotal.demo.pcc.server.function;

import org.apache.geode.cache.execute.Function;
import org.apache.geode.cache.execute.FunctionContext;
import org.apache.geode.cache.execute.FunctionException;
import org.apache.geode.cache.execute.RegionFunctionContext;

import java.util.Set;

public interface DataAwareFunction extends Function {

    boolean validateFilters(Set<?> filters);

    boolean validateRequest(Object request);

    void process(RegionFunctionContext regionFunctionContext);

    @Override
    default void execute(FunctionContext functionContext) {
        if (!(functionContext instanceof RegionFunctionContext)) {
            throw new FunctionException(
                    "This is a data aware function, and has to be called using FunctionService.onRegion.");
        }
        RegionFunctionContext regionFunctionContext = (RegionFunctionContext) functionContext;
        Set<?> filters = regionFunctionContext.getFilter();
        Object request = regionFunctionContext.getArguments();
        if (validateFilters(filters) && validateRequest(request)) {
            process(regionFunctionContext);
        }
    }

    @Override
    default String getId() {
        return this.getClass().getSimpleName();
    }

    @Override
    default boolean hasResult() {
        return true;
    }

    @Override
    default boolean isHA() {
        return true;
    }

    @Override
    default boolean optimizeForWrite() {
        return true;
    }

}
