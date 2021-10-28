/*
 * Copyright (C) 2011 John Currier
 * Copyright (C) 2017 Daniel Watt
 * Copyright (C) 2018 Nils Petzaell
 * Copyright (C) 2021 Yoichiro Takeda
 *
 * This file is a part of the SchemaSpy project (http://schemaspy.org).
 *
 * SchemaSpy is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * SchemaSpy is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package org.schemaspy.view;

import org.schemaspy.model.Enum;
import org.schemaspy.util.Markdown;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Writer;
import java.lang.invoke.MethodHandles;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The page that lists all of the enums (stored procedures and functions)
 * in the schema.
 *
 * @author John Currier
 * @author Daniel Watt
 * @author Nils Petzaell
 */
public class HtmlEnumsPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final MustacheCompiler mustacheCompiler;

    public HtmlEnumsPage(MustacheCompiler mustacheCompiler) {
        this.mustacheCompiler = mustacheCompiler;
    }

    public void write(Collection<Enum> enums, Writer writer) {

        LOGGER.info("hogehogeho");
        LOGGER.info(enums.stream().map(e -> e.getLabel()).collect(Collectors.joining(",")));

        PageData pageData = new PageData.Builder()
                .templateName("enums.html")
                .scriptName("enums.js")
                .addToScope("enums", enums)
                .addToScope("md2html", (Function<String,String>) md -> Markdown.toHtml(md, mustacheCompiler.getRootPath(0)))
                .getPageData();

        try {
            mustacheCompiler.write(pageData, writer);
        } catch (IOException e) {
            LOGGER.error("Failed to write enums page", e);
        }
    }

}
