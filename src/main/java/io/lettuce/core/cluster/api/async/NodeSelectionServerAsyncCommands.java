/*
 * Copyright 2017-2021 the original author or authors.
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
package io.lettuce.core.cluster.api.async;

import java.util.Date;
import java.util.List;
import java.util.Map;

import io.lettuce.core.FlushMode;
import io.lettuce.core.KillArgs;
import io.lettuce.core.TrackingArgs;
import io.lettuce.core.UnblockType;
import io.lettuce.core.protocol.CommandType;

/**
 * Asynchronous executed commands on a node selection for Server Control.
 *
 * @param <K> Key type.
 * @param <V> Value type.
 * @author Mark Paluch
 * @since 4.0
 * @generated by io.lettuce.apigenerator.CreateAsyncNodeSelectionClusterApi
 */
public interface NodeSelectionServerAsyncCommands<K, V> {

    /**
     * Asynchronously rewrite the append-only file.
     *
     * @return String simple-string-reply always {@code OK}.
     */
    AsyncExecutions<String> bgrewriteaof();

    /**
     * Asynchronously save the dataset to disk.
     *
     * @return String simple-string-reply.
     */
    AsyncExecutions<String> bgsave();

    /**
     * Control tracking of keys in the context of server-assisted client cache invalidation.
     *
     * @param enabled {@code true} to enable key tracking.
     * @return String simple-string-reply {@code OK}.
     * @since 6.0
     */
    AsyncExecutions<String> clientCaching(boolean enabled);

    /**
     * Get the current connection name.
     *
     * @return K bulk-string-reply The connection name, or a null bulk reply if no name is set.
     */
    AsyncExecutions<K> clientGetname();

    /**
     * Returns the client ID we are redirecting our tracking notifications to.
     *
     * @return the ID of the client we are redirecting the notifications to. The command returns -1 if client tracking is not
     *         enabled, or 0 if client tracking is enabled but we are not redirecting the notifications to any client.
     * @since 6.0
     */
    AsyncExecutions<Long> clientGetredir();

    /**
     * Get the id of the current connection.
     *
     * @return Long The command just returns the ID of the current connection.
     * @since 5.3
     */
    AsyncExecutions<Long> clientId();

    /**
     * Kill the connection of a client identified by ip:port.
     *
     * @param addr ip:port.
     * @return String simple-string-reply {@code OK} if the connection exists and has been closed.
     */
    AsyncExecutions<String> clientKill(String addr);

    /**
     * Kill connections of clients which are filtered by {@code killArgs}.
     *
     * @param killArgs args for the kill operation.
     * @return Long integer-reply number of killed connections.
     */
    AsyncExecutions<Long> clientKill(KillArgs killArgs);

    /**
     * Get the list of client connections.
     *
     * @return String bulk-string-reply a unique string, formatted as follows: One client connection per line (separated by LF),
     *         each line is composed of a succession of property=value fields separated by a space character.
     */
    AsyncExecutions<String> clientList();

    /**
     * Stop processing commands from clients for some time.
     *
     * @param timeout the timeout value in milliseconds.
     * @return String simple-string-reply The command returns OK or an error if the timeout is invalid.
     */
    AsyncExecutions<String> clientPause(long timeout);

    /**
     * Set the current connection name.
     *
     * @param name the client name.
     * @return simple-string-reply {@code OK} if the connection name was successfully set.
     */
    AsyncExecutions<String> clientSetname(K name);

    /**
     * Enables the tracking feature of the Redis server, that is used for server assisted client side caching. Tracking messages
     * are either available when using the RESP3 protocol or through Pub/Sub notification when using RESP2.
     *
     * @param args for the CLIENT TRACKING operation.
     * @return String simple-string-reply {@code OK}.
     * @since 6.0
     */
    AsyncExecutions<String> clientTracking(TrackingArgs args);

    /**
     * Unblock the specified blocked client.
     *
     * @param id the client id.
     * @param type unblock type.
     * @return Long integer-reply number of unblocked connections.
     * @since 5.1
     */
    AsyncExecutions<Long> clientUnblock(long id, UnblockType type);

    /**
     * Returns an array reply of details about all Redis commands.
     *
     * @return List&lt;Object&gt; array-reply.
     */
    AsyncExecutions<List<Object>> command();

    /**
     * Get total number of Redis commands.
     *
     * @return Long integer-reply of number of total commands in this Redis server.
     */
    AsyncExecutions<Long> commandCount();

    /**
     * Returns an array reply of details about the requested commands.
     *
     * @param commands the commands to query for.
     * @return List&lt;Object&gt; array-reply.
     */
    AsyncExecutions<List<Object>> commandInfo(String... commands);

    /**
     * Returns an array reply of details about the requested commands.
     *
     * @param commands the commands to query for.
     * @return List&lt;Object&gt; array-reply.
     */
    AsyncExecutions<List<Object>> commandInfo(CommandType... commands);

    /**
     * Get the value of a configuration parameter.
     *
     * @param parameter name of the parameter.
     * @return Map&lt;String, String&gt; bulk-string-reply.
     */
    AsyncExecutions<Map<String, String>> configGet(String parameter);

    /**
     * Reset the stats returned by INFO.
     *
     * @return String simple-string-reply always {@code OK}.
     */
    AsyncExecutions<String> configResetstat();

