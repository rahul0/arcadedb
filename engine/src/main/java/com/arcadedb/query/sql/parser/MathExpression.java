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
/* Generated By:JJTree: Do not edit this line. OMathExpression.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_USERTYPE_VISIBILITY_PUBLIC=true */
package com.arcadedb.query.sql.parser;

import com.arcadedb.database.Identifiable;
import com.arcadedb.database.Record;
import com.arcadedb.exception.ArcadeDBException;
import com.arcadedb.exception.CommandExecutionException;
import com.arcadedb.query.sql.executor.AggregationContext;
import com.arcadedb.query.sql.executor.CommandContext;
import com.arcadedb.query.sql.executor.Result;
import com.arcadedb.query.sql.executor.ResultInternal;
import com.arcadedb.utility.DateUtils;

import java.math.*;
import java.time.*;
import java.time.temporal.*;
import java.util.*;
import java.util.stream.*;

public class MathExpression extends SimpleNode {
  private static final Object               NULL_VALUE       = new Object();
  protected            List<MathExpression> childExpressions = new ArrayList<MathExpression>();
  protected final      List<Operator>       operators        = new ArrayList<>();

  public Expression getExpandContent() {
    throw new CommandExecutionException("Invalid expand expression");
  }

  public boolean isDefinedFor(final Result currentRecord) {
    return true;
  }

  public boolean isDefinedFor(final Record currentRecord) {
    return true;
  }

  public enum Operator {
    STAR(10) {
      @Override
      public Number apply(final Integer left, final Integer right) {
        return left * right;
      }

      @Override
      public Number apply(final Long left, final Long right) {
        return left * right;
      }

      @Override
      public Number apply(final Float left, final Float right) {
        return left * right;
      }

      @Override
      public Number apply(final Double left, final Double right) {
        return left * right;
      }

      @Override
      public Number apply(final BigDecimal left, final BigDecimal right) {
        return left.multiply(right);
      }

      @Override
      public Object apply(final Object left, final Object right) {
        if (left == null || right == null) {
          return null;
        }
        return super.apply(left, right);
      }
    }, SLASH(10) {
      @Override
      public Number apply(final Integer left, final Integer right) {
        if (left % right == 0) {
          return left / right;
        }
        return ((double) left) / right;
      }

      @Override
      public Number apply(final Long left, final Long right) {
        if (left % right == 0) {
          return left / right;
        }
        return ((double) left) / right;
      }

      @Override
      public Number apply(final Float left, final Float right) {
        return left / right;
      }

      @Override
      public Number apply(final Double left, final Double right) {
        return left / right;
      }

      @Override
      public Number apply(final BigDecimal left, final BigDecimal right) {
        return left.divide(right, RoundingMode.HALF_UP);
      }

      @Override
      public Object apply(final Object left, final Object right) {
        if (left == null || right == null) {
          return null;
        }
        return super.apply(left, right);
      }

    }, REM(10) {
      @Override
      public Number apply(final Integer left, final Integer right) {
        return left % right;
      }

      @Override
      public Number apply(final Long left, final Long right) {
        return left % right;
      }

      @Override
      public Number apply(final Float left, final Float right) {
        return left % right;
      }

      @Override
      public Number apply(final Double left, final Double right) {
        return left % right;
      }

      @Override
      public Number apply(final BigDecimal left, final BigDecimal right) {
        return left.remainder(right);
      }

      @Override
      public Object apply(final Object left, final Object right) {
        if (left == null || right == null)
          return null;
        return super.apply(left, right);
      }

    }, PLUS(20) {
      @Override
      public Number apply(final Integer left, final Integer right) {
        final int sum = left + right;
        if (sum < 0 && left > 0 && right > 0)
          // SPECIAL CASE: UPGRADE TO LONG
          return left.longValue() + right;
        return sum;
      }

      @Override
      public Number apply(final Long left, final Long right) {
        return left + right;
      }

      @Override
      public Number apply(final Float left, final Float right) {
        return left + right;
      }

      @Override
      public Number apply(final Double left, final Double right) {
        return left + right;
      }

      @Override
      public Number apply(final BigDecimal left, final BigDecimal right) {
        return left.add(right);
      }

      @Override
      public Object apply(final Object left, final Object right) {
        if (left == null && right == null)
          return null;
        else if (left == null)
          return right;
        else if (right == null)
          return left;
        else if (left instanceof Number && right instanceof Number)
          return super.apply(left, right);
        else if (DateUtils.isDate(left) || DateUtils.isDate(right)) {
          final ChronoUnit highestPrecision = DateUtils.getHigherPrecision(left, right);
          final Long leftAsLong = DateUtils.dateTimeToTimestamp(left, highestPrecision);
          final Long rightAsLong = DateUtils.dateTimeToTimestamp(right, highestPrecision);

          final Number r = apply(leftAsLong, rightAsLong);
          return Duration.of(r.longValue(), highestPrecision);
        } else if (left instanceof Collection) {
          final Collection<Object> coll = (Collection<Object>) left;
          coll.add(right);
          return left;
        } else if (left instanceof Map) {
          final Map<String, Object> mapLeft = (Map<String, Object>) left;

          if (right instanceof Map) {
            final Map<String, Object> mapRight = (Map<String, Object>) right;
            mapLeft.putAll(mapRight);
          } else if (right instanceof Collection) {
            final Collection<Object> arrayRight = (Collection<Object>) right;
            if (arrayRight.size() % 2 != 0)
              throw new IllegalArgumentException("Cannot add items to the maps because the collection contains odd entries");

            for (final Iterator<Object> iter = arrayRight.iterator(); iter.hasNext(); ) {
              final String key = iter.next().toString();
              final Object value = iter.next();
              mapLeft.put(key, value);
            }
          } else if (right.getClass().isArray()) {
            final Object[] arrayRight = (Object[]) right;
            if (arrayRight.length % 2 != 0)
              throw new IllegalArgumentException("Cannot add items to the maps because the array contains odd entries");
            for (int i = 0; i < arrayRight.length; i += 2)
              mapLeft.put(arrayRight[i].toString(), arrayRight[i + 1]);
          }
          return left;
        }
        return String.valueOf(left) + right;
      }
    },

