/*
 * Copyright (C) 2019 Nils Petzaell
 * Copyright (C) 2021 Yoichiro Takeda
 *
 * This file is part of SchemaSpy.
 *
 * SchemaSpy is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SchemaSpy is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with SchemaSpy. If not, see <http://www.gnu.org/licenses/>.
 */
package org.schemaspy.input.dbms.service;

import org.schemaspy.Config;
import org.schemaspy.model.Database;
import org.schemaspy.model.Enum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EnumService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final SqlService sqlService;

    public EnumService(SqlService sqlService) {
        this.sqlService = sqlService;
    }

    public void gatherEnums(Config config, Database database) {
        initEnums(config, database);
    }

    /**
     * Initializes stored procedures / functions.
     *
     * @throws SQLException
     */
    private void initEnums(Config config, Database db) {
        String sql = config.getDbProperties().getProperty("selectEnumsSql");

        if (sql != null) {

            try (PreparedStatement stmt = sqlService.prepareStatement(sql, db, null);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    String key = rs.getString("key");
                    String name = rs.getString("name");
                    String label = rs.getString("label");
                    String description = rs.getString("description");

                    Enum enumModel = new Enum(name, label, description);
                    db.getEnumsMap().put(key, enumModel);
                }
            } catch (SQLException sqlException) {
                // don't die just because this failed
                LOGGER.warn("Failed to retrieve stored procedure/function details using sql '{}'", sql, sqlException);
            }
        }
    }

}