    /**
     * Rewrite the configuration file with the in memory configuration.
     *
     * @return String simple-string-reply {@code OK} when the configuration was rewritten properly. Otherwise an error is
     *         returned.
     */
    AsyncExecutions<String> configRewrite();

    /**
     * Set a configuration parameter to the given value.
     *
     * @param parameter the parameter name.
     * @param value the parameter value.
     * @return String simple-string-reply: {@code OK} when the configuration was set properly. Otherwise an error is returned.
     */
    AsyncExecutions<String> configSet(String parameter, String value);

    /**
     * Return the number of keys in the selected database.
     *
     * @return Long integer-reply.
     */
    AsyncExecutions<Long> dbsize();

    /**
     * Crash and recover.
     *
     * @param delay optional delay in milliseconds.
     * @return String simple-string-reply.
     */
    AsyncExecutions<String> debugCrashAndRecover(Long delay);

    /**
     * Get debugging information about the internal hash-table state.
     *
     * @param db the database number.
     * @return String simple-string-reply.
     */
    AsyncExecutions<String> debugHtstats(int db);

    /**
     * Get debugging information about a key.
     *
     * @param key the key.
     * @return String simple-string-reply.
     */
    AsyncExecutions<String> debugObject(K key);

    /**
     * Save RDB, clear the database and reload RDB.
     *
     * @return String simple-string-reply The commands returns OK on success.
     */
    AsyncExecutions<String> debugReload();

    /**
     * Restart the server gracefully.
     *
     * @param delay optional delay in milliseconds.
     * @return String simple-string-reply.
     */
    AsyncExecutions<String> debugRestart(Long delay);

    /**
     * Get debugging information about the internal SDS length.
     *
     * @param key the key.
     * @return String simple-string-reply.
     */
    AsyncExecutions<String> debugSdslen(K key);

    /**
     * Remove all keys from all databases.
     *
     * @return String simple-string-reply.
     */
    AsyncExecutions<String> flushall();

    /**
     * Remove all keys from all databases using the specified {@link FlushMode}.
     *
     * @param flushMode the flush mode (sync/async).
     * @return String simple-string-reply.
     * @since 6.1
     */
    AsyncExecutions<String> flushall(FlushMode flushMode);

    /**
     * Remove all keys asynchronously from all databases.
     *
     * @return String simple-string-reply.
     * @deprecated since 6.1, use {@link #flushall(FlushMode)} instead.
     */
    @Deprecated
    AsyncExecutions<String> flushallAsync();

    /**
     * Remove all keys from the current database.
     *
     * @return String simple-string-reply.
     */
    AsyncExecutions<String> flushdb();

    /**
     * Remove all keys from the current database using the specified {@link FlushMode}.
     *
     * @param flushMode the flush mode (sync/async).
     * @return String simple-string-reply.
     * @since 6.1
     */
    AsyncExecutions<String> flushdb(FlushMode flushMode);

    /**
     * Remove all keys asynchronously from the current database.
     *
     * @return String simple-string-reply.
     * @deprecated since 6.1, use {@link #flushdb(FlushMode)} instead.
     */
    @Deprecated
    AsyncExecutions<String> flushdbAsync();

    /**
     * Get information and statistics about the server.
     *
     * @return String bulk-string-reply as a collection of text lines.
     */
    AsyncExecutions<String> info();

    /**
     * Get information and statistics about the server.
     *
     * @param section the section type: string.
     * @return String bulk-string-reply as a collection of text lines.
     */
    AsyncExecutions<String> info(String section);

    /**
     * Get the UNIX time stamp of the last successful save to disk.
     *
     * @return Date integer-reply an UNIX time stamp.
     */
    AsyncExecutions<Date> lastsave();

    /**
     * Reports the number of bytes that a key and its value require to be stored in RAM.
     *
     * @return memory usage in bytes.
     * @since 5.2
     */
    AsyncExecutions<Long> memoryUsage(K key);

    /**
     * Synchronously save the dataset to disk.
     *
     * @return String simple-string-reply The commands returns OK on success.
     */
    AsyncExecutions<String> save();

    /**
     * Make the server a replica of another instance, or promote it as master.
     *
     * @param host the host type: string.
     * @param port the port type: string.
     * @return String simple-string-reply.
     */
    AsyncExecutions<String> slaveof(String host, int port);

    /**
     * Promote server as master.
     *
     * @return String simple-string-reply.
     */
    AsyncExecutions<String> slaveofNoOne();

    /**
     * Read the slow log.
     *
     * @return List&lt;Object&gt; deeply nested multi bulk replies.
     */
    AsyncExecutions<List<Object>> slowlogGet();

    /**
     * Read the slow log.
     *
     * @param count the count.
     * @return List&lt;Object&gt; deeply nested multi bulk replies.
     */
    AsyncExecutions<List<Object>> slowlogGet(int count);

    /**
     * Obtaining the current length of the slow log.
     *
     * @return Long length of the slow log.
     */
    AsyncExecutions<Long> slowlogLen();

    /**
     * Resetting the slow log.
     *
     * @return String simple-string-reply The commands returns OK on success.
     */
    AsyncExecutions<String> slowlogReset();

    /**
     * Return the current server time.
     *
     * @return List&lt;V&gt; array-reply specifically:
     *
     *         A multi bulk reply containing two elements:
     *
     *         unix time in seconds. microseconds.
     */
    AsyncExecutions<List<V>> time();
}