    MINUS(20) {
      @Override
      public Number apply(final Integer left, final Integer right) {
        final int result = left - right;
        if (result > 0 && left < 0 && right > 0)
          // SPECIAL CASE: UPGRADE TO LONG
          return left.longValue() - right;

        return result;
      }

      @Override
      public Number apply(final Long left, final Long right) {
        return left - right;
      }

      @Override
      public Number apply(final Float left, final Float right) {
        return left - right;
      }

      @Override
      public Number apply(final Double left, final Double right) {
        return left - right;
      }

      @Override
      public Number apply(final BigDecimal left, final BigDecimal right) {
        return left.subtract(right);
      }

      @Override
      public Object apply(final Object left, final Object right) {
        Object result = null;
        if (left == null && right == null) {
          result = null;
        } else if (left instanceof Number && right == null) {
          result = left;
        } else if (right instanceof Number && left == null) {
          result = apply(0, this, (Number) right);
        } else if (left instanceof Number && right instanceof Number) {
          result = apply((Number) left, this, (Number) right);
        } else if (DateUtils.isDate(left) || DateUtils.isDate(right)) {
          final ChronoUnit highestPrecision = DateUtils.getHigherPrecision(left, right);
          final Long leftAsLong = DateUtils.dateTimeToTimestamp(left, highestPrecision);
          final Long rightAsLong = DateUtils.dateTimeToTimestamp(right, highestPrecision);
          final Number r = apply(leftAsLong, rightAsLong);
          result = Duration.of(r.longValue(), highestPrecision);
        } else if (left instanceof Collection) {
          final Collection<Object> coll = (Collection<Object>) left;
          coll.remove(right);
          return left;
        } else if (left instanceof Map) {
          final Map<String, Object> mapLeft = (Map<String, Object>) left;

          if (right instanceof Map) {
            final Map<String, Object> mapRight = (Map<String, Object>) right;
            for (Map.Entry<?, ?> entry : ((Map<?, ?>) mapRight).entrySet()) {
              if (entry.getValue().equals(mapLeft.get(entry.getKey())))
                mapLeft.remove(entry.getKey());
            }
          } else if (right instanceof Collection) {
            final Collection<Object> arrayRight = (Collection<Object>) right;
            for (final Iterator<Object> iter = arrayRight.iterator(); iter.hasNext(); ) {
              final String key = iter.next().toString();
              mapLeft.remove(key);
            }
          } else if (right.getClass().isArray()) {
            final Object[] arrayRight = (Object[]) right;
            for (int i = 0; i < arrayRight.length; ++i)
              mapLeft.remove(arrayRight[i].toString());
          }
          return left;
        }

        return result;
      }
    },

