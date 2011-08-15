package net.threadtxtest.service.internal.aspect;


import net.threadtxtest.service.internal.util.StringUtil;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * The aspect for logging all the incoming arguments.
 */
public final class ArgumentLoggerAspect {
    private static final Logger LOG = LoggerFactory.getLogger(ArgumentLoggerAspect.class);

    public void before(JoinPoint joinPoint) {
        if (!LOG.isTraceEnabled()) {
            // omit complex argument logging if trace if switched off
            return;
        }

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(joinPoint.getTarget().getClass().getName());
        stringBuilder.append('.');
        stringBuilder.append(joinPoint.getSignature().getName());
        stringBuilder.append('(');
        final Object[] args = joinPoint.getArgs();

        if (args != null && args.length > 0) {
            boolean next = false;
            for (final Object arg : args) {
                if (next) {
                    stringBuilder.append(", ");
                } else {
                    next = true;
                }

                try {
                    StringUtil.stringify(arg, stringBuilder);
                } catch (IOException e) {
                    LOG.error("Internal aspect error", e);
                    stringBuilder.append("<error>");
                }
            }
        }

        stringBuilder.append(')');
        LOG.trace(stringBuilder.toString());
    }
}