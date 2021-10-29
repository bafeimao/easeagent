package com.megaease.easeagent.plugin.bridge;

import com.megaease.easeagent.plugin.api.context.AsyncContext;
import com.megaease.easeagent.plugin.api.context.ProgressContext;
import com.megaease.easeagent.plugin.api.trace.*;
import com.megaease.easeagent.plugin.utils.NoNull;

import java.util.function.Function;

public class NoOpTracer {
    public static final Tracing NO_OP_TRACING = NoopTracing.INSTANCE;
    public static final Span NO_OP_SPAN = NoopSpan.INSTANCE;
    public static final EmptyExtractor NO_OP_EXTRACTOR = EmptyExtractor.INSTANCE;
    public static final EmptyMessagingTracing NO_OP_MESSAGING_TRACING = EmptyMessagingTracing.INSTANCE;

    public static Span noNullSpan(Span span) {
        return NoNull.of(span, NO_OP_SPAN);
    }

    public static Extractor noNullExtractor(Extractor extractor) {
        return NoNull.of(extractor, NO_OP_EXTRACTOR);
    }

    private static class NoopSpan implements Span {
        private static final NoopSpan INSTANCE = new NoopSpan();

        @Override
        public boolean isNoop() {
            return true;
        }

        @Override
        public Span start() {
            return this;
        }

        @Override
        public Span start(long timestamp) {
            return this;
        }

        @Override
        public Span name(String name) {
            return this;
        }

        @Override
        public Span kind(Kind kind) {
            return this;
        }

        @Override
        public Span annotate(String value) {
            return this;
        }

        @Override
        public Span annotate(long timestamp, String value) {
            return this;
        }

        @Override
        public Span remoteServiceName(String remoteServiceName) {
            return this;
        }

        /**
         * Returns true in order to prevent secondary conditions when in no-op mode
         */
        @Override
        public boolean remoteIpAndPort(String remoteIp, int port) {
            return true;
        }

        @Override
        public Span tag(String key, String value) {
            return this;
        }

        @Override
        public Span error(Throwable throwable) {
            return this;
        }

        @Override
        public void finish(long timestamp) {
        }

        @Override
        public void abandon() {
        }

        @Override
        public void finish() {

        }

        @Override
        public void flush() {
        }

        @Override
        public void inject(Request request) {

        }

        @Override
        public Span maybeScope() {
            return this;
        }

        @Override
        public String toString() {
            return "NoopSpan";
        }
    }

    private static class NoopTracing implements Tracing {
        private static final NoopTracing INSTANCE = new NoopTracing();

        @Override
        public Span currentSpan() {
            return NoopSpan.INSTANCE;
        }

        @Override
        public Span nextSpan() {
            return null;
        }

        @Override
        public Span nextSpan(Message message) {
            return NoopSpan.INSTANCE;
        }

        @Override
        public AsyncContext exportAsync(Request request) {
            return NoOpContext.NO_OP_ASYNC_CONTEXT;
        }

        @Override
        public Span importAsync(AsyncContext snapshot) {
            return NoopSpan.INSTANCE;
        }

        @Override
        public ProgressContext nextProgress(Request request) {
            return NoOpContext.NO_OP_PROGRESS_CONTEXT;
        }

        @Override
        public Span importProgress(Request request) {
            return NoopSpan.INSTANCE;
        }

        @Override
        public MessagingTracing<? extends Request> messagingTracing() {
            return EmptyMessagingTracing.INSTANCE;
        }

        @Override
        public String toString() {
            return "NoopTracing";
        }

        @Override
        public boolean isNoop() {
            return true;
        }
    }

    private static class EmptyMessagingTracing<R extends MessagingRequest> implements MessagingTracing {
        private static final EmptyMessagingTracing INSTANCE = new EmptyMessagingTracing();
        private static final Function NOOP_SAMPLER = r -> false;

        @Override
        public Extractor extractor() {
            return EmptyExtractor.INSTANCE;
        }

        @Override
        public Injector injector() {
            return EmptyInjector.INSTANCE;
        }

        @Override
        public Function<R, Boolean> consumerSampler() {
            return NOOP_SAMPLER;
        }

        @Override
        public Function<R, Boolean> producerSampler() {
            return NOOP_SAMPLER;
        }

        @Override
        public boolean consumerSampler(MessagingRequest request) {
            return false;
        }

        @Override
        public boolean producerSampler(MessagingRequest request) {
            return false;
        }
    }

    private static class EmptyMessage implements Message {
        private static final EmptyMessage INSTANCE = new EmptyMessage();
        private static final Object OBJ_INSTANCE = new Object();

        @Override
        public Object get() {
            return OBJ_INSTANCE;
        }
    }

    private static class EmptyExtractor implements Extractor {
        private static final EmptyExtractor INSTANCE = new EmptyExtractor();


        @Override
        public Message extract(MessagingRequest request) {
            return EmptyMessage.INSTANCE;
        }
    }

    private static class EmptyInjector implements Injector {
        private static final EmptyInjector INSTANCE = new EmptyInjector();

        @Override
        public void inject(Span span, MessagingRequest request) {

        }
    }
}
