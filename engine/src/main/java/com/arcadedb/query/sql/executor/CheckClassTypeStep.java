/*
 * Copyright © 2021-present Arcade Data Ltd (info@arcadedata.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-FileCopyrightText: 2021-present Arcade Data Ltd (info@arcadedata.com)
 * SPDX-License-Identifier: Apache-2.0
 */
package com.arcadedb.query.sql.executor;

import com.arcadedb.database.Database;
import com.arcadedb.exception.CommandExecutionException;
import com.arcadedb.exception.TimeoutException;
import com.arcadedb.schema.DocumentType;
import com.arcadedb.schema.Schema;

/**
 * <p>
 * This step is used just as a gate check for classes (eg. for CREATE VERTEX to make sure that the passed class is a vertex class).
 * </p>
 * <p>
 * It accepts two values: a target class and a parent class. If the two classes are the same or if the parent class is indeed
 * a parent class of the target class, then the syncPool() returns an empty result set, otherwise it throws an PCommandExecutionException
 * </p>
 *
 * @author Luigi Dell'Aquila (luigi.dellaquila-(at)-gmail.com)
 */
public class CheckClassTypeStep extends AbstractExecutionStep {

  private final String targetClass;
  private final String parentClass;

  boolean found = false;

  /**
   * @param targetClass      a class to be checked
   * @param parentClass      a class that is supposed to be the same or a parent class of the target class
   * @param context          execution context
   * @param profilingEnabled true to collect execution stats
   */
  public CheckClassTypeStep(final String targetClass, final String parentClass, final CommandContext context, final boolean profilingEnabled) {
    super(context, profilingEnabled);
    this.targetClass = targetClass;
    this.parentClass = parentClass;
  }

  @Override
  public ResultSet syncPull(final CommandContext context, final int nRecords) throws TimeoutException {
    pullPrevious(context, nRecords);

    final long begin = profilingEnabled ? System.nanoTime() : 0;
    try {
      if (found) {
        return new InternalResultSet();
      }
      if (this.targetClass.equals(this.parentClass)) {
        return new InternalResultSet();
      }
      final Database db = context.getDatabase();

      final Schema schema = db.getSchema();
      final DocumentType parentClazz = schema.getType(this.parentClass);
      if (parentClazz == null) {
        throw new CommandExecutionException("Class not found: " + this.parentClass);
      }
      final DocumentType targetClazz = schema.getType(this.targetClass);
      if (targetClazz == null) {
        throw new CommandExecutionException("Class not found: " + this.targetClass);
      }

      if (parentClazz.equals(targetClazz)) {
        found = true;
      } else {
        for (final DocumentType subclass : parentClazz.getSubTypes()) {
          if (subclass.equals(targetClazz)) {
            this.found = true;
            break;
          }
        }
      }
      if (!found) {
        throw new CommandExecutionException("Type '" + this.targetClass + "' is not a subtype of '" + this.parentClass + "'");
      }
      return new InternalResultSet();
    } finally {
      if (profilingEnabled) {
        cost += (System.nanoTime() - begin);
      }
    }
  }

  @Override
  public String prettyPrint(final int depth, final int indent) {
    final String spaces = ExecutionStepInternal.getIndent(depth, indent);
    final StringBuilder result = new StringBuilder();
    result.append(spaces);
    result.append("+ CHECK TYPE HIERARCHY");
    if (profilingEnabled) {
      result.append(" (").append(getCostFormatted()).append(")");
    }
    result.append("\n");
    result.append("  ").append(this.parentClass);
    return result.toString();
  }

}
