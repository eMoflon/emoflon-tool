Copyright (c) 2007 BEA Systems, Inc.
All rights reserved.  This program and the accompanying materials 
are made available under the terms of the Eclipse Public License v1.0
which accompanies this distribution, and is available at
http://www.eclipse.org/legal/epl-v10.html

This is a framework for developing Java 6 annotation processors that will
run both in Eclipse and in Sun javac.

To use this framework, start by editing Processor.java according to the 
"TODO" comments.

You should rename and repackage the processors you build on this framework.
When you do so, be aware that package and class names appear not only in the 
code but also in the META-INF/services/javax.annotation.processing.Processor 
file, in plugin.xml, and in META-INF/manifest.mf. 

The methods in the utils package may or may not be helpful to your needs.
You may wish to edit or add more methods reflecting your particular
use patterns.  These are just suggestions to help you get started.