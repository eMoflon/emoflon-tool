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
public interface NoArgVisitor<T>
{
   public T visit(Addition n);

   public T visit(Arguments n);

   public T visit(ArrayAccess n);

   public T visit(ArrayAccessorList n);

   public T visit(Assignment n);

   public T visit(Block n);

   public T visit(BooleanLiteral n);

   public T visit(Brackets n);

   public T visit(BreakStatement n);

   public T visit(CaseStatement n);

   public T visit(Catch n);

   public T visit(CharLiteral n);

   public T visit(ConditionalAnd n);

   public T visit(ConditionalOr n);

   public T visit(ContinueStatement n);

   public T visit(Declaration n);

   public T visit(DefaultStatement n);

   public T visit(Dereference n);

   public T visit(DoWhile n);

   public T visit(EmptyExpression n);

   public T visit(EqualityExpression n);

   public T visit(ExclusiveOr n);

   public T visit(ExpressionStatement n);

   public T visit(For n);

   public T visit(Identifier n);

   public T visit(IfElse n);

   public T visit(InclusiveAnd n);

   public T visit(InclusiveOr n);

   public T visit(IncrementExpression n);

   public T visit(Instanceof n);

   public T visit(Label n);

   public T visit(Maybe n);

   public T visit(MethodCall n);

   public T visit(MultipleStatements n);

   public T visit(Multiplication n);

   public T visit(NewExpression n);

   public T visit(NullLiteral n);

   public T visit(NumberLiteral n);

   public T visit(Operator n);

   public T visit(OperatorOperandPair n);

   public T visit(RelationalExpression n);

   public T visit(ReturnStatement n);

   public T visit(Shift n);

   public T visit(StringLiteral n);

   public T visit(Switch n);

   public T visit(TernaryExpression n);

   public T visit(Throw n);

   public T visit(TryCatch n);

   public T visit(TypeCast n);

   public T visit(TypeDereference n);

   public T visit(TypeExpression n);

   public T visit(UnaryExpression n);

   public T visit(UnaryPrefix n);

   public T visit(While n);
}
