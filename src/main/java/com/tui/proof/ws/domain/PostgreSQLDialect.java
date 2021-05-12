package com.tui.proof.ws.domain;
import java.sql.Types;

import com.tui.proof.ws.domain.jsondata.FlightJsonType;
import org.hibernate.dialect.PostgreSQL94Dialect;

public class PostgreSQLDialect extends PostgreSQL94Dialect {

    public PostgreSQLDialect() {
        super();
        registerColumnType(Types.JAVA_OBJECT, FlightJsonType.JSON_TYPE);
    }
}