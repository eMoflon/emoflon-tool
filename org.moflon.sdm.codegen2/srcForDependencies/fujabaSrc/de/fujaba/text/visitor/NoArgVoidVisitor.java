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
public interface NoArgVoidVisitor
{

   public void visit(Addition n);

   public void visit(Arguments n);

   public void visit(ArrayAccess n);

   public void visit(ArrayAccessorList n);

   public void visit(Assignment n);

   public void visit(Block n);

   public void visit(BooleanLiteral n);

   public void visit(Brackets n);

   public void visit(BreakStatement n);

   public void visit(CaseStatement n);

   public void visit(Catch n);

   public void visit(CharLiteral n);

   public void visit(ConditionalAnd n);

   public void visit(ConditionalOr n);

   public void visit(ContinueStatement n);

   public void visit(Declaration n);

   public void visit(DefaultStatement n);

   public void visit(Dereference n);

   public void visit(DoWhile n);

   public void visit(EmptyExpression n);

   public void visit(EqualityExpression n);

   public void visit(ExclusiveOr n);

   public void visit(ExpressionStatement n);

   public void visit(For n);

   public void visit(Identifier n);

   public void visit(IfElse n);

   public void visit(InclusiveAnd n);

   public void visit(InclusiveOr n);

   public void visit(IncrementExpression n);

   public void visit(Instanceof n);

   public void visit(Label n);

   public void visit(Maybe n);

   public void visit(MethodCall n);

   public void visit(MultipleStatements n);

   public void visit(Multiplication n);

   public void visit(NewExpression n);

   public void visit(NullLiteral n);

   public void visit(NumberLiteral n);

   public void visit(Operator n);

   public void visit(OperatorOperandPair n);

   public void visit(RelationalExpression n);

   public void visit(ReturnStatement n);

   public void visit(Shift n);

   public void visit(StringLiteral n);

   public void visit(Switch n);

   public void visit(TernaryExpression n);

   public void visit(Throw n);

   public void visit(TryCatch n);

   public void visit(TypeCast n);

   public void visit(TypeDereference n);

   public void visit(TypeExpression n);

   public void visit(UnaryExpression n);

   public void visit(UnaryPrefix n);

   public void visit(While n);

}
