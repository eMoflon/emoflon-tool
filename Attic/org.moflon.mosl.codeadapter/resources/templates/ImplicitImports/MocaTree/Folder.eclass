class Folder extends TreeElement{
	<> - file (0..*) -> File
	<> - subFolder (0..*) -> Folder
	- parentFolder (0..1) -> Folder
}