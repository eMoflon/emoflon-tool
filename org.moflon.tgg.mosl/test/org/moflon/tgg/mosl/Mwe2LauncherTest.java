package org.moflon.tgg.mosl;
import org.eclipse.emf.mwe2.launch.runtime.Mwe2Launcher;

public class Mwe2LauncherTest {

	public static void main(String[] args) {
		new Mwe2Launcher().run(new String[] {
				"src/org/moflon/tgg/mosl/GenerateTGG.mwe2" });
	}
}
