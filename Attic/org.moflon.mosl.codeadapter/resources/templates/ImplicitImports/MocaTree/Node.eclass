class Node extends Text {
  startIndex : EInt
  startLineIndex : EInt
  stopIndex : EInt
  stopLineIndex : EInt
  
  <> - attribute (0..*) -> Attribute
  <> - children (0..*) -> Text
  - file (0..1) -> File
}