    LSHIFT(30) {
      @Override
      public Number apply(final Integer left, final Integer right) {
        return left << right;
      }

      @Override
      public Number apply(final Long left, final Long right) {
        return left << right;
      }

      @Override
      public Number apply(final Float left, final Float right) {
        return null;
      }

      @Override
      public Number apply(final Double left, final Double right) {
        return null;
      }

      @Override
      public Number apply(final BigDecimal left, final BigDecimal right) {
        return null;
      }

      @Override
      public Object apply(final Object left, final Object right) {
        if (left == null || right == null) {
          return null;
        }
        return super.apply(left, right);
      }
    },

    RSHIFT(30) {
      @Override
      public Number apply(final Integer left, final Integer right) {
        return left >> right;
      }

      @Override
      public Number apply(final Long left, final Long right) {
        return left >> right;
      }

      @Override
      public Number apply(final Float left, final Float right) {
        return null;
      }

      @Override
      public Number apply(final Double left, final Double right) {
        return null;
      }

      @Override
      public Number apply(final BigDecimal left, final BigDecimal right) {
        return null;
      }

      @Override
      public Object apply(final Object left, final Object right) {
        if (left == null || right == null) {
          return null;
        }
        return super.apply(left, right);
      }

    },

    RUNSIGNEDSHIFT(30) {
      @Override
      public Number apply(final Integer left, final Integer right) {
        return left >>> right;
      }

      @Override
      public Number apply(final Long left, final Long right) {
        return left >>> right;
      }

      @Override
      public Number apply(final Float left, final Float right) {
        return null;
      }

      @Override
      public Number apply(final Double left, final Double right) {
        return null;
      }

      @Override
      public Number apply(final BigDecimal left, final BigDecimal right) {
        return null;
      }

      @Override
      public Object apply(final Object left, final Object right) {
        if (left == null || right == null) {
          return null;
        }
        return super.apply(left, right);
      }

    },

    BIT_AND(40) {
      @Override
      public Number apply(final Integer left, final Integer right) {
        return left & right;
      }

      @Override
      public Number apply(final Long left, final Long right) {
        return left & right;
      }

      @Override
      public Number apply(final Float left, final Float right) {
        return null;
      }

      @Override
      public Number apply(final Double left, final Double right) {
        return null;
      }

      @Override
      public Number apply(final BigDecimal left, final BigDecimal right) {
        return null;
      }

      public Object apply(final Object left, final Object right) {
        if (left == null || right == null) {
          return null;
        }
        return super.apply(left, right);
      }
    },

    XOR(50) {
      @Override
      public Number apply(final Integer left, final Integer right) {
        return left ^ right;
      }

      @Override
      public Number apply(final Long left, final Long right) {
        return left ^ right;
      }

      @Override
      public Number apply(final Float left, final Float right) {
        return null;
      }

      @Override
      public Number apply(final Double left, final Double right) {
        return null;
      }

      @Override
      public Number apply(final BigDecimal left, final BigDecimal right) {
        return null;
      }

      @Override
      public Object apply(final Object left, final Object right) {
        if (left == null && right == null) {
          return null;
        }
        if (left instanceof Number && right == null) {
          return apply((Number) left, this, 0);
        }
        if (right instanceof Number && left == null) {
          return apply(0, this, (Number) right);
        }

        if (left instanceof Number && right instanceof Number) {
          return apply((Number) left, this, (Number) right);
        }

        return null;
      }

    },

    BIT_OR(60) {
      @Override
      public Number apply(final Integer left, final Integer right) {
        return left | right;
      }

      @Override
      public Number apply(final Long left, final Long right) {
        return left | right;
      }

      @Override
      public Number apply(final Float left, final Float right) {
        return null;
      }

      @Override
      public Number apply(final Double left, final Double right) {
        return null;
      }

      @Override
      public Number apply(final BigDecimal left, final BigDecimal right) {
        return null;
      }

      public Object apply(final Object left, final Object right) {
        if (left == null && right == null) {
          return null;
        }
        return super.apply(left == null ? 0 : left, right == null ? 0 : right);
      }

    },

