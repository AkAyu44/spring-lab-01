package kz.iitu.springlab.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(4)
public class CallTracingAspect {

    private static final ThreadLocal<Integer> DEPTH =
            ThreadLocal.withInitial(() -> 0);

    @Before("kz.iitu.springlab.aspect.Pointcuts.serviceOperation()")
    public void traceBefore(JoinPoint jp) {
        int depth = DEPTH.get();

        System.out.println(
                indent(depth) +
                        "[TRACE] -> " +
                        jp.getSignature().toShortString()
        );

        DEPTH.set(depth + 1);
    }

    @After("kz.iitu.springlab.aspect.Pointcuts.serviceOperation()")
    public void traceAfter(JoinPoint jp) {
        int depth = Math.max(DEPTH.get() - 1, 0);

        DEPTH.set(depth);

        System.out.println(
                indent(depth) +
                        "[TRACE] <- " +
                        jp.getSignature().toShortString()
        );
    }

    private String indent(int depth) {
        return "  ".repeat(depth);
    }
}