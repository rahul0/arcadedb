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
/* Generated By:JJTree: Do not edit this line. ORid.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_USERTYPE_VISIBILITY_PUBLIC=true */
package com.arcadedb.query.sql.parser;

import com.arcadedb.database.Identifiable;
import com.arcadedb.database.RID;
import com.arcadedb.exception.CommandExecutionException;
import com.arcadedb.query.sql.executor.BasicCommandContext;
import com.arcadedb.query.sql.executor.CommandContext;
import com.arcadedb.query.sql.executor.Result;

import java.util.*;

public class Rid extends SimpleNode {
  protected PInteger   bucket;
  protected PInteger   position;
  protected Expression expression;
  protected boolean    legacy;

  public Rid(final int id) {
    super(id);
  }

  public Rid(final SqlParser p, final int id) {
    super(p, id);
  }

  @Override
  public String toString(final String prefix) {
    return "#" + bucket.getValue() + ":" + position.getValue();
  }

  public void toString(final Map<String, Object> params, final StringBuilder builder) {
    if (legacy) {
      builder.append("#" + bucket.getValue() + ":" + position.getValue());
    } else {
      builder.append("{\"@rid\":");
      expression.toString(params, builder);
      builder.append("}");
    }
  }

  public RID toRecordId(final Result target, final CommandContext ctx) {
    if (legacy) {
      return new RID(ctx.getDatabase(), bucket.value.intValue(), position.value.longValue());
    } else {
      final Object result = expression.execute(target, ctx);
      if (result == null)
        return null;

      if (result instanceof Identifiable)
        return ((Identifiable) result).getIdentity();

      if (result instanceof String) {
        if (!(((String) result).startsWith("#") && (((String) result).contains(":"))))
          throw new CommandExecutionException("Cannot convert to RID: " + result);

        final String[] parts = ((String) result).substring(1).split(":");
        if (parts.length != 2)
          throw new CommandExecutionException("Cannot convert to RID: " + result);

        try {
          return new RID(ctx.getDatabase(), Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
        } catch (final Exception e) {
          throw new CommandExecutionException("Cannot convert to RID: " + result);
        }
      }
      return null;
    }
  }

  public RID toRecordId(final Identifiable target, final CommandContext ctx) {
    if (legacy) {
      return new RID(ctx.getDatabase(), bucket.value.intValue(), position.value.longValue());
    } else {
      final Object result = expression.execute(target, ctx);
      if (result == null)
        return null;

      if (result instanceof Identifiable)
        return ((Identifiable) result).getIdentity();

      if (result instanceof String)
        throw new UnsupportedOperationException();

      return null;
    }
  }

  public Rid copy() {
    final Rid result = new Rid(-1);
    result.bucket = bucket == null ? null : bucket.copy();
    result.position = position == null ? null : position.copy();
    result.expression = expression == null ? null : expression.copy();
    result.legacy = legacy;
    return result;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    final Rid oRid = (Rid) o;

    if (!Objects.equals(bucket, oRid.bucket))
      return false;
    if (!Objects.equals(position, oRid.position))
      return false;
    if (!Objects.equals(expression, oRid.expression))
      return false;
    return legacy == oRid.legacy;
  }

  @Override
  public int hashCode() {
    int result = bucket != null ? bucket.hashCode() : 0;
    result = 31 * result + (position != null ? position.hashCode() : 0);
    result = 31 * result + (expression != null ? expression.hashCode() : 0);
    return result;
  }

  public void setBucket(final PInteger bucket) {
    this.bucket = bucket;
  }

  public void setPosition(final PInteger position) {
    this.position = position;
  }

  public void setLegacy(final boolean b) {
    this.legacy = b;
  }

  public PInteger getBucket() {
    if (expression != null) {
      final RID rid = toRecordId((Result) null, new BasicCommandContext());
      if (rid != null) {
        final PInteger result = new PInteger(-1);
        result.setValue(rid.getBucketId());
        return result;
      }
    }
    return bucket;
  }

  public PInteger getPosition() {
    if (expression != null) {
      final RID rid = toRecordId((Result) null, new BasicCommandContext());
      if (rid != null) {
        final PInteger result = new PInteger(-1);
        result.setValue(rid.getPosition());
        return result;
      }
    }
    return position;
  }
}
/* JavaCC - OriginalChecksum=c2c6d67d7722e29212e438574698d7cd (do not edit this line) */
