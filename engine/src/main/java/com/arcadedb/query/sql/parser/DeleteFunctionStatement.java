/* Generated by: JJTree: Do not edit this line. DeleteFunctionStatement.java Version 1.1 */
/* ParserGeneratorCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.arcadedb.query.sql.parser;

import com.arcadedb.database.Database;
import com.arcadedb.query.sql.executor.CommandContext;
import com.arcadedb.query.sql.executor.InternalResultSet;
import com.arcadedb.query.sql.executor.ResultInternal;
import com.arcadedb.query.sql.executor.ResultSet;

import java.util.*;

public class DeleteFunctionStatement extends SimpleExecStatement {
  protected Identifier libraryName;
  protected Identifier functionName;

  public DeleteFunctionStatement(final int id) {
    super(id);
  }

  public DeleteFunctionStatement(final SqlParser p, final int id) {
    super(p, id);
  }

  @Override
  public ResultSet executeSimple(final CommandContext ctx) {
    final Database database = ctx.getDatabase();

    database.getSchema().getFunctionLibrary(libraryName.getStringValue()).unregisterFunction(functionName.getStringValue());

    return new InternalResultSet().add(new ResultInternal().setProperty("operation", "delete function").setProperty("libraryName", libraryName.getStringValue())
        .setProperty("functionName", functionName.getStringValue()));
  }

  @Override
  public void toString(final Map<String, Object> params, final StringBuilder builder) {
    builder.append("DELETE FUNCTION ");
    libraryName.toString(params, builder);
    builder.append(".");
    functionName.toString(params, builder);
  }

  @Override
  public DefineFunctionStatement copy() {
    final DefineFunctionStatement result = new DefineFunctionStatement(-1);
    result.libraryName = libraryName == null ? null : libraryName.copy();
    result.functionName = functionName == null ? null : functionName.copy();
    return result;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    final DefineFunctionStatement that = (DefineFunctionStatement) o;

    if (!Objects.equals(libraryName, that.libraryName))
      return false;
    return Objects.equals(functionName, that.functionName);
  }

  @Override
  public int hashCode() {
    int result = libraryName != null ? libraryName.hashCode() : 0;
    result = 31 * result + (functionName != null ? functionName.hashCode() : 0);
    return result;
  }
}
/* ParserGeneratorCC - OriginalChecksum=9adb6ad010d0e34d647466e36d295454 (do not edit this line) */
