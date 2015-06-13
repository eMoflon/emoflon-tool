/*
 * The FUJABA ToolSuite project:
 *
 *   FUJABA is the acronym for 'From Uml to Java And Back Again'
 *   and originally aims to provide an environment for round-trip
 *   engineering using UML as visual programming language. During
 *   the last years, the environment has become a base for several
 *   research activities, e.g. distributed software, database
 *   systems, modelling mechanical and electrical systems and
 *   their simulation. Thus, the environment has become a project,
 *   where this source code is part of. Further details are avail-
 *   able via http://www.fujaba.de
 *
 *      Copyright (C) Fujaba Development Group
 *
 *   This library is free software; you can redistribute it and/or
 *   modify it under the terms of the GNU Lesser General Public
 *   License as published by the Free Software Foundation; either
 *   version 2.1 of the License, or (at your option) any later version.
 *
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with this library; if not, write to the Free
 *   Software Foundation, Inc., 59 Temple Place, Suite 330, Boston,
 *   MA 02111-1307, USA or download the license under
 *   http://www.gnu.org/copyleft/lesser.html
 *
 * WARRANTY:
 *
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 *
 * Contact address:
 *
 *   Fujaba Management Board
 *   Software Engineering Group
 *   University of Paderborn
 *   Warburgerstr. 100
 *   D-33098 Paderborn
 *   Germany
 *
 *   URL  : http://www.fujaba.de
 *   email: info@fujaba.de
 *
 */
package de.fujaba.text.visitor;

import de.fujaba.text.expression.Addition;
import de.fujaba.text.expression.Arguments;
import de.fujaba.text.expression.ArrayAccess;
import de.fujaba.text.expression.ArrayAccessorList;
import de.fujaba.text.expression.Assignment;
import de.fujaba.text.expression.BooleanLiteral;
import de.fujaba.text.expression.Brackets;
import de.fujaba.text.expression.CharLiteral;
import de.fujaba.text.expression.ConditionalAnd;
import de.fujaba.text.expression.ConditionalOr;
import de.fujaba.text.expression.Declaration;
import de.fujaba.text.expression.Dereference;
import de.fujaba.text.expression.EmptyExpression;
import de.fujaba.text.expression.EqualityExpression;
import de.fujaba.text.expression.ExclusiveOr;
import de.fujaba.text.expression.Identifier;
import de.fujaba.text.expression.InclusiveAnd;
import de.fujaba.text.expression.InclusiveOr;
import de.fujaba.text.expression.IncrementExpression;
import de.fujaba.text.expression.Instanceof;
import de.fujaba.text.expression.Maybe;
import de.fujaba.text.expression.MethodCall;
import de.fujaba.text.expression.MultipleStatements;
import de.fujaba.text.expression.Multiplication;
import de.fujaba.text.expression.NewExpression;
import de.fujaba.text.expression.NullLiteral;
import de.fujaba.text.expression.NumberLiteral;
import de.fujaba.text.expression.Operator;
import de.fujaba.text.expression.OperatorOperandPair;
import de.fujaba.text.expression.RelationalExpression;
import de.fujaba.text.expression.Shift;
import de.fujaba.text.expression.StringLiteral;
import de.fujaba.text.expression.TernaryExpression;
import de.fujaba.text.expression.Throw;
import de.fujaba.text.expression.TypeCast;
import de.fujaba.text.expression.TypeDereference;
import de.fujaba.text.expression.TypeExpression;
import de.fujaba.text.expression.UnaryExpression;
import de.fujaba.text.expression.UnaryPrefix;
import de.fujaba.text.statement.Block;
import de.fujaba.text.statement.BreakStatement;
import de.fujaba.text.statement.CaseStatement;
import de.fujaba.text.statement.Catch;
import de.fujaba.text.statement.ContinueStatement;
import de.fujaba.text.statement.DefaultStatement;
import de.fujaba.text.statement.DoWhile;
import de.fujaba.text.statement.ExpressionStatement;
import de.fujaba.text.statement.For;
import de.fujaba.text.statement.IfElse;
import de.fujaba.text.statement.Label;
import de.fujaba.text.statement.ReturnStatement;
import de.fujaba.text.statement.Switch;
import de.fujaba.text.statement.TryCatch;
import de.fujaba.text.statement.While;

/**
 * 
 * 
 * @author patrick.oppermann@cs.uni-kassel.de
 */
public interface ArgVisitor<R, T>
{
   public R visit(Addition n, T argu);

   public R visit(Arguments n, T argu);

   public R visit(ArrayAccess n, T argu);

   public R visit(ArrayAccessorList n, T argu);

   public R visit(Assignment n, T argu);

   public R visit(Block n, T argu);

   public R visit(BooleanLiteral n, T argu);

   public R visit(Brackets n, T argu);

   public R visit(BreakStatement n, T argu);

   public R visit(CaseStatement n, T argu);

   public R visit(Catch n, T argu);

   public R visit(CharLiteral n, T argu);

   public R visit(ConditionalAnd n, T argu);

   public R visit(ConditionalOr n, T argu);

   public R visit(ContinueStatement n, T argu);

   public R visit(Declaration n, T argu);

   public R visit(DefaultStatement n, T argu);

   public R visit(Dereference n, T argu);

   public R visit(DoWhile n, T argu);

   public R visit(EmptyExpression n, T argu);

   public R visit(EqualityExpression n, T argu);

   public R visit(ExclusiveOr n, T argu);

   public R visit(ExpressionStatement n, T argu);

   public R visit(For n, T argu);

   public R visit(Identifier n, T argu);

   public R visit(IfElse n, T argu);

   public R visit(InclusiveAnd n, T argu);

   public R visit(InclusiveOr n, T argu);

   public R visit(IncrementExpression n, T argu);

   public R visit(Instanceof n, T argu);

   public R visit(Label n, T argu);

   public R visit(Maybe n, T argu);

   public R visit(MethodCall n, T argu);

   public R visit(MultipleStatements n, T argu);

   public R visit(Multiplication n, T argu);

   public R visit(NewExpression n, T argu);

   public R visit(NullLiteral n, T argu);

   public R visit(NumberLiteral n, T argu);

   public R visit(Operator n, T argu);

   public R visit(OperatorOperandPair n, T argu);

   public R visit(RelationalExpression n, T argu);

   public R visit(ReturnStatement n, T argu);

   public R visit(Shift n, T argu);

   public R visit(StringLiteral n, T argu);

   public R visit(Switch n, T argu);

   public R visit(TernaryExpression n, T argu);

   public R visit(Throw n, T argu);

   public R visit(TryCatch n, T argu);

   public R visit(TypeCast n, T argu);

   public R visit(TypeDereference n, T argu);

   public R visit(TypeExpression n, T argu);

   public R visit(UnaryExpression n, T argu);

   public R visit(UnaryPrefix n, T argu);

   public R visit(While n, T argu);
}
