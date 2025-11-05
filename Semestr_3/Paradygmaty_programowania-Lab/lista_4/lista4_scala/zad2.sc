sealed trait FileSystem
case class Disk(letter: Char, contents: List[FileSystem]) extends FileSystem
case class Folder(name: String, contents: List[FileSystem]) extends FileSystem
case class File(name: String) extends FileSystem

val fs = Disk('C', List(
  Folder("Documents", List(
    File("note.txt")
  )),
  File("word.exe")
))
