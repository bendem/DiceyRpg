package be.bendem.bot.storage;

import be.bendem.bot.utils.Sanity;
import be.bendem.bot.utils.Tuple;

import java.sql.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * That class is crazy, I'll most likely regret it later, but still, PreparedStatement
 * is horrible!
 */
public class SqlQuery {

    private static final Map<Class<?>, SQLType> TYPE_BINDINGS;
    static {
        Map<Class<?>, SQLType> map = new HashMap<>();

        map.put(String.class, JDBCType.VARCHAR);
        map.put(Long.class, JDBCType.BIGINT);
        map.put(Integer.class, JDBCType.INTEGER);
        map.put(Short.class, JDBCType.SMALLINT);
        map.put(Byte.class, JDBCType.TINYINT);
        map.put(Time.class, JDBCType.TIME);
        map.put(Timestamp.class, JDBCType.TIMESTAMP);
        map.put(String.class, JDBCType.VARCHAR);
        map.put(Boolean.class, JDBCType.BOOLEAN);

        TYPE_BINDINGS = Collections.unmodifiableMap(map);
    }

    private final Connection connection;
    private final String sql;
    private final Map<Integer, Tuple<SQLType, Object>> args;
    private SqlQuery next;

    public SqlQuery(Connection connection, String sql) {
        this.connection = connection;
        this.sql = sql;
        args = new HashMap<>();
        next = null;
    }

    /**
     * Adds a query to be executed after this one.
     *
     * @param query the next query to execute
     * @return the query currently worked on
     */
    public SqlQuery add(SqlQuery query) {
        if(next == null) {
            next = query;
        } else {
            next.add(query);
        }
        return this;
    }

    /**
     * Sets a parameter binding.
     * <p>
     * The type is recognized using a binding table, if not found, the underlying jdbc
     * driver will specify the SQL type. If you want to specify a type, use {@link
     * SqlQuery#set(int, Object, SQLType}.
     *
     * @param index the index of the argument
     * @param obj the value to bind
     * @return the query
     */
    public SqlQuery set(int index, Object obj) {
        Sanity.truthness(index > 0, "Sql arguments start at 1, you used " + index);
        args.put(index, new Tuple<>(TYPE_BINDINGS.getOrDefault(obj.getClass(), null), obj));
        return this;
    }


    /**
     * Sets a parameter binding.
     * <p>
     * If the type is null, the underlying jdbc driver will specify the SQL type.
     *
     * @param index the index of the argument
     * @param obj the value to bind
     * @param type the SQL type of the argument
     * @return the query
     */
    public SqlQuery set(int index, Object obj, SQLType type) {
        Sanity.truthness(index > 0, "Sql arguments start at 1, you used " + index);
        args.put(index, new Tuple<>(type, obj));
        return this;
    }

    public int exec() {
        try {
            int count = buildQuery().executeUpdate();
            return next.exec() + count;
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet query() {
        try {
            return buildQuery().executeQuery();
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private PreparedStatement buildQuery() {
        try {
            PreparedStatement s = connection.prepareStatement(sql);
            for(Map.Entry<Integer, Tuple<SQLType, Object>> arg : args.entrySet()) {
                int key = arg.getKey();
                Tuple<SQLType, Object> val = arg.getValue();

                if(val.getSecond() == null) {
                    s.setNull(key, val.getFirst().getVendorTypeNumber());
                }

                if(val.getFirst() == null) {
                    s.setObject(key, val.getSecond());
                } else {
                    s.setObject(key, val.getSecond(), val.getFirst());
                }
            }

            return s;
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
