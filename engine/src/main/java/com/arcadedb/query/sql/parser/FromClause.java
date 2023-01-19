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
/* Generated By:JJTree: Do not edit this line. OFromClause.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_USERTYPE_VISIBILITY_PUBLIC=true */
package com.arcadedb.query.sql.parser;

import java.util.*;

public class FromClause extends SimpleNode {
  FromItem item;

  public FromClause(final int id) {
    super(id);
  }

  public void toString(final Map<String, Object> params, final StringBuilder builder) {
    if (item != null) {
      item.toString(params, builder);
    }
  }

  public FromItem getItem() {
    return item;
  }

  public void setItem(final FromItem item) {
    this.item = item;
  }

  public FromClause copy() {
    final FromClause result = new FromClause(-1);
    result.item = item.copy();
    return result;
  }

  @Override
  protected Object[] getIdentityElements() {
    return new Object[] { item };
  }

  @Override
  protected SimpleNode[] getCacheableElements() {
    return new SimpleNode[] { item };
  }

  public boolean refersToParent() {
    return item.refersToParent();
  }
}
/* JavaCC - OriginalChecksum=051839d20dabfa4cce26ebcbe0d03a86 (do not edit this line) */