    NULL_COALESCING(25) {
      @Override
      public Number apply(final Integer left, final Integer right) {
        return left != null ? left : right;
      }

      @Override
      public Number apply(final Long left, final Long right) {
        return left != null ? left : right;
      }

      @Override
      public Number apply(final Float left, final Float right) {
        return left != null ? left : right;
      }

      @Override
      public Number apply(final Double left, final Double right) {
        return left != null ? left : right;
      }

      @Override
      public Number apply(final BigDecimal left, final BigDecimal right) {
        return left != null ? left : right;
      }

      public Object apply(final Object left, final Object right) {
        return left != null ? left : right;
      }
    };
    private final int priority;

    Operator(final int priority) {
      this.priority = priority;
    }

    public abstract Number apply(Integer left, final Integer right);

    public abstract Number apply(final Long left, final Long right);

    public abstract Number apply(final Float left, final Float right);

    public abstract Number apply(Double left, final Double right);

    public abstract Number apply(final BigDecimal left, final BigDecimal right);

    public Object apply(final Object left, final Object right) {
      if (left == null)
        return right;

      if (right == null)
        return left;

      if (left instanceof Number && right instanceof Number)
        return apply((Number) left, this, (Number) right);

      return null;
    }

    public Number apply(final Number a, final Operator operation, final Number b) {
      if (a == null || b == null)
        throw new IllegalArgumentException("Cannot increment a null value");

      if (a instanceof Integer || a instanceof Short) {
        if (b instanceof Integer || b instanceof Short) {
          return operation.apply(a.intValue(), b.intValue());
        } else if (b instanceof Long) {
          return operation.apply(a.longValue(), b.longValue());
        } else if (b instanceof Float)
          return operation.apply(a.floatValue(), b.floatValue());
        else if (b instanceof Double)
          return operation.apply(a.doubleValue(), b.doubleValue());
        else if (b instanceof BigDecimal)
          return operation.apply(new BigDecimal((Integer) a), (BigDecimal) b);
      } else if (a instanceof Long) {
        if (b instanceof Integer || b instanceof Long || b instanceof Short)
          return operation.apply(a.longValue(), b.longValue());
        else if (b instanceof Float)
          return operation.apply(a.floatValue(), b.floatValue());
        else if (b instanceof Double)
          return operation.apply(a.doubleValue(), b.doubleValue());
        else if (b instanceof BigDecimal)
          return operation.apply(new BigDecimal((Long) a), (BigDecimal) b);
      } else if (a instanceof Float) {
        if (b instanceof Short || b instanceof Integer || b instanceof Long || b instanceof Float)
          return operation.apply(a.floatValue(), b.floatValue());
        else if (b instanceof Double)
          return operation.apply(a.doubleValue(), b.doubleValue());
        else if (b instanceof BigDecimal)
          return operation.apply(BigDecimal.valueOf((Float) a), (BigDecimal) b);

      } else if (a instanceof Double) {
        if (b instanceof Short || b instanceof Integer || b instanceof Long || b instanceof Float || b instanceof Double)
          return operation.apply(a.doubleValue(), b.doubleValue());
        else if (b instanceof BigDecimal)
          return operation.apply(BigDecimal.valueOf((Double) a), (BigDecimal) b);

      } else if (a instanceof BigDecimal) {
        if (b instanceof Integer)
          return operation.apply((BigDecimal) a, new BigDecimal((Integer) b));
        else if (b instanceof Long)
          return operation.apply((BigDecimal) a, new BigDecimal((Long) b));
        else if (b instanceof Short)
          return operation.apply((BigDecimal) a, new BigDecimal((Short) b));
        else if (b instanceof Float)
          return operation.apply((BigDecimal) a, BigDecimal.valueOf((Float) b));
        else if (b instanceof Double)
          return operation.apply((BigDecimal) a, BigDecimal.valueOf((Double) b));
        else if (b instanceof BigDecimal)
          return operation.apply((BigDecimal) a, (BigDecimal) b);
      }

      throw new IllegalArgumentException("Cannot increment value '" + a + "' (" + a.getClass() + ") with '" + b + "' (" + b.getClass() + ")");

    }

    public int getPriority() {
      return priority;
    }
  }

  public MathExpression(final int id) {
    super(id);
  }

  @Override
  protected SimpleNode[] getCacheableElements() {
    return childExpressions.toArray(new MathExpression[childExpressions.size()]);
  }

