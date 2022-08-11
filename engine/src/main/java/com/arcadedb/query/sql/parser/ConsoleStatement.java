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
/* Generated By:JJTree: Do not edit this line. OConsoleStatement.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_USERTYPE_VISIBILITY_PUBLIC=true */
package com.arcadedb.query.sql.parser;

import com.arcadedb.database.Identifiable;
import com.arcadedb.exception.CommandExecutionException;
import com.arcadedb.log.LogManager;
import com.arcadedb.query.sql.executor.CommandContext;
import com.arcadedb.query.sql.executor.InternalResultSet;
import com.arcadedb.query.sql.executor.ResultInternal;
import com.arcadedb.query.sql.executor.ResultSet;

import java.util.*;
import java.util.logging.Level;

public class ConsoleStatement extends SimpleExecStatement {
  protected Identifier logLevel;
  protected Expression message;

  public ConsoleStatement(int id) {
    super(id);
  }

  public ConsoleStatement(SqlParser p, int id) {
    super(p, id);
  }

  @Override
  public ResultSet executeSimple(CommandContext ctx) {
    InternalResultSet result = new InternalResultSet();
    ResultInternal item = new ResultInternal();
    Object msg = "" + message.execute((Identifiable) null, ctx);

    if (logLevel.getStringValue().equalsIgnoreCase("log")) {
      LogManager.instance().log(this, Level.INFO, "%s", msg);
    } else if (logLevel.getStringValue().equalsIgnoreCase("output")) {
      System.out.println(msg);
    } else if (logLevel.getStringValue().equalsIgnoreCase("error")) {
      System.err.println(msg);
      LogManager.instance().log(this, Level.SEVERE, "%s", msg);
    } else if (logLevel.getStringValue().equalsIgnoreCase("warn")) {
      LogManager.instance().log(this, Level.WARNING, "%s", msg);
    } else if (logLevel.getStringValue().equalsIgnoreCase("debug")) {
      LogManager.instance().log(this, Level.FINE, "%s", msg);
    } else {
      throw new CommandExecutionException("Unsupported log level: " + logLevel);
    }

    item.setProperty("operation", "console");
    item.setProperty("level", logLevel.getStringValue());
    item.setProperty("message", msg);
    result.add(item);
    return result;

  }

  @Override
  public void toString(Map<String, Object> params, StringBuilder builder) {
    builder.append("CONSOLE.");
    logLevel.toString(params, builder);
    builder.append(" ");
    message.toString(params, builder);
  }

  @Override
  public ConsoleStatement copy() {
    ConsoleStatement result = new ConsoleStatement(-1);
    result.logLevel = logLevel == null ? null : logLevel.copy();
    result.message = message == null ? null : message.copy();
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    ConsoleStatement that = (ConsoleStatement) o;

    if (!Objects.equals(logLevel, that.logLevel))
      return false;
    return Objects.equals(message, that.message);
  }

  @Override
  public int hashCode() {
    int result = logLevel != null ? logLevel.hashCode() : 0;
    result = 31 * result + (message != null ? message.hashCode() : 0);
    return result;
  }
}
/* JavaCC - OriginalChecksum=626c09cda52a1a8a63eeefcb37bd66a1 (do not edit this line) */
