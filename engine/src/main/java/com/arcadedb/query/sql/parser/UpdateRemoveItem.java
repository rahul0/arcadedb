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
/* Generated By:JJTree: Do not edit this line. OUpdateRemoveItem.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_USERTYPE_VISIBILITY_PUBLIC=true */
package com.arcadedb.query.sql.parser;

import com.arcadedb.query.sql.executor.CommandContext;
import com.arcadedb.query.sql.executor.MultiValue;
import com.arcadedb.query.sql.executor.ResultInternal;

import java.util.*;

public class UpdateRemoveItem extends SimpleNode {
  Expression left;
  Expression right;

  public UpdateRemoveItem(final int id) {
    super(id);
  }

  public void toString(final Map<String, Object> params, final StringBuilder builder) {
    left.toString(params, builder);
    if (right != null) {
      builder.append(" = ");
      right.toString(params, builder);
    }
  }

  public UpdateRemoveItem copy() {
    final UpdateRemoveItem result = new UpdateRemoveItem(-1);
    result.left = left == null ? null : left.copy();
    result.right = right == null ? null : right.copy();
    return result;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    final UpdateRemoveItem that = (UpdateRemoveItem) o;

    if (!Objects.equals(left, that.left))
      return false;
    return Objects.equals(right, that.right);
  }

  @Override
  public int hashCode() {
    int result = left != null ? left.hashCode() : 0;
    result = 31 * result + (right != null ? right.hashCode() : 0);
    return result;
  }

  public void applyUpdate(final ResultInternal result, final CommandContext ctx) {
    if (right != null) {
      final Object leftVal = left.execute(result, ctx);
      final Object rightVal = right.execute(result, ctx);
      if (MultiValue.isMultiValue(leftVal)) {
        MultiValue.remove(leftVal, rightVal, false);
      }
    } else {
      left.applyRemove(result, ctx);
    }
  }
}
/* JavaCC - OriginalChecksum=72e240d3dc1196fdea69e8fdc2bd69ca (do not edit this line) */