  public Object execute(final Identifiable iCurrentRecord, final CommandContext context) {
    if (childExpressions.isEmpty()) {
      return null;
    }
    if (childExpressions.size() == 1) {
      return childExpressions.get(0).execute(iCurrentRecord, context);
    }

    if (childExpressions.size() == 2) {
      final Object leftValue = childExpressions.get(0).execute(iCurrentRecord, context);
      final Object rightValue = childExpressions.get(1).execute(iCurrentRecord, context);
      return operators.get(0).apply(leftValue, rightValue);
    }

    return calculateWithOpPriority(iCurrentRecord, context);
  }

  public Object execute(final Result iCurrentRecord, final CommandContext context) {
    if (childExpressions.isEmpty())
      return null;

    if (childExpressions.size() == 1)
      return childExpressions.get(0).execute(iCurrentRecord, context);

    if (childExpressions.size() == 2) {
      final Object leftValue = childExpressions.get(0).execute(iCurrentRecord, context);
      final Object rightValue = childExpressions.get(1).execute(iCurrentRecord, context);
      return operators.get(0).apply(leftValue, rightValue);
    }

    return calculateWithOpPriority(iCurrentRecord, context);
  }

  private Object calculateWithOpPriority(final Result iCurrentRecord, final CommandContext context) {
    final Deque valuesStack = new ArrayDeque<>();
    final Deque<Operator> operatorsStack = new ArrayDeque<Operator>();

    final MathExpression nextExpression = childExpressions.get(0);
    final Object val = nextExpression.execute(iCurrentRecord, context);
    valuesStack.push(val == null ? NULL_VALUE : val);

    for (int i = 0; i < operators.size() && i + 1 < childExpressions.size(); i++) {
      final Operator nextOperator = operators.get(i);
      final Object rightValue = childExpressions.get(i + 1).execute(iCurrentRecord, context);

      if (!operatorsStack.isEmpty() && operatorsStack.peek().getPriority() <= nextOperator.getPriority()) {
        Object right = valuesStack.poll();
        right = right == NULL_VALUE ? null : right;
        Object left = valuesStack.poll();
        left = left == NULL_VALUE ? null : left;
        final Object calculatedValue = operatorsStack.poll().apply(left, right);
        valuesStack.push(calculatedValue == null ? NULL_VALUE : calculatedValue);
      }
      operatorsStack.push(nextOperator);

      valuesStack.push(rightValue == null ? NULL_VALUE : rightValue);
    }

    return iterateOnPriorities(valuesStack, operatorsStack);
  }

  private Object calculateWithOpPriority(final Identifiable iCurrentRecord, final CommandContext context) {
    final Deque valuesStack = new ArrayDeque<>();
    final Deque<Operator> operatorsStack = new ArrayDeque<Operator>();

    final MathExpression nextExpression = childExpressions.get(0);
    final Object val = nextExpression.execute(iCurrentRecord, context);
    valuesStack.push(val == null ? NULL_VALUE : val);

    for (int i = 0; i < operators.size() && i + 1 < childExpressions.size(); i++) {
      final Operator nextOperator = operators.get(i);
      final Object rightValue = childExpressions.get(i + 1).execute(iCurrentRecord, context);

      if (!operatorsStack.isEmpty() && operatorsStack.peek().getPriority() <= nextOperator.getPriority()) {
        Object right = valuesStack.poll();
        right = right == NULL_VALUE ? null : right;
        Object left = valuesStack.poll();
        left = left == NULL_VALUE ? null : left;
        final Object calculatedValue = operatorsStack.poll().apply(left, right);
        valuesStack.push(calculatedValue == null ? NULL_VALUE : calculatedValue);
      }
      operatorsStack.push(nextOperator);

      valuesStack.push(rightValue == null ? NULL_VALUE : rightValue);
    }

    return iterateOnPriorities(valuesStack, operatorsStack);
  }

