/*
 * Copyright 2021 Arcade Data Ltd
 *
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

package com.arcadedb.importer;

import com.arcadedb.database.DatabaseInternal;
import com.arcadedb.log.LogManager;

import java.io.IOException;
import java.util.logging.Level;

public class Importer extends AbstractImporter {
  public Importer(final String[] args) {
    super(args);
  }

  public Importer(final DatabaseInternal database, final String url) {
    super(database);
    settings.url = url;
  }

  public static void main(final String[] args) {
    new Importer(args).load();
    System.exit(0);
  }

  public void load() {
    source = null;

    try {
      final String cfgValue = settings.options.get("maxValueSampling");

      final AnalyzedSchema analyzedSchema = new AnalyzedSchema(cfgValue != null ? Integer.parseInt(cfgValue) : 100);

      openDatabase();

      startImporting();

      loadFromSource(settings.url, AnalyzedEntity.ENTITY_TYPE.DATABASE, analyzedSchema);
      loadFromSource(settings.documents, AnalyzedEntity.ENTITY_TYPE.DOCUMENT, analyzedSchema);
      loadFromSource(settings.vertices, AnalyzedEntity.ENTITY_TYPE.VERTEX, analyzedSchema);
      loadFromSource(settings.edges, AnalyzedEntity.ENTITY_TYPE.EDGE, analyzedSchema);

    } catch (Exception e) {
      LogManager.instance().log(this, Level.SEVERE, "Error on parsing source %s", e, source);
    } finally {
      if (database != null) {
        database.async().waitCompletion();
        stopImporting();
        closeDatabase();
      }
      closeInputFile();
    }
  }

  protected void loadFromSource(final String url, final AnalyzedEntity.ENTITY_TYPE entityType, final AnalyzedSchema analyzedSchema) throws IOException {
    if (url == null)
      // SKIP IT
      return;

    final SourceDiscovery sourceDiscovery = new SourceDiscovery(url);
    final SourceSchema sourceSchema = sourceDiscovery.getSchema(settings, entityType, analyzedSchema);
    if (sourceSchema == null) {
      //LogManager.instance().log(this, Level.WARNING, "XML importing aborted because unable to determine the schema");
      return;
    }

    updateDatabaseSchema(sourceSchema.getSchema());

    source = sourceDiscovery.getSource();
    parser = new Parser(source, 0);
    parser.reset();

    sourceSchema.getContentImporter().load(sourceSchema, entityType, parser, database, context, settings);
  }

  protected void closeDatabase() {
    if (!databaseCreatedDuringImporting)
      return;

    if (database != null) {
      if (database.isTransactionActive())
        database.commit();
      database.close();
    }
  }
}
