/*
 * Copyright 2011-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.lettuce.core.cluster;

import java.net.SocketAddress;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

import io.lettuce.core.cluster.models.partitions.Partitions;
import io.lettuce.core.cluster.models.partitions.RedisClusterNode;
import io.lettuce.core.internal.LettuceAssert;
import io.lettuce.core.resource.ClientResources;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/**
 * Round-Robin socket address supplier. Cluster nodes are iterated circular/infinitely.
 *
 * @author Mark Paluch
 * @author Christian Lang
 */
class RoundRobinSocketAddressSupplier implements Supplier<SocketAddress> {

    private static final InternalLogger logger = InternalLoggerFactory.getInstance(RoundRobinSocketAddressSupplier.class);

    private final Supplier<Partitions> partitions;

    private final Function<Collection<RedisClusterNode>, Collection<RedisClusterNode>> sortFunction;

    private final ClientResources clientResources;

    private final RoundRobin<RedisClusterNode> roundRobin;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public RoundRobinSocketAddressSupplier(Supplier<Partitions> partitions,
            Function<? extends Collection<RedisClusterNode>, Collection<RedisClusterNode>> sortFunction,
            ClientResources clientResources) {

        LettuceAssert.notNull(partitions, "Partitions must not be null");
        LettuceAssert.notNull(sortFunction, "Sort-Function must not be null");

        this.partitions = partitions;
        this.roundRobin = new RoundRobin<>(
                (l, r) -> l.getUri() == r.getUri() || (l.getUri() != null && l.getUri().equals(r.getUri())));
        this.sortFunction = (Function) sortFunction;
        this.clientResources = clientResources;
        resetRoundRobin(partitions.get());
    }

    @Override
    public SocketAddress get() {

        Partitions partitions = this.partitions.get();
        if (!roundRobin.isConsistent(partitions)) {
            resetRoundRobin(partitions);
        }

        RedisClusterNode redisClusterNode = roundRobin.next();
        return getSocketAddress(redisClusterNode);
    }

    protected void resetRoundRobin(Partitions partitions) {
        roundRobin.rebuild(sortFunction.apply(partitions));
    }

    protected SocketAddress getSocketAddress(RedisClusterNode redisClusterNode) {

        SocketAddress resolvedAddress = clientResources.socketAddressResolver().resolve(redisClusterNode.getUri());
        logger.debug("Resolved SocketAddress {} using for Cluster node {}", resolvedAddress, redisClusterNode.getNodeId());
        return resolvedAddress;
    }

}