  private Object iterateOnPriorities(Deque values, Deque<Operator> operators) {
    while (true) {
      if (values.isEmpty()) {
        return null;
      }
      if (values.size() == 1) {
        return values.getFirst();
      }

      final Deque valuesStack = new ArrayDeque<>();
      final Deque<Operator> operatorsStack = new ArrayDeque<Operator>();

      valuesStack.push(values.removeLast());

      while (!operators.isEmpty()) {
        final Operator nextOperator = operators.removeLast();
        final Object rightValue = values.removeLast();

        if (!operatorsStack.isEmpty() && operatorsStack.peek().getPriority() <= nextOperator.getPriority()) {
          Object right = valuesStack.poll();
          right = right == NULL_VALUE ? null : right;
          Object left = valuesStack.poll();
          left = left == NULL_VALUE ? null : left;
          final Object calculatedValue = operatorsStack.poll().apply(left, right);
          valuesStack.push(calculatedValue == null ? NULL_VALUE : calculatedValue);
        }
        operatorsStack.push(nextOperator);
        valuesStack.push(rightValue == null ? NULL_VALUE : rightValue);
      }
      if (!operatorsStack.isEmpty()) {
        Object right = valuesStack.poll();
        right = right == NULL_VALUE ? null : right;
        Object left = valuesStack.poll();
        left = left == NULL_VALUE ? null : left;
        final Object val = operatorsStack.poll().apply(left, right);
        valuesStack.push(val == null ? NULL_VALUE : val);
      }

      values = valuesStack;
      operators = operatorsStack;
    }
  }

  public List<MathExpression> getChildExpressions() {
    return childExpressions;
  }

  public void setChildExpressions(final List<MathExpression> childExpressions) {
    this.childExpressions = childExpressions;
  }

  public List<Operator> getOperators() {
    return operators;
  }

  public void toString(final Map<String, Object> params, final StringBuilder builder) {
    for (int i = 0; i < childExpressions.size(); i++) {
      if (i > 0) {
        builder.append(" ");
        switch (operators.get(i - 1)) {
        case PLUS:
          builder.append("+");
          break;
        case MINUS:
          builder.append("-");
          break;
        case STAR:
          builder.append("*");
          break;
        case SLASH:
          builder.append("/");
          break;
        case REM:
          builder.append("%");
          break;
        case LSHIFT:
          builder.append("<<");
          break;
        case RSHIFT:
          builder.append(">>");
          break;
        case RUNSIGNEDSHIFT:
          builder.append(">>>");
          break;
        case BIT_AND:
          builder.append("&");
          break;
        case BIT_OR:
          builder.append("|");
          break;
        case XOR:
          builder.append("^");
          break;
        }
        builder.append(" ");
      }
      childExpressions.get(i).toString(params, builder);
    }
  }

  public boolean isIndexedFunctionCall(CommandContext context) {
    if (this.childExpressions.size() != 1) {
      return false;
    }
    return this.childExpressions.get(0).isIndexedFunctionCall(context);
  }

  public long estimateIndexedFunction(final FromClause target, final CommandContext context, final BinaryCompareOperator operator, final Object right) {
    if (this.childExpressions.size() != 1) {
      return -1;
    }
    return this.childExpressions.get(0).estimateIndexedFunction(target, context, operator, right);
  }

  public Iterable<Record> executeIndexedFunction(final FromClause target, final CommandContext context, final BinaryCompareOperator operator,
      final Object right) {
    if (this.childExpressions.size() != 1) {
      return null;
    }
    return this.childExpressions.get(0).executeIndexedFunction(target, context, operator, right);
  }

  /**
   * tests if current expression is an indexed function AND that function can also be executed without using the index
   *
   * @param target  the query target
   * @param context the execution context
   *
   * @return true if current expression is an indexed function AND that function can also be executed without using the index, false
   * otherwise
   */
  public boolean canExecuteIndexedFunctionWithoutIndex(final FromClause target, final CommandContext context, final BinaryCompareOperator operator,
      final Object right) {
    if (this.childExpressions.size() != 1) {
      return false;
    }
    return this.childExpressions.get(0).canExecuteIndexedFunctionWithoutIndex(target, context, operator, right);
  }

  /**
   * tests if current expression is an indexed function AND that function can be used on this target
   *
   * @param target  the query target
   * @param context the execution context
   *
   * @return true if current expression is an indexed function AND that function can be used on this target, false otherwise
   */
  public boolean allowsIndexedFunctionExecutionOnTarget(final FromClause target, final CommandContext context, final BinaryCompareOperator operator,
      final Object right) {
    if (this.childExpressions.size() != 1) {
      return false;
    }
    return this.childExpressions.get(0).allowsIndexedFunctionExecutionOnTarget(target, context, operator, right);
  }

