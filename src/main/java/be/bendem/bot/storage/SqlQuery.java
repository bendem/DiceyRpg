package be.bendem.bot.storage;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class SqlQuery {

    private final Connection connection;
    private final String sql;
    private final Map<Integer, Object> args;
    private SqlQuery next;

    public SqlQuery(Connection connection, String sql) {
        this.connection = connection;
        this.sql = sql;
        args = new HashMap<>();
        next = null;
    }

    public SqlQuery add(SqlQuery query) {
        if(next == null) {
            next = query;
        } else {
            next.add(query);
        }
        return this;
    }

    public SqlQuery set(int index, Object obj) {
        args.put(index, obj);
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
            for(Map.Entry<Integer, Object> arg : args.entrySet()) {
                int key = arg.getKey();
                Object val = arg.getValue();
                if(val instanceof String) {
                    s.setString(key, (String) val);
                } else if(val instanceof Integer) {
                    s.setInt(key, (Integer) val);
                } else if(val instanceof Short) {
                    s.setShort(key, (Short) val);
                } else if(val instanceof Byte) {
                    s.setByte(key, (Byte) val);
                } else if(val instanceof Float) {
                    s.setFloat(key, (Float) val);
                } else if(val instanceof Double) {
                    s.setDouble(key, (Double) val);
                } else if(val instanceof Date) {
                    s.setDate(key, (Date) val);
                }
            }

            return s;
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
