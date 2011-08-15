package net.threadtxtest.service.internal.aspect;

import net.threadtxtest.service.internal.util.StringUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

/**
 * Profiles method invocation, logs all the arguments.
 */
public final class ProfilerAspect {
    private static final Logger LOG = LoggerFactory.getLogger(ProfilerAspect.class);

    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        final long beforeStart = System.currentTimeMillis();

        final Object retVal = proceedingJoinPoint.proceed();

        if (LOG.isTraceEnabled()) {
            final long elapsed = System.currentTimeMillis() - beforeStart;
            LOG.trace(MessageFormat.format("{0}.{1} took {2} ms, retVal = {3}",
                    proceedingJoinPoint.getTarget().getClass().getName(),
                    proceedingJoinPoint.getSignature().getName(),
                    elapsed,
                    StringUtil.stringify(retVal)));
        }

        return retVal;
    }
}