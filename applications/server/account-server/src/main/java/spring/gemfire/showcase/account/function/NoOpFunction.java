package spring.gemfire.showcase.account.function;

import nyla.solutions.core.util.Debugger;
import org.apache.geode.cache.execute.Function;
import org.apache.geode.cache.execute.FunctionContext;
import org.apache.logging.log4j.LogManager;

public class NoOpFunction implements Function<Object[]> {
    @Override
    public void execute(FunctionContext<Object[]> functionContext) {
        LogManager.getLogger(NoOpFunction.class).info("NO OPERATION {}", Debugger.toString(functionContext.getArguments()));
    }

    @Override
    public boolean hasResult() {
        return false;
    }

    @Override
    public boolean isHA() {
        return false;
    }
}