  /**
   * tests if current expression is an indexed function AND the function has also to be executed after the index search. In some
   * cases, the index search is accurate, so this condition can be excluded from further evaluation. In other cases the result from
   * the index is a superset of the expected result, so the function has to be executed anyway for further filtering
   *
   * @param target  the query target
   * @param context the execution context
   *
   * @return true if current expression is an indexed function AND the function has also to be executed after the index search.
   */
  public boolean executeIndexedFunctionAfterIndexSearch(final FromClause target, final CommandContext context, final BinaryCompareOperator operator,
      final Object right) {
    if (this.childExpressions.size() != 1) {
      return false;
    }
    return this.childExpressions.get(0).executeIndexedFunctionAfterIndexSearch(target, context, operator, right);
  }

  public boolean isBaseIdentifier() {
    if (this.childExpressions != null && childExpressions.size() == 1)
      return childExpressions.get(0).isBaseIdentifier();

    return false;
  }

  public boolean isExpand() {
    for (final MathExpression expr : this.childExpressions) {
      if (expr.isExpand()) {
        if (this.childExpressions.size() > 1) {
          throw new CommandExecutionException("Cannot calculate expand() with other expressions");
        }
        return true;
      }
    }
    return false;
  }

  public boolean isEarlyCalculated(final CommandContext context) {
    for (final MathExpression exp : childExpressions) {
      if (!exp.isEarlyCalculated(context)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAggregate(CommandContext context) {
    for (final MathExpression expr : this.childExpressions) {
      if (expr.isAggregate(context)) {
        return true;
      }
    }
    return false;
  }

  public boolean isCount() {
    if (this.childExpressions.size() != 1) {
      return false;
    }
    return this.childExpressions.get(0).isCount();
  }

  public SimpleNode splitForAggregation(final AggregateProjectionSplit aggregateProj, final CommandContext context) {
    if (isAggregate(context)) {
      final MathExpression result = new MathExpression(-1);
      int i = 0;
      for (final MathExpression expr : this.childExpressions) {
        if (i > 0)
          result.operators.add(operators.get(i - 1));

        final SimpleNode splitResult = expr.splitForAggregation(aggregateProj, context);
        if (splitResult instanceof MathExpression) {
          final MathExpression res = (MathExpression) splitResult;
          if (res.isEarlyCalculated(context) || res.isAggregate(context)) {
            result.childExpressions.add(res);
          } else {
            throw new CommandExecutionException("Cannot mix aggregate and single record attribute values in the same projection");
          }
        } else if (splitResult instanceof Expression) {
          result.childExpressions.add(((Expression) splitResult).mathExpression);//this comes from a splitted aggregate function
        }
        i++;
      }
      return result;
    } else {
      return this;
    }
  }

  public AggregationContext getAggregationContext(final CommandContext context) {
    throw new UnsupportedOperationException("multiple math expressions do not allow plain aggregation");
  }

  public MathExpression copy() {
    MathExpression result = null;
    try {
      result = getClass().getConstructor(Integer.TYPE).newInstance(-1);
    } catch (final Exception e) {
      throw new ArcadeDBException(e);
    }
    result.childExpressions = childExpressions.stream().map(x -> x.copy()).collect(Collectors.toList());
    result.operators.addAll(operators);
    return result;
  }

  public void extractSubQueries(final Identifier letAlias, final SubQueryCollector collector) {
    for (final MathExpression expr : this.childExpressions) {
      expr.extractSubQueries(letAlias, collector);
    }
  }

  public void extractSubQueries(final SubQueryCollector collector) {
    for (final MathExpression expr : this.childExpressions) {
      expr.extractSubQueries(collector);
    }
  }

  protected Object[] getIdentityElements() {
    return new Object[] { childExpressions, operators };
  }

  public List<String> getMatchPatternInvolvedAliases() {
    final List<String> result = new ArrayList<String>();
    for (final MathExpression exp : childExpressions) {
      final List<String> x = exp.getMatchPatternInvolvedAliases();
      if (x != null) {
        result.addAll(x);
      }
    }
    if (result.isEmpty()) {
      return null;
    }
    return result;
  }

  public void applyRemove(final ResultInternal result, final CommandContext context) {
    if (childExpressions.size() != 1) {
      throw new CommandExecutionException("cannot apply REMOVE " + this);
    }
    childExpressions.get(0).applyRemove(result, context);
  }
}
/* JavaCC - OriginalChecksum=c255bea24e12493e1005ba2a4d1dbb9d (do not edit this line) */
