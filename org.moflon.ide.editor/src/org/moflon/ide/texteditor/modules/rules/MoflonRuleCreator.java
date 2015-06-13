package org.moflon.ide.texteditor.modules.rules;

import java.util.Collection;

import org.eclipse.jface.text.rules.IRule;

abstract class MoflonRuleCreator {

	protected abstract Collection<IRule> getRules();
}
