package com.catinthedark.ld31.lib.io;

/**
 * Created by over on 14.11.14.
 */

import com.catinthedark.ld31.lib.common.DispatchableLogicFunction;
import com.catinthedark.ld31.lib.common.RunnableEx;

import java.util.concurrent.BlockingQueue;

/**
 * Настоящая киллер фича, пацаны. Если у тебя есть какая-то система, которая бешенно спамит тебя
 * сообщениями(например физический движок, рассказывающий тебе примерно раз в 10мс о том, что произошла коллизия
 * игрока с демоном, например) и вы не можете ее раздуплить. И вам не хочется, чтобы ваша логика, обрабатывающаяя
 * эти сообщения отрабатывала каждые 10мс, а очередь сообщений чтобы вдруг переполиналсь, single message port
 * решение как раз на этот случай.
 */
public class SingleMsgPort<T> implements Port<T> {
    private final BlockingQueue<RunnableEx> queue;
    private final DispatchableLogicFunction<T> fn;

    public SingleMsgPort(BlockingQueue<RunnableEx> queue, DispatchableLogicFunction<T> fn) {
        this.queue = queue;
        this.fn = fn;
    }

    @Override
    public void write(T msg, Runnable onWrite) throws InterruptedException {
        queue.offer(() -> {
            fn.apply(msg);
        });
        onWrite.run();

    }
}
