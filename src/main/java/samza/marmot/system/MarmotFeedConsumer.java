/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package samza.marmot.system;

import org.apache.samza.Partition;
import org.apache.samza.system.IncomingMessageEnvelope;
import org.apache.samza.system.SystemStreamPartition;
import org.apache.samza.util.BlockingEnvelopeMap;
import samza.marmot.MockData;
import samza.marmot.domain.MarmotEvent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * Author: Eric Han
 * Date:   15/4/29
 */
public class MarmotFeedConsumer extends BlockingEnvelopeMap {
    private final String systemName;
    private final String channel;
    private ExecutorService executorService;
    private AtomicInteger num = new AtomicInteger();
    volatile boolean going = true;

    public MarmotFeedConsumer(String systemName, String channel) {
        this.systemName = systemName;
        this.channel = channel;
    }

    public void onEvent(final MarmotEvent event) {
        SystemStreamPartition partition = new SystemStreamPartition(systemName, channel, new Partition(0));
        try {
            IncomingMessageEnvelope incoming = new IncomingMessageEnvelope(partition, null, null, event);
            put(partition, incoming);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Override
    public void register(SystemStreamPartition partition, String startingOffset) {
        super.register(partition, startingOffset);
        String channel = partition.getStream();
        System.out.println(channel);
    }

    @Override
    public void start() {
        executorService = Executors.newFixedThreadPool(2);
        executorService.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        while (going) {
                            long time = System.currentTimeMillis();
                            String rawEvent = MockData.getData() + "," + time;
                            onEvent(new MarmotEvent(num.incrementAndGet(), time, rawEvent));
                            try {
                                Thread.sleep(10_000);
                            } catch (InterruptedException e) {
                                System.err.println(e);
                            }
                        }
                    }
                }
        );
    }

    @Override
    public void stop() {
        going = false;
        executorService.shutdown();
    }
}